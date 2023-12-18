package cs3500.pa05.controller;

import cs3500.pa05.model.BujoReader;
import cs3500.pa05.model.DayJson;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.EventJson;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.TaskJson;
import cs3500.pa05.model.WeekJson;
import cs3500.pa05.view.WeekView;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * represents a controller for the welcome screen
 */
public class WelcomeController implements Controller {
  @FXML
  private VBox taskQueue;
  @FXML
  private VBox sunday;
  @FXML
  private VBox monday;
  @FXML
  private VBox tuesday;
  @FXML
  private VBox wednesday;
  @FXML
  private VBox thursday;
  @FXML
  private VBox friday;
  @FXML
  private VBox saturday;
  @FXML
  private Button newAgenda;
  @FXML
  private MenuButton bujos;
  private JournalController controller;
  private WeekView view;
  private List<File> bujoList;
  private Stage stage;

  /**
   * Constructs a WelcomeController with the specified Stage.
   * Initializes the stage, controller, view, and bujoList with appropriate values.
   *
   * @param stage the Stage to associate with the controller
   */
  public WelcomeController(Stage stage) {
    this.stage = stage;
    this.controller = new JournalController(stage);
    this.view = new WeekView(controller);
    this.bujoList = getFiles();
  }


  /**
   * Executes the run method.
   *
   * @throws IllegalStateException If an illegal state is encountered.
   * @throws IOException If an I/O error occurs.
   */
  @Override
  public void run() throws IllegalStateException, IOException {
    newAgenda.setOnAction(e -> {
      stage.setScene(view.load());
      controller.run();
      stage.show();
    });
    List<MenuItem> menuItems = createButtons();
    bujos.getItems().setAll(menuItems);
    selectBujo(menuItems);
  }

  /**
   * Selects a bujo from the provided list.
   *
   * @param bujos The list of bujos to choose from.
   */
  public void selectBujo(List<MenuItem> bujos) {
    for (MenuItem b : bujos) {
      String name = b.getText();
      b.setOnAction(e -> {
        readBujos(name);
      });
    }
  }

  /**
   * Reads bujo data from the specified filepath.
   *
   * @param filepath The filepath of the bujo file to read.
   */
  private void readBujos(String filepath) {
    BujoReader reader = new BujoReader();
    try {
      WeekJson week = reader.readFile(filepath);
      parseJson(week);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Parses JSON data representing a week.
   *
   * @param week The WeekJson object to parse.
   */
  private void parseJson(WeekJson week) {
    for (DayJson day : week.days()) {
      controller.setTasks(deserializeTasks(day.tasks()));
      controller.setEvents(deserializeEvents(day.events()));
      controller.getStage().getScene().getStylesheets().add(week.theme());
      controller.setEventMax(week.event());
      controller.setTaskMax(week.tasks());
      controller.setEventCountMax(deserializeItems(week.eventCount()));
      controller.setTaskCountMax(deserializeItems(week.taskCount()));
      controller.setTaskCount(week.t());
      controller.setEventCount(week.e());
      controller.setNotes(week.notes());
      Scene scene = view.load();
      stage.setScene(scene);
      controller.run();
      stage.show();
    }
  }

  /**
   * Deserializes a list of TaskJson objects into Task objects.
   *
   * @param tasks The list of TaskJson objects to deserialize.
   * @return The ObservableList of Task objects.
   */
  private ObservableList<Task> deserializeTasks(List<TaskJson> tasks) {
    ObservableList<Task> parsed = FXCollections.observableArrayList();
    for (TaskJson t : tasks) {
      Task newtask = new Task(t.name(), t.desc(), t.day(), t.status());
      parsed.add(newtask);
    }
    return parsed;
  }

  /**
   * Deserializes a list of EventJson objects into Event objects.
   *
   * @param events The list of EventJson objects to deserialize.
   * @return The ObservableList of Event objects.
   */
  private ObservableList<Event> deserializeEvents(List<EventJson> events) {
    ObservableList<Event> parsedevents = FXCollections.observableArrayList();
    for (EventJson e : events) {
      LocalTime time = LocalTime.parse(e.time());
      Event newevent = new Event(e.name(), e.desc(), e.day(), time,
          e.duration(), e.status());
      parsedevents.add(newevent);
    }
    return parsedevents;
  }

  private int[] deserializeItems(String array) {
    String formattedInput = array.replace("[", "").replace("]", "");
    String[] stringArray = formattedInput.split(", ");
    int[] intArray = new int[stringArray.length];
    for (int i = 0; i < stringArray.length; i++) {
      intArray[i] = Integer.parseInt(stringArray[i]);
    }
    return intArray;
  }

  /**
   * get all the files from the directory
   *
   * @return a list of bujo files
   *
   */
  public List<File> getFiles() {
    String directoryPath = "bujoFiles/";
    File directory = new File(directoryPath);
    List<File> fileList = new ArrayList<>();

    if (directory.exists() && directory.isDirectory()) {
      File[] files = directory.listFiles();
      if (files != null) {
        fileList = Arrays.asList(files);
      }
    }
    return fileList;
  }

  /**
   * creates a list of all the bujo files that exist in the directory
   *
   * @return a list of all the files in the form of a menuitem
   *
   */
  private List<MenuItem> createButtons() {
    List<MenuItem> options = new ArrayList<>();
    for (File f : bujoList) {
      MenuItem item = new MenuItem(f.toString());
      options.add(item);
    }
    return options;
  }
}
