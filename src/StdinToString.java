import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class StdinToString {
  public static String read() throws IOException {
    StringBuilder result = new StringBuilder();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    int c;
    while ((c = reader.read()) != -1) {
        result.append((char)c);
    }
    reader.close();
    return result.toString();
  }
}