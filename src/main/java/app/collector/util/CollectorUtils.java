package app.collector.util;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

/**
 * Class utils
 * 
 * @author Ogarkov.Sergey
 *
 */
public class CollectorUtils {

	private static final Logger LOGGER = Logger.getLogger(CollectorUtils.class);

	public static void saveToFile(String file, String content) {
		try (PrintWriter output = new PrintWriter(new FileWriter(file, true))) {
			output.print(content);
			output.flush();
		} catch (Exception e) {
			LOGGER.error(e);
		}
	}

}
