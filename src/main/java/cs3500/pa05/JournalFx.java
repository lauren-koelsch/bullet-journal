package cs3500.pa05;

import cs3500.pa05.controller.Controller;
import cs3500.pa05.controller.SplashController;
import cs3500.pa05.view.SplashView;
import cs3500.pa05.view.View;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Driver class that drives the application
 */
public class JournalFx extends Application {
  /**
   * @param primaryStage the primary stage for this application, onto which
   *                     the application scene can be set.
   *                     Applications may create other stages, if needed, but they will not be
   *                     primary stages.
   */

  @Override
  public void start(Stage primaryStage) {
    Controller ctrl = new SplashController(primaryStage);
    View wv = new SplashView(ctrl);

    try {
      primaryStage.setScene(wv.load());
      ctrl.run();
      primaryStage.show();

    } catch (IllegalStateException | IOException exc) {
      System.err.println("Unable to load GUI.");
    }
  }

  /**
   * Entry point of the application
   *
   * @param args arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
}
