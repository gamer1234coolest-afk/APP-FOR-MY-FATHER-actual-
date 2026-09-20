package com.example.model

import androidx.annotation.DrawableRes
import com.example.R

data class GalleryItem(
    val id: String,
    val title: String,
    val category: String,
    @DrawableRes val imageRes: Int,
    val woodType: String, // Repurposed for Tech/Item Key Specs (e.g. Connectivity, Battery, Material)
    val finish: String,   // Repurposed for Colorway & Surface Finish
    val dimensions: String,
    val year: String,
    val description: String,
    val clientHighlight: String,
    val price: String = "$35",
    val stockStatus: String = "In Stock", // "In Stock", "Low Stock", "Special Order"
    val stockQuantity: Int = 10
)

data class ServiceItem(
    val id: String,
    val title: String,
    val category: String,
    val summary: String,
    val description: String,
    val startingPrice: String,
    val typicalLeadTime: String,
    val keyMaterials: List<String>,
    val processSteps: List<String>,
    @DrawableRes val sampleImageRes: Int? = null
)

data class CoreValue(
    val title: String,
    val shortSummary: String,
    val detail: String
)

data class TeamMember(
    val name: String,
    val role: String,
    val experience: String,
    val note: String,
    @DrawableRes val photoRes: Int? = null
)

object BusinessData {
    val businessName = "Apex Enterprises"
    val founderName = "Soman Paliath"
    val founderRole = "Proprietor & Entrepreneur"
    val establishedYear = "2024"
    val phone = "87548 25880"
    val email = "apexentp2025@gmail.com"
    val address = "Shop no: B02, KOKO Chennai Food Street, 181/3, Veerathamman Kovil St, Jalladiampet, Medavakkam, Chennai, Tamil Nadu 600100"
    val hours = "Mon–Sat: 8:30 AM – 9:30 PM • Sun: 9:00 AM – 8:00 PM"

    val storyParagraph1 = "Soman Paliath is an entrepreneur and the proprietor of Apex Enterprises, a customer-focused retail and service business based in Jalladiampet, Medavakkam, Chennai. With a practical, customer-first approach to business, Soman is focused on building Apex Enterprises as a convenient destination for everyday stationery, mobile accessories, toys, gift items, and essential document and printing services."

    val storyParagraph2 = "Soman Paliath believes that a successful local business is built on three simple principles: quality products, fair pricing, and dependable service. His approach is centered around understanding the everyday needs of customers and providing useful products and services at prices that offer genuine value. This customer-oriented philosophy is particularly important in a competitive, price-sensitive local market."

    val storyParagraph3 = "Through Apex Enterprises, Soman brings together retail products and essential services under one roof, making it easier for students, families, professionals, and nearby residents to access their everyday requirements. His long-term vision is to develop Apex Enterprises into a trusted local brand while continuously improving its product range, services, technology, and customer experience."

    val missionStatement = "To build a trusted and customer-friendly local business that combines quality products, affordable pricing, and reliable everyday services under one roof."

    val coreValues = listOf(
        CoreValue(
            title = "Quality Products",
            shortSummary = "Useful everyday products at competitive prices",
            detail = "We understand our neighborhood's daily needs and curate dependable stationery, durable mobile accessories, safe toys, and delightful gifts that offer true utility."
        ),
        CoreValue(
            title = "Fair & Affordable Pricing",
            shortSummary = "Genuine value for students & local families",
            detail = "Our pricing is transparent and competitive, designed especially for students, working professionals, and households in our local Medavakkam community."
        ),
        CoreValue(
            title = "Dependable Service",
            shortSummary = "Reliable, quick document & print solutions",
            detail = "Fast Xerox, crystal-clear printouts, high-res scanning, lamination, spiral binding, and passport photo reprints delivered promptly with care."
        ),
        CoreValue(
            title = "Customer-First Convenience",
            shortSummary = "Retail products & essential services under one roof",
            detail = "Located at KOKO Chennai Food Street, we make daily errands effortless—combining everyday shopping with essential document processing in one quick stop."
        )
    )

    val teamMembers = listOf(
        TeamMember(
            name = "Soman Paliath",
            role = "Proprietor & Entrepreneur",
            experience = "Business & Retail Services Specialist",
            note = "Built on quality products, fair pricing, and dependable service. Dedicated to serving students, professionals, and families across Medavakkam.",
            photoRes = R.drawable.img_store_owner
        ),
        TeamMember(
            name = "Apex Service Staff",
            role = "Document & Retail Assistant",
            experience = "Printing, Scanning & Spiral Binding Specialist",
            note = "Handles high-speed Xerox copies, project report spiral binding, thermal lamination, and parcel courier dispatches."
        )
    )

    val galleryItems = listOf(
        GalleryItem(
            id = "prod_1",
            title = "Student Exam & School Stationery Bundle",
            category = "Office & School Stationery",
            imageRes = R.drawable.img_stationery_showcase,
            woodType = "Classmate Notebooks • Ball & Gel Pens • Geometry Instruments",
            finish = "Assorted Ruled / Unruled • Highlighters & Sticky Notes",
            dimensions = "A4 & Long Size Formats • Pack of Essentials",
            year = "2024",
            description = "Complete everyday school and college set including notebooks, smooth-flow ball and gel pens, highlighters, correction tape, and geometry box instruments.",
            clientHighlight = "Best-seller among local school and college students in Medavakkam.",
            price = "₹180",
            stockStatus = "In Stock",
            stockQuantity = 25
        ),
        GalleryItem(
            id = "prod_2",
            title = "JK Copier A4 Multipurpose 75 GSM Paper Ream",
            category = "Office & School Stationery",
            imageRes = R.drawable.img_stationery_showcase,
            woodType = "500 Sheets • Premium 75 GSM High-Brightness White Paper",
            finish = "Jam-Free Laser & Inkjet Compatible Pack",
            dimensions = "Standard A4 (210 x 297 mm)",
            year = "2024",
            description = "Bright, high-contrast multipurpose copier paper engineered for jam-free printing, xerox copying, college reports, and office documentation.",
            clientHighlight = "Popular for college project submissions and small business billing.",
            price = "₹340",
            stockStatus = "In Stock",
            stockQuantity = 40
        ),
        GalleryItem(
            id = "prod_3",
            title = "Fast-Charging Braided Type-C & Lightning Cables",
            category = "Mobile Accessories",
            imageRes = R.drawable.img_tech_showcase,
            woodType = "Heavy-Duty Braided Nylon • 65W Fast Charge & High-Speed Data Sync",
            finish = "Reinforced Aluminum Connectors • Anti-Tangle Cable",
            dimensions = "1.2 Meter Extended Length",
            year = "2024",
            description = "Durable, tangle-free fast charging cables supporting quick charge standards for Android, iPhone, power banks, and tablets.",
            clientHighlight = "Tested for over 10,000 bend cycles with reinforced strain relief.",
            price = "₹199",
            stockStatus = "In Stock",
            stockQuantity = 15
        ),
        GalleryItem(
            id = "prod_4",
            title = "Universal Shockproof Phone Cases & 11D Tempered Glass",
            category = "Mobile Accessories",
            imageRes = R.drawable.img_tech_showcase,
            woodType = "9H Hardness Edge-to-Edge Protection • Impact TPU Cushioning",
            finish = "Oleophobic Anti-Fingerprint & Anti-Scratch Coating",
            dimensions = "Available for all popular Vivo, Oppo, Redmi, Samsung & iPhone models",
            year = "2024",
            description = "Complete protection for your smartphone. Edge-to-edge tempered glass applied bubble-free at our store counter plus durable drop-tested covers.",
            clientHighlight = "Free dust-free installation when purchased in-store.",
            price = "₹149",
            stockStatus = "In Stock",
            stockQuantity = 30
        ),
        GalleryItem(
            id = "prod_5",
            title = "Creative STEM Building Blocks & Educational Toys",
            category = "Toys",
            imageRes = R.drawable.img_toys_showcase,
            woodType = "Ages 3+ to 10+ • Safe Non-Toxic ABS Plastic & Smooth Edges",
            finish = "Vibrant Multicolor Interlocking Blocks with Storage Box",
            dimensions = "Modular Creative Snap Pieces",
            year = "2024",
            description = "Screen-free creative entertainment that develops spatial reasoning, hand-eye coordination, and problem-solving skills for kids of all ages.",
            clientHighlight = "Wonderful birthday gift choice for young curious minds.",
            price = "₹299",
            stockStatus = "In Stock",
            stockQuantity = 12
        ),
        GalleryItem(
            id = "prod_6",
            title = "Executive Diary & Premium Metal Pen Gift Set",
            category = "Gift Items",
            imageRes = R.drawable.img_hero_store,
            woodType = "PU Leatherette Hardbound Journal + Refillable Brass-Accent Pen",
            finish = "Gold Embossed Cover • Elegant Satin-Lined Presentation Box",
            dimensions = "A5 Format Diary + Metal Rollerball Pen",
            year = "2024",
            description = "A sophisticated, practical gift bundle suitable for teacher appreciation, corporate milestones, birthdays, and festive occasions.",
            clientHighlight = "Includes complimentary gift packaging.",
            price = "₹399",
            stockStatus = "In Stock",
            stockQuantity = 10
        ),
        GalleryItem(
            id = "prod_7",
            title = "Pan-India Express Courier Booking & Dispatch",
            category = "Courier",
            imageRes = R.drawable.img_hero_banner,
            woodType = "Express Documents, Parcels & E-Commerce Shipments",
            finish = "Waterproof Tamper-Evident Envelope & Bubble Wrap Packing",
            dimensions = "Envelopes to 10kg+ Box Shipments",
            year = "2024",
            description = "Reliable domestic courier booking with end-to-end SMS tracking. Fast dispatch for urgent documents, certificates, and personal packages across India.",
            clientHighlight = "Safe packing and instant consignment tracking number provided.",
            price = "From ₹60",
            stockStatus = "Available",
            stockQuantity = 99
        )
    )

    val services = listOf(
        ServiceItem(
            id = "srv_xerox",
            title = "High-Speed Xerox (Black & White and Colour)",
            category = "Document Services",
            summary = "Sharp, clear single-sided and double-sided copies on quality 75 GSM paper.",
            description = "Fast photocopy service for school notes, legal forms, Aadhaar/PAN cards, and office documents. Clean laser reproduction with minimal waiting time.",
            startingPrice = "₹2 / Page",
            typicalLeadTime = "Instant / 1 to 3 Minutes",
            keyMaterials = listOf("75 GSM Copier Paper", "High-Resolution Laser Toners", "Auto-Feeder Scanner"),
            processSteps = listOf("Document Inspection", "Single or Double-Sided Setup", "High-Speed Laser Copy", "Verification & Handover"),
            sampleImageRes = R.drawable.img_stationery_showcase
        ),
        ServiceItem(
            id = "srv_printouts",
            title = "Computer Printouts (A4 & A3 / Colour & B&W)",
            category = "Document Services",
            summary = "Send your files directly via WhatsApp, Email, or USB drive for instant printing.",
            description = "Print your tickets, resume, college project reports, study materials, and certificates. Available in crisp monochrome or high-definition vibrant color.",
            startingPrice = "₹5 / Page",
            typicalLeadTime = "Instant / 2 to 5 Minutes",
            keyMaterials = listOf("Digital Laser Printers", "Glossy & Bond Paper Options", "WhatsApp / Email File Reception"),
            processSteps = listOf("Send File via WhatsApp/Email", "Format & Margin Check", "High-Resolution Print", "Collation"),
            sampleImageRes = R.drawable.img_tech_showcase
        ),
        ServiceItem(
            id = "srv_scan",
            title = "High-Resolution Document Scanning & PDF Creation",
            category = "Digital Services",
            summary = "Crystal-clear digitization of certificates, marksheets, and documents sent to WhatsApp/Email.",
            description = "Convert paper records, ID cards, signed contracts, and multi-page manuscripts into organized, searchable PDF or JPEG files ready for online job applications or university submissions.",
            startingPrice = "₹10 / Document",
            typicalLeadTime = "Instant / 2 to 4 Minutes",
            keyMaterials = listOf("Optical Flatbed & ADF Scanner", "Multi-Page PDF Compiler", "Instant WhatsApp/Cloud Delivery"),
            processSteps = listOf("Document Placement & Resolution Setting", "Color Optical Scan", "PDF Conversion & Optimization", "Direct Transfer to Your Phone"),
            sampleImageRes = R.drawable.img_hero_store
        ),
        ServiceItem(
            id = "srv_binding",
            title = "Spiral Binding & Soft Project Binding",
            category = "Finishing Services",
            summary = "Professional spiral coils, clear plastic sheets, and soft binding for student projects and reports.",
            description = "Give your college assignments, training manuals, and office documentation a polished finish. Sturdy plastic spiral coils and transparent PVC front covers prevent tearing and page loss.",
            startingPrice = "₹30 / Book",
            typicalLeadTime = "5 to 15 Minutes",
            keyMaterials = listOf("Durable Plastic Spiral Coils", "Transparent PVC Covers", "Opaque Cardboard Backing"),
            processSteps = listOf("Page Alignment & Hole Punching", "Coil Threading", "End Crimping", "Quality Inspection"),
            sampleImageRes = R.drawable.img_toys_showcase
        ),
        ServiceItem(
            id = "srv_lamination",
            title = "Hot Thermal Lamination (ID Card to A3 Size)",
            category = "Preservation Services",
            summary = "Waterproof, tear-resistant protective seal for valuable certificates, licenses, and menus.",
            description = "Protect your essential diplomas, birth certificates, vehicle RC cards, and business documents against moisture, fingerprints, and accidental folds with crystal-clear thermal pouches.",
            startingPrice = "₹15 / Card",
            typicalLeadTime = "Instant / 2 to 3 Minutes",
            keyMaterials = listOf("Thermal Heavy-Duty Lamination Pouches", "Dual-Heater Roller Laminator", "Precision Corner Trimmer"),
            processSteps = listOf("Dust Inspection", "Pouch Enclosure", "Heated Roller Sealing", "Edge Trimming"),
            sampleImageRes = R.drawable.img_stationery_showcase
        ),
        ServiceItem(
            id = "srv_passport_photo",
            title = "Instant Passport Size Photo Reprint & Fresh Copies",
            category = "Photo Services",
            summary = "Quick passport-size photos for visas, school admissions, job applications, and ID badges.",
            description = "Printed on premium high-gloss photo paper with standardized white or blue backgrounds. Fast reprints directly from your mobile phone photo or WhatsApp image.",
            startingPrice = "₹50 (8 Photos)",
            typicalLeadTime = "5 to 10 Minutes",
            keyMaterials = listOf("High-Gloss Photo Paper", "Dye-Sub Color Photo Inks", "Precise Die-Cutter"),
            processSteps = listOf("Photo Crop & Background Setup", "Color Balancing", "High-Resolution Photo Print", "Precision Cut & Envelope Pack"),
            sampleImageRes = R.drawable.img_hero_banner
        )
    )
}
