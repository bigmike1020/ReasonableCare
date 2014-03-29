package reasonablecare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Table {

  private final List<String> columnNames = new ArrayList<String>();
  private final int[] columnLengths;

  private final List<List<String>> data = new ArrayList<List<String>>();

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

    List<String> row = new ArrayList<String>();
    int column = 0;
    for (Object datum : data) {
      String str = toString(datum);
      row.add(str);
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
      
      List<String> data = this.data.get(row);
      
      for (int column = 0; column < data.size(); ++column) {
        if (column != 0) {
          sb.append("|");
        }

        String datum = data.get(column);
        sb.append(datum);
        sb.append(blanks, 0, columnLengths[column] - datum.length());
      }
      
      sb.append('\n');
    }

    return sb.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((columnNames == null) ? 0 : columnNames.hashCode());
    result = prime * result + ((data == null) ? 0 : data.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof Table))
      return false;
    Table other = (Table) obj;
    if (columnNames == null) {
      if (other.columnNames != null)
        return false;
    } else if (!columnNames.equals(other.columnNames))
      return false;
    if (data == null) {
      if (other.data != null)
        return false;
    } else if (!data.equals(other.data))
      return false;
    return true;
  }

}
