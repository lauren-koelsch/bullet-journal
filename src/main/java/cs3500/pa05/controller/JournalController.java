package cs3500.pa05.controller;

import cs3500.pa05.model.BujoWriter;
import cs3500.pa05.model.CalendarItem;
import cs3500.pa05.model.Complete;
import cs3500.pa05.model.DayJson;
import cs3500.pa05.model.DayLabel;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.EventJson;
import cs3500.pa05.model.JournalTheme;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.TaskJson;
import cs3500.pa05.model.Theme;
import cs3500.pa05.model.WeekJson;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * represents the controller for the bullet jounral
 */
public class JournalController implements Controller {
  @FXML
  private TextField totalEvents;
  @FXML
  private TextField totalTasks;
  @FXML
  private TextField taskDayEdit;
  @FXML
  private TextField eventDayEdit;
  @FXML
  private TextField eventNameEdit;
  @FXML
  private TextField eventDescEdit;
  @FXML
  private TextField eventStatusEdit;
  @FXML
  private TextField eventStartEdit;
  @FXML
  private TextField eventDurationEdit;
  @FXML
  private Button viewEvents;
  @FXML
  private TextField taskNameEdit;
  @FXML
  private TextField taskDescEdit;
  @FXML
  private TextField taskStatEdit;
  @FXML
  private Button saveEvents;
  @FXML
  private Button viewTasks;
  @FXML
  private TextArea notesText;
  @FXML
  private Button saveNotes;
  @FXML
  private TextArea maxTasks;
  @FXML
  private TextArea maxEvents;
  @FXML
  private Button saveTasks;
  private final BujoWriter bujo;
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
  private Button newTask;
  @FXML
  private Button newEvent;
  private final Stage stage;
  @FXML
  private Button save;
  @FXML
  private Popup popup;
  @FXML
  private ComboBox<DayLabel> days;
  @FXML
  private ComboBox<Complete> status;
  @FXML
  private ComboBox<Theme> themes;
  @FXML
  private ComboBox<String> colors;
  private Map<String, Color> map = makeMap();
  @FXML
  private ImageView icon;
  private JournalTheme theme;
  private String themeString;
  @FXML
  private Button saveFile;
  private ObservableList<Task> listTasks;
  private ObservableList<Event> listEvents;
  private int maxE;
  private int maxT;
  private String notes;
  private int[] weekdaysEvent;
  private int[] weekdaysTask;
  @FXML
  private Button tryAgain;
  private int taskCount;
  private int eventCount;

  /**
   * Constructs a new JournalController object with the specified stage.
   *
   * @param stage The stage for the journal controller.
   */
  public JournalController(Stage stage) {
    this.stage = stage;
    listTasks = FXCollections.observableArrayList();
    listEvents = FXCollections.observableArrayList();
    theme = new JournalTheme();
    bujo = new BujoWriter();
    weekdaysEvent = new int[7];
    weekdaysTask = new int[7];
    maxT = 10000;
    maxE = 10000;
    notesText = new TextArea();
    taskCount = 0;
    eventCount = 0;

  }

  /**
   * initializes the state of the bullet journal
   * when the user clicks either button from the journal fxml field
   * there will be a popup created
   */
  public void run() {
    newTask.setOnAction(e -> makePopup(e));
    newEvent.setOnAction(e -> makePopup(e));
    saveFile.setOnAction(e -> makePopup(e));
    saveEvents.setOnAction(e -> extractEventMax());
    saveTasks.setOnAction(e -> extractTaskMax());
    saveNotes.setOnAction(e -> extractNoteMax());
    totalEvents.setText(String.valueOf(eventCount));
    totalTasks.setText(String.valueOf(taskCount));
    loadTheme();
    loadColors();
    loadAll();
  }

  /**
   * represents a task pop up when a button is clicked
   */
  @FXML
  private void makePopup(ActionEvent event) {
    popup = new Popup();
    FXMLLoader loader =
        new FXMLLoader(getClass().getClassLoader().getResource(getFxmlFileName(event)));
    loader.setController(this);
    try {
      Scene scene = loader.load();
      AnchorPane content = (AnchorPane) scene.getRoot();
      loadAbstract();
      String fileName = getFxmlFileName(event);
      accessFields(content, fileName);
      popup.getContent().add(content);
      popup.show(stage);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Accesses and configures the fields and event handlers of a
   * given AnchorPane based on the specified file name.
   *
   * @param content The AnchorPane representing the content to be accessed and configured
   * @param fileName The name of the file associated with the content.
   */
  private void accessFields(AnchorPane content, String fileName) {
    if (fileName.equals("taskspopup.fxml")) {
      save.setOnAction(e -> {
        extractTaskData(content);
        taskQueue(listTasks);
        popup.hide();
      });
    } else if (fileName.equals("eventspopup.fxml")) {
      save.setOnAction(e -> {
        extractEventData(content);
        popup.hide();
      });
    } else if (fileName.equals("saveFile.fxml")) {
      save.setOnAction(e -> {
        extractSaveData(content);
        popup.hide();
      });
    }
  }

  /**
   * Check what item is being passed and assign it to a specific day.
   *
   * @param item Event or Task
   */
  private void addItem(CalendarItem item) {
    VBox container;
    if (item instanceof Event) {
      container = createEvent((Event) item);
    } else if (item instanceof Task) {
      container = createTask((Task) item);
    } else {
      return; // Handle other types of calendar items if needed
    }
    addToDayContainer(item.getDay(), container);
  }

  /**
   * Add the container to the appropriate day container.
   *
   * @param day      DayLabel indicating the day
   * @param container VBox container to be added
   */
  private void addToDayContainer(DayLabel day, VBox container) {
    switch (day) {
      case SUNDAY:
        sunday.getChildren().add(container);
        break;
      case MONDAY:
        monday.getChildren().add(container);
        break;
      case TUESDAY:
        tuesday.getChildren().add(container);
        break;
      case WEDNESDAY:
        wednesday.getChildren().add(container);
        break;
      case THURSDAY:
        thursday.getChildren().add(container);
        break;
      case FRIDAY:
        friday.getChildren().add(container);
        break;
      case SATURDAY:
        saturday.getChildren().add(container);
        break;
      default:
        break;
    }
  }

  /**
   * Extracts the notes from the input field.
   *
   * @return the extracted notes
   */
  private String extractNoteMax() {
    notes = notesText.getText();
    return notes;
  }

  /**
   * Loads the available options for days and completion status in the corresponding ComboBoxes.
   */
  private void loadAbstract() {
    ObservableList<DayLabel> dayList = FXCollections.observableArrayList(DayLabel.SUNDAY,
        DayLabel.MONDAY, DayLabel.TUESDAY, DayLabel.WEDNESDAY,
        DayLabel.THURSDAY, DayLabel.FRIDAY, DayLabel.SATURDAY);
    days.setItems(dayList);
    ObservableList<Complete> statusList =
        FXCollections.observableArrayList(Complete.NOT_COMPLETED);
    status.setItems(statusList);
  }

  /**
   * Extracts save data from the specified AnchorPane and writes the data to a file.
   * The extracted data is used to generate a WeekJson object
   * and save it to a file using the BujoWriter.
   *
   * @param content The AnchorPane containing the save data.
   */
  private void extractSaveData(AnchorPane content)  {
    TextField file = (TextField) content.lookup("#bujoSave");
    String userfile = file.getText();
    WeekJson week = generateWeekJson(taskParse(getListTasks()), eventParse(getListEvents()));
    try {
      bujo.writeBujoFile(week, userfile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * creates an hbox with a string lable
   *
   * @param input the name
   * @return an hbox
   */
  private HBox createHbox(String input) {
    Label l = new Label(input);
    HBox n = new HBox(l);
    n.setAlignment(Pos.CENTER);
    l.setAlignment(Pos.CENTER_LEFT);
    return n;
  }

  /**
   * Returns the numerical value associated with the given day label.
   *
   * @param day the day label to get the value for
   * @return the numerical value of the day label
   * @throws IllegalArgumentException if the day label is invalid
   */
  private int dayVal(DayLabel day) {
    if (DayLabel.SUNDAY.equals(day)) {
      return 0;
    } else if (DayLabel.MONDAY.equals(day)) {
      return 1;
    } else if (DayLabel.TUESDAY.equals(day)) {
      return 2;
    } else if (DayLabel.WEDNESDAY.equals(day)) {
      return 3;
    } else if (DayLabel.THURSDAY.equals(day)) {
      return 4;
    } else if (DayLabel.FRIDAY.equals(day)) {
      return 5;
    } else if (DayLabel.SATURDAY.equals(day)) {
      return 6;
    } else {
      throw new IllegalArgumentException("Invalid day label: " + day);
    }
  }

  /**
   * Generates a WeekJson object based on the provided lists of TaskJson and EventJson.
   *
   * @param taskJsons  The list of TaskJson objects.
   * @param eventJsons The list of EventJson objects.
   * @return The generated WeekJson object.
   */
  private WeekJson generateWeekJson(List<TaskJson> taskJsons, List<EventJson> eventJsons) {
    WeekJson weekJson = new WeekJson(new ArrayList<>(), theme.getThemeFilename(),
        theme.getIconFilename(), maxE, maxT, extractNoteMax(),
        Arrays.toString(weekdaysEvent), Arrays.toString(weekdaysTask), eventCount, taskCount);
    Map<String, DayJson> daysMap = initializeDaysMap();

    for (TaskJson taskJson : taskJsons) {
      String dayOfWeek = taskJson.dayToString();
      if (daysMap.containsKey(dayOfWeek)) {
        DayJson dayJson = daysMap.get(dayOfWeek);
        dayJson.tasks().add(taskJson);
      }
    }
    for (EventJson eventJson : eventJsons) {
      String dayOfWeek = eventJson.dayToString();
      if (daysMap.containsKey(dayOfWeek)) {
        DayJson dayJson = daysMap.get(dayOfWeek);
        dayJson.events().add(eventJson);
      }
    }
    List<DayJson> daysList = new ArrayList<>(daysMap.values());
    weekJson.days().addAll(daysList);
    return weekJson;
  }

  /**
   * Initialize the days map with DayLabel as keys and empty DayJson objects as values.
   *
   * @return Initialized days map
   */
  private Map<String, DayJson> initializeDaysMap() {
    Map<String, DayJson> daysMap = new HashMap<>();
    daysMap.put("SUNDAY", new DayJson(DayLabel.SUNDAY, new ArrayList<>(), new ArrayList<>()));
    daysMap.put("MONDAY", new DayJson(DayLabel.MONDAY, new ArrayList<>(), new ArrayList<>()));
    daysMap.put("TUESDAY", new DayJson(DayLabel.TUESDAY, new ArrayList<>(), new ArrayList<>()));
    daysMap.put("WEDNESDAY", new DayJson(DayLabel.WEDNESDAY, new ArrayList<>(), new ArrayList<>()));
    daysMap.put("THURSDAY", new DayJson(DayLabel.THURSDAY, new ArrayList<>(), new ArrayList<>()));
    daysMap.put("FRIDAY", new DayJson(DayLabel.FRIDAY, new ArrayList<>(), new ArrayList<>()));
    daysMap.put("SATURDAY", new DayJson(DayLabel.SATURDAY, new ArrayList<>(), new ArrayList<>()));
    return daysMap;
  }

  /**
   * loads all the tasks and events from the listTasks
   * and listEvents
   */
  private void loadAll() {
    for (Task t : listTasks) {
      addItem(t);
    }
    for (Event e : listEvents) {
      addItem(e);
    }
  }


  /**
   * creates a Vbox that represents all the content for the
   * event
   *
   * @param event an event
   * @return a vbox with the event content
   */
  private VBox createEvent(Event event) {
    VBox parent = new VBox();
    parent.setAlignment(Pos.CENTER);
    Button n6 = new Button("View");
    n6.setMinWidth(50);
    n6.setMaxWidth(50);
    n6.setMinHeight(22);
    n6.setMaxHeight(22);
    HBox n1 = createHbox(event.getName());
    HBox n2 = createHbox(event.getDescription());
    HBox n3 = createHbox(event.getStartTime().toString());
    HBox n4 = createHbox(String.valueOf(event.getDuration()));
    HBox n5 = createHbox(event.getStatus().toString());
    parent.getChildren().addAll(n1, n2, n3, n4, n5, n6);
    n6.setOnAction(e -> makeEventPop(event));
    return parent;
  }

  private void makeEventPop(Event ev) {
    popup = new Popup();
    FXMLLoader loader =
        new FXMLLoader(getClass().getClassLoader().getResource("eventPop.fxml"));
    loader.setController(this);
    try {
      eventNameEdit.setText(ev.getName());
      eventDescEdit.setText(ev.getDescription());
      eventStatusEdit.setText(ev.getStatus().toString());
      eventStartEdit.setText(ev.getStartTime().toString());
      eventDurationEdit.setText(String.valueOf(ev.getDuration()));
      eventDayEdit.setText(ev.getDay().toString());
      Scene scene = loader.load();
      AnchorPane content = (AnchorPane) scene.getRoot();
      popup.getContent().add(content);
      popup.show(stage);
      viewEvents.setOnAction(e -> {
        popup.hide();
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Extracts event data from the specified AnchorPane and creates an Event object.
   * The extracted data is used to populate the Event object and add it to the list of events.
   *
   * @param content The AnchorPane containing the event data.
   */
  private void extractEventData(AnchorPane content) {
    TextField events = (TextField) content.lookup("#eventName");
    TextField start = (TextField) content.lookup("#eventStart");
    TextField dur = (TextField) content.lookup("#eventDuration");
    TextArea desc = (TextArea) content.lookup("#eventDescription");
    String eventName = events.getText();
    String timeInput = start.getText();
    LocalTime localTime = LocalTime.parse(timeInput);
    String durInput = dur.getText();
    int duration = Integer.parseInt(durInput);
    String description = desc.getText();
    DayLabel selectedDay = days.getValue();
    Complete selectedStatus = status.getValue();
    Event userEvent = new Event(eventName, description, selectedDay,
        localTime, duration, selectedStatus);
    checkEventMax(userEvent, selectedDay);
  }

  /**
   * Checks if the maximum number of events has been reached for the given day
   * and adds the event to the appropriate list if there is still capacity.
   *
   * @param event the event to be added
   * @param day   the day label for which the event is being added
   * @throws IllegalStateException if an unexpected error occurs while checking the event maximum
   */
  private void checkEventMax(Event event, DayLabel day) {
    try {
      int val = dayVal(day);
      if (weekdaysEvent[val] < maxE) {
        listEvents.add(event);
        addItem(event);
        eventCount++;
        totalEvents.setText(String.valueOf(eventCount));
        weekdaysEvent[val]++;
      } else {
        System.out.println("u are at max");
      }
    } catch (Exception e) {
      throw new IllegalStateException("An unexpected error occurred while "
          + "checking the event maximum.");
    }
  }

  /**
   * Extracts the maximum number of events from the input field.
   *
   * @return the maximum number of events
   */
  private int extractEventMax() {
    String events = maxEvents.getText();
    if (events.isEmpty()) {
      return maxE;
    } else {
      maxE = Integer.parseInt(events);
      return maxE;
    }
  }

  /**
   * Converts a list of events to a list of EventJson objects.
   *
   * @param events The list of events to be converted.
   * @return The list of EventJson objects.
   */
  private List<EventJson> eventParse(ObservableList<Event> events) {
    List<EventJson> eventJson = new ArrayList<>();
    for (Event e : events) {
      EventJson parsed = new EventJson(e.getName(), e.getDescription(),
          e.getDay(), e.getStartTime().toString(), e.getDuration(), e.getStatus());
      eventJson.add(parsed);
    }
    return eventJson;
  }

  /**
   * Creates a VBox layout for displaying a task.
   *
   * @param task The task to be displayed.
   * @return The created VBox layout.
   */
  private VBox createTask(Task task) {
    VBox parent = new VBox();
    parent.setAlignment(Pos.CENTER);
    Button n4 = new Button("View");
    n4.setMinWidth(50);
    n4.setMaxWidth(50);
    n4.setMinHeight(22);
    n4.setMaxHeight(22);
    HBox n1 = createHbox(task.getName());
    HBox n2 = createHbox(task.getDescription());
    HBox n3 = createHbox(task.getStatus().toString());
    parent.getChildren().addAll(n1, n2, n3, n4);
    n4.setOnAction(e -> makeTaskPop(task));
    return parent;
  }

  /**
   * creates task popup
   *
   * @param t the task to give the popup
   */
  private void makeTaskPop(Task t) {
    popup = new Popup();
    FXMLLoader loader =
        new FXMLLoader(getClass().getClassLoader().getResource("taskPop.fxml"));
    loader.setController(this);
    try {
      taskNameEdit.setText(t.getName());
      taskDescEdit.setText(t.getDescription());
      taskStatEdit.setText(t.getStatus().toString());
      taskDayEdit.setText(t.getDay().toString());
      Scene scene = loader.load();
      AnchorPane content = (AnchorPane) scene.getRoot();
      popup.getContent().add(content);
      popup.show(stage);
      viewTasks.setOnAction(e -> popup.hide());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Extracts task data from the specified AnchorPane and creates a Task object.
   * The extracted data is used to populate the Task object and add it to the list of tasks.
   *
   * @param content The AnchorPane containing the task data.
   */
  private void extractTaskData(AnchorPane content) {
    TextField task = (TextField) content.lookup("#taskName");
    TextArea desc = (TextArea) content.lookup("#taskDescription");
    String taskName = task.getText();
    String description = desc.getText();
    DayLabel selectedDay = days.getValue();
    Complete selectedStatus = status.getValue();
    Task userTask = new Task(taskName, description, selectedDay, selectedStatus);
    checkTaskMax(userTask, selectedDay);
  }


  /**
   * Checks if the maximum number of tasks has been reached for the given day
   * and adds the task to the appropriate list if there is still capacity.
   *
   * @param task the task to be added
   * @param day  the day label for which the task is being added
   * @throws IllegalStateException if an unexpected error occurs while checking the task maximum
   */
  private void checkTaskMax(Task task, DayLabel day) {
    try {
      int val = dayVal(day);
      if (weekdaysTask[val] < maxT) {
        listTasks.add(task);
        addItem(task);
        taskCount++;
        totalTasks.setText(String.valueOf(taskCount));
        weekdaysTask[val]++;
      } else {
        System.out.println("u are at max");
      }
    } catch (Exception e) {
      throw new IllegalStateException("An unexpected error occurred while "
          + "checking the event maximum.");
    }
  }

  /**
   * Extracts the maximum number of tasks from the input field.
   *
   * @return the maximum number of tasks
   */
  private int extractTaskMax() {
    String tasks = maxTasks.getText();
    if (tasks.isEmpty()) {
      return maxT;
    } else {
      maxT = Integer.parseInt(tasks);
      return maxT;
    }
  }

  /**
   * Converts a list of tasks to a list of TaskJson objects.
   *
   * @param tasks The list of tasks to be converted.
   * @return The list of TaskJson objects.
   */
  private List<TaskJson> taskParse(ObservableList<Task> tasks) {
    List<TaskJson> taskJson = new ArrayList<>();
    for (Task t : tasks) {
      TaskJson parsed = new TaskJson(t.getName(), t.getDescription(),
          t.getDay(), t.getStatus());
      taskJson.add(parsed);
    }
    return taskJson;
  }

  /**
   * Populates the taskQueue layout with the specified list of tasks.
   *
   * @param tasks The list of tasks to be displayed in the taskQueue.
   */
  private void taskQueue(ObservableList<Task> tasks) {
    taskQueue.getChildren().clear();
    for (Task t : tasks) {
      VBox queue = createTask(t);
      taskQueue.getChildren().add(queue);
    }
  }

  /**
   * Loads the available themes into the themes ComboBox.
   */
  private void loadTheme() {
    ObservableList<Theme> themeList = FXCollections.observableArrayList(Theme.SUMMER,
        Theme.COOLBLUE, Theme.DARKMODE, Theme.DEFAULT);
    themes.setItems(themeList);

    theme.setThemeSelection(themes.getValue());
    themes.setOnAction(e -> {
      theme.setThemeSelection(themes.getValue());
      this.stage.getScene().getRoot().setStyle(null);
      this.stage.getScene().getStylesheets().clear();
      this.stage.getScene().getStylesheets().add(theme.getThemeFilename());
      changeIcon();
    });
  }

  private Map<String, Color> makeMap() {
    Map<String, Color> map = new HashMap<>();
    map.put("Pink", Color.LIGHTPINK);
    map.put("Orange", Color.LIGHTSALMON);
    map.put("Green", Color.LIGHTGREEN);
    map.put("Blue", Color.LIGHTSKYBLUE);
    map.put("Purple", Color.MEDIUMPURPLE);
    return map;
  }

  /**
   * Sets up the combo box that allows the user to change the background color to make custom themes
   */
  private void loadColors() {
    ObservableList<String> colorList = FXCollections.observableArrayList(
        "Pink", "Orange", "Green", "Blue", "Purple");
    colors.setItems(colorList);

    colors.setOnAction(e -> changeBackgroundColor());
  }

  /**
   * Changes the icon to match the theme
   */
  private void changeIcon() {
    Image newIcon =
        new Image(getClass().getClassLoader().getResourceAsStream(theme.getIconFilename()));
    icon.setImage(newIcon);
  }

  /**
   * Changes the background color of the bullet journal
   */
  private void changeBackgroundColor() {
    BackgroundFill backgroundFill =
        new BackgroundFill(map.get(colors.getValue()), CornerRadii.EMPTY, Insets.EMPTY);

    this.sunday.setBackground(new Background(backgroundFill));
    this.monday.setBackground(new Background(backgroundFill));
    this.tuesday.setBackground(new Background(backgroundFill));
    this.wednesday.setBackground(new Background(backgroundFill));
    this.thursday.setBackground(new Background(backgroundFill));
    this.friday.setBackground(new Background(backgroundFill));
    this.saturday.setBackground(new Background(backgroundFill));
    this.taskQueue.setBackground(new Background(backgroundFill));
    this.stage.getScene().getStylesheets().clear();
    this.stage.getScene().getRoot().setStyle(null);
    String decaCode = map.get(colors.getValue()).toString();
    String hexCode = decaCode.substring(2, 8);
    this.stage.getScene().getRoot().setStyle("-fx-background-color: #" + hexCode);
  }

  /**
   * Sets the tasks for the view.
   *
   * @param tasks The list of tasks to be set.
   */
  public void setTasks(ObservableList<Task> tasks) {
    this.listTasks = tasks;
  }

  /**
   * Sets the events for the view.
   *
   * @param events The list of events to be set.
   */
  public void setEvents(ObservableList<Event> events) {
    this.listEvents = events;
  }

  /**
   * set the max num of events
   *
   * @param max num of events
   */
  public void setEventMax(int max) {
    maxE = max;
  }

  /**
   * Sets the maximum number of tasks.
   *
   * @param max the maximum number of tasks to be set
   */
  public void setTaskMax(int max) {
    maxT = max;
  }

  /**
   * Sets the task count for each weekday.
   *
   * @param max the array containing the task count for each weekday
   */
  public void setTaskCountMax(int[] max) {
    weekdaysTask = max;
  }

  /**
   * Sets the event count for each weekday.
   *
   * @param max the array containing the event count for each weekday
   */
  public void setEventCountMax(int[] max) {
    weekdaysEvent = max;
  }

  /**
   * Sets the notes text to the specified string.
   *
   * @param str the string to be set as the notes text
   */
  public void setNotes(String str) {
    notesText.setText(str);
  }

  /**
   * Sets the task count.
   *
   * @param count the task count to be set
   */
  public void setTaskCount(int count) {
    taskCount = count;
  }

  /**
   * Sets the event count.
   *
   * @param count the event count to be set
   */
  public void setEventCount(int count) {
    eventCount = count;
  }

  /**
   * Returns the stage associated with the application.
   *
   * @return the stage object
   */
  public Stage getStage() {
    return this.stage;
  }

  /**
   * Returns the list of tasks.
   *
   * @return The list of tasks.
   */
  public ObservableList<Task> getListTasks() {
    return listTasks;
  }

  /**
   * Returns the list of events.
   *
   * @return The list of events.
   */
  public ObservableList<Event> getListEvents() {
    return listEvents;
  }

  /**
   * checks which file for the popup
   *
   * @param event on a button click
   * @return return the corresponding popup fxml file
   */
  private String getFxmlFileName(ActionEvent event) {
    if (event.getSource() == newTask) {
      return "taskspopup.fxml";
    } else if (event.getSource() == newEvent) {
      return "eventspopup.fxml";
    } else if (event.getSource() == saveFile) {
      return "saveFile.fxml";
    }
    return null;
  }
}


