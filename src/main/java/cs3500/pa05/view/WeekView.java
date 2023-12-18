package cs3500.pa05.view;

import cs3500.pa05.controller.Controller;
import cs3500.pa05.model.JournalTheme;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * represents the weekview of the user with the button
 * functionalities
 */
public class WeekView implements View {
  private final FXMLLoader loader;

  private JournalTheme journalTheme;

  private Scene scene;

  /**
   * Represents the display for a week
   *
   * @param controller controller that is used to control the actions of the week display
   */
  public WeekView(Controller controller) {
    // look up and store the layout
    this.loader = new FXMLLoader();
    this.loader.setLocation(getClass().getClassLoader().getResource("journal.fxml"));
    this.loader.setController(controller);
    journalTheme = new JournalTheme();
  }

  /**
   * Loads a scene from a Bullet Journal GUI layout.
   *
   * @return the layout
   */
  @Override
  public Scene load() {
    // load the layout
    try {
      if (this.loader.getRoot() == null) {
        scene = this.loader.load();
      }
      journalTheme.changeTheme();
      String filename = journalTheme.getThemeFilename();
      scene.getStylesheets()
          .add(getClass().getClassLoader().getResource(filename).toExternalForm());
      return scene;
    } catch (IOException exc) {
      exc.printStackTrace();
      throw new IllegalStateException("Unable to load layout.");
    }
  }
}
