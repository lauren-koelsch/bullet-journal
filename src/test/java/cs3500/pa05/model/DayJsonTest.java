package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class DayJsonTest {

  @Test
  public void testNullDay() {

    DayJson day = new DayJson(DayLabel.SATURDAY, null, null);
    DayJson expected = new DayJson(DayLabel.SATURDAY, new ArrayList<>(), new ArrayList<>());

    assertEquals(expected, day);
  }
}