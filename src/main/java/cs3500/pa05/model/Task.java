package cs3500.pa05.model;

/**
 * represent a task that the user needs to complete
 */
public class Task extends CalendarItem {

  /**
   * Constructs a Task object with the specified name, description, day, and completion status.
   * Initializes the parent class (BaseTask) using the provided parameters.
   *
   * @param name        the name of the task
   * @param description the description of the task
   * @param day         the day associated with the task
   * @param status      the completion status of the task
   */
  public Task(String name, String description, DayLabel day, Complete status) {
    super(name, description, day, status);
  }
}
