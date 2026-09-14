package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.SettingsSubTab
import com.example.ui.theme.ColorTheme
import com.example.ui.theme.ThemeMode

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SettingsSection(
    themeMode: ThemeMode,
    colorTheme: ColorTheme,
    selectedSubTab: SettingsSubTab = SettingsSubTab.APPEARANCE,
    onSelectSubTab: (SettingsSubTab) -> Unit,
    onSelectThemeMode: (ThemeMode) -> Unit,
    onSelectColorTheme: (ColorTheme) -> Unit,
    onResetDefaults: () -> Unit = {},
    portalContent: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("settings_screen_container")
    ) {
        // --- Top Settings Tab Row: Display & Themes vs. Craftsman Portal ---
        TabRow(
            selectedTabIndex = selectedSubTab.ordinal,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("settings_top_tab_row")
        ) {
            Tab(
                selected = selectedSubTab == SettingsSubTab.APPEARANCE,
                onClick = { onSelectSubTab(SettingsSubTab.APPEARANCE) },
                text = {
                    Text(
                        text = "Display & Themes",
                        fontWeight = if (selectedSubTab == SettingsSubTab.APPEARANCE) FontWeight.Bold else FontWeight.Normal,
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Palette,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                },
                modifier = Modifier.testTag("settings_tab_appearance")
            )

            Tab(
                selected = selectedSubTab == SettingsSubTab.PORTAL,
                onClick = { onSelectSubTab(SettingsSubTab.PORTAL) },
                text = {
                    Text(
                        text = "Staff Portal",
                        fontWeight = if (selectedSubTab == SettingsSubTab.PORTAL) FontWeight.Bold else FontWeight.Normal,
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.AdminPanelSettings,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                },
                modifier = Modifier.testTag("settings_tab_portal")
            )
        }

        // --- Content Switching ---
        when (selectedSubTab) {
            SettingsSubTab.APPEARANCE -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("settings_appearance_lazy_column"),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // --- Header ---
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("settings_header_card"),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                            ),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(52.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Settings,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onPrimary,
                                            modifier = Modifier.size(28.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Column {
                                    Text(
                                        text = "Preferences & Style",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "Customize dark/light display mode and color themes",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }

                    // --- Section 1: Display Mode (Light / Dark / System) ---
                    item {
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("settings_card_display_mode"),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Icon(
                                        imageVector = when (themeMode) {
                                            ThemeMode.LIGHT -> Icons.Default.LightMode
                                            ThemeMode.DARK -> Icons.Default.DarkMode
                                            ThemeMode.SYSTEM -> Icons.Default.BrightnessAuto
                                        },
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Column {
                                        Text(
                                            text = "Display Mode",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "Choose dark mode or light mode appearance",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                // Mode Selection Options
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    DisplayModeOption(
                                        title = "Light",
                                        icon = Icons.Default.LightMode,
                                        isSelected = themeMode == ThemeMode.LIGHT,
                                        modifier = Modifier
                                            .weight(1f)
                                            .testTag("theme_mode_light"),
                                        onClick = { onSelectThemeMode(ThemeMode.LIGHT) }
                                    )

                                    DisplayModeOption(
                                        title = "Dark",
                                        icon = Icons.Default.DarkMode,
                                        isSelected = themeMode == ThemeMode.DARK,
                                        modifier = Modifier
                                            .weight(1f)
                                            .testTag("theme_mode_dark"),
                                        onClick = { onSelectThemeMode(ThemeMode.DARK) }
                                    )

                                    DisplayModeOption(
                                        title = "System",
                                        icon = Icons.Default.BrightnessAuto,
                                        isSelected = themeMode == ThemeMode.SYSTEM,
                                        modifier = Modifier
                                            .weight(1f)
                                            .testTag("theme_mode_system"),
                                        onClick = { onSelectThemeMode(ThemeMode.SYSTEM) }
                                    )
                                }
                            }
                        }
                    }

                    // --- Section 2: Color Themes (Red, Blue, Yellow, and Combined) ---
                    item {
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("settings_card_color_themes"),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ColorLens,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Column {
                                        Text(
                                            text = "Color Themes",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "Red, blue, yellow, or all of them combined",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                // Combined Theme (Highlighted)
                                ColorThemeCard(
                                    title = "All Combined (Red + Blue + Yellow)",
                                    description = "Harmonized triad palette blending ruby red, cobalt blue, and golden amber accents across the entire app.",
                                    swatches = listOf(Color(0xFFC62828), Color(0xFF1565C0), Color(0xFFF57F17)),
                                    isSelected = colorTheme == ColorTheme.COMBINED,
                                    isMultiColor = true,
                                    testTag = "theme_color_combined",
                                    onClick = { onSelectColorTheme(ColorTheme.COMBINED) }
                                )

                                // Red Theme
                                ColorThemeCard(
                                    title = "Crimson Red",
                                    description = "Bold ruby, cherry woodcraft, and warm scarlet accents.",
                                    swatches = listOf(Color(0xFFB3261E), Color(0xFF904A42), Color(0xFFFFDAD6)),
                                    isSelected = colorTheme == ColorTheme.RED,
                                    testTag = "theme_color_red",
                                    onClick = { onSelectColorTheme(ColorTheme.RED) }
                                )

                                // Blue Theme
                                ColorThemeCard(
                                    title = "Cobalt Blue",
                                    description = "Deep ocean sapphire, denim, and cool artisan cobalt tones.",
                                    swatches = listOf(Color(0xFF1565C0), Color(0xFF535F70), Color(0xFFD4E3FF)),
                                    isSelected = colorTheme == ColorTheme.BLUE,
                                    testTag = "theme_color_blue",
                                    onClick = { onSelectColorTheme(ColorTheme.BLUE) }
                                )

                                // Yellow Theme
                                ColorThemeCard(
                                    title = "Sunlit Yellow",
                                    description = "Bright sunflower, warm golden ochre, and honey tones.",
                                    swatches = listOf(Color(0xFFB58500), Color(0xFF6B5F3E), Color(0xFFFFE082)),
                                    isSelected = colorTheme == ColorTheme.YELLOW,
                                    testTag = "theme_color_yellow",
                                    onClick = { onSelectColorTheme(ColorTheme.YELLOW) }
                                )

                                // Warm Amber
                                ColorThemeCard(
                                    title = "Warm Amber",
                                    description = "Golden amber, warm honey, and natural earth tones.",
                                    swatches = listOf(Color(0xFF85441E), Color(0xFF755945), Color(0xFFFFDBCB)),
                                    isSelected = colorTheme == ColorTheme.WOODCRAFT,
                                    testTag = "theme_color_amber",
                                    onClick = { onSelectColorTheme(ColorTheme.WOODCRAFT) }
                                )
                            }
                        }
                    }

                    // --- Section 3: Staff Portal Callout (Inside Settings) ---
                    item {
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("settings_card_portal_callout"),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                verticalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = MaterialTheme.colorScheme.primaryContainer,
                                        modifier = Modifier.size(44.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = Icons.Default.AdminPanelSettings,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                    }
                                    Column {
                                        Text(
                                            text = "Staff Portal",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "Store Management & Inventory inside Settings",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }

                                Text(
                                    text = "The Staff Portal is securely tucked inside Settings. Log in with your PIN as Alex (Founder), Sam (STEM Lead), or Maya (Stationery Specialist) to manage live catalog inventory, review customer inquiries, update service pricing, and publish store announcements.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Button(
                                    onClick = { onSelectSubTab(SettingsSubTab.PORTAL) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("settings_btn_go_to_portal"),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primary
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Open Staff Portal")
                                }
                            }
                        }
                    }

                    // --- Section 4: Live Theme Preview Card ---
                    item {
                        OutlinedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("settings_card_live_preview"),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.outlinedCardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                verticalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Palette,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = "Active Theme Preview",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Text(
                                    text = "Rendered preview with your selected ${colorTheme.label} and ${themeMode.label}:",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                // Preview row of interactive components
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Button(
                                        onClick = { },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("Primary", style = MaterialTheme.typography.labelMedium)
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = MaterialTheme.colorScheme.secondaryContainer,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier.padding(vertical = 10.dp)
                                        ) {
                                            Text(
                                                text = "Secondary",
                                                style = MaterialTheme.typography.labelMedium,
                                                color = MaterialTheme.colorScheme.onSecondaryContainer
                                            )
                                        }
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = MaterialTheme.colorScheme.tertiaryContainer,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier.padding(vertical = 10.dp)
                                        ) {
                                            Text(
                                                text = "Tertiary",
                                                style = MaterialTheme.typography.labelMedium,
                                                color = MaterialTheme.colorScheme.onTertiaryContainer
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // --- Bottom Reset Actions ---
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            OutlinedButton(
                                onClick = onResetDefaults,
                                modifier = Modifier.testTag("settings_btn_reset_defaults")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.RestartAlt,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Reset Settings to Default")
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }

            SettingsSubTab.PORTAL -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("settings_portal_container")
                ) {
                    // Portal header with breadcrumb back to Display & Themes
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Shield,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "Settings > Craftsman Portal",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            TextButton(
                                onClick = { onSelectSubTab(SettingsSubTab.APPEARANCE) },
                                modifier = Modifier.testTag("portal_btn_back_to_themes")
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Themes & Appearance", style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }

                    // Embedded Portal Section
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) {
                        portalContent()
                    }
                }
            }
        }
    }
}

@Composable
private fun DisplayModeOption(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
        animationSpec = tween(200),
        label = "DisplayModeBorder"
    )
    val containerColor by animateColorAsState(
        targetValue = if (isSelected)
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
        else
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
        animationSpec = tween(200),
        label = "DisplayModeContainer"
    )

    Surface(
        shape = RoundedCornerShape(14.dp),
        color = containerColor,
        border = androidx.compose.foundation.BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = borderColor
        ),
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 14.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
            if (isSelected) {
                Spacer(modifier = Modifier.height(4.dp))
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

@Composable
private fun ColorThemeCard(
    title: String,
    description: String,
    swatches: List<Color>,
    isSelected: Boolean,
    isMultiColor: Boolean = false,
    testTag: String,
    onClick: () -> Unit
) {
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f),
        animationSpec = tween(200),
        label = "ColorThemeBorder"
    )
    val containerColor by animateColorAsState(
        targetValue = if (isSelected)
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
        else
            MaterialTheme.colorScheme.surface,
        animationSpec = tween(200),
        label = "ColorThemeContainer"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = androidx.compose.foundation.BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = borderColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Color swatch circles
                Box(
                    modifier = Modifier.size(46.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (isMultiColor) {
                        // Tri-color circle preview
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.sweepGradient(
                                        colors = listOf(
                                            swatches[0],
                                            swatches[1],
                                            swatches[2],
                                            swatches[0]
                                        )
                                    )
                                )
                                .border(1.5.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), CircleShape)
                        )
                    } else {
                        // Single dominant theme circle with accent ring
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(swatches[0])
                                .border(1.5.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), CircleShape)
                        )
                    }
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 16.sp
                    )

                    // Mini swatch dots
                    Row(
                        modifier = Modifier.padding(top = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        swatches.forEach { color ->
                            Box(
                                modifier = Modifier
                                    .size(14.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .border(1.dp, Color.White.copy(alpha = 0.5f), CircleShape)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            RadioButton(
                selected = isSelected,
                onClick = onClick,
                modifier = Modifier.testTag("${testTag}_radio")
            )
        }
    }
}
