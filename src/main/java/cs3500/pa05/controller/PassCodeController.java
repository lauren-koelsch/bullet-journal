package cs3500.pa05.controller;

import cs3500.pa05.model.CalendarItem;
import cs3500.pa05.view.WelcomeView;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * represents a password controller
 */
public class PassCodeController implements Controller {
  @FXML
  private TextField passCode;
  @FXML
  private Button login;
  private Stage stage;
  private WelcomeController controller;
  /**
   * represents a welcome view stage
   */
  private WelcomeView view;
  private String code;

  /**
   * Constructs a PassCodeController with the specified Stage.
   * Initializes the stage, controller, and view with a WelcomeController and WelcomeView.
   * Sets the default passcode to "agenda".
   *
   * @param stage the Stage to associate with the controller
   */
  public PassCodeController(Stage stage) {
    this.stage = stage;
    this.controller = new WelcomeController(stage);
    this.view = new WelcomeView(controller);
    code = "agenda";
  }

  /**
   * Executes the run method.
   *
   * @throws IllegalStateException If an illegal state is encountered.
   * @throws IOException If an I/O error occurs.
   */
  @Override
  public void run() throws IllegalStateException, IOException {
    login.setOnAction(e -> checkCode());
  }

  /**
   * Checks the entered passcode and sets the scene if it matches the code.
   */
  private void checkCode() {
    if (passCode.getText().equals(code)) {
      if (passCode.getText().equals(code)) {
        stage.setScene(view.load());
        try {
          controller.run();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        stage.show();
      }
    }
  }
}
