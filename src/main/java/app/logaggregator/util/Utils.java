package app.logaggregator.util;

import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Class utils 
 * 
 * @author Ogarkov.Sergey
 *
 */
public class Utils {
	
	
	public static void saveToFile(String file, String content) {
		try (PrintWriter output = new PrintWriter(new FileWriter(file, true))) {
			output.print(content);
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
