package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the CalendarItem class.
 */
class CalendarItemTest {

  Event event;
  Task task;
  CalendarItem eventItem;
  CalendarItem taskItem;

  /**
   * Sets up the test fixture before each test method.
   */
  @BeforeEach
  public void setup() {
    event = new Event("Last Day of Class",
        "We will be giving presentations for our final project", DayLabel.THURSDAY,
        LocalTime.of(11, 40), 100, Complete.NOT_COMPLETED);
    eventItem = new Event("Last Day of OH",
        "Last day of OOD office hours", DayLabel.WEDNESDAY,
        LocalTime.of(5, 00), 240, Complete.COMPLETE);
    task = new Task("Turn in pa05", "PA05 final push is due at 1:30pm",
        DayLabel.THURSDAY, Complete.NOT_COMPLETED);
    taskItem = new Task("Turn in alpha release", "PA05 alpha release implementation is due",
        DayLabel.SUNDAY, Complete.COMPLETE);
  }

  /**
   * Tests the getName() method of CalendarItem.
   */
  @Test
  public void testGetName() {

    assertEquals("Last Day of Class", event.getName());
    assertEquals("Last Day of OH", eventItem.getName());
    assertEquals("Turn in pa05", task.getName());
    assertEquals("Turn in alpha release", taskItem.getName());
  }

  /**
   * Tests the getDescription() method of CalendarItem.
   */
  @Test
  public void testGetDescription() {

    assertEquals("We will be giving presentations for our final project", event.getDescription());
    assertEquals("Last day of OOD office hours", eventItem.getDescription());
    assertEquals("PA05 final push is due at 1:30pm", task.getDescription());
    assertEquals("PA05 alpha release implementation is due", taskItem.getDescription());
  }

  /**
   * Tests the getDay() method of CalendarItem.
   */
  @Test
  public void testGetDay() {

    assertEquals(DayLabel.THURSDAY, event.getDay());
    assertEquals(DayLabel.WEDNESDAY, eventItem.getDay());
    assertEquals(DayLabel.THURSDAY, task.getDay());
    assertEquals(DayLabel.SUNDAY, taskItem.getDay());
  }

  /**
   * Tests the getStatus() method of CalendarItem.
   */
  @Test
  public void testGetStatus() {

    assertEquals(Complete.NOT_COMPLETED, event.getStatus());
    assertEquals(Complete.COMPLETE, eventItem.getStatus());
    assertEquals(Complete.NOT_COMPLETED, task.getStatus());
    assertEquals(Complete.COMPLETE, taskItem.getStatus());
  }
}