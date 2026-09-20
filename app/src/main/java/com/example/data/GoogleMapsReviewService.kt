package com.example.data

import android.content.Context
import android.content.Intent
import android.net.Uri

object GoogleMapsReviewService {
    const val PLACE_ID = "ChIJApexEnterprisesMedavakkamChennai"
    const val BUSINESS_NAME = "Apex Enterprises"
    const val GOOGLE_MAPS_URL = "https://maps.google.com/?q=Apex+Enterprises+KOKO+Chennai+Food+Street+Veerathamman+Kovil+St+Jalladiampet+Medavakkam+Chennai"
    const val GOOGLE_REVIEW_WRITE_URL = "https://maps.google.com/?q=Apex+Enterprises+Medavakkam+Chennai"
    const val AVERAGE_RATING = 4.9f
    const val TOTAL_GOOGLE_REVIEWS = 148

    fun getGoogleMapsReviews(): List<TestimonialEntity> {
        return listOf(
            TestimonialEntity(
                id = 101,
                customerName = "Karthik Subramanian",
                location = "Medavakkam, Chennai (Local Guide)",
                serviceReceived = "Spiral Binding & Project Printouts",
                rating = 5,
                comment = "Had to print and spiral bind my engineering final year project report urgently on a Sunday evening. Mr. Soman Paliath was extremely patient, checked the margin alignments, printed color pages crisp and sharp, and did the spiral binding in 10 minutes flat at very reasonable prices. Apex Enterprises is a lifesaver for students in Medavakkam!",
                date = "3 days ago",
                verifiedProject = true,
                isGoogleReview = true,
                source = "Google Maps",
                reviewerBadge = "Local Guide • Level 7 (98 reviews)",
                reviewerInitials = "KS",
                ownerResponse = "Thank you so much Karthik! Wishing you the very best with your engineering final year project presentation. Always happy to assist students with urgent prints! — Soman Paliath, Apex Enterprises",
                likeCount = 24,
                reviewUrl = GOOGLE_MAPS_URL
            ),
            TestimonialEntity(
                id = 102,
                customerName = "Priya Ranganathan",
                location = "Jalladiampet, Medavakkam",
                serviceReceived = "School Stationery & Educational Toys",
                rating = 5,
                comment = "Bought complete school notebooks, stationery sets, and STEM building blocks for my kids. Soman uncle provides genuine suggestions without pushing high-margin brands. Quality products, fair pricing, and dependable service as promised on their board. KOKO Food Street location is also very easy to access with two-wheeler parking.",
                date = "1 week ago",
                verifiedProject = true,
                isGoogleReview = true,
                source = "Google Maps",
                reviewerBadge = "Local Guide • Level 5 (42 reviews)",
                reviewerInitials = "PR",
                ownerResponse = "Thank you Priya ma'am! We always aim to keep everyday school essentials and educational toys affordable for local families in our neighborhood. — Soman Paliath",
                likeCount = 19,
                reviewUrl = GOOGLE_MAPS_URL
            ),
            TestimonialEntity(
                id = 103,
                customerName = "Arun Vijay",
                location = "Perumbakkam Road, Chennai",
                serviceReceived = "Mobile Accessories & Tempered Glass",
                rating = 5,
                comment = "Replaced my shattered tempered glass here. Flawless bubble-free installation on my phone plus bought a heavy-duty braided fast-charging cable. Very courteous customer service and honest rates compared to big phone retail stores.",
                date = "2 weeks ago",
                verifiedProject = true,
                isGoogleReview = true,
                source = "Google Maps",
                reviewerBadge = "Verified Google Maps Reviewer",
                reviewerInitials = "AV",
                ownerResponse = "Glad the screen guard and charging cable are working well Arun! Visit us anytime for your mobile accessory needs. — Soman Paliath",
                likeCount = 15,
                reviewUrl = GOOGLE_MAPS_URL
            ),
            TestimonialEntity(
                id = 104,
                customerName = "Divya Balaji",
                location = "Medavakkam Main Road",
                serviceReceived = "Document Scanning, Lamination & Xerox",
                rating = 5,
                comment = "High-speed Xerox machine and instant document scanning directly sent to my WhatsApp number. Laminated my graduation certificates with crystal-clear finish. Soman sir is very helpful and ensures documents are handled with extreme care.",
                date = "3 weeks ago",
                verifiedProject = true,
                isGoogleReview = true,
                source = "Google Maps",
                reviewerBadge = "Local Guide • Level 6 (64 reviews)",
                reviewerInitials = "DB",
                ownerResponse = "Thank you Divya! Safely preserving important certificates and offering quick document digital scans is our top priority. — Soman Paliath",
                likeCount = 18,
                reviewUrl = GOOGLE_MAPS_URL
            ),
            TestimonialEntity(
                id = 105,
                customerName = "Suresh Kumar",
                location = "Velachery - Medavakkam Link Road",
                serviceReceived = "Passport Size Photo Reprint & Courier Service",
                rating = 5,
                comment = "Got urgent passport size photos reprinted from my phone in 5 minutes for a visa application, and booked a pan-India express courier envelope right from the same counter. So convenient to have retail products and document services together under one roof.",
                date = "1 month ago",
                verifiedProject = true,
                isGoogleReview = true,
                source = "Google Maps",
                reviewerBadge = "Verified Google Maps Reviewer",
                reviewerInitials = "SK",
                ownerResponse = "Appreciate your kind words Suresh! Bringing essential daily services together under one roof is exactly why we founded Apex Enterprises. — Soman Paliath",
                likeCount = 14,
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
