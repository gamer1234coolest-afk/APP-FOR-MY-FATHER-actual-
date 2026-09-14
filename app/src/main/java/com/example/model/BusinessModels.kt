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
    val businessName = "Beacon Tech, Toys & Stationery"
    val founderName = "Alex Mercer"
    val founderRole = "Founder & Lead Curator"
    val establishedYear = "2012"
    val phone = "(555) 482-9301"
    val email = "hello@beacontechtoys.com"
    val address = "142 Main Street, Arts & Tech District"
    val hours = "Mon–Sat: 9:00 AM – 7:00 PM • Sun: 10:00 AM – 4:00 PM"

    val storyParagraph1 = "Founded in 2012, Beacon Tech, Toys & Stationery started as a neighborhood dream to unite the three things that ignite everyday curiosity and creativity: dependable modern tech, enriching educational toys, and tactile, beautiful stationery."

    val storyParagraph2 = "In an age of impersonal online warehouses, Alex Mercer wanted to create a welcoming local storefront where parents and kids can test programmable STEM robots, writers and artists can feel fountain pen nibs on 160gsm bamboo paper, and professionals can test ergonomic keyboards before buying."

    val storyParagraph3 = "Over a decade later, we remain proudly local and family-operated. We partner with local schools for classroom supplies, host weekend tabletop game days, and provide friendly, patient tech advice to every customer who walks through our doors."

    val missionStatement = "To inspire curiosity, creative expression, and daily productivity in our community by curating durable tech gadgets, imaginative toys, and premium stationery with warm, personal neighborhood care."

    val coreValues = listOf(
        CoreValue(
            title = "Curated & Tested Quality",
            shortSummary = "Every product in our store is hands-on tested",
            detail = "We reject flimsy plastic junk and disposable electronics. From mechanical keyboards to bleedproof journals and STEM kits, we only stock what we proudly use ourselves."
        ),
        CoreValue(
            title = "Community & Learning First",
            shortSummary = "STEM workshops, board game days, and school support",
            detail = "We partner with local public schools, sponsor youth robotics clubs, and host open community puzzle tables every Saturday morning."
        ),
        CoreValue(
            title = "Hands-On Discovery",
            shortSummary = "Try before you buy in our open-sample studio",
            detail = "Customers are encouraged to test switch sounds on mechanical keyboards, try out fountain pen inks, and test toy mechanics at our demo counters."
        ),
        CoreValue(
            title = "Friendly Neighborhood Care",
            shortSummary = "Device setup, free gift wrapping, and local warranty help",
            detail = "You deal directly with real, passionate humans who know the products inside out and stand behind every purchase with hassle-free local support."
        )
    )

    val teamMembers = listOf(
        TeamMember(
            name = "Alex Mercer",
            role = "Founder & Curator",
            experience = "14 Years in Tech Retail & Community Organizing",
            note = "Passionate about mechanical keyboards, ergonomic desk setups, and supporting local downtown vibrancy.",
            photoRes = R.drawable.img_store_owner
        ),
        TeamMember(
            name = "Sam Rivera",
            role = "Toys & STEM Learning Lead",
            experience = "8 Years in Robotics & Child Development",
            note = "Leads our weekend coding workshops and tests every puzzle and robotics kit for safety, durability, and fun."
        ),
        TeamMember(
            name = "Maya Lin",
            role = "Stationery & Calligraphy Specialist",
            experience = "9 Years in Fine Paper & Bookbinding",
            note = "Curates our Japanese gel pens, imported fountain inks, and premium notebooks. Teaches bullet journaling."
        )
    )

    val galleryItems = listOf(
        GalleryItem(
            id = "prod_1",
            title = "Aura Pro Wireless Noise-Cancelling Headphones",
            category = "Tech & Gadgets",
            imageRes = R.drawable.img_tech_showcase,
            woodType = "Bluetooth 5.3 • 40h Battery • Hi-Res Audio",
            finish = "Matte Midnight Black • Memory Foam Ear Cushions",
            dimensions = "Over-Ear Ergonomic (Foldable with hard travel case)",
            year = "2024",
            description = "Hybrid active noise cancellation with 40mm beryllium drivers, transparency mode, multi-point connection, and ultra-comfortable protein leather cushions.",
            clientHighlight = "Our #1 best-selling headphones for study sessions and remote work focus.",
            price = "$149",
            stockStatus = "In Stock",
            stockQuantity = 8
        ),
        GalleryItem(
            id = "prod_2",
            title = "Keyflow 75% Custom Mechanical Keyboard",
            category = "Tech & Gadgets",
            imageRes = R.drawable.img_tech_showcase,
            woodType = "Hot-Swappable Gateron Yellow Switches • Tri-Mode Wireless",
            finish = "Anodized Slate Aluminum Frame • PBT Dye-Sub Keycaps",
            dimensions = "Compact 75% Layout (310 x 120 x 38mm)",
            year = "2024",
            description = "Pre-lubed linear switches with silicone dampening pads for an ultra-creamy sound profile. Connects via Bluetooth, 2.4GHz wireless dongle, or braided USB-C cable.",
            clientHighlight = "Demo unit available on our store counter to try switch clicks in person.",
            price = "$89",
            stockStatus = "In Stock",
            stockQuantity = 5
        ),
        GalleryItem(
            id = "prod_3",
            title = "RoboMaster STEM Programmable Robot Builder Kit",
            category = "Toys & Games",
            imageRes = R.drawable.img_toys_showcase,
            woodType = "Ages 8-14 • Scratch & Python Visual Coding Support",
            finish = "Durable Eco-ABS Plastic • Ultrasonic & Line Tracking Sensors",
            dimensions = "420 Modular Snap Pieces + Rechargeable Lithium Pack",
            year = "2024",
            description = "Build a line-following rover, obstacle-avoiding bot, or robotic arm. Controlled via intuitive mobile app with step-by-step interactive STEM missions.",
            clientHighlight = "Adopted by 3 local school STEM clubs for coding tournaments.",
            price = "$79",
            stockStatus = "In Stock",
            stockQuantity = 6
        ),
        GalleryItem(
            id = "prod_4",
            title = "MindBender 3D Geometric Wooden Puzzle Sphere",
            category = "Toys & Games",
            imageRes = R.drawable.img_toys_showcase,
            woodType = "Ages 6+ • FSC-Certified Natural Beechwood & Non-Toxic Dyes",
            finish = "Smooth Hand-Waxed Finish • 48 Interlocking Precision Segments",
            dimensions = "5.5\" Diameter • Includes Felt Storage Pouch & Stand",
            year = "2024",
            description = "A deeply satisfying tactile puzzle that challenges spatial reasoning and patience. A timeless screen-free gift for both inquisitive kids and adults.",
            clientHighlight = "Perfect coffee table brainteaser and stress reliever.",
            price = "$34",
            stockStatus = "In Stock",
            stockQuantity = 12
        ),
        GalleryItem(
            id = "prod_5",
            title = "Heritage Solid Brass Fountain Pen & Ink Gift Set",
            category = "Fine Stationery",
            imageRes = R.drawable.img_stationery_showcase,
            woodType = "German Precision Iridium Nib (Medium) • Piston Converter Included",
            finish = "Brushed Raw Brass with Anti-Oxidation Protective Coat",
            dimensions = "5.4\" Length • 38g Perfectly Balanced Weight",
            year = "2024",
            description = "Engineered for effortless, buttery ink flow without scratching. Includes a 30ml bottle of rich archival black ink and a retro gift tin box.",
            clientHighlight = "Complimentary in-store laser name engraving available upon purchase.",
            price = "$45",
            stockStatus = "In Stock",
            stockQuantity = 14
        ),
        GalleryItem(
            id = "prod_6",
            title = "Loomis Linen Hardcover Dot-Grid Journal",
            category = "Fine Stationery",
            imageRes = R.drawable.img_stationery_showcase,
            woodType = "160gsm Ultra-Thick Bleedproof Bamboo Paper • 192 Numbered Pages",
            finish = "Sage Green Woven Linen Cover • Gold Foil Embossed Spine",
            dimensions = "A5 Format (5.8\" x 8.3\") • Lay-Flat 180° Binding",
            year = "2024",
            description = "Heavyweight bleedproof pages handle fountain pens, wet brush markers, and watercolor with zero ghosting. Features dual ribbon bookmarks and an expandable rear pocket.",
            clientHighlight = "Favorite choice among our neighborhood bullet journaling group.",
            price = "$24",
            stockStatus = "In Stock",
            stockQuantity = 20
        )
    )

    val services = listOf(
        ServiceItem(
            id = "srv_device_setup",
            title = "Device Setup & Precision Screen Protection",
            category = "Tech Services",
            summary = "Dust-free tempered glass alignment, initial setup, and phone/tablet data transfer.",
            description = "Bring in any smartphone, tablet, or handheld console. We apply 9H tempered glass with machine alignment, transfer your data seamlessly, and answer all questions.",
            startingPrice = "$15+",
            typicalLeadTime = "15 to 30 Minutes",
            keyMaterials = listOf("9H Tempered Glass", "Anti-Static Clean Room", "High-Speed USB-C Transfer"),
            processSteps = listOf("Inspection & Screen Cleaning", "Dust Extraction", "Precision Alignment & Application", "Data Transfer & Quality Check"),
            sampleImageRes = R.drawable.img_tech_showcase
        ),
        ServiceItem(
            id = "srv_gift_curation",
            title = "Custom Gift Curation & Artisanal Wrapping",
            category = "Gifts & Bundling",
            summary = "Bespoke gift boxes combining toys, stationery, and tech with hand-tied ribbons.",
            description = "Looking for a thoughtful gift for a birthday, teacher, or tech lover? Tell us your budget and interests; we will assemble, ribbon-tie, and add a handwritten calligraphy card.",
            startingPrice = "Complimentary with $35+ Purchase",
            typicalLeadTime = "Same Day / While You Wait",
            keyMaterials = listOf("Recycled Kraft Boxes", "Cotton Woven Ribbons", "Handmade Calligraphy Cards"),
            processSteps = listOf("Discuss Recipient Profile", "Curate Matching Items", "Custom Box Packing", "Ribbon & Calligraphy Card"),
            sampleImageRes = R.drawable.img_toys_showcase
        ),
        ServiceItem(
            id = "srv_laser_engraving",
            title = "In-House Laser Personalization & Monogramming",
            category = "Personalization",
            summary = "Custom laser engraved names, quotes, or logos on brass pens, notebooks, and tech cases.",
            description = "Add a unique touch to your gear. Our precision fiber laser personalizes metal fountain pens, leather pen sleeves, wooden puzzles, and aluminum tech accessories.",
            startingPrice = "$12+",
            typicalLeadTime = "1 to 2 Hours",
            keyMaterials = listOf("Fiber Laser Engraver", "Vector Monogram Fonts", "Protective Clear Polish"),
            processSteps = listOf("Choose Font & Placement", "Digital Preview Approval", "Precision Laser Etch", "Final Polish & Handover"),
            sampleImageRes = R.drawable.img_stationery_showcase
        ),
        ServiceItem(
            id = "srv_school_supplies",
            title = "Classroom & Bulk School Supply Fulfillment",
            category = "Bulk & Community",
            summary = "Volume discounts and organized bundle kits for local teachers, schools, and offices.",
            description = "We assemble back-to-school packs, STEM club robotics components, and office desk supplies with neighborhood volume discounts and free local drop-off.",
            startingPrice = "15% to 25% Volume Discount",
            typicalLeadTime = "2 to 3 Business Days",
            keyMaterials = listOf("Classroom Bulk Packs", "STEM Project Bundles", "Recycled Paper Sets"),
            processSteps = listOf("Submit Supply List", "Quote & Availability Check", "Package by Class/Desk", "Local School Delivery or Pickup")
        )
    )
}
