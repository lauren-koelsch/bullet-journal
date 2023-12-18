package cs3500.pa05.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.WeekJson;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * represents a filereader for the bujo files
 */
public class BujoReader {

  /**
   * Reads a WeekJson object from the specified file.
   *
   * @param path The path of the file to read.
   * @return The WeekJson object read from the file.
   * @throws IOException If an I/O error occurs while reading the file.
   */
  public WeekJson readFile(String path) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    File file = new File(path);
    WeekJson weekJson = objectMapper.readValue(file, WeekJson.class);
    return weekJson;
  }
}
