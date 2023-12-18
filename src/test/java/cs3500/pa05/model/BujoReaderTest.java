package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the BujoReader class.
 */
class BujoReaderTest {

  /**
   * Tests the readFile method of the BujoReader class.
   *
   * @throws IOException if an I/O error occurs.
   */
  @Test
  public void testReadBujoFile() throws IOException {

    TaskJson task = new TaskJson("Turn in pa05", "PA05 final push is due at 1:30pm",
        DayLabel.THURSDAY, Complete.NOT_COMPLETED);

    EventJson event = new EventJson("Last Day of Class",
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

    WeekJson wj = new WeekJson(dayList, "theme1.css", "blueCalendar.png",
        2, 2, "Buuujo",
        "[0, 0, 0, 0, 1, 0, 0]", "[0, 0, 0, 0, 1, 0, 0]", 1, 1);


    BujoReader reader = new BujoReader();
    WeekJson week = reader.readFile("bujoFiles/TEST.bujo");

    assertEquals(wj, week);
  }
}