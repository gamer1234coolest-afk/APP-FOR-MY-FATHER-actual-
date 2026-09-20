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
            val existingConfig = shopConfigDao.getShopConfigSync()
            val needsReseed = existingConfig == null || existingConfig.businessName != "Apex Enterprises"

            // Check for legacy data and reseed if present
            if (needsReseed || galleryDao.getLegacyCount() > 0) {
                galleryDao.deleteAll()
                serviceDao.deleteAll()
                testimonialDao.deleteAll()
                donationDao.deleteAll()
            }

            // 1. Seed Testimonials if empty
            if (testimonialDao.getCount() == 0) {
                val googleReviews = GoogleMapsReviewService.getGoogleMapsReviews()
                val directTestimonials = listOf(
                    TestimonialEntity(
                        customerName = "Meenakshi Sundaram",
                        location = "Jalladiampet, Medavakkam",
                        serviceReceived = "School Stationery & Notebook Sets",
                        rating = 5,
                        comment = "Apex Enterprises is our trusted neighborhood shop for all school requirements. Soman sir is always welcoming, very honest with product suggestions, and keeps top brands like Classmate and Camlin at very affordable prices.",
                        date = "2 weeks ago",
                        verifiedProject = true,
                        isGoogleReview = false,
                        source = "Direct Customer",
                        reviewerInitials = "MS"
                    ),
                    TestimonialEntity(
                        customerName = "Ramesh Krishnan",
                        location = "Medavakkam Main Road",
                        serviceReceived = "High-Speed Xerox & Document Scanning",
                        rating = 5,
                        comment = "Printed 60 copies of commercial agreements and scanned all original property documents to PDF. Fast service, crisp clean printouts, and prompt WhatsApp file sharing with no waiting around.",
                        date = "1 month ago",
                        verifiedProject = true,
                        isGoogleReview = false,
                        source = "Direct Customer",
                        reviewerInitials = "RK"
                    ),
                    TestimonialEntity(
                        customerName = "Anitha Anand",
                        location = "Perumbakkam, Chennai",
                        serviceReceived = "Passport Size Photos & Thermal Lamination",
                        rating = 5,
                        comment = "Got instant passport size photos reprinted for school admissions and got certificates laminated in 5 minutes. Having all printing, finishing, and courier booking together in one shop is so convenient.",
                        date = "2 months ago",
                        verifiedProject = true,
                        isGoogleReview = false,
                        source = "Direct Customer",
                        reviewerInitials = "AA"
                    )
                )
                testimonialDao.insertAll(googleReviews + directTestimonials)
            }

            // 2. Seed Stock / Gallery Items if empty
            if (galleryDao.getCount() == 0) {
                val initialStock = BusinessData.galleryItems.map { item ->
                    val resName = when (item.id) {
                        "prod_1", "prod_2" -> "img_stationery_showcase"
                        "prod_3", "prod_4" -> "img_tech_showcase"
                        "prod_5", "prod_6" -> "img_toys_showcase"
                        else -> "img_stationery_showcase"
                    }
                    GalleryItemEntity.fromGalleryItem(item, resName)
                }
                galleryDao.insertAll(initialStock)
            }

            // 3. Seed Shop Config if empty or outdated
            if (needsReseed) {
                shopConfigDao.saveShopConfig(ShopConfigEntity())
            }

            // 4. Seed Services if empty
            if (serviceDao.getCount() == 0) {
                val initialServices = BusinessData.services.mapIndexed { index, srv ->
                    val resName = when (srv.id) {
                        "srv_xerox_print" -> "img_stationery_showcase"
                        "srv_scan_digital" -> "img_hero_store"
                        "srv_spiral_binding" -> "img_stationery_showcase"
                        "srv_lamination" -> "img_hero_store"
                        "srv_courier_booking" -> "img_tech_showcase"
                        "srv_passport_photo" -> "img_toys_showcase"
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
                        donorName = "Medavakkam Youth & Student Club",
                        amountDollars = 500.0,
                        tierTitle = "Local Student Stationery & Exam Kits",
                        frequency = "One-Time",
                        note = "Equipping 25 local school students with notebooks, geometry boxes, and exam writing pads.",
                        paymentMethod = "UPI / Google Pay",
                        referenceCode = "APX-STU-101",
                        timestamp = System.currentTimeMillis() - 86400000L * 3
                    ),
                    DonationEntity(
                        donorName = "Jalladiampet Residents Association",
                        amountDollars = 250.0,
                        tierTitle = "School Notebook & Bag Sponsorship",
                        frequency = "Monthly Patron",
                        note = "Supporting notebook packs and school bags for underprivileged elementary children.",
                        paymentMethod = "Bank Transfer",
                        referenceCode = "APX-RES-202",
                        timestamp = System.currentTimeMillis() - 86400000L * 7
                    ),
                    DonationEntity(
                        donorName = "Chennai Food Street Vendors Community",
                        amountDollars = 100.0,
                        tierTitle = "Community Children Toy & Puzzle Drive",
                        frequency = "One-Time",
                        note = "Creative building blocks and educational toys for neighborhood learning corners.",
                        paymentMethod = "Cash Counter",
                        referenceCode = "APX-TOY-303",
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
