package cs3500.pa05.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * The BujoWriter class is responsible for writing a Bujo file in JSON format.
 */

public class BujoWriter {

  /**
   * Writes the provided WeekJson object to a Bujo file with the specified name.
   *
   * @param week The WeekJson object representing the week's data to be written.
   * @param name The name of the Bujo file to be created.
   * @throws IOException if an I/O error occurs during file writing.
   */
  public void writeBujoFile(WeekJson week, String name) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String jsonString = objectMapper.writeValueAsString(week);
    StringBuilder filepath = new StringBuilder();
    filepath.append("bujoFiles/");
    filepath.append(name);
    filepath.append(".bujo");
    File file = new File(filepath.toString());

    if (file.exists()) {
      updateExistingWeek(file, objectMapper, week);
    } else {
      createNewWeek(file, jsonString);
    }
  }

  /**
   * Updates the existing Bujo file with the new data.
   *
   * @param file         The existing Bujo file.
   * @param objectMapper The ObjectMapper for JSON serialization.
   * @param week         The new WeekJson object.
   * @throws IOException if an I/O error occurs during file writing.
   */
  private void updateExistingWeek(File file, ObjectMapper objectMapper, WeekJson week)
      throws IOException {
    try {
      BujoReader reader = new BujoReader();
      WeekJson prev = reader.readFile(file.toPath().toString());
      WeekJson updatedWeek = updatedWeek(prev, week); // Update the previous week with new data
      String updatedJsonString = objectMapper.writeValueAsString(updatedWeek);
      FileWriter fileWriter = new FileWriter(file);
      fileWriter.write(updatedJsonString);
      fileWriter.close(); // Remember to close the FileWriter after writing
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Creates a new Bujo file with the provided data.
   *
   * @param file       The new Bujo file to be created.
   * @param jsonString The JSON string representing the WeekJson data.
   * @throws IOException if an I/O error occurs during file writing.
   */
  private void createNewWeek(File file, String jsonString) throws IOException {
    try (FileWriter fileWriter = new FileWriter(file)) {
      fileWriter.write(jsonString);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Updates the previous week's data with the new week's data.
   *
   * @param prev  The previous WeekJson object.
   * @param newer The newer WeekJson object with updated data.
   * @return The WeekJson object containing the updated week's data.
   */
  private WeekJson updatedWeek(WeekJson prev, WeekJson newer) {
    List<DayJson> updatedDays = new ArrayList<>();

    for (DayJson newDay : newer.days()) {
      DayJson prevDay = findMatchingDay(prev, newDay);
      if (prevDay != null) {
        List<TaskJson> updatedTasks = updateTasks(prevDay, newDay);
        List<EventJson> updatedEvents = updateEvents(prevDay, newDay);
        DayJson updatedDay = new DayJson(prevDay.day(), updatedTasks, updatedEvents);
        updatedDays.add(updatedDay);
      }
    }

    WeekJson updatedWeek = createUpdatedWeekJson(newer, updatedDays);
    return updatedWeek;
  }

  /**
   * Finds the matching DayJson object in the previous week's data based on the day label.
   *
   * @param prev   The previous WeekJson object.
   * @param newDay The DayJson object from the new week.
   * @return The matching DayJson object from the previous week, or null if not found.
   */
  private DayJson findMatchingDay(WeekJson prev, DayJson newDay) {
    for (DayJson prevDay : prev.days()) {
      if (prevDay.day().name().equals(newDay.day().name())) {
        return prevDay;
      }
    }
    return null;
  }

  /**
   * Updates the tasks in the previous day with the tasks from the new day.
   *
   * @param prevDay The previous DayJson object.
   * @param newDay  The new DayJson object.
   * @return The updated list of TaskJson objects.
   */
  private List<TaskJson> updateTasks(DayJson prevDay, DayJson newDay) {
    List<TaskJson> updatedTasks = new ArrayList<>(prevDay.tasks());

    for (TaskJson task : newDay.tasks()) {
      if (!prevDay.tasks().contains(task)) {
        updatedTasks.add(task);
      }
    }

    return updatedTasks;
  }

  /**
   * Updates the events in the previous day with the events from the new day.
   *
   * @param prevDay The previous DayJson object.
   * @param newDay  The new DayJson object.
   * @return The updated list of EventJson objects.
   */
  private List<EventJson> updateEvents(DayJson prevDay, DayJson newDay) {
    List<EventJson> updatedEvents = new ArrayList<>(prevDay.events());

    for (EventJson event : newDay.events()) {
      if (!prevDay.events().contains(event)) {
        updatedEvents.add(event);
      }
    }

    return updatedEvents;
  }

  /**
   * Creates and returns the updated WeekJson object.
   *
   * @param newer      The newer WeekJson object.
   * @param updatedDays The list of updated DayJson objects.
   * @return The updated WeekJson object.
   */
  private WeekJson createUpdatedWeekJson(WeekJson newer, List<DayJson> updatedDays) {
    return new WeekJson(
        updatedDays,
        newer.theme(),
        newer.icon(),
        newer.event(),
        newer.tasks(),
        newer.notes(),
        newer.eventCount(),
        newer.taskCount(),
        newer.e(),
        newer.t()
    );
  }

}

