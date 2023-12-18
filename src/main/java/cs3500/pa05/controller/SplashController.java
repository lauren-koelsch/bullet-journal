package cs3500.pa05.controller;

import cs3500.pa05.view.PassCodeView;
import cs3500.pa05.view.WeekView;
import cs3500.pa05.view.WelcomeView;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * represents a splash screen controller
 */
public class SplashController implements Controller {
  @FXML
  private Button cont;
  private Stage stage;
  /**
   * represents a passcode controller which will be
   * initialized
   */
  private PassCodeController controller;
  private PassCodeView view;

  /**
   * Constructs a SplashController with the specified Stage.
   * Initializes the stage, controller, and view with a PassCodeController and PassCodeView.
   *
   * @param stage the Stage to associate with the controller
   */
  public SplashController(Stage stage) {
    this.stage = stage;
    this.controller = new PassCodeController(stage);
    this.view = new PassCodeView(controller);
  }

  /**
   * Executes the run method.
   *
   * @throws IllegalStateException If an illegal state is encountered.
   * @throws IOException If an I/O error occurs.
   */
  @Override
  public void run() throws IllegalStateException, IOException {
    cont.setOnAction(e -> {
      stage.setScene(view.load());
      try {
        controller.run();
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
      stage.show();
    });
  }
}
