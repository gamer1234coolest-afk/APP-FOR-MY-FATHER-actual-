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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Forest
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.data.DonationEntity
import com.example.ui.DonationFormState
import java.text.NumberFormat
import java.util.Locale

data class DonationTier(
    val title: String,
    val amount: Double,
    val description: String,
    val icon: ImageVector
)

val donationTiers = listOf(
    DonationTier(
        title = "Youth STEM Discovery Kits",
        amount = 15.0,
        description = "Provides tactile robotics and circuitry beginner kits for local after-school clubs.",
        icon = Icons.Default.Star
    ),
    DonationTier(
        title = "Classroom Maker & Stationery Supplies",
        amount = 35.0,
        description = "Supplies notebooks, drafting pencils, and craft materials for neighborhood classrooms.",
        icon = Icons.Default.VolunteerActivism
    ),
    DonationTier(
        title = "Community Electronics Repair Bench",
        amount = 75.0,
        description = "Supports our free monthly neighborhood repair cafe, offering soldering tools and diagnostics.",
        icon = Icons.Default.CreditCard
    ),
    DonationTier(
        title = "Apex Community Student Support Sponsor",
        amount = 150.0,
        description = "Sponsors notebook sets, geometry tools, and exam writing kits for local neighborhood school children.",
        icon = Icons.Default.Favorite
    )
)

@Composable
fun DonationSection(
    formState: DonationFormState,
    donations: List<DonationEntity>,
    onUpdateForm: (
        name: String?,
        tier: String?,
        amount: String?,
        freq: String?,
        note: String?,
        paymentMethod: String?,
        anon: Boolean?
    ) -> Unit,
    onSelectTier: (title: String, amount: Double) -> Unit,
    onSubmit: () -> Unit,
    onDismissSuccess: () -> Unit
) {
    val totalRaised = donations.sumOf { it.amountDollars }
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("donation_section_list"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("donation_hero_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(44.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.VolunteerActivism,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Apex Community & Student Support Initiative",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "Apex Enterprises • Medavakkam Neighborhood Fund",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Supporting local school students, examination kits, and community learning resources in Medavakkam and Jalladiampet. Soman Paliath and Apex Enterprises believe every child deserves access to dependable school stationery.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Stats row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.6f))
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "COMMUNITY RAISED",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = currencyFormat.format(totalRaised),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "ACTIVE PATRONS",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = "${donations.size} Supporters",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }

        // Section Title: Select Donation Tier
        item {
            Text(
                text = "1. Select a Support Tier",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        // Tiers
        items(donationTiers) { tier ->
            val isSelected = formState.selectedTier == tier.title &&
                    formState.customAmount == tier.amount.toInt().toString()

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectTier(tier.title, tier.amount) }
                    .then(
                        if (isSelected) Modifier.border(
                            2.dp,
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(12.dp)
                        )
                        else Modifier
                    )
                    .testTag("tier_card_${tier.amount.toInt()}"),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected)
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                    else
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = tier.icon,
                                contentDescription = null,
                                tint = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = tier.title,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "$${tier.amount.toInt()}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = tier.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Section: Donation Details Form
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("donation_form_card"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "2. Contribution Details",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    // Frequency selector
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = formState.frequency == "One-Time",
                            onClick = { onUpdateForm(null, null, null, "One-Time", null, null, null) },
                            label = { Text("One-Time Gift") },
                            modifier = Modifier.weight(1f)
                        )
                        FilterChip(
                            selected = formState.frequency == "Monthly Patron",
                            onClick = { onUpdateForm(null, null, null, "Monthly Patron", null, null, null) },
                            label = { Text("Monthly Patron") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Custom amount input
                    OutlinedTextField(
                        value = formState.customAmount,
                        onValueChange = { onUpdateForm(null, "Custom Amount", it, null, null, null, null) },
                        label = { Text("Donation Amount ($ USD)") },
                        prefix = { Text("$ ") },
                        isError = formState.amountError != null,
                        supportingText = formState.amountError?.let { { Text(it) } },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_donation_amount"),
                        shape = RoundedCornerShape(10.dp)
                    )

                    // Donor Name
                    OutlinedTextField(
                        value = formState.donorName,
                        onValueChange = { onUpdateForm(it, null, null, null, null, null, null) },
                        label = { Text("Your Name or Family Name") },
                        placeholder = { Text("e.g., The Taylor Family or Alex M.") },
                        enabled = !formState.isAnonymous,
                        isError = formState.nameError != null,
                        supportingText = formState.nameError?.let { { Text(it) } },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_donor_name"),
                        shape = RoundedCornerShape(10.dp)
                    )

                    // Anonymous Checkbox
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onUpdateForm(null, null, null, null, null, null, !formState.isAnonymous)
                            }
                    ) {
                        Checkbox(
                            checked = formState.isAnonymous,
                            onCheckedChange = {
                                onUpdateForm(null, null, null, null, null, null, it)
                            }
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Keep my name anonymous on the public Patrons Wall",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    // Note or Dedication
                    OutlinedTextField(
                        value = formState.note,
                        onValueChange = { onUpdateForm(null, null, null, null, it, null, null) },
                        label = { Text("Dedication or Message (Optional)") },
                        placeholder = { Text("A note of encouragement for young STEM learners or staff") },
                        minLines = 2,
                        maxLines = 3,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_donation_note"),
                        shape = RoundedCornerShape(10.dp)
                    )

                    // Payment Method selector
                    Text(
                        text = "Payment Method",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Credit Card", "Google Pay", "Bank Transfer").forEach { method ->
                            FilterChip(
                                selected = formState.paymentMethod == method,
                                onClick = { onUpdateForm(null, null, null, null, null, method, null) },
                                label = { Text(method) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Donate Button
                    Button(
                        onClick = onSubmit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("btn_submit_donation"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Contribute $${formState.customAmount.ifBlank { "0" }} as Patron",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "256-Bit Encrypted Secure Processing Guarantee",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        }

        // Section: Recent Patrons Wall
        item {
            Text(
                text = "Community Supporters & Patrons Wall",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(donations) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("patron_wall_item_${item.id}"),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                modifier = Modifier.size(32.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Favorite,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = if (item.isAnonymous) "Anonymous Community Patron" else item.donorName,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = item.tierTitle,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        Text(
                            text = currencyFormat.format(item.amountDollars),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    if (item.note.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "“${item.note}”",
                            style = MaterialTheme.typography.bodySmall,
                            fontStyle = FontStyle.Italic,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }

    // Success Dialog
    if (formState.isSubmittedSuccessfully) {
        AlertDialog(
            onDismissRequest = onDismissSuccess,
            icon = {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(44.dp)
                )
            },
            title = {
                Text(
                    text = "Thank You, Honored Patron!",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text(
                        text = "Your contribution has been received! Soman Paliath, the Apex Enterprises team, and local student beneficiaries are deeply grateful for your support in fostering education and learning."
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Patron Certificate Ref: #${formState.lastReferenceCode}",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = onDismissSuccess,
                    modifier = Modifier.testTag("btn_close_donation_success")
                ) {
                    Text("Return to Shop")
                }
            }
        )
    }
}
