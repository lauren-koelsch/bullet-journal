package cs3500.pa05.view;

import javafx.scene.Scene;

/**
 * The View interface represents a graphical user interface view.
 * Implementing classes provide the functionality to load a graphical scene.
 */
public interface View {
  /**
   * Loads and returns the graphical scene associated with the view.
   *
   * @return The loaded scene.
   * @throws IllegalStateException If the view is in an invalid state for loading the scene.
   */
  Scene load() throws IllegalStateException;
}
