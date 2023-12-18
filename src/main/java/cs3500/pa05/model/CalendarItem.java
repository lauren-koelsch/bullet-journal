package cs3500.pa05.model;

/**
 * The CalendarItem class represents an abstract calendar item.
 */
public abstract class CalendarItem {
  String name;
  String description;
  DayLabel day;
  Complete status;
  int max;

  /**
   * Constructs a new CalendarItem object with the specified name, description, day, and status.
   *
   * @param name        The name of the calendar item.
   * @param description The description of the calendar item.
   * @param day         The day label associated with the calendar item.
   * @param status      The completion status of the calendar item.
   */
  public CalendarItem(String name, String description, DayLabel day, Complete status) {
    this.name = name;
    this.description = description;
    this.day = day;
    this.status = status;
    this.max = max;
  }

  /**
   * Retrieves the day label associated with the calendar item.
   *
   * @return The day label of the calendar item.
   */
  public DayLabel getDay() {
    return this.day;
  }

  /**
   * Retrieves the name of the calendar item.
   *
   * @return The name of the calendar item.
   */
  public String getName() {
    return name;
  }

  /**
   * Retrieves the completion status of the calendar item.
   *
   * @return The completion status of the calendar item.
   */
  public Complete getStatus() {
    return status;
  }

  /**
   * Retrieves the description of the calendar item.
   *
   * @return The description of the calendar item.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Retrieves the amx
   *
   * @return max.
   */
  public int getMax() {
    return max;
  }
}
