package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.DonationEntity
import com.example.ui.CraftsmanUser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Apex Enterprises", appName)
  }

  @Test
  fun `verify donation model and reference code`() {
    val donation = DonationEntity(
      donorName = "Thomas Vance",
      amountDollars = 75.0,
      tierTitle = "Historic Hand Tool Preservation",
      frequency = "One-Time",
      referenceCode = "PAT-1996"
    )
    assertEquals("Thomas Vance", donation.donorName)
    assertEquals(75.0, donation.amountDollars, 0.001)
    assertEquals("PAT-1996", donation.referenceCode)
  }

  @Test
  fun `verify staff profiles`() {
    val alex = CraftsmanUser("c_1", "Soman Paliath", "Proprietor & Entrepreneur", "1996", "Retail & Services")
    val assistant = CraftsmanUser("c_2", "Apex Store Assistant", "Document & Retail Lead", "2025", "Document Services")
    assertTrue(alex.defaultPin == "1996")
    assertTrue(assistant.name.contains("Assistant"))
  }

  @Test
  fun `verify theme mode and color themes`() {
    val dark = com.example.ui.theme.ThemeMode.DARK
    val light = com.example.ui.theme.ThemeMode.LIGHT
    val red = com.example.ui.theme.ColorTheme.RED
    val blue = com.example.ui.theme.ColorTheme.BLUE
    val yellow = com.example.ui.theme.ColorTheme.YELLOW
    val combined = com.example.ui.theme.ColorTheme.COMBINED

    assertEquals("Dark Mode", dark.label)
    assertEquals("Light Mode", light.label)
    assertEquals("Crimson Red", red.label)
    assertEquals("Cobalt Blue", blue.label)
    assertEquals("Sunlit Gold", yellow.label)
    assertEquals("Triad Combined", combined.label)
  }

  @Test
  fun `verify app sections and settings sub tabs`() {
    val sections = com.example.ui.AppSection.entries
    assertTrue(sections.contains(com.example.ui.AppSection.SETTINGS))
    // Portal is no longer a separate main top-level section
    assertEquals(8, sections.size)

    val subTabs = com.example.ui.SettingsSubTab.entries
    assertEquals(2, subTabs.size)
    assertEquals("Display & Themes", com.example.ui.SettingsSubTab.APPEARANCE.label)
    assertEquals("Staff Portal", com.example.ui.SettingsSubTab.PORTAL.label)
  }
}

