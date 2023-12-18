package cs3500.pa05.view;

import cs3500.pa05.controller.Controller;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 *  Passes code through the view
 */
public class PassCodeView implements View {
  private final FXMLLoader loader;

  /**
   * Represents the display for a week
   *
   * @param controller controller that is used to control the actions of the week display
   */
  public PassCodeView(Controller controller) {
    // look up and store the layout
    this.loader = new FXMLLoader();
    this.loader.setLocation(getClass().getClassLoader().getResource("password.fxml"));
    this.loader.setController(controller);
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
      Scene scene = this.loader.load();
      return scene;
    } catch (IOException exc) {
      throw new IllegalStateException("Unable to load layout.");
    }
  }
}
