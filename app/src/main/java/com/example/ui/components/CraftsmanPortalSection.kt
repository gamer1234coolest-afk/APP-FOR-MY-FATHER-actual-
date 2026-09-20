package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Announcement
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.data.InquiryEntity
import com.example.data.ShopConfigEntity
import com.example.data.TestimonialEntity
import com.example.model.GalleryItem
import com.example.model.ServiceItem
import com.example.ui.CraftsmanUser
import com.example.ui.StockFormState

@Composable
fun CraftsmanPortalSection(
    authenticatedUser: CraftsmanUser?,
    availableUsers: List<CraftsmanUser>,
    loginError: String?,
    onLogin: (CraftsmanUser, String) -> Boolean,
    onLogout: () -> Unit,
    // Stock / Inventory
    stockItems: List<GalleryItem>,
    showStockDialog: Boolean,
    stockFormState: StockFormState,
    onOpenAddStock: () -> Unit,
    onOpenEditStock: (GalleryItem) -> Unit,
    onCloseStockDialog: () -> Unit,
    onUpdateStockForm: (
        title: String?,
        category: String?,
        woodType: String?,
        finish: String?,
        dimensions: String?,
        price: String?,
        status: String?,
        quantity: Int?,
        description: String?,
        highlight: String?,
        preset: String?
    ) -> Unit,
    onSaveStockItem: () -> Unit,
    onQuickUpdateStockStatus: (id: String, status: String) -> Unit,
    onDeleteStockItem: (id: String) -> Unit,
    onResetStockDefaults: () -> Unit,
    // Shop Config
    shopConfig: ShopConfigEntity,
    onUpdateAnnouncement: (text: String, active: Boolean, type: String) -> Unit,
    onUpdateShopDetails: (hours: String, phone: String, email: String, address: String) -> Unit,
    // Services
    services: List<ServiceItem>,
    onUpdateServicePriceLead: (id: String, price: String, lead: String) -> Unit,
    // Inquiries
    inquiries: List<InquiryEntity>,
    onUpdateInquiryStatus: (id: Long, status: String) -> Unit,
    onDeleteInquiry: (id: Long) -> Unit,
    // Testimonials
    testimonials: List<TestimonialEntity>,
    onDeleteTestimonial: (id: Long) -> Unit
) {
    if (authenticatedUser == null) {
        CraftsmanLoginView(
            availableUsers = availableUsers,
            errorMessage = loginError,
            onLogin = onLogin
        )
    } else {
        CraftsmanStudioDashboard(
            user = authenticatedUser,
            onLogout = onLogout,
            stockItems = stockItems,
            onOpenAddStock = onOpenAddStock,
            onOpenEditStock = onOpenEditStock,
            onQuickUpdateStockStatus = onQuickUpdateStockStatus,
            onDeleteStockItem = onDeleteStockItem,
            onResetStockDefaults = onResetStockDefaults,
            shopConfig = shopConfig,
            onUpdateAnnouncement = onUpdateAnnouncement,
            onUpdateShopDetails = onUpdateShopDetails,
            services = services,
            onUpdateServicePriceLead = onUpdateServicePriceLead,
            inquiries = inquiries,
            onUpdateInquiryStatus = onUpdateInquiryStatus,
            onDeleteInquiry = onDeleteInquiry,
            testimonials = testimonials,
            onDeleteTestimonial = onDeleteTestimonial
        )
    }

    if (showStockDialog) {
        StockItemEditDialog(
            state = stockFormState,
            onUpdate = onUpdateStockForm,
            onSave = onSaveStockItem,
            onDismiss = onCloseStockDialog
        )
    }
}

@Composable
fun CraftsmanLoginView(
    availableUsers: List<CraftsmanUser>,
    errorMessage: String?,
    onLogin: (CraftsmanUser, String) -> Boolean
) {
    var selectedUser by remember { mutableStateOf(availableUsers.first()) }
    var enteredPin by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("craftsman_login_view"),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(72.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.AdminPanelSettings,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Staff & Management Portal",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Apex Enterprises Store Management & Operations",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("login_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Select Team Member",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    availableUsers.forEach { user ->
                        val isSelected = selectedUser.id == user.id
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected)
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
                            else
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedUser = user }
                                .then(
                                    if (isSelected) Modifier.border(
                                        1.5.dp,
                                        MaterialTheme.colorScheme.primary,
                                        RoundedCornerShape(10.dp)
                                    )
                                    else Modifier
                                )
                                .padding(12.dp)
                                .testTag("craftsman_profile_${user.id}")
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = CircleShape,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = user.name.take(1),
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = user.name,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "${user.role} • ${user.specialty}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    OutlinedTextField(
                        value = enteredPin,
                        onValueChange = { enteredPin = it },
                        label = { Text("Staff PIN Code") },
                        placeholder = { Text("Enter PIN") },
                        leadingIcon = { Icon(Icons.Default.Key, contentDescription = null) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        isError = errorMessage != null,
                        supportingText = errorMessage?.let { error ->
                            { Text(error, color = MaterialTheme.colorScheme.error) }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_craftsman_pin"),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Button(
                        onClick = { onLogin(selectedUser, enteredPin) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("btn_craftsman_login"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Lock, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Log In to Staff Portal", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun CraftsmanStudioDashboard(
    user: CraftsmanUser,
    onLogout: () -> Unit,
    // Stock
    stockItems: List<GalleryItem>,
    onOpenAddStock: () -> Unit,
    onOpenEditStock: (GalleryItem) -> Unit,
    onQuickUpdateStockStatus: (String, String) -> Unit,
    onDeleteStockItem: (String) -> Unit,
    onResetStockDefaults: () -> Unit,
    // Shop Config
    shopConfig: ShopConfigEntity,
    onUpdateAnnouncement: (String, Boolean, String) -> Unit,
    onUpdateShopDetails: (String, String, String, String) -> Unit,
    // Services
    services: List<ServiceItem>,
    onUpdateServicePriceLead: (String, String, String) -> Unit,
    // Inquiries
    inquiries: List<InquiryEntity>,
    onUpdateInquiryStatus: (Long, String) -> Unit,
    onDeleteInquiry: (Long) -> Unit,
    // Testimonials
    testimonials: List<TestimonialEntity>,
    onDeleteTestimonial: (Long) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Stock & Gallery", "Announcements", "Services Pricing", "Client Inquiries", "Reviews")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("craftsman_dashboard")
    ) {
        // Top Craftsman Header Bar
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = user.name.take(1),
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = user.name,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = user.role,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Button(
                    onClick = onLogout,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("btn_craftsman_logout")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Logout,
                        contentDescription = "Log Out",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Sign Out",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }

        // Subtabs
        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            edgePadding = 12.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title, fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal) },
                    modifier = Modifier.testTag("subtab_$index")
                )
            }
        }

        // Tab contents
        when (selectedTab) {
            0 -> StockManagerTab(
                items = stockItems,
                onAdd = onOpenAddStock,
                onEdit = onOpenEditStock,
                onUpdateStatus = onQuickUpdateStockStatus,
                onDelete = onDeleteStockItem,
                onReset = onResetStockDefaults
            )
            1 -> ShopAnnouncementTab(
                config = shopConfig,
                onUpdateAnnouncement = onUpdateAnnouncement,
                onUpdateShopDetails = onUpdateShopDetails
            )
            2 -> ServicesPricingTab(
                services = services,
                onUpdate = onUpdateServicePriceLead
            )
            3 -> InquiriesManagerTab(
                inquiries = inquiries,
                onUpdateStatus = onUpdateInquiryStatus,
                onDelete = onDeleteInquiry
            )
            4 -> TestimonialsModerationTab(
                testimonials = testimonials,
                onDelete = onDeleteTestimonial
            )
        }
    }
}

@Composable
fun StockManagerTab(
    items: List<GalleryItem>,
    onAdd: () -> Unit,
    onEdit: (GalleryItem) -> Unit,
    onUpdateStatus: (String, String) -> Unit,
    onDelete: (String) -> Unit,
    onReset: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("stock_manager_tab"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Store Inventory & Products",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${items.size} products currently in catalog",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                Button(
                    onClick = onAdd,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("btn_add_new_stock")
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add Product")
                }
            }
        }

        items(items) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("stock_item_card_${item.id}"),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${item.category} • ${item.woodType}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Price: ${item.price} • Dimensions: ${item.dimensions}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { onEdit(item) },
                                modifier = Modifier.testTag("btn_edit_stock_${item.id}")
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
                            }
                            IconButton(
                                onClick = { onDelete(item.id) },
                                modifier = Modifier.testTag("btn_delete_stock_${item.id}")
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Stock status selector
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "Status: ",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                        listOf("In Stock", "Made to Order", "Sold").forEach { status ->
                            FilterChip(
                                selected = item.stockStatus == status,
                                onClick = { onUpdateStatus(item.id, status) },
                                label = { Text(status, style = MaterialTheme.typography.labelSmall) }
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(
                onClick = onReset,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("btn_reset_catalog"),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Restore Workshop Default Pieces")
            }
        }
    }
}

@Composable
fun ShopAnnouncementTab(
    config: ShopConfigEntity,
    onUpdateAnnouncement: (String, Boolean, String) -> Unit,
    onUpdateShopDetails: (String, String, String, String) -> Unit
) {
    var announcementText by remember(config.announcementText) { mutableStateOf(config.announcementText) }
    var isActive by remember(config.isAnnouncementActive) { mutableStateOf(config.isAnnouncementActive) }

    var hours by remember(config.hours) { mutableStateOf(config.hours) }
    var phone by remember(config.phone) { mutableStateOf(config.phone) }
    var email by remember(config.email) { mutableStateOf(config.email) }
    var address by remember(config.address) { mutableStateOf(config.address) }

    var isSavedConfirmation by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("shop_announcement_tab"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Top Shop Announcement Banner",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Displayed prominently to clients at the top of the app",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                        Switch(
                            checked = isActive,
                            onCheckedChange = { isActive = it },
                            modifier = Modifier.testTag("switch_announcement_active")
                        )
                    }

                    OutlinedTextField(
                        value = announcementText,
                        onValueChange = { announcementText = it },
                        label = { Text("Banner Message") },
                        minLines = 2,
                        maxLines = 4,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_announcement_text"),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Button(
                        onClick = {
                            onUpdateAnnouncement(announcementText, isActive, "INFO")
                            isSavedConfirmation = true
                        },
                        modifier = Modifier.align(Alignment.End),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Save Announcement")
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Workshop Operations & Contact Info",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = hours,
                        onValueChange = { hours = it },
                        label = { Text("Shop Operating Hours") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Direct Telephone") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Workshop Email") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Shop Physical Address") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Button(
                        onClick = {
                            onUpdateShopDetails(hours, phone, email, address)
                            isSavedConfirmation = true
                        },
                        modifier = Modifier.align(Alignment.End),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Update Shop Details")
                    }
                }
            }
        }
    }
}

@Composable
fun ServicesPricingTab(
    services: List<ServiceItem>,
    onUpdate: (id: String, price: String, lead: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("services_pricing_tab"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Services Pricing & Lead Time Editor",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Updates the customer-facing quotes and service catalog immediately.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )
        }

        items(services) { service ->
            var price by remember(service.startingPrice) { mutableStateOf(service.startingPrice) }
            var lead by remember(service.typicalLeadTime) { mutableStateOf(service.typicalLeadTime) }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("service_edit_card_${service.id}"),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = service.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = service.summary,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = price,
                            onValueChange = { price = it },
                            label = { Text("Starting Price") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        OutlinedTextField(
                            value = lead,
                            onValueChange = { lead = it },
                            label = { Text("Typical Lead Time") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = { onUpdate(service.id, price, lead) },
                        modifier = Modifier.align(Alignment.End),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Save Price & Lead")
                    }
                }
            }
        }
    }
}

@Composable
fun InquiriesManagerTab(
    inquiries: List<InquiryEntity>,
    onUpdateStatus: (Long, String) -> Unit,
    onDelete: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("inquiries_manager_tab"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Client Inquiries & Quote Requests (${inquiries.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        if (inquiries.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No pending customer inquiries in queue.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            items(inquiries) { inq ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("inquiry_manage_card_${inq.id}"),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column {
                                Text(
                                    text = inq.fullName,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Ref: #${inq.referenceCode} • ${inq.serviceNeeded}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "Email: ${inq.email} • Phone: ${inq.phone.ifBlank { "N/A" }}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    text = "Budget: ${inq.budget} • Timeline: ${inq.timeline}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }

                            IconButton(
                                onClick = { onDelete(inq.id) }
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Project notes: “${inq.details}”",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Status Chips
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text("Status:", style = MaterialTheme.typography.labelSmall)
                            listOf("NEW", "CONTACTED", "IN WORKSHOP", "COMPLETED").forEach { st ->
                                FilterChip(
                                    selected = inq.status == st,
                                    onClick = { onUpdateStatus(inq.id, st) },
                                    label = { Text(st, style = MaterialTheme.typography.labelSmall) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TestimonialsModerationTab(
    testimonials: List<TestimonialEntity>,
    onDelete: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("testimonials_moderation_tab"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Client Testimonials Moderation (${testimonials.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(testimonials) { test ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = test.customerName,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${test.serviceReceived} • ${test.location} • ${test.rating} Stars",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        IconButton(onClick = { onDelete(test.id) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete Review", tint = MaterialTheme.colorScheme.error)
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "“${test.comment}”",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun StockItemEditDialog(
    state: StockFormState,
    onUpdate: (
        title: String?,
        category: String?,
        woodType: String?,
        finish: String?,
        dimensions: String?,
        price: String?,
        status: String?,
        quantity: Int?,
        description: String?,
        highlight: String?,
        preset: String?
    ) -> Unit,
    onSave: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (state.isEditing) "Edit Catalog Product" else "Add New Product",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = state.title,
                        onValueChange = { onUpdate(it, null, null, null, null, null, null, null, null, null, null) },
                        label = { Text("Product Title") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    )
                }
                item {
                    OutlinedTextField(
                        value = state.category,
                        onValueChange = { onUpdate(null, it, null, null, null, null, null, null, null, null, null) },
                        label = { Text("Category (Tech, Toys, Stationery)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    )
                }
                item {
                    OutlinedTextField(
                        value = state.woodType,
                        onValueChange = { onUpdate(null, null, it, null, null, null, null, null, null, null, null) },
                        label = { Text("Brand / Series / Material") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    )
                }
                item {
                    OutlinedTextField(
                        value = state.finish,
                        onValueChange = { onUpdate(null, null, null, it, null, null, null, null, null, null, null) },
                        label = { Text("Model / Variant / Color") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    )
                }
                item {
                    OutlinedTextField(
                        value = state.dimensions,
                        onValueChange = { onUpdate(null, null, null, null, it, null, null, null, null, null, null) },
                        label = { Text("Specs / Dimensions / Age") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    )
                }
                item {
                    OutlinedTextField(
                        value = state.price,
                        onValueChange = { onUpdate(null, null, null, null, null, it, null, null, null, null, null) },
                        label = { Text("Price ($)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    )
                }
                item {
                    OutlinedTextField(
                        value = state.description,
                        onValueChange = { onUpdate(null, null, null, null, null, null, null, null, it, null, null) },
                        label = { Text("Description & Key Features") },
                        minLines = 2,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onSave,
                modifier = Modifier.testTag("btn_save_stock_dialog")
            ) {
                Text("Save Product")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
