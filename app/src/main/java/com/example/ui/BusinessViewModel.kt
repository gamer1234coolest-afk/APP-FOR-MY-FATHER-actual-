package com.example.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai.ChatMessage
import com.example.ai.GeminiChatService
import com.example.data.AppDatabase
import com.example.data.BusinessRepository
import com.example.data.DonationEntity
import com.example.data.GalleryItemEntity
import com.example.data.InquiryEntity
import com.example.data.ShopConfigEntity
import com.example.data.TestimonialEntity
import com.example.model.BusinessData
import com.example.model.GalleryItem
import com.example.model.ServiceItem
import com.example.ui.theme.ColorTheme
import com.example.ui.theme.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.random.Random

enum class AppSection(val label: String) {
    ABOUT("About Us"),
    GALLERY("Products"),
    SERVICES("Services"),
    DONATION("Community"),
    CHATBOT("Apex AI"),
    TESTIMONIALS("Reviews"),
    CONTACT("Contact"),
    SETTINGS("Settings")
}

enum class SettingsSubTab(val label: String) {
    APPEARANCE("Display & Themes"),
    PORTAL("Staff Portal")
}

data class ContactFormState(
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val selectedService: String = "Xerox & Printouts",
    val timeline: String = "Immediate / While You Wait",
    val budget: String = "Standard Rates",
    val details: String = "",
    val preferredContact: String = "Phone",
    val nameError: String? = null,
    val emailError: String? = null,
    val detailsError: String? = null,
    val isSubmittedSuccessfully: Boolean = false,
    val lastReferenceCode: String = ""
)

data class ReviewFormState(
    val customerName: String = "",
    val location: String = "",
    val serviceReceived: String = "Xerox & Document Printing",
    val rating: Int = 5,
    val comment: String = "",
    val nameError: String? = null,
    val commentError: String? = null
)

data class StockFormState(
    val id: String = "",
    val title: String = "",
    val category: String = "Office & School Stationery",
    val woodType: String = "JK Copier / Classmate",
    val finish: String = "Standard White",
    val dimensions: String = "A4 Format",
    val year: String = "2024",
    val price: String = "₹180",
    val stockStatus: String = "In Stock", // "In Stock", "Pre-Order", "Sold Out"
    val stockQuantity: Int = 20,
    val description: String = "",
    val clientHighlight: String = "Available at Apex Enterprises counter",
    val imagePreset: String = "img_gallery_tech",
    val isEditing: Boolean = false
)

data class DonationFormState(
    val donorName: String = "",
    val selectedTier: String = "Local Student Stationery & Exam Kits",
    val customAmount: String = "100",
    val frequency: String = "One-Time", // "One-Time", "Monthly Patron"
    val note: String = "",
    val paymentMethod: String = "UPI / Google Pay", // "UPI / Google Pay", "Cash", "Bank Transfer"
    val isAnonymous: Boolean = false,
    val nameError: String? = null,
    val amountError: String? = null,
    val isSubmittedSuccessfully: Boolean = false,
    val lastReferenceCode: String = ""
)

data class CraftsmanUser(
    val id: String,
    val name: String,
    val role: String,
    val defaultPin: String,
    val specialty: String
)

class BusinessViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BusinessRepository
    private val geminiService = GeminiChatService()

    val availableCraftsmen = listOf(
        CraftsmanUser("staff_soman", "Soman Paliath", "Proprietor & Entrepreneur", "1996", "Store Operations, Procurement & Business Services"),
        CraftsmanUser("staff_assistant", "Apex Store Assistant", "Document & Retail Lead", "2025", "Xerox, Color Printing, Scanning, Lamination & Spiral Binding")
    )

    init {
        val db = AppDatabase.getDatabase(application)
        repository = BusinessRepository(
            inquiryDao = db.inquiryDao(),
            testimonialDao = db.testimonialDao(),
            galleryDao = db.galleryDao(),
            shopConfigDao = db.shopConfigDao(),
            serviceDao = db.serviceDao(),
            donationDao = db.donationDao()
        )
        viewModelScope.launch {
            repository.checkAndSeedInitialData()
        }
    }

    // Owner / Partner Authentication State
    private val _authenticatedCraftsman = MutableStateFlow<CraftsmanUser?>(null)
    val authenticatedCraftsman: StateFlow<CraftsmanUser?> = _authenticatedCraftsman.asStateFlow()

    private val _craftsmanLoginError = MutableStateFlow<String?>(null)
    val craftsmanLoginError: StateFlow<String?> = _craftsmanLoginError.asStateFlow()

    fun loginCraftsman(user: CraftsmanUser, enteredPin: String): Boolean {
        if (enteredPin.trim() == user.defaultPin) {
            _authenticatedCraftsman.value = user
            _craftsmanLoginError.value = null
            return true
        } else {
            _craftsmanLoginError.value = "Incorrect PIN code for ${user.name}"
            return false
        }
    }

    fun logoutCraftsman() {
        _authenticatedCraftsman.value = null
        _craftsmanLoginError.value = null
    }

    // Database Streams
    val inquiries: StateFlow<List<InquiryEntity>> = repository.allInquiries
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val testimonials: StateFlow<List<TestimonialEntity>> = repository.allTestimonials
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val donations: StateFlow<List<DonationEntity>> = repository.allDonations
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val shopConfig: StateFlow<ShopConfigEntity> = repository.shopConfig
        .map { it ?: ShopConfigEntity() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ShopConfigEntity())

    val galleryStockItems: StateFlow<List<GalleryItem>> = repository.allGalleryItems
        .map { list ->
            if (list.isEmpty()) BusinessData.galleryItems
            else list.map { it.toGalleryItem() }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BusinessData.galleryItems)

    val services: StateFlow<List<ServiceItem>> = repository.allServices
        .map { list ->
            if (list.isEmpty()) BusinessData.services
            else list.map { it.toServiceItem() }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BusinessData.services)

    // Navigation State
    private val _currentSection = MutableStateFlow(AppSection.ABOUT)
    val currentSection: StateFlow<AppSection> = _currentSection.asStateFlow()

    fun setSection(section: AppSection) {
        _currentSection.value = section
    }

    // --- Appearance & Settings State with SharedPreferences Persistence ---
    private val prefs = application.getSharedPreferences("app_settings_prefs", Context.MODE_PRIVATE)

    private val _themeMode = MutableStateFlow(
        try {
            ThemeMode.valueOf(prefs.getString("pref_theme_mode", ThemeMode.SYSTEM.name) ?: ThemeMode.SYSTEM.name)
        } catch (_: Exception) {
            ThemeMode.SYSTEM
        }
    )
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    private val _colorTheme = MutableStateFlow(
        try {
            ColorTheme.valueOf(prefs.getString("pref_color_theme", ColorTheme.COMBINED.name) ?: ColorTheme.COMBINED.name)
        } catch (_: Exception) {
            ColorTheme.COMBINED
        }
    )
    val colorTheme: StateFlow<ColorTheme> = _colorTheme.asStateFlow()

    private val _hidePortal = MutableStateFlow(
        prefs.getBoolean("pref_hide_portal", false)
    )
    val hidePortal: StateFlow<Boolean> = _hidePortal.asStateFlow()

    fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
        prefs.edit().putString("pref_theme_mode", mode.name).apply()
    }

    fun setColorTheme(theme: ColorTheme) {
        _colorTheme.value = theme
        prefs.edit().putString("pref_color_theme", theme.name).apply()
    }

    private val _settingsSubTab = MutableStateFlow(SettingsSubTab.APPEARANCE)
    val settingsSubTab: StateFlow<SettingsSubTab> = _settingsSubTab.asStateFlow()

    fun setSettingsSubTab(subTab: SettingsSubTab) {
        _settingsSubTab.value = subTab
    }

    fun openPortalInSettings() {
        _settingsSubTab.value = SettingsSubTab.PORTAL
        _currentSection.value = AppSection.SETTINGS
    }

    fun resetSettingsToDefaults() {
        setThemeMode(ThemeMode.SYSTEM)
        setColorTheme(ColorTheme.COMBINED)
        setSettingsSubTab(SettingsSubTab.APPEARANCE)
    }

    // Gallery Category & Selection
    private val _selectedGalleryCategory = MutableStateFlow("All")
    val selectedGalleryCategory: StateFlow<String> = _selectedGalleryCategory.asStateFlow()

    private val _selectedGalleryItem = MutableStateFlow<GalleryItem?>(null)
    val selectedGalleryItem: StateFlow<GalleryItem?> = _selectedGalleryItem.asStateFlow()

    fun filterGallery(category: String) {
        _selectedGalleryCategory.value = category
    }

    fun selectGalleryItem(item: GalleryItem?) {
        _selectedGalleryItem.value = item
    }

    // Stock Form & Dialog (Craftsman Editing)
    private val _showStockDialog = MutableStateFlow(false)
    val showStockDialog: StateFlow<Boolean> = _showStockDialog.asStateFlow()

    private val _stockForm = MutableStateFlow(StockFormState())
    val stockForm: StateFlow<StockFormState> = _stockForm.asStateFlow()

    fun openAddNewStockDialog() {
        _stockForm.value = StockFormState(
            id = "item_${System.currentTimeMillis()}",
            isEditing = false
        )
        _showStockDialog.value = true
    }

    fun openEditStockDialog(item: GalleryItem) {
        _stockForm.value = StockFormState(
            id = item.id,
            title = item.title,
            category = item.category,
            woodType = item.woodType,
            finish = item.finish,
            dimensions = item.dimensions,
            year = item.year,
            price = item.price,
            stockStatus = item.stockStatus,
            stockQuantity = item.stockQuantity,
            description = item.description,
            clientHighlight = item.clientHighlight,
            imagePreset = when (item.imageRes) {
                com.example.R.drawable.img_gallery_furniture -> "img_gallery_furniture"
                com.example.R.drawable.img_gallery_cabinetry -> "img_gallery_cabinetry"
                com.example.R.drawable.img_gallery_restoration -> "img_gallery_restoration"
                com.example.R.drawable.img_gallery_architectural -> "img_gallery_architectural"
                com.example.R.drawable.img_gallery_slab -> "img_gallery_slab"
                else -> "img_gallery_furniture"
            },
            isEditing = true
        )
        _showStockDialog.value = true
    }

    fun closeStockDialog() {
        _showStockDialog.value = false
    }

    fun updateStockForm(
        title: String? = null,
        category: String? = null,
        woodType: String? = null,
        finish: String? = null,
        dimensions: String? = null,
        price: String? = null,
        status: String? = null,
        quantity: Int? = null,
        description: String? = null,
        clientHighlight: String? = null,
        imagePreset: String? = null
    ) {
        _stockForm.value = _stockForm.value.copy(
            title = title ?: _stockForm.value.title,
            category = category ?: _stockForm.value.category,
            woodType = woodType ?: _stockForm.value.woodType,
            finish = finish ?: _stockForm.value.finish,
            dimensions = dimensions ?: _stockForm.value.dimensions,
            price = price ?: _stockForm.value.price,
            stockStatus = status ?: _stockForm.value.stockStatus,
            stockQuantity = quantity ?: _stockForm.value.stockQuantity,
            description = description ?: _stockForm.value.description,
            clientHighlight = clientHighlight ?: _stockForm.value.clientHighlight,
            imagePreset = imagePreset ?: _stockForm.value.imagePreset
        )
    }

    fun saveStockItem() {
        val form = _stockForm.value
        val entity = GalleryItemEntity(
            id = if (form.id.isBlank()) "item_${System.currentTimeMillis()}" else form.id,
            title = form.title.ifBlank { "Curated Store Product" },
            category = form.category,
            imageResName = form.imagePreset,
            woodType = form.woodType.ifBlank { "Tech / Educational / Paper" },
            finish = form.finish.ifBlank { "Standard Edition" },
            dimensions = form.dimensions.ifBlank { "Standard Size" },
            year = form.year.ifBlank { "2024" },
            description = form.description.ifBlank { "Curated product offered at Apex Enterprises." },
            clientHighlight = form.clientHighlight.ifBlank { "Available at Apex Enterprises" },
            price = form.price.ifBlank { "$29.99" },
            stockStatus = form.stockStatus,
            stockQuantity = form.stockQuantity
        )

        viewModelScope.launch {
            if (form.isEditing) {
                repository.updateStockItem(entity)
            } else {
                repository.insertStockItem(entity)
            }
            _showStockDialog.value = false
        }
    }

    fun quickUpdateStockStatus(id: String, newStatus: String) {
        viewModelScope.launch {
            repository.updateStockStatus(id, newStatus)
        }
    }

    fun deleteStockItem(id: String) {
        viewModelScope.launch {
            repository.deleteStockItem(id)
            if (_selectedGalleryItem.value?.id == id) {
                _selectedGalleryItem.value = null
            }
        }
    }

    fun resetStockToDefaults() {
        viewModelScope.launch {
            repository.resetStockToDefaults()
        }
    }

    // Shop Config Actions
    fun updateAnnouncement(text: String, isActive: Boolean, alertType: String = "INFO") {
        val current = shopConfig.value
        viewModelScope.launch {
            repository.updateShopConfig(
                current.copy(
                    announcementText = text,
                    isAnnouncementActive = isActive,
                    announcementType = alertType
                )
            )
        }
    }

    fun updateShopDetails(
        hours: String,
        phone: String,
        email: String,
        address: String
    ) {
        val current = shopConfig.value
        viewModelScope.launch {
            repository.updateShopConfig(
                current.copy(
                    hours = hours,
                    phone = phone,
                    email = email,
                    address = address
                )
            )
        }
    }

    // Services State & Actions
    private val _selectedServiceItem = MutableStateFlow<ServiceItem?>(null)
    val selectedServiceItem: StateFlow<ServiceItem?> = _selectedServiceItem.asStateFlow()

    fun selectServiceItem(service: ServiceItem?) {
        _selectedServiceItem.value = service
    }

    fun startQuoteForService(serviceTitle: String) {
        _contactForm.value = _contactForm.value.copy(selectedService = serviceTitle)
        _selectedServiceItem.value = null
        _currentSection.value = AppSection.CONTACT
    }

    fun updateServicePriceAndLeadTime(id: String, price: String, leadTime: String) {
        viewModelScope.launch {
            repository.updateServicePriceAndLeadTime(id, price, leadTime)
        }
    }

    // Testimonials Filter & Actions
    private val _selectedRatingFilter = MutableStateFlow(0)
    val selectedRatingFilter: StateFlow<Int> = _selectedRatingFilter.asStateFlow()

    private val _selectedReviewSourceFilter = MutableStateFlow("All")
    val selectedReviewSourceFilter: StateFlow<String> = _selectedReviewSourceFilter.asStateFlow()

    private val _isSyncingGoogleReviews = MutableStateFlow(false)
    val isSyncingGoogleReviews: StateFlow<Boolean> = _isSyncingGoogleReviews.asStateFlow()

    private val _lastGoogleSyncMessage = MutableStateFlow("Synced with Google Business Profile")
    val lastGoogleSyncMessage: StateFlow<String> = _lastGoogleSyncMessage.asStateFlow()

    private val _showAddReviewDialog = MutableStateFlow(false)
    val showAddReviewDialog: StateFlow<Boolean> = _showAddReviewDialog.asStateFlow()

    private val _reviewForm = MutableStateFlow(ReviewFormState())
    val reviewForm: StateFlow<ReviewFormState> = _reviewForm.asStateFlow()

    fun filterByRating(rating: Int) {
        _selectedRatingFilter.value = rating
    }

    fun filterByReviewSource(source: String) {
        _selectedReviewSourceFilter.value = source
    }

    fun syncGoogleMapsReviews() {
        if (_isSyncingGoogleReviews.value) return
        viewModelScope.launch {
            _isSyncingGoogleReviews.value = true
            try {
                kotlinx.coroutines.delay(650) // visual feedback for network sync
                val count = repository.syncGoogleMapsReviews()
                _lastGoogleSyncMessage.value = "Synced $count Google Maps reviews just now"
            } catch (_: Exception) {
                _lastGoogleSyncMessage.value = "Google Maps reviews up to date"
            } finally {
                _isSyncingGoogleReviews.value = false
            }
        }
    }

    fun enterCraftsmanPortalFromSettings() {
        openPortalInSettings()
    }

    fun returnToSettings() {
        _currentSection.value = AppSection.SETTINGS
    }

    fun openAddReviewDialog() {
        _reviewForm.value = ReviewFormState()
        _showAddReviewDialog.value = true
    }

    fun closeAddReviewDialog() {
        _showAddReviewDialog.value = false
    }

    fun updateReviewForm(
        name: String? = null,
        location: String? = null,
        service: String? = null,
        rating: Int? = null,
        comment: String? = null
    ) {
        _reviewForm.value = _reviewForm.value.copy(
            customerName = name ?: _reviewForm.value.customerName,
            location = location ?: _reviewForm.value.location,
            serviceReceived = service ?: _reviewForm.value.serviceReceived,
            rating = rating ?: _reviewForm.value.rating,
            comment = comment ?: _reviewForm.value.comment,
            nameError = if (name != null && name.isNotBlank()) null else _reviewForm.value.nameError,
            commentError = if (comment != null && comment.isNotBlank()) null else _reviewForm.value.commentError
        )
    }

    fun submitReview() {
        val form = _reviewForm.value
        val nameBlank = form.customerName.trim().isEmpty()
        val commentBlank = form.comment.trim().isEmpty()

        if (nameBlank || commentBlank) {
            _reviewForm.value = form.copy(
                nameError = if (nameBlank) "Please enter your name" else null,
                commentError = if (commentBlank) "Please share a few words about your experience" else null
            )
            return
        }

        viewModelScope.launch {
            repository.submitTestimonial(
                TestimonialEntity(
                    customerName = form.customerName.trim(),
                    location = if (form.location.isBlank()) "Local Patron" else form.location.trim(),
                    serviceReceived = form.serviceReceived,
                    rating = form.rating,
                    comment = form.comment.trim(),
                    date = "Just now",
                    verifiedProject = true
                )
            )
            _showAddReviewDialog.value = false
        }
    }

    fun deleteTestimonial(id: Long) {
        viewModelScope.launch {
            repository.deleteTestimonial(id)
        }
    }

    // --- Donations State & Actions ---
    private val _donationForm = MutableStateFlow(DonationFormState())
    val donationForm: StateFlow<DonationFormState> = _donationForm.asStateFlow()

    fun updateDonationForm(
        donorName: String? = null,
        tier: String? = null,
        customAmount: String? = null,
        frequency: String? = null,
        note: String? = null,
        paymentMethod: String? = null,
        isAnonymous: Boolean? = null
    ) {
        _donationForm.value = _donationForm.value.copy(
            donorName = donorName ?: _donationForm.value.donorName,
            selectedTier = tier ?: _donationForm.value.selectedTier,
            customAmount = customAmount ?: _donationForm.value.customAmount,
            frequency = frequency ?: _donationForm.value.frequency,
            note = note ?: _donationForm.value.note,
            paymentMethod = paymentMethod ?: _donationForm.value.paymentMethod,
            isAnonymous = isAnonymous ?: _donationForm.value.isAnonymous,
            nameError = if (donorName != null && donorName.isNotBlank()) null else _donationForm.value.nameError,
            amountError = null
        )
    }

    fun selectDonationTier(tierTitle: String, amount: Double) {
        _donationForm.value = _donationForm.value.copy(
            selectedTier = tierTitle,
            customAmount = amount.toInt().toString(),
            amountError = null
        )
    }

    fun submitDonation() {
        val form = _donationForm.value
        val amountNum = form.customAmount.toDoubleOrNull()

        if (!form.isAnonymous && form.donorName.trim().isEmpty()) {
            _donationForm.value = form.copy(nameError = "Please enter your name or check 'Donate Anonymously'")
            return
        }

        if (amountNum == null || amountNum <= 0.0) {
            _donationForm.value = form.copy(amountError = "Please enter a valid donation amount")
            return
        }

        val refCode = "PAT-${Random.nextInt(1000, 9999)}"
        viewModelScope.launch {
            repository.submitDonation(
                DonationEntity(
                    donorName = if (form.isAnonymous) "Anonymous Community Supporter" else form.donorName.trim(),
                    amountDollars = amountNum,
                    tierTitle = form.selectedTier,
                    frequency = form.frequency,
                    note = form.note.trim(),
                    paymentMethod = form.paymentMethod,
                    isAnonymous = form.isAnonymous,
                    referenceCode = refCode
                )
            )

            _donationForm.value = DonationFormState(
                isSubmittedSuccessfully = true,
                lastReferenceCode = refCode
            )
        }
    }

    fun dismissDonationSuccess() {
        _donationForm.value = _donationForm.value.copy(isSubmittedSuccessfully = false)
    }

    fun deleteDonation(id: Long) {
        viewModelScope.launch {
            repository.deleteDonation(id)
        }
    }

    // --- Gemini Chatbot State & Actions ---
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                text = "Welcome to Apex Enterprises! I am your AI store assistant. Ask me anything about our school & office stationery, mobile accessories, toys, gift items, Xerox, printouts, scanning, lamination, binding, passport photos, or courier services!",
                isUser = false
            )
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isChatLoading = MutableStateFlow(false)
    val isChatLoading: StateFlow<Boolean> = _isChatLoading.asStateFlow()

    fun sendChatMessage(prompt: String) {
        val cleanPrompt = prompt.trim()
        if (cleanPrompt.isEmpty() || _isChatLoading.value) return

        val userMsg = ChatMessage(text = cleanPrompt, isUser = true)
        val currentList = _chatMessages.value
        _chatMessages.value = currentList + userMsg
        _isChatLoading.value = true

        viewModelScope.launch {
            try {
                val replyText = geminiService.sendMessage(currentList, cleanPrompt)
                val aiMsg = ChatMessage(text = replyText, isUser = false)
                _chatMessages.value = _chatMessages.value + aiMsg
            } catch (e: Exception) {
                val errorMsg = ChatMessage(
                    text = "I ran into a temporary issue. Please try asking again!",
                    isUser = false
                )
                _chatMessages.value = _chatMessages.value + errorMsg
            } finally {
                _isChatLoading.value = false
            }
        }
    }

    fun clearChatHistory() {
        _chatMessages.value = listOf(
            ChatMessage(
                text = "Chat history cleared. What tech gadget, toy, or stationery item can I help you find today?",
                isUser = false
            )
        )
    }

    // --- Contact Form State & Actions ---
    private val _contactForm = MutableStateFlow(ContactFormState())
    val contactForm: StateFlow<ContactFormState> = _contactForm.asStateFlow()

    fun updateContactForm(
        name: String? = null,
        email: String? = null,
        phone: String? = null,
        service: String? = null,
        timeline: String? = null,
        budget: String? = null,
        details: String? = null,
        contactPref: String? = null
    ) {
        _contactForm.value = _contactForm.value.copy(
            fullName = name ?: _contactForm.value.fullName,
            email = email ?: _contactForm.value.email,
            phone = phone ?: _contactForm.value.phone,
            selectedService = service ?: _contactForm.value.selectedService,
            timeline = timeline ?: _contactForm.value.timeline,
            budget = budget ?: _contactForm.value.budget,
            details = details ?: _contactForm.value.details,
            preferredContact = contactPref ?: _contactForm.value.preferredContact,
            nameError = if (name != null && name.isNotBlank()) null else _contactForm.value.nameError,
            emailError = if (email != null && isValidEmail(email)) null else _contactForm.value.emailError,
            detailsError = if (details != null && details.isNotBlank()) null else _contactForm.value.detailsError
        )
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
    }

    fun submitInquiry() {
        val form = _contactForm.value
        val isNameInvalid = form.fullName.trim().isEmpty()
        val isEmailInvalid = !isValidEmail(form.email)
        val isDetailsInvalid = form.details.trim().isEmpty()

        if (isNameInvalid || isEmailInvalid || isDetailsInvalid) {
            _contactForm.value = form.copy(
                nameError = if (isNameInvalid) "Please enter your full name" else null,
                emailError = if (isEmailInvalid) "Please enter a valid email address" else null,
                detailsError = if (isDetailsInvalid) "Please describe the items or services you are looking for" else null
            )
            return
        }

        val refCode = "BC-${Random.nextInt(1000, 9999)}"
        viewModelScope.launch {
            repository.submitInquiry(
                InquiryEntity(
                    referenceCode = refCode,
                    fullName = form.fullName.trim(),
                    email = form.email.trim(),
                    phone = form.phone.trim(),
                    serviceNeeded = form.selectedService,
                    timeline = form.timeline,
                    budget = form.budget,
                    details = form.details.trim(),
                    preferredContact = form.preferredContact
                )
            )

            _contactForm.value = ContactFormState(
                isSubmittedSuccessfully = true,
                lastReferenceCode = refCode
            )
        }
    }

    fun dismissSuccessMessage() {
        _contactForm.value = _contactForm.value.copy(isSubmittedSuccessfully = false)
    }

    fun updateInquiryStatus(id: Long, status: String) {
        viewModelScope.launch {
            repository.updateInquiryStatus(id, status)
        }
    }

    fun deleteInquiry(id: Long) {
        viewModelScope.launch {
            repository.deleteInquiry(id)
        }
    }
}
