package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The DayJson record represents a JSON representation of a day, including tasks and events.
 */
public record DayJson(@JsonProperty("day") DayLabel day,
                      @JsonProperty("tasks") List<TaskJson> tasks,
                      @JsonProperty("events") List<EventJson> events) {
  /**
   * Constructs a new DayJson object with the specified day, tasks, and events.
   *
   * @param day    The day label associated with the JSON representation.
   * @param tasks  The list of tasks for the day.
   * @param events The list of events for the day.
   */
  public DayJson {
    if (tasks == null) {
      tasks = new ArrayList<>();
    }
    if (events == null) {
      events = new ArrayList<>();
    }
  }

  /**
   * Retrieves the list of tasks for the day.
   *
   * @return The list of tasks.
   */
  public List<TaskJson> tasks() {
    return tasks;
  }
}
