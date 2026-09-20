package com.example

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.BusinessData
import com.example.ui.AppSection
import com.example.ui.BusinessViewModel
import com.example.ui.components.AboutSection
import com.example.ui.components.ContactSection
import com.example.ui.components.CraftsmanPortalSection
import com.example.ui.components.DonationSection
import com.example.ui.components.GallerySection
import com.example.ui.components.ServicesSection
import com.example.ui.components.SettingsSection
import com.example.ui.components.TestimonialsSection
import com.example.ui.components.WoodcraftAiSection
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private val viewModel: BusinessViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
            val colorTheme by viewModel.colorTheme.collectAsStateWithLifecycle()
            MyApplicationTheme(
                themeMode = themeMode,
                colorTheme = colorTheme
            ) {
                MainAppScreen(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen(viewModel: BusinessViewModel) {
    val context = LocalContext.current
    val currentSection by viewModel.currentSection.collectAsStateWithLifecycle()
    val galleryStockItems by viewModel.galleryStockItems.collectAsStateWithLifecycle()
    val galleryCategory by viewModel.selectedGalleryCategory.collectAsStateWithLifecycle()
    val selectedGalleryItem by viewModel.selectedGalleryItem.collectAsStateWithLifecycle()
    val services by viewModel.services.collectAsStateWithLifecycle()
    val testimonialFilter by viewModel.selectedRatingFilter.collectAsStateWithLifecycle()
    val showAddReviewDialog by viewModel.showAddReviewDialog.collectAsStateWithLifecycle()
    val reviewForm by viewModel.reviewForm.collectAsStateWithLifecycle()
    val contactForm by viewModel.contactForm.collectAsStateWithLifecycle()
    val testimonials by viewModel.testimonials.collectAsStateWithLifecycle()
    val inquiries by viewModel.inquiries.collectAsStateWithLifecycle()
    val donations by viewModel.donations.collectAsStateWithLifecycle()
    val donationForm by viewModel.donationForm.collectAsStateWithLifecycle()
    val chatMessages by viewModel.chatMessages.collectAsStateWithLifecycle()
    val isChatLoading by viewModel.isChatLoading.collectAsStateWithLifecycle()
    val authenticatedCraftsman by viewModel.authenticatedCraftsman.collectAsStateWithLifecycle()
    val craftsmanLoginError by viewModel.craftsmanLoginError.collectAsStateWithLifecycle()
    val showStockDialog by viewModel.showStockDialog.collectAsStateWithLifecycle()
    val stockFormState by viewModel.stockForm.collectAsStateWithLifecycle()
    val shopConfig by viewModel.shopConfig.collectAsStateWithLifecycle()
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
    val colorTheme by viewModel.colorTheme.collectAsStateWithLifecycle()
    val settingsSubTab by viewModel.settingsSubTab.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("main_scaffold"),
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.size(36.dp),
                                color = Color(0xFFFDB813)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.img_app_icon),
                                    contentDescription = "Apex Enterprises Logo",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Apex Enterprises",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    },
                    actions = {
                        // Quick shortcut to Apex AI
                        IconButton(
                            onClick = { viewModel.setSection(AppSection.CHATBOT) },
                            modifier = Modifier.testTag("topbar_btn_ai")
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "Apex AI Chat",
                                tint = if (currentSection == AppSection.CHATBOT)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // Quick shortcut to Settings & Staff Portal
                        IconButton(
                            onClick = { viewModel.setSection(AppSection.SETTINGS) },
                            modifier = Modifier.testTag("topbar_btn_settings")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "App Settings & Staff Portal",
                                tint = if (currentSection == AppSection.SETTINGS)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // Call Store
                        IconButton(
                            onClick = {
                                val telUri = "tel:${shopConfig.phone.ifBlank { BusinessData.phone }.replace("[^0-9]".toRegex(), "")}"
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse(telUri))
                                context.startActivity(intent)
                            },
                            modifier = Modifier.testTag("topbar_btn_call")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Call,
                                contentDescription = "Call Store",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface
                    )
                )

                // Top Announcement Banner (Craftsman Editable)
                if (shopConfig.isAnnouncementActive && shopConfig.announcementText.isNotBlank()) {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("shop_announcement_banner")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Campaign,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = shopConfig.announcementText,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

                // Full Scrollable Section Tabs for 100% Discoverability
                val visibleSections = AppSection.entries
                val currentTabIndex = visibleSections.indexOf(currentSection).let { if (it >= 0) it else 0 }

                ScrollableTabRow(
                    selectedTabIndex = currentTabIndex,
                    edgePadding = 12.dp,
                    containerColor = MaterialTheme.colorScheme.surface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("main_section_tabs")
                ) {
                    visibleSections.forEach { section ->
                        val isSelected = currentSection == section
                        Tab(
                            selected = isSelected,
                            onClick = { viewModel.setSection(section) },
                            text = {
                                Text(
                                    text = section.label,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            },
                            icon = {
                                val icon = when (section) {
                                    AppSection.ABOUT -> Icons.Default.Person
                                    AppSection.GALLERY -> Icons.Default.Collections
                                    AppSection.SERVICES -> Icons.Default.Build
                                    AppSection.DONATION -> Icons.Default.VolunteerActivism
                                    AppSection.CHATBOT -> Icons.Default.AutoAwesome
                                    AppSection.TESTIMONIALS -> Icons.Default.Star
                                    AppSection.CONTACT -> Icons.Default.Mail
                                    AppSection.SETTINGS -> Icons.Default.Settings
                                }
                                Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp))
                            },
                            modifier = Modifier.testTag("tab_${section.name.lowercase()}")
                        )
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp,
                modifier = Modifier.testTag("main_bottom_nav")
            ) {
                // Primary Core Tabs
                NavigationBarItem(
                    selected = currentSection == AppSection.GALLERY,
                    onClick = { viewModel.setSection(AppSection.GALLERY) },
                    icon = { Icon(Icons.Default.Collections, contentDescription = "Gallery") },
                    label = { Text("Gallery") },
                    modifier = Modifier.testTag("nav_item_gallery"),
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )

                NavigationBarItem(
                    selected = currentSection == AppSection.SERVICES,
                    onClick = { viewModel.setSection(AppSection.SERVICES) },
                    icon = { Icon(Icons.Default.Build, contentDescription = "Services") },
                    label = { Text("Services") },
                    modifier = Modifier.testTag("nav_item_services"),
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )

                NavigationBarItem(
                    selected = currentSection == AppSection.CHATBOT,
                    onClick = { viewModel.setSection(AppSection.CHATBOT) },
                    icon = { Icon(Icons.Default.AutoAwesome, contentDescription = "Apex AI") },
                    label = { Text("AI") },
                    modifier = Modifier.testTag("nav_item_chatbot"),
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )

                NavigationBarItem(
                    selected = currentSection == AppSection.SETTINGS,
                    onClick = { viewModel.setSection(AppSection.SETTINGS) },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    modifier = Modifier.testTag("nav_item_settings"),
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Crossfade(
                targetState = currentSection,
                animationSpec = tween(250),
                label = "AppSectionCrossfade"
            ) { section ->
                when (section) {
                    AppSection.ABOUT -> {
                        AboutSection(
                            onNavigateToGallery = { viewModel.setSection(AppSection.GALLERY) },
                            onNavigateToContact = { viewModel.setSection(AppSection.CONTACT) }
                        )
                    }

                    AppSection.GALLERY -> {
                        GallerySection(
                            items = galleryStockItems,
                            selectedCategory = galleryCategory,
                            onSelectCategory = { viewModel.filterGallery(it) },
                            selectedItem = selectedGalleryItem,
                            onSelectItem = { viewModel.selectGalleryItem(it) },
                            onCommissionSimilar = { serviceName ->
                                viewModel.startQuoteForService(serviceName)
                            }
                        )
                    }

                    AppSection.SERVICES -> {
                        ServicesSection(
                            services = services,
                            onRequestQuoteForService = { serviceTitle ->
                                viewModel.startQuoteForService(serviceTitle)
                            }
                        )
                    }

                    AppSection.DONATION -> {
                        DonationSection(
                            formState = donationForm,
                            donations = donations,
                            onUpdateForm = { name, tier, amount, freq, note, method, anon ->
                                viewModel.updateDonationForm(
                                    donorName = name,
                                    tier = tier,
                                    customAmount = amount,
                                    frequency = freq,
                                    note = note,
                                    paymentMethod = method,
                                    isAnonymous = anon
                                )
                            },
                            onSelectTier = { title, amt ->
                                viewModel.selectDonationTier(title, amt)
                            },
                            onSubmit = { viewModel.submitDonation() },
                            onDismissSuccess = { viewModel.dismissDonationSuccess() }
                        )
                    }

                    AppSection.CHATBOT -> {
                        WoodcraftAiSection(
                            messages = chatMessages,
                            isLoading = isChatLoading,
                            onSendMessage = { viewModel.sendChatMessage(it) },
                            onClearChat = { viewModel.clearChatHistory() }
                        )
                    }

                    AppSection.TESTIMONIALS -> {
                        TestimonialsSection(
                            testimonials = testimonials,
                            selectedRatingFilter = testimonialFilter,
                            onFilterByRating = { viewModel.filterByRating(it) },
                            showAddDialog = showAddReviewDialog,
                            reviewForm = reviewForm,
                            onOpenAddDialog = { viewModel.openAddReviewDialog() },
                            onCloseAddDialog = { viewModel.closeAddReviewDialog() },
                            onUpdateReviewForm = { name, loc, srv, rating, comment ->
                                viewModel.updateReviewForm(name, loc, srv, rating, comment)
                            },
                            onSubmitReview = { viewModel.submitReview() }
                        )
                    }

                    AppSection.CONTACT -> {
                        ContactSection(
                            formState = contactForm,
                            inquiries = inquiries,
                            onUpdateForm = { name, email, phone, srv, timeline, budget, details, pref ->
                                viewModel.updateContactForm(
                                    name = name,
                                    email = email,
                                    phone = phone,
                                    service = srv,
                                    timeline = timeline,
                                    budget = budget,
                                    details = details,
                                    contactPref = pref
                                )
                            },
                            onSubmit = { viewModel.submitInquiry() },
                            onDismissSuccess = { viewModel.dismissSuccessMessage() },
                            onDeleteInquiry = { viewModel.deleteInquiry(it) }
                        )
                    }

                    AppSection.SETTINGS -> {
                        SettingsSection(
                            themeMode = themeMode,
                            colorTheme = colorTheme,
                            selectedSubTab = settingsSubTab,
                            onSelectSubTab = { viewModel.setSettingsSubTab(it) },
                            onSelectThemeMode = { viewModel.setThemeMode(it) },
                            onSelectColorTheme = { viewModel.setColorTheme(it) },
                            onResetDefaults = { viewModel.resetSettingsToDefaults() },
                            portalContent = {
                                CraftsmanPortalSection(
                                    authenticatedUser = authenticatedCraftsman,
                                    availableUsers = viewModel.availableCraftsmen,
                                    loginError = craftsmanLoginError,
                                    onLogin = { user, pin -> viewModel.loginCraftsman(user, pin) },
                                    onLogout = { viewModel.logoutCraftsman() },
                                    stockItems = galleryStockItems,
                                    showStockDialog = showStockDialog,
                                    stockFormState = stockFormState,
                                    onOpenAddStock = { viewModel.openAddNewStockDialog() },
                                    onOpenEditStock = { viewModel.openEditStockDialog(it) },
                                    onCloseStockDialog = { viewModel.closeStockDialog() },
                                    onUpdateStockForm = { title, cat, wood, fin, dim, price, stat, qty, desc, hl, preset ->
                                        viewModel.updateStockForm(
                                            title = title,
                                            category = cat,
                                            woodType = wood,
                                            finish = fin,
                                            dimensions = dim,
                                            price = price,
                                            status = stat,
                                            quantity = qty,
                                            description = desc,
                                            clientHighlight = hl,
                                            imagePreset = preset
                                        )
                                    },
                                    onSaveStockItem = { viewModel.saveStockItem() },
                                    onQuickUpdateStockStatus = { id, status -> viewModel.quickUpdateStockStatus(id, status) },
                                    onDeleteStockItem = { viewModel.deleteStockItem(it) },
                                    onResetStockDefaults = { viewModel.resetStockToDefaults() },
                                    shopConfig = shopConfig,
                                    onUpdateAnnouncement = { text, active, type -> viewModel.updateAnnouncement(text, active, type) },
                                    onUpdateShopDetails = { hours, phone, email, address -> viewModel.updateShopDetails(hours, phone, email, address) },
                                    services = services,
                                    onUpdateServicePriceLead = { id, price, lead -> viewModel.updateServicePriceAndLeadTime(id, price, lead) },
                                    inquiries = inquiries,
                                    onUpdateInquiryStatus = { id, status -> viewModel.updateInquiryStatus(id, status) },
                                    onDeleteInquiry = { viewModel.deleteInquiry(it) },
                                    testimonials = testimonials,
                                    onDeleteTestimonial = { viewModel.deleteTestimonial(it) }
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
