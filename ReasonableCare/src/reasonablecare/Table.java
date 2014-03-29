package reasonablecare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Table {

  private final List<String> columnNames = new ArrayList<String>();
  private final int[] columnLengths;

  private final List<String[]> data = new ArrayList<String[]>();

  public Table(String col1, String... cols) {
    columnNames.add(col1);
    Collections.addAll(columnNames, cols);
    columnLengths = new int[columnNames.size()];

    // Get the length of each header
    int column = 0;
    for (String header : columnNames) {
      columnLengths[column] = Math.max(columnLengths[column], header.length());
      ++column;
    }
  }

  public void add(Object... data) {
    if (data.length != columnNames.size()) {
      throw new RuntimeException("Incorrect number of columns");
    }

    String[] row = new String[data.length];
    int column = 0;
    for (Object datum : data) {
      String str = toString(datum);
      row[column] = str;
      columnLengths[column] = Math.max(columnLengths[column], str.length());

      ++column;
    }
    this.data.add(row);
  }

  private static String toString(Object obj) {
    return (obj == null) ? "null" : obj.toString();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    char[] blanks = new char[1000];
    Arrays.fill(blanks, ' ');

    // Print headers
    for (int column = 0; column < columnNames.size(); ++column) {
      if (column != 0) {
        sb.append("|");
      }

      String header = columnNames.get(column);
      sb.append(header);
      sb.append(blanks, 0, columnLengths[column] - header.length());
    }
    sb.append('\n');

    // Print data
    for (int row = 0; row < this.data.size(); ++row) {
      
      String[] data = this.data.get(row);
      
      for (int column = 0; column < data.length; ++column) {
        if (column != 0) {
          sb.append("|");
        }

        String datum = data[column];
        sb.append(datum);
        sb.append(blanks, 0, columnLengths[column] - datum.length());
      }
      
      sb.append('\n');
    }

    return sb.toString();
  }

}
