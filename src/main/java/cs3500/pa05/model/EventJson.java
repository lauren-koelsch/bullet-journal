package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalTime;

/**
 *  represents an event in json record form
 *
 * @param name event name
 * @param desc event description
 * @param day event day
 * @param time event start time
 * @param duration event duration
 * @param status event completion status
 */
public record EventJson(@JsonProperty("name") String name,
                        @JsonProperty("description") String desc,
                        @JsonProperty("day") DayLabel day,
                        @JsonProperty("time")String time,
                        @JsonProperty("duration") int duration,
                        @JsonProperty("status") Complete status) {
  /**
   * Returns a string representation of the day.
   *
   * @return The string representation of the day.
   */
  public String dayToString() {
    return day.toString();
  }
}
