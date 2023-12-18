package cs3500.pa05.model;

import java.time.LocalTime;
import javafx.scene.control.Button;

/**
 * The Event class represents an event in a calendar.
 * It extends the CalendarItem class and adds properties specific to events.
 */
public class Event extends CalendarItem {

  private LocalTime startTime;
  private int duration;

  /**
   * Constructs an Event object with the specified properties.
   *
   * @param name        The name of the event.
   * @param description The description of the event.
   * @param day         The day of the event.
   * @param startTime   The start time of the event.
   * @param duration    The duration of the event in minutes.
   * @param status      The completion status of the event.
   */
  public Event(String name, String description, DayLabel day,
               LocalTime startTime, int duration, Complete status) {
    super(name, description, day, status);
    this.startTime = startTime;
    this.duration = duration;
  }

  /**
   * Returns the start time of the event.
   *
   * @return The start time of the event.
   */
  public LocalTime getStartTime() {
    return startTime;
  }

  /**
   * Returns the duration of the event in minutes.
   *
   * @return The duration of the event.
   */
  public int getDuration() {
    return duration;
  }

}
