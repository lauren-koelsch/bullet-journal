package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the JournalTheme class.
 */
class JournalThemeTest {


  JournalTheme journalTheme;

  /**
   * Sets up the test fixture.
   */
  @BeforeEach
  public void setup() {
    journalTheme = new JournalTheme();
  }

  /**
   * Tests the setThemeSelection method of the JournalTheme class.
   */
  @Test
  public void testSetThemeSelection() {

    assertEquals(null, journalTheme.getThemeFilename());
    assertEquals(null, journalTheme.getIconFilename());

    journalTheme.setThemeSelection(Theme.COOLBLUE);

    assertEquals("theme1.css", journalTheme.getThemeFilename());
    assertEquals("blueCalendar.png", journalTheme.getIconFilename());

    journalTheme.setThemeSelection(Theme.SUMMER);

    assertEquals("theme2.css", journalTheme.getThemeFilename());
    assertEquals("palmTree.png", journalTheme.getIconFilename());

    journalTheme.setThemeSelection(Theme.DARKMODE);

    assertEquals("theme3.css", journalTheme.getThemeFilename());
    assertEquals("redCalendar.png", journalTheme.getIconFilename());

    journalTheme.setThemeSelection(null);

    assertEquals("default.css", journalTheme.getThemeFilename());
    assertEquals("calendar.png", journalTheme.getIconFilename());
  }
}