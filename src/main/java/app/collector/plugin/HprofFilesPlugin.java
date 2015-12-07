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
 * Plugin class collect hprof files
 * 
 * @author Ogarkov.Sergey
 *
 */
public class HprofFilesPlugin extends Builder implements Plugin {

	private static final String HPROF = "C:\\Program Files\\Apache Software Foundation\\Tomcat 6.0";

	@Override
	public void make() {
		copyFiles(HPROF);
	}

	private void copyFiles(String source) {
		Path dir = new File(source).toPath();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
			for (Path file : stream) {
				if (file.getFileName().toString().indexOf(".hprof") > -1) {
					Files.copy(file.toFile().toPath(),
							new File(this.getPluginPath() + File.separator + file.getFileName()).toPath(),
							StandardCopyOption.REPLACE_EXISTING);
				}
			}
		} catch (IOException | DirectoryIteratorException e) {
			problem++;
			LOGGER.error(this.name, e);
		}
	}
}
