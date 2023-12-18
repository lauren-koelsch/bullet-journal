package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The WeekJson record represents a JSON object representing a week in a calendar.
 */

public record WeekJson(@JsonProperty("week") List<DayJson> days,
                       @JsonProperty("theme") String theme,
                       @JsonProperty("icon") String icon,
                       @JsonProperty("Max Events") int event,
                       @JsonProperty("Max Tasks") int tasks,
                       @JsonProperty("Notes/Quotes") String notes,
                       @JsonProperty("Event Day Count") String eventCount,
                       @JsonProperty("Task Day Count") String taskCount,
                       @JsonProperty("Event Total Count") int e,
                       @JsonProperty("Task Total Count") int t) {
}
