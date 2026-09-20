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
        You are the helpful, friendly Store AI Assistant at Apex Enterprises, representing proprietor Soman Paliath in Jalladiampet, Medavakkam, Chennai.
        You assist customers with retail products and essential document services:
        1. Products:
           - Office & School Stationery: Notebooks (Classmate/Camlin), pens, exam pads, geometry boxes, JK Copier A4 paper reams, sticky notes, files & folders.
           - Mobile Accessories: Fast-charging braided Type-C & Lightning cables, 9H/11D tempered glass screen guards (free installation in store), shockproof phone cases, chargers.
           - Toys: Safe creative STEM building blocks, educational toys, puzzles for kids of all ages.
           - Gift Items: Executive diary and metal pen gift sets, personalized gift pairings for teachers and birthdays.
           - Courier: Reliable Pan-India domestic express courier booking and parcel dispatch with tracking.
        2. Essential Services:
           - Xerox: High-speed black & white (₹2/pg) and vibrant color photocopying.
           - Printouts: Computer printouts (A4/A3) from WhatsApp, Email, or USB drive (₹5/pg).
           - Document Scanning: High-resolution digital scanning of certificates and records sent directly to WhatsApp/Email (₹10/doc).
           - Binding: Spiral binding and soft project report binding with transparent covers (₹30/book).
           - Lamination: Hot thermal lamination from ID card to A3 size (₹15/card).
           - Passport Size Photos: Instant photo reprints & fresh copies (₹50 for 8 photos).
        Key Business Facts:
        - Proprietor: Soman Paliath
        - Philosophy: Quality Products, Fair Pricing, Dependable Service. "Trust. Value. Convenience. Service."
        - Address: Shop no: B02, KOKO Chennai Food Street, 181/3, Veerathamman Kovil St, Jalladiampet, Medavakkam, Chennai, Tamil Nadu 600100
        - Mobile / WhatsApp: 87548 25880
        - Email: apexentp2025@gmail.com
        - Hours: Mon–Sat: 8:30 AM – 9:30 PM • Sun: 9:00 AM – 8:00 PM
        Tone: Courteous, humble, practical, neighborhood-first, and helpful. Keep responses concise, clear, and focused on customer convenience.
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
            q.contains("xerox") || q.contains("print") || q.contains("copy") || q.contains("document") || q.contains("a4") || q.contains("scan") || q.contains("pdf") -> {
                "At Apex Enterprises, we provide prompt, high-quality document services! Xerox starts at ₹2/page for B&W, and computer printouts start at ₹5/page. You can send your files directly to our WhatsApp (87548 25880) or Email (apexentp2025@gmail.com) for instant printing while you wait. We also offer high-res document scanning to PDF (₹10/doc)."
            }
            q.contains("bind") || q.contains("lamination") || q.contains("project") || q.contains("spiral") || q.contains("photo") || q.contains("passport") -> {
                "We provide fast finishing services for students and professionals: Spiral and soft project binding (₹30/book) with durable coils and transparent front sheets, hot thermal lamination for ID cards to A3 documents (₹15/card), and instant passport-size photo reprints (₹50 for 8 copies)."
            }
            q.contains("stationery") || q.contains("pen") || q.contains("notebook") || q.contains("pencil") || q.contains("book") || q.contains("paper") || q.contains("school") || q.contains("office") -> {
                "We stock a comprehensive range of everyday school and office stationery: Classmate and Camlin notebooks, long books, exam writing pads, geometry instruments, ball & gel pens, highlighters, sticky notes, files, and JK Copier 75 GSM A4 paper reams (₹340). Bulk school orders also welcome!"
            }
            q.contains("mobile") || q.contains("cable") || q.contains("cover") || q.contains("glass") || q.contains("case") || q.contains("charger") || q.contains("tempered") -> {
                "Our mobile accessories counter carries durable fast-charging braided Type-C & Lightning cables (from ₹199), shockproof phone covers, and 9H/11D tempered glass screen guards (₹149) with free dust-free installation right at our store counter."
            }
            q.contains("toy") || q.contains("gift") || q.contains("game") || q.contains("kid") || q.contains("block") -> {
                "We offer safe, engaging STEM building block sets and creative educational toys for kids of all ages (from ₹299), as well as sophisticated executive diary and metal rollerball pen gift sets (₹399) complete with presentation boxes."
            }
            q.contains("courier") || q.contains("parcel") || q.contains("speed") || q.contains("post") || q.contains("dispatch") || q.contains("track") -> {
                "Apex Enterprises is an authorized booking point for domestic and express pan-India courier service. We pack your documents and parcels securely in tamper-evident packaging and provide an instant SMS tracking number. Booking starts from ₹60."
            }
            q.contains("owner") || q.contains("soman") || q.contains("proprietor") || q.contains("founder") || q.contains("who") -> {
                "Apex Enterprises is founded and managed by Soman Paliath, an entrepreneur with a practical, customer-first approach. Soman's philosophy is built on three simple principles: quality products, fair pricing, and dependable service. Everything you need is brought together under one roof."
            }
            q.contains("hour") || q.contains("location") || q.contains("address") || q.contains("where") || q.contains("contact") || q.contains("phone") -> {
                "Apex Enterprises is located at Shop no: B02, KOKO Chennai Food Street, 181/3, Veerathamman Kovil St, Jalladiampet, Medavakkam, Chennai, Tamil Nadu 600100. We are open Monday to Saturday from 8:30 AM to 9:30 PM, and Sundays from 9:00 AM to 8:00 PM. Call or WhatsApp us at 87548 25880."
            }
            else -> {
                "Welcome to Apex Enterprises! Managed by Soman Paliath in Medavakkam, Chennai, we bring quality retail products (stationery, mobile accessories, toys, gifts) and essential document services (Xerox, printouts, scanning, spiral binding, lamination, passport photos, and courier) under one convenient roof. How can I assist you today?"
            }
        }
    }
}
