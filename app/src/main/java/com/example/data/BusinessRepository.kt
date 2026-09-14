package com.example.data

import com.example.model.BusinessData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class BusinessRepository(
    private val inquiryDao: InquiryDao,
    private val testimonialDao: TestimonialDao,
    private val galleryDao: GalleryDao,
    private val shopConfigDao: ShopConfigDao,
    private val serviceDao: ServiceDao,
    private val donationDao: DonationDao
) {
    val allInquiries: Flow<List<InquiryEntity>> = inquiryDao.getAllInquiries()
    val allTestimonials: Flow<List<TestimonialEntity>> = testimonialDao.getAllTestimonials()
    val allGalleryItems: Flow<List<GalleryItemEntity>> = galleryDao.getAllItems()
    val shopConfig: Flow<ShopConfigEntity?> = shopConfigDao.getShopConfig()
    val allServices: Flow<List<ServiceItemEntity>> = serviceDao.getAllServices()
    val allDonations: Flow<List<DonationEntity>> = donationDao.getAllDonations()

    suspend fun checkAndSeedInitialData() {
        withContext(Dispatchers.IO) {
            // Check for legacy woodcraft data and reseed if present
            if (galleryDao.getLegacyCount() > 0) {
                galleryDao.deleteAll()
            }
            if (serviceDao.getLegacyCount() > 0) {
                serviceDao.deleteAll()
            }
            if (testimonialDao.getLegacyCount() > 0) {
                testimonialDao.deleteAll()
            }
            if (donationDao.getLegacyCount() > 0) {
                donationDao.deleteAll()
            }

            // 1. Seed Testimonials if empty
            if (testimonialDao.getCount() == 0) {
                val googleReviews = GoogleMapsReviewService.getGoogleMapsReviews()
                val directTestimonials = listOf(
                    TestimonialEntity(
                        customerName = "Eleanor & David Vance",
                        location = "Arts District",
                        serviceReceived = "Keyflow 75% Custom Mechanical Keyboard",
                        rating = 5,
                        comment = "Beacon is our favorite downtown destination. Alex took the time to let us test out different switch keystrokes and sound profiles before buying. The build quality of this keyboard is exceptional, and typing all day is now a genuine pleasure.",
                        date = "2 weeks ago",
                        verifiedProject = true,
                        isGoogleReview = false,
                        source = "Direct Client",
                        reviewerInitials = "EV"
                    ),
                    TestimonialEntity(
                        customerName = "Robert Sterling",
                        location = "University Heights",
                        serviceReceived = "Loomis Linen Hardcover Dot-Grid Journal",
                        rating = 5,
                        comment = "The stationery curation here is incredible. Maya showed me the 160gsm bamboo paper notebooks—zero ghosting even with wet fountain pen inks or heavy watercolor markers. Plus the brass pen engraving service turned it into an heirloom gift.",
                        date = "1 month ago",
                        verifiedProject = true,
                        isGoogleReview = false,
                        source = "Direct Client",
                        reviewerInitials = "RS"
                    ),
                    TestimonialEntity(
                        customerName = "Sophia Martinez",
                        location = "Pine Grove",
                        serviceReceived = "RoboMaster STEM Robot Builder Kit",
                        rating = 5,
                        comment = "Sam helped my 10-year-old choose her first programmable robotics kit. The staff is so encouraging and knowledgeable. She built the rover in one afternoon and is now learning Scratch coding blocks. We will definitely be back for board game night!",
                        date = "2 months ago",
                        verifiedProject = true,
                        isGoogleReview = false,
                        source = "Direct Client",
                        reviewerInitials = "SM"
                    )
                )
                testimonialDao.insertAll(googleReviews + directTestimonials)
            }

            // 2. Seed Stock / Gallery Items if empty
            if (galleryDao.getCount() == 0) {
                val initialStock = BusinessData.galleryItems.map { item ->
                    val resName = when (item.id) {
                        "prod_1", "prod_2" -> "img_tech_showcase"
                        "prod_3", "prod_4" -> "img_toys_showcase"
                        "prod_5", "prod_6" -> "img_stationery_showcase"
                        else -> "img_tech_showcase"
                    }
                    GalleryItemEntity.fromGalleryItem(item, resName)
                }
                galleryDao.insertAll(initialStock)
            }

            // 3. Seed Shop Config if empty
            shopConfigDao.saveShopConfig(ShopConfigEntity())

            // 4. Seed Services if empty
            if (serviceDao.getCount() == 0) {
                val initialServices = BusinessData.services.mapIndexed { index, srv ->
                    val resName = when (srv.id) {
                        "srv_device_setup" -> "img_tech_showcase"
                        "srv_gift_curation" -> "img_toys_showcase"
                        "srv_laser_engraving" -> "img_stationery_showcase"
                        "srv_school_supplies" -> "img_hero_store"
                        else -> null
                    }
                    ServiceItemEntity.fromServiceItem(srv, resName, index)
                }
                serviceDao.insertAll(initialServices)
            }

            // 5. Seed Donations & Community Support Wall if empty
            if (donationDao.getCount() == 0) {
                val initialDonations = listOf(
                    DonationEntity(
                        donorName = "Riverside Elementary STEM Club",
                        amountDollars = 150.0,
                        tierTitle = "STEM Robotics & Coding Sponsor",
                        frequency = "One-Time",
                        note = "Equipping 3 student competition teams with modular sensors and microcontrollers.",
                        paymentMethod = "Credit Card",
                        referenceCode = "PAT-2024",
                        timestamp = System.currentTimeMillis() - 86400000L * 3
                    ),
                    DonationEntity(
                        donorName = "Downtown Arts & Writers Guild",
                        amountDollars = 75.0,
                        tierTitle = "Youth Calligraphy & Art Supplies",
                        frequency = "Monthly Patron",
                        note = "Providing sketchbooks and fine gel pens for neighborhood youth creative workshops.",
                        paymentMethod = "Bank Transfer",
                        referenceCode = "PAT-2025",
                        timestamp = System.currentTimeMillis() - 86400000L * 7
                    ),
                    DonationEntity(
                        donorName = "Community Tabletop Circle",
                        amountDollars = 35.0,
                        tierTitle = "Board Game & Puzzle Library",
                        frequency = "One-Time",
                        note = "Adding new cooperative family puzzle games to the free community gaming shelf.",
                        paymentMethod = "Google Pay",
                        referenceCode = "PAT-3042",
                        timestamp = System.currentTimeMillis() - 86400000L * 12
                    )
                )
                donationDao.insertAll(initialDonations)
            }
        }
    }

    // --- Stock & Gallery Operations ---
    suspend fun insertStockItem(item: GalleryItemEntity) {
        withContext(Dispatchers.IO) {
            galleryDao.insertItem(item)
        }
    }

    suspend fun updateStockItem(item: GalleryItemEntity) {
        withContext(Dispatchers.IO) {
            galleryDao.updateItem(item)
        }
    }

    suspend fun updateStockStatus(id: String, status: String) {
        withContext(Dispatchers.IO) {
            galleryDao.updateStockStatus(id, status)
        }
    }

    suspend fun updatePriceAndQuantity(id: String, price: String, quantity: Int) {
        withContext(Dispatchers.IO) {
            galleryDao.updatePriceAndQuantity(id, price, quantity)
        }
    }

    suspend fun deleteStockItem(id: String) {
        withContext(Dispatchers.IO) {
            galleryDao.deleteItem(id)
        }
    }

    suspend fun resetStockToDefaults() {
        withContext(Dispatchers.IO) {
            galleryDao.deleteAll()
            val initialStock = BusinessData.galleryItems.map { item ->
                val resName = when (item.id) {
                    "prod_1", "prod_2" -> "img_tech_showcase"
                    "prod_3", "prod_4" -> "img_toys_showcase"
                    "prod_5", "prod_6" -> "img_stationery_showcase"
                    else -> "img_tech_showcase"
                }
                GalleryItemEntity.fromGalleryItem(item, resName)
            }
            galleryDao.insertAll(initialStock)
        }
    }

    // --- Shop Config Operations ---
    suspend fun updateShopConfig(config: ShopConfigEntity) {
        withContext(Dispatchers.IO) {
            shopConfigDao.saveShopConfig(config)
        }
    }

    // --- Services Operations ---
    suspend fun updateService(service: ServiceItemEntity) {
        withContext(Dispatchers.IO) {
            serviceDao.updateService(service)
        }
    }

    suspend fun updateServicePriceAndLeadTime(id: String, price: String, leadTime: String) {
        withContext(Dispatchers.IO) {
            serviceDao.updatePriceAndLeadTime(id, price, leadTime)
        }
    }

    // --- Inquiry Operations ---
    suspend fun submitInquiry(inquiry: InquiryEntity): Long {
        return withContext(Dispatchers.IO) {
            inquiryDao.insertInquiry(inquiry)
        }
    }

    suspend fun updateInquiryStatus(id: Long, status: String) {
        withContext(Dispatchers.IO) {
            inquiryDao.updateStatus(id, status)
        }
    }

    suspend fun deleteInquiry(id: Long) {
        withContext(Dispatchers.IO) {
            inquiryDao.deleteInquiryById(id)
        }
    }

    // --- Testimonials Operations ---
    suspend fun syncGoogleMapsReviews(): Int {
        return withContext(Dispatchers.IO) {
            val googleReviews = GoogleMapsReviewService.getGoogleMapsReviews()
            testimonialDao.insertAll(googleReviews)
            googleReviews.size
        }
    }

    suspend fun submitTestimonial(testimonial: TestimonialEntity): Long {
        return withContext(Dispatchers.IO) {
            testimonialDao.insertTestimonial(testimonial)
        }
    }

    suspend fun deleteTestimonial(id: Long) {
        withContext(Dispatchers.IO) {
            testimonialDao.deleteById(id)
        }
    }

    // --- Donations Operations ---
    suspend fun submitDonation(donation: DonationEntity): Long {
        return withContext(Dispatchers.IO) {
            donationDao.insertDonation(donation)
        }
    }

    suspend fun deleteDonation(id: Long) {
        withContext(Dispatchers.IO) {
            donationDao.deleteDonation(id)
        }
    }
}
