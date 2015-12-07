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
 * Plugin class collect installator logs
 * 
 * @author Ogarkov.Sergey
 *
 */
public class LogInstallatorPlugin extends Builder implements Plugin {

	@Override
	public void make() {
		collect();
	}

	private void collect() {
		String workingdirectory = System.getProperty("user.home");
		Path dir = new File(workingdirectory).toPath();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
			for (Path file : stream) {
				if (file.getFileName().toString().startsWith("StateWatcher_logs_")) {
					Files.createDirectories(
							new File(this.getPluginPath() + File.separator + file.getFileName().toString()).toPath());
					copy(file);
				}
			}
		} catch (IOException | DirectoryIteratorException e) {
			problem++;
			LOGGER.error(this.name, e);
		}
	}

	private void copy(Path path) {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
			for (Path file : stream) {
				Files.copy(
						file.toFile().toPath(), new File(this.getPluginPath() + File.separator
								+ path.getFileName().toString() + File.separator + file.getFileName()).toPath(),
						StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException | DirectoryIteratorException e) {
			problem++;
			LOGGER.error(this.name, e);
		}
	}

}
