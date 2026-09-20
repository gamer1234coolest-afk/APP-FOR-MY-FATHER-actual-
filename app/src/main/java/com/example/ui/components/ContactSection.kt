package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.InquiryEntity
import com.example.model.BusinessData
import com.example.ui.ContactFormState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ContactSection(
    formState: ContactFormState,
    inquiries: List<InquiryEntity>,
    onUpdateForm: (
        name: String?,
        email: String?,
        phone: String?,
        service: String?,
        timeline: String?,
        budget: String?,
        details: String?,
        contactPref: String?
    ) -> Unit,
    onSubmit: () -> Unit,
    onDismissSuccess: () -> Unit,
    onDeleteInquiry: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val serviceOptions = listOf(
        "Device Setup & Screen Protection",
        "Curated Gift Bundle / Gift Wrapping",
        "In-House Laser Engraving & Personalization",
        "Classroom & Bulk Educational Supplies",
        "Product Availability / Item Hold",
        "General Inquiry"
    )

    val timelineOptions = listOf(
        "Urgent (Today / Tomorrow)",
        "Within 1 Week",
        "Within 2–4 Weeks",
        "Flexible / Planning Phase"
    )

    val budgetOptions = listOf(
        "Under $50",
        "$50 – $150",
        "$150 – $300",
        "$300+",
        "Flexible / Browsing"
    )

    var expandedServiceMenu by remember { mutableStateOf(false) }
    var expandedTimelineMenu by remember { mutableStateOf(false) }
    var expandedBudgetMenu by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("contact_section_list"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Direct Contact Quick Actions Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("direct_contact_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Get in Touch with Our Team",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Have questions about stock, need a recommendation, or looking for bulk classroom orders? Contact us anytime.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_DIAL).apply {
                                    data = Uri.parse("tel:${BusinessData.phone.replace("[^0-9]".toRegex(), "")}")
                                }
                                context.startActivity(intent)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("btn_call_direct"),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Icon(imageVector = Icons.Default.Call, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Call Store", fontSize = 13.sp)
                        }

                        OutlinedButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_SENDTO).apply {
                                    data = Uri.parse("mailto:${BusinessData.email}?subject=Apex Enterprises Inquiry")
                                }
                                context.startActivity(intent)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("btn_email_direct")
                        ) {
                            Icon(imageVector = Icons.Default.Email, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Email Shop", fontSize = 13.sp)
                        }
                    }
                }
            }
        }

        // Success Card Banner
        if (formState.isSubmittedSuccessfully) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("inquiry_success_card"),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.tertiary,
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Inquiry Sent to Store Team!",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                            }
                            IconButton(
                                onClick = onDismissSuccess,
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Dismiss",
                                    tint = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Thank you! Our store team will check inventory and get back to you promptly. Your reference is:",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Text(
                                text = "REF: ${formState.lastReferenceCode}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "We typically respond within 24 hours. A copy has also been saved to your local inquiry history below.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        // Main Contact & Quote Request Form
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("quote_request_form_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Send an Inquiry or Order Request",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Tell us what products, services, or custom gifts you are looking for.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )

                    // Full Name
                    OutlinedTextField(
                        value = formState.fullName,
                        onValueChange = { onUpdateForm(it, null, null, null, null, null, null, null) },
                        label = { Text("Your Full Name *") },
                        isError = formState.nameError != null,
                        supportingText = formState.nameError?.let { { Text(it) } },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_full_name"),
                        singleLine = true
                    )

                    // Email Address
                    OutlinedTextField(
                        value = formState.email,
                        onValueChange = { onUpdateForm(null, it, null, null, null, null, null, null) },
                        label = { Text("Email Address *") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        isError = formState.emailError != null,
                        supportingText = formState.emailError?.let { { Text(it) } },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_email"),
                        singleLine = true
                    )

                    // Phone Number
                    OutlinedTextField(
                        value = formState.phone,
                        onValueChange = { onUpdateForm(null, null, it, null, null, null, null, null) },
                        label = { Text("Phone Number (Optional)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_phone"),
                        singleLine = true
                    )

                    // Service Needed Dropdown
                    ExposedDropdownMenuBox(
                        expanded = expandedServiceMenu,
                        onExpandedChange = { expandedServiceMenu = !expandedServiceMenu },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = formState.selectedService,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Service Needed *") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedServiceMenu) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .testTag("select_service_needed")
                        )
                        ExposedDropdownMenu(
                            expanded = expandedServiceMenu,
                            onDismissRequest = { expandedServiceMenu = false }
                        ) {
                            serviceOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        onUpdateForm(null, null, null, option, null, null, null, null)
                                        expandedServiceMenu = false
                                    }
                                )
                            }
                        }
                    }

                    // Desired Timeline Dropdown
                    ExposedDropdownMenuBox(
                        expanded = expandedTimelineMenu,
                        onExpandedChange = { expandedTimelineMenu = !expandedTimelineMenu },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = formState.timeline,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Desired Timeline") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTimelineMenu) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .testTag("select_timeline")
                        )
                        ExposedDropdownMenu(
                            expanded = expandedTimelineMenu,
                            onDismissRequest = { expandedTimelineMenu = false }
                        ) {
                            timelineOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        onUpdateForm(null, null, null, null, option, null, null, null)
                                        expandedTimelineMenu = false
                                    }
                                )
                            }
                        }
                    }

                    // Estimated Budget Dropdown
                    ExposedDropdownMenuBox(
                        expanded = expandedBudgetMenu,
                        onExpandedChange = { expandedBudgetMenu = !expandedBudgetMenu },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = formState.budget,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Estimated Budget") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedBudgetMenu) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .testTag("select_budget")
                        )
                        ExposedDropdownMenu(
                            expanded = expandedBudgetMenu,
                            onDismissRequest = { expandedBudgetMenu = false }
                        ) {
                            budgetOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        onUpdateForm(null, null, null, null, null, option, null, null)
                                        expandedBudgetMenu = false
                                    }
                                )
                            }
                        }
                    }

                    // Project Details & Notes
                    OutlinedTextField(
                        value = formState.details,
                        onValueChange = { onUpdateForm(null, null, null, null, null, null, it, null) },
                        label = { Text("Inquiry Details & Requests *") },
                        placeholder = { Text("e.g., Inquiring about Keyflow keyboard switch types, laser engraving on a fountain pen, or classroom robotics") },
                        isError = formState.detailsError != null,
                        supportingText = formState.detailsError?.let { { Text(it) } },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_details"),
                        minLines = 3,
                        maxLines = 6
                    )

                    // Preferred Contact Method
                    Column {
                        Text(
                            text = "Preferred Contact Method:",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("Phone", "Email").forEach { method ->
                                FilterChip(
                                    selected = formState.preferredContact == method,
                                    onClick = { onUpdateForm(null, null, null, null, null, null, null, method) },
                                    label = { Text(method) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                    modifier = Modifier.testTag("chip_contact_$method")
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Submit Button
                    Button(
                        onClick = onSubmit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("btn_submit_quote_inquiry"),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Send Inquiry to Apex Enterprises")
                    }
                }
            }
        }

        // History of Submitted Inquiries
        if (inquiries.isNotEmpty()) {
            item {
                Text(
                    text = "Your Recent Inquiries (${inquiries.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            items(inquiries, key = { it.id }) { inquiry ->
                InquiryHistoryCard(
                    inquiry = inquiry,
                    onDelete = { onDeleteInquiry(inquiry.id) }
                )
            }
        }
    }
}

@Composable
private fun InquiryHistoryCard(
    inquiry: InquiryEntity,
    onDelete: () -> Unit
) {
    val dateFormat = remember { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) }
    val formattedDate = remember(inquiry.timestamp) { dateFormat.format(Date(inquiry.timestamp)) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("inquiry_history_${inquiry.referenceCode}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = inquiry.referenceCode,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = formattedDate,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Inquiry",
                            tint = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = inquiry.serviceNeeded,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = inquiry.details,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Budget: ${inquiry.budget}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = "Timeline: ${inquiry.timeline}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}
