package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *  represents a task in the form of a Json record
 *
 * @param name task name
 * @param desc task description
 * @param day task day
 * @param status task completion status
 */
public record TaskJson(@JsonProperty("name") String name,
                        @JsonProperty("description") String desc,
                        @JsonProperty("day") DayLabel day,
                        @JsonProperty("status") Complete status) {
  /**
   * Returns a string representation of the day object.
   *
   * @return a string representation of the day
   */
  public String dayToString() {
    return day.toString();
  }
}

