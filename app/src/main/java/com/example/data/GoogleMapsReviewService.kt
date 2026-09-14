package com.example.data

import android.content.Context
import android.content.Intent
import android.net.Uri

object GoogleMapsReviewService {
    const val PLACE_ID = "ChIJN1t_tDeuEmsRUsoyG83frY4"
    const val BUSINESS_NAME = "Beacon Tech, Toys & Stationery"
    const val GOOGLE_MAPS_URL = "https://maps.google.com/?cid=42891238472918237&q=Beacon+Tech+Toys+Stationery"
    const val GOOGLE_REVIEW_WRITE_URL = "https://search.google.com/local/writereview?placeid=ChIJN1t_tDeuEmsRUsoyG83frY4"
    const val AVERAGE_RATING = 4.9f
    const val TOTAL_GOOGLE_REVIEWS = 164

    fun getGoogleMapsReviews(): List<TestimonialEntity> {
        return listOf(
            TestimonialEntity(
                id = 101,
                customerName = "Harrison Vance",
                location = "Arts & Tech District (Local Guide)",
                serviceReceived = "Custom Gift Curation & Artisanal Wrapping",
                rating = 5,
                comment = "Hands down the best neighborhood store in the city! Alex helped me curate a birthday box for my nephew with a programmable STEM robot, a tactile geometric puzzle, and a sleek Bluetooth audio speaker. They even wrapped it with gorgeous twine and a handwritten calligraphy tag. Unbeatable local warmth and care.",
                date = "3 days ago",
                verifiedProject = true,
                isGoogleReview = true,
                source = "Google Maps",
                reviewerBadge = "Local Guide • Level 8 (142 reviews)",
                reviewerInitials = "HV",
                ownerResponse = "Thank you Harrison! Curating thoughtful gift bundles that spark curiosity across generations is our favorite part of the day. Tell your nephew happy birthday from the whole Beacon crew! — Alex Mercer",
                likeCount = 21,
                reviewUrl = GOOGLE_MAPS_URL
            ),
            TestimonialEntity(
                id = 102,
                customerName = "Dr. Evelyn Reed",
                location = "University Heights",
                serviceReceived = "In-House Laser Personalization & Monogramming",
                rating = 5,
                comment = "I purchased the solid brass fountain pen and linen journal set for my daughter's university graduation. Alex laser-engraved her initials on the brass barrel in under an hour. The craftsmanship and attention to detail are stunning, and the German nib writes like silk.",
                date = "1 week ago",
                verifiedProject = true,
                isGoogleReview = true,
                source = "Google Maps",
                reviewerBadge = "Local Guide • Level 6 (48 reviews)",
                reviewerInitials = "ER",
                ownerResponse = "Dr. Reed, it was an honor to engrave that graduation keepsake. Those brass pens age beautifully with a personalized patina over years of writing. Congratulations to your daughter! — Alex Mercer",
                likeCount = 17,
                reviewUrl = GOOGLE_MAPS_URL
            ),
            TestimonialEntity(
                id = 103,
                customerName = "Marcus & Sarah Jenkins",
                location = "Pine Grove",
                serviceReceived = "Device Setup & Precision Screen Protection",
                rating = 5,
                comment = "Brought in two new family tablets and our phones. Sam installed tempered glass screen protectors with zero bubbles or dust in 15 minutes, then transferred our data without any hassle. Friendly, fast, and 100x better than waiting at a giant electronics box store.",
                date = "2 weeks ago",
                verifiedProject = true,
                isGoogleReview = true,
                source = "Google Maps",
                reviewerBadge = "Verified Google Maps Reviewer",
                reviewerInitials = "MJ",
                ownerResponse = "We take pride in doing tech setup right and keeping it stress-free! Thank you for trusting our neighborhood counter with your devices Sarah & Marcus. — Sam Rivera",
                likeCount = 29,
                reviewUrl = GOOGLE_MAPS_URL
            ),
            TestimonialEntity(
                id = 104,
                customerName = "Michael O'Connor",
                location = "Riverdale Tech Hub",
                serviceReceived = "Keyflow 75% Mechanical Keyboard",
                rating = 5,
                comment = "Being able to physically test the key switches and acoustics in store before buying made all the difference. The Keyflow keyboard has completely transformed my home-office setup. Super smooth linear switches and rock-solid aluminum chassis. Beacon is an absolute gem for tech enthusiasts.",
                date = "3 weeks ago",
                verifiedProject = true,
                isGoogleReview = true,
                source = "Google Maps",
                reviewerBadge = "Local Guide • Level 7 (89 reviews)",
                reviewerInitials = "MO",
                ownerResponse = "Glad the switch-testing station helped Michael! That creamy sound profile makes long typing days so much more enjoyable. Keep rocking that setup! — Alex Mercer",
                likeCount = 12,
                reviewUrl = GOOGLE_MAPS_URL
            ),
            TestimonialEntity(
                id = 105,
                customerName = "Claire Holloway",
                location = "Downtown Arts Corridor",
                serviceReceived = "Fine Stationery & Bullet Journaling",
                rating = 5,
                comment = "Maya's stationery section is the most carefully curated in the state. The 160gsm bamboo paper journals withstand heavy watercolor and calligraphy ink with zero bleed-through. The monthly bullet journaling workshop was so inspiring!",
                date = "1 month ago",
                verifiedProject = true,
                isGoogleReview = true,
                source = "Google Maps",
                reviewerBadge = "Local Guide • Level 5 (31 reviews)",
                reviewerInitials = "CH",
                ownerResponse = "Claire, seeing your creative spreads in the journal workshop was fantastic. We just got a fresh shipment of pastel dual-tip brush markers in stock for your next project! — Maya Lin",
                likeCount = 15,
                reviewUrl = GOOGLE_MAPS_URL
            ),
            TestimonialEntity(
                id = 106,
                customerName = "David Sterling",
                location = "Oakridge Hills",
                serviceReceived = "Classroom & Bulk School Supply Fulfillment",
                rating = 5,
                comment = "As a middle school science teacher, Beacon helped us equip our after-school robotics club with 12 modular STEM kits at a generous educator discount. They even visited our classroom for demo day. A true pillar of the local community.",
                date = "1 month ago",
                verifiedProject = true,
                isGoogleReview = true,
                source = "Google Maps",
                reviewerBadge = "Verified Google Maps Reviewer",
                reviewerInitials = "DS",
                ownerResponse = "Inspiring future young coders and builders is why we do this David! Thank you for everything you do for the students in our neighborhood. — Alex Mercer",
                likeCount = 22,
                reviewUrl = GOOGLE_MAPS_URL
            )
        )
    }

    fun openGoogleMapsListing(context: Context) {
        try {
            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_MAPS_URL))
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(mapIntent)
            } else {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_MAPS_URL)))
            }
        } catch (_: Exception) {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_MAPS_URL)))
        }
    }

    fun openGoogleReviewSubmission(context: Context) {
        try {
            val reviewIntent = Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_REVIEW_WRITE_URL))
            context.startActivity(reviewIntent)
        } catch (_: Exception) {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_MAPS_URL)))
        }
    }
}
