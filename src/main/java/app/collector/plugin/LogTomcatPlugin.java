package app.collector.plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import app.collector.core.Builder;
import app.collector.core.Plugin;

/**
 * Plugin class collect tomcat logs
 * 
 * @author Ogarkov.Sergey
 *
 */
public class LogTomcatPlugin extends Builder implements Plugin {

	private final static String LOGS = "C:\\Program Files\\Apache Software Foundation\\Tomcat 6.0\\logs";

	@Override
	public void make() {
		copyFiles(LOGS);
	}

	private void copyFiles(String source) {
		Path dir = new File(source).toPath();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
			for (Path file : stream) {
				Files.copy(file.toFile().toPath(),
						new File(this.getPluginPath() + File.separator + file.getFileName()).toPath(),
						StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException | DirectoryIteratorException x) {
			problem++;
			LOGGER.error(this.name, x);
		}
	}
}
