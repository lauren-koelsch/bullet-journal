package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The BujoWriter class provides functionality to write a WeekJson object to a file.
 */
class BujoWriterTest {

  BujoWriter bujoWriter;
  String name;
  String invalid;
  TaskJson task;
  EventJson event;
  WeekJson wj;

  @BeforeEach
  public void setup() {
    task = new TaskJson("Turn in pa05", "PA05 final push is due at 1:30pm",
        DayLabel.THURSDAY, Complete.NOT_COMPLETED);

    event = new EventJson("Last Day of Class",
        "We will give presentations", DayLabel.THURSDAY,
        "11:40", 100, Complete.NOT_COMPLETED);

    List<TaskJson> taskList = new ArrayList<>();
    taskList.add(task);

    List<EventJson> eventList = new ArrayList<>();
    eventList.add(event);

    List<TaskJson> emptyTaskList = new ArrayList<>();

    List<EventJson> emptyEventList = new ArrayList<>();

    DayJson sunday = new DayJson(DayLabel.SUNDAY, emptyTaskList, emptyEventList);
    DayJson monday = new DayJson(DayLabel.MONDAY, emptyTaskList, emptyEventList);
    DayJson tuesday = new DayJson(DayLabel.TUESDAY, emptyTaskList, emptyEventList);
    DayJson wednesday = new DayJson(DayLabel.WEDNESDAY, emptyTaskList, emptyEventList);
    DayJson thursday = new DayJson(DayLabel.THURSDAY, taskList, eventList);
    DayJson friday = new DayJson(DayLabel.FRIDAY, emptyTaskList, emptyEventList);
    DayJson saturday = new DayJson(DayLabel.SATURDAY, emptyTaskList, emptyEventList);

    List<DayJson> dayList = new ArrayList<>();
    dayList.add(wednesday);
    dayList.add(monday);
    dayList.add(thursday);
    dayList.add(sunday);
    dayList.add(tuesday);
    dayList.add(friday);
    dayList.add(saturday);

    wj = new WeekJson(dayList, "theme1.css", "blueCalendar.png",
        2, 2, "Buuujo",
        "[0, 0, 0, 0, 1, 0, 0]", "[0, 0, 0, 0, 1, 0, 0]", 1, 1);

    bujoWriter = new BujoWriter();

    name = "test";
    invalid = "beach";
  }

  /**
   * Writes a WeekJson object to a file with the specified name.
   *
   * @throws IOException if an I/O error occurs during file writing
   */
  @Test
  void testWriteBujoFile() {

    try {
      // Call the writeBujoFile method
      File file = new File("bujoFiles/" + name + ".bujo");
      bujoWriter.writeBujoFile(wj, name);
      assertTrue(file.exists());

    } catch (IOException e) {
      // Handle any exceptions that may occur during the test
      Assertions.fail("An unexpected exception occurred: " + e.getMessage());
    }
  }
}
