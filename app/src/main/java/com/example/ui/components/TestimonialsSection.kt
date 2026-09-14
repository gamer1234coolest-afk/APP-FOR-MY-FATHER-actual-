package com.example.ui.components

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TestimonialEntity
import com.example.ui.ReviewFormState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TestimonialsSection(
    testimonials: List<TestimonialEntity>,
    selectedRatingFilter: Int,
    onFilterByRating: (Int) -> Unit,
    showAddDialog: Boolean,
    reviewForm: ReviewFormState,
    onOpenAddDialog: () -> Unit,
    onCloseAddDialog: () -> Unit,
    onUpdateReviewForm: (name: String?, location: String?, service: String?, rating: Int?, comment: String?) -> Unit,
    onSubmitReview: () -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredTestimonials = remember(testimonials, selectedRatingFilter) {
        if (selectedRatingFilter == 0) {
            testimonials
        } else {
            testimonials.filter { it.rating == selectedRatingFilter }
        }
    }

    val averageRating = remember(testimonials) {
        if (testimonials.isEmpty()) 5.0 else testimonials.map { it.rating }.average()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("testimonials_list"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Testimonials Header Summary Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("testimonials_summary_card"),
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Client Testimonials",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Reviews from our community of tech, STEM, and stationery enthusiasts",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Button(
                            onClick = onOpenAddDialog,
                            modifier = Modifier.testTag("btn_open_write_review"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.RateReview,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Write Review", fontSize = 13.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = String.format("%.1f", averageRating),
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            RatingStarsRow(rating = averageRating.toInt(), starSize = 20)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Based on ${testimonials.size} client reviews • 100% Recommended",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }
        }

        // Filter chips row
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("rating_filter_row"),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filter:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.outline
                )

                FilterChip(
                    selected = selectedRatingFilter == 0,
                    onClick = { onFilterByRating(0) },
                    label = { Text("All (${testimonials.size})") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )

                FilterChip(
                    selected = selectedRatingFilter == 5,
                    onClick = { onFilterByRating(5) },
                    label = { Text("5 Stars ★") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )

                FilterChip(
                    selected = selectedRatingFilter == 4,
                    onClick = { onFilterByRating(4) },
                    label = { Text("4 Stars ★") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        }

        // Testimonial Cards
        if (filteredTestimonials.isEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No reviews found for this filter.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        } else {
            items(filteredTestimonials, key = { it.id }) { testimonial ->
                TestimonialCard(testimonial = testimonial)
            }
        }
    }

    if (showAddDialog) {
        AddReviewDialog(
            form = reviewForm,
            onDismiss = onCloseAddDialog,
            onUpdateForm = onUpdateReviewForm,
            onSubmit = onSubmitReview
        )
    }
}

@Composable
fun TestimonialCard(testimonial: TestimonialEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("testimonial_card_${testimonial.id}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            // Header: Name, location, verified badge & date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = testimonial.customerName.firstOrNull()?.toString() ?: "P",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = testimonial.customerName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            if (testimonial.verifiedProject) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = "Verified Client",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                        if (testimonial.location.isNotBlank()) {
                            Text(
                                text = testimonial.location,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }

                Text(
                    text = testimonial.date,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Rating & Service Tag Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RatingStarsRow(rating = testimonial.rating, starSize = 16)

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = testimonial.serviceReceived,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Testimonial quote text
            Text(
                text = "\"${testimonial.comment}\"",
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Italic,
                lineHeight = 21.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun RatingStarsRow(rating: Int, starSize: Int = 18, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..5) {
            Icon(
                imageVector = if (i <= rating) Icons.Default.Star else Icons.Outlined.StarBorder,
                contentDescription = null,
                tint = if (i <= rating) Color(0xFFE68A00) else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                modifier = Modifier.size(starSize.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReviewDialog(
    form: ReviewFormState,
    onDismiss: () -> Unit,
    onUpdateForm: (name: String?, location: String?, service: String?, rating: Int?, comment: String?) -> Unit,
    onSubmit: () -> Unit
) {
    val serviceOptions = listOf(
        "Device Setup & Screen Protection",
        "Curated Gift Bundle / Gift Wrapping",
        "In-House Laser Engraving & Personalization",
        "Classroom & Bulk Educational Supplies",
        "In-Store Purchase & Customer Support"
    )

    var expandedServiceMenu by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Share Your Experience",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("add_review_dialog_form"),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "We love hearing how your gear, toys, or stationery are serving you.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )

                // Customer Name
                OutlinedTextField(
                    value = form.customerName,
                    onValueChange = { onUpdateForm(it, null, null, null, null) },
                    label = { Text("Your Full Name *") },
                    isError = form.nameError != null,
                    supportingText = form.nameError?.let { { Text(it) } },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("review_input_name"),
                    singleLine = true
                )

                // Location / City
                OutlinedTextField(
                    value = form.location,
                    onValueChange = { onUpdateForm(null, it, null, null, null) },
                    label = { Text("Your City / Neighborhood (Optional)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("review_input_location"),
                    singleLine = true
                )

                // Service Received selector
                ExposedDropdownMenuBox(
                    expanded = expandedServiceMenu,
                    onExpandedChange = { expandedServiceMenu = !expandedServiceMenu },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = form.serviceReceived,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Service Received") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedServiceMenu) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .testTag("review_service_selector")
                    )
                    ExposedDropdownMenu(
                        expanded = expandedServiceMenu,
                        onDismissRequest = { expandedServiceMenu = false }
                    ) {
                        serviceOptions.forEach { service ->
                            DropdownMenuItem(
                                text = { Text(service) },
                                onClick = {
                                    onUpdateForm(null, null, service, null, null)
                                    expandedServiceMenu = false
                                }
                            )
                        }
                    }
                }

                // Interactive Star Rating Selector
                Column {
                    Text(
                        text = "Star Rating *",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (starIndex in 1..5) {
                            Icon(
                                imageVector = if (starIndex <= form.rating) Icons.Default.Star else Icons.Outlined.StarBorder,
                                contentDescription = "Rate $starIndex star",
                                tint = if (starIndex <= form.rating) Color(0xFFE68A00) else MaterialTheme.colorScheme.outline,
                                modifier = Modifier
                                    .size(32.dp)
                                    .clickable { onUpdateForm(null, null, null, starIndex, null) }
                                    .testTag("rate_star_$starIndex")
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${form.rating} of 5 Stars",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                // Testimonial text
                OutlinedTextField(
                    value = form.comment,
                    onValueChange = { onUpdateForm(null, null, null, null, it) },
                    label = { Text("Your Testimonial / Story *") },
                    isError = form.commentError != null,
                    supportingText = form.commentError?.let { { Text(it) } },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("review_input_comment"),
                    minLines = 3,
                    maxLines = 5
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onSubmit,
                modifier = Modifier.testTag("btn_submit_review")
            ) {
                Text("Submit Review")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.testTag("btn_cancel_review")
            ) {
                Text("Cancel")
            }
        }
    )
}
