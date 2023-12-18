package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Event class.
 */
class EventTest {

  Event event;

  /**
   * Sets up the test fixture before each test method.
   */
  @BeforeEach
  public void setup() {
    event = new Event("Last Day of Class",
        "We will be giving presentations for our final project", DayLabel.THURSDAY,
        LocalTime.of(11, 40), 100, Complete.NOT_COMPLETED);
  }

  /**
   * Tests the getters of the Event class.
   */
  @Test
  public void testGetters() {

    assertEquals(LocalTime.of(11, 40), event.getStartTime());

    assertEquals(100, event.getDuration());
  }
}