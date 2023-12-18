package cs3500.pa05.controller;

import java.io.IOException;

/**
 * The Controller interface represents a controller component in the application.
 * It defines the contract for controllers that handle the application's logic and behavior.
 */
public interface Controller {
  /**
   * Runs the controller's logic.
   *
   * @throws IllegalStateException If the controller is in an illegal state to run.
   * @throws IOException           If an I/O error occurs during the execution.
   */
  void run() throws IllegalStateException, IOException;
}
