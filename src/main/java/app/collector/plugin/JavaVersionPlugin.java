package app.collector.plugin;

import java.io.File;

import app.collector.core.Builder;
import app.collector.core.Plugin;
import app.collector.util.CollectorUtils;
import app.collector.util.CommandExecutor;

/**
 * Plugin class collect java version info
 * 
 * @author Ogarkov.Sergey
 *
 */
public class JavaVersionPlugin extends Builder implements Plugin {

	private final static String FILE = "javaversion.log";
	private final static String COMMAND = "echo %JAVA_HOME%";

	@Override
	public void make() {
		execute();
	}

	private void execute() {
		try {
			CollectorUtils.saveToFile(this.getPluginPath() + File.separator + FILE, CommandExecutor.launch(COMMAND));
		} catch (Exception e) {
			problem++;
			LOGGER.error(this.name, e);
		}
	}
}
