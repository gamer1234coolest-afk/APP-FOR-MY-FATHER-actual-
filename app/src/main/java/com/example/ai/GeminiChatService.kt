package com.example.ai

import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

data class ChatMessage(
    val id: String = "msg_${System.currentTimeMillis()}_${(100..999).random()}",
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

class GeminiChatService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val systemPrompt = """
        You are the friendly Store AI Specialist at Beacon Tech, Toys & Stationery, representing founder Alex Mercer, STEM Lead Sam Rivera, and Stationery Specialist Maya Lin.
        You are deeply knowledgeable in our three core departments:
        1. Tech & Gadgets: Mechanical keyboards (Gateron switches, keycaps, ergonomics), noise-cancelling Bluetooth headphones, and device setup/screen protection services.
        2. Educational Toys & STEM: Programmable robotics kits (Scratch & Python), tactile 3D wooden brainteasers, tabletop board games, and hands-on learning for all ages.
        3. Fine Stationery: Imported Japanese gel pens, solid brass fountain pens, 160gsm bleedproof bamboo notebooks, in-house laser engraving, and artisanal gift wrapping.
        Speak with the warmth, enthusiasm, patience, and helpfulness of a passionate neighborhood shopkeeper.
        Key shop facts:
        - Founded in 2012 by Alex Mercer
        - Address: 142 Main Street, Arts & Tech District
        - Phone: (555) 482-9301
        - Hours: Mon–Sat: 9:00 AM – 7:00 PM • Sun: 10:00 AM – 4:00 PM
        - Services: Device Setup & Screen Protection ($15+), Laser Engraving ($12+), Complimentary Gift Wrapping ($35+ orders), Bulk Classroom Supplies.
        - Hands-on discovery: Customers are welcome to test switch acoustics, try pen nibs, and demo STEM bots at our store counters.
        Keep responses helpful, informative, friendly, and under 3-4 paragraphs.
    """.trimIndent()

    suspend fun sendMessage(
        history: List<ChatMessage>,
        userMessage: String
    ): String = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Exception) {
            ""
        }

        // If no valid key provided or placeholder, use the store advisory engine
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY" || apiKey.contains("PLACEHOLDER")) {
            return@withContext getStoreFallbackResponse(userMessage)
        }

        try {
            val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"

            val contentsArray = JSONArray()

            // Include relevant previous conversation turns (last 6 max)
            val recentHistory = history.takeLast(6)
            for (msg in recentHistory) {
                val turn = JSONObject()
                turn.put("role", if (msg.isUser) "user" else "model")
                val parts = JSONArray()
                val partObj = JSONObject()
                partObj.put("text", msg.text)
                parts.put(partObj)
                turn.put("parts", parts)
                contentsArray.put(turn)
            }

            // Current user message
            val currentTurn = JSONObject()
            currentTurn.put("role", "user")
            val currentParts = JSONArray()
            val currentPart = JSONObject()
            currentPart.put("text", userMessage)
            currentParts.put(currentPart)
            currentTurn.put("parts", currentParts)
            contentsArray.put(currentTurn)

            val requestJson = JSONObject()
            requestJson.put("contents", contentsArray)

            // System instruction
            val sysInstruction = JSONObject()
            val sysParts = JSONArray()
            val sysPart = JSONObject()
            sysPart.put("text", systemPrompt)
            sysParts.put(sysPart)
            sysInstruction.put("parts", sysParts)
            requestJson.put("systemInstruction", sysInstruction)

            // Generation config
            val genConfig = JSONObject()
            genConfig.put("temperature", 0.7)
            genConfig.put("topP", 0.95)
            requestJson.put("generationConfig", genConfig)

            val body = requestJson.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
            val request = Request.Builder()
                .url(url)
                .post(body)
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()

            if (!response.isSuccessful || responseBody == null) {
                Log.w("GeminiChatService", "Gemini HTTP ${response.code}: $responseBody")
                return@withContext getStoreFallbackResponse(userMessage)
            }

            val rootJson = JSONObject(responseBody)
            val candidates = rootJson.optJSONArray("candidates")
            if (candidates != null && candidates.length() > 0) {
                val firstCandidate = candidates.getJSONObject(0)
                val content = firstCandidate.optJSONObject("content")
                val parts = content?.optJSONArray("parts")
                if (parts != null && parts.length() > 0) {
                    val text = parts.getJSONObject(0).optString("text", "")
                    if (text.isNotBlank()) {
                        return@withContext text
                    }
                }
            }

            getStoreFallbackResponse(userMessage)
        } catch (e: Exception) {
            Log.e("GeminiChatService", "Failed to call Gemini API", e)
            getStoreFallbackResponse(userMessage)
        }
    }

    private fun getStoreFallbackResponse(query: String): String {
        val q = query.lowercase()
        return when {
            q.contains("keyboard") || q.contains("switch") || q.contains("headphone") || q.contains("bluetooth") || q.contains("tech") || q.contains("audio") -> {
                "In our Tech department, we specialize in high-quality daily driver peripherals! Our Keyflow 75% Mechanical Keyboard ($89) features hot-swappable pre-lubed Gateron Yellow switches with silicone sound dampening, giving a delightfully smooth, creamy typing sound. For audio, our Aura Pro Wireless ANC Headphones ($149) feature 40-hour battery life and memory foam cups for long focus sessions. You can test both at our demo table in the store anytime!"
            }
            q.contains("toy") || q.contains("robot") || q.contains("stem") || q.contains("puzzle") || q.contains("kid") || q.contains("game") -> {
                "Our Toys & STEM section is all about screen-positive, creative discovery! For young engineers (ages 8-14), the RoboMaster STEM Builder Kit ($79) includes 420 modular snap pieces, obstacle avoidance sensors, and supports drag-and-drop Scratch visual coding. For tactile puzzle lovers, our handcrafted beechwood MindBender 3D Puzzle Sphere ($34) is an all-time neighborhood favorite. We also host free board game mornings every Saturday!"
            }
            q.contains("pen") || q.contains("stationery") || q.contains("journal") || q.contains("notebook") || q.contains("paper") || q.contains("ink") || q.contains("engrav") -> {
                "Maya curates our fine stationery collection with obsessive care! Our Heritage Solid Brass Fountain Pen ($45) comes with a precision German iridium nib and 30ml archival ink bottle—and we offer complimentary in-house laser name engraving. Pair it with our Loomis Linen Hardcover Journal ($24), featuring ultra-thick 160gsm bamboo paper that handles fountain pen ink and wet watercolor without bleeding or ghosting."
            }
            q.contains("gift") || q.contains("wrap") || q.contains("bundle") || q.contains("present") -> {
                "We love curating custom gift bundles! When you order or purchase $35+ in our shop, we provide complimentary artisanal gift wrapping with recycled kraft paper, woven cotton cord, and a hand-lettered calligraphy card. Tell us the recipient's age or hobbies, and we'll help you craft the perfect tech, toy, or stationery pairing."
            }
            q.contains("price") || q.contains("cost") || q.contains("setup") || q.contains("service") -> {
                "Our popular in-store services and products are transparently priced: Tempered Glass Screen Application & Device Setup starts at $15; Laser Engraving & Personalization starts at $12; Mechanical Keyboards are $89; STEM Robotics Kits are $79; and Hardcover Dot-Grid Journals are $24. We also offer 15-25% volume discounts for local teachers and school clubs."
            }
            q.contains("hour") || q.contains("location") || q.contains("address") || q.contains("where") || q.contains("visit") -> {
                "You can visit Beacon Tech, Toys & Stationery at 142 Main Street in the Arts & Tech District. We're open Monday through Saturday from 9:00 AM to 7:00 PM, and Sundays from 10:00 AM to 4:00 PM. We'd love to show you around and let you test our demo items!"
            }
            else -> {
                "Welcome to Beacon Tech, Toys & Stationery! We are your neighborhood destination for curated mechanical keyboards, audio tech, programmable STEM toys, 3D puzzles, and fine paper goods. Whether you're looking for personalized gift ideas, switch recommendations, fountain pen nib advice, or device setup, I'm here to help. What can I help you explore today?"
            }
        }
    }
}
