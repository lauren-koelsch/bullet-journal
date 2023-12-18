package cs3500.pa05.model;

/**
 * represents a theme that the user could change to
 * for the bullet journal
 */
public class JournalTheme {

  private String themeFilename;
  private String iconFilename;
  private Theme themeSelection;

  /**
   * Sets the theme selection for the object and applies the corresponding theme.
   *
   * @param theme the selected theme to be set
   */
  public void setThemeSelection(Theme theme) {
    this.themeSelection = theme;
    changeTheme();
  }

  /**
   * Changes the theme of the object based on the selected theme.
   * The theme selection can be one of the following values: Theme.COOLBLUE, Theme.SUMMER,
   * Theme.DARKMODE. If the theme selection is not recognized, the default theme will be applied.
   */
  public void changeTheme() {
    if (themeSelection == Theme.COOLBLUE) {
      themeFilename = "theme1.css";
      iconFilename = "blueCalendar.png";
    } else if (themeSelection == Theme.SUMMER) {
      themeFilename = "theme2.css";
      iconFilename = "palmTree.png";
    } else if (themeSelection == Theme.DARKMODE) {
      themeFilename = "theme3.css";
      iconFilename = "redCalendar.png";
    } else {
      themeFilename = "default.css";
      iconFilename = "calendar.png";
    }
  }

  /**
   * Retrieves the filename of the theme associated with the object.
   *
   * @return the filename of the theme
   */
  public String getThemeFilename() {
    return this.themeFilename;
  }

  /**
   * Retrieves the filename of the icon associated with the object.
   *
   * @return the filename of the icon
   */
  public String getIconFilename() {
    return this.iconFilename;
  }
}
