package app.collector.plugin;

import java.io.File;

import app.collector.core.Builder;
import app.collector.core.Plugin;
import app.collector.util.CollectorUtils;
import app.collector.util.CommandExecutor;

/**
 * Plugin class collect system info windows
 * 
 * @author Ogarkov.Sergey
 *
 */
public class SystemInfoWindowsPlugin extends Builder implements Plugin {

	private final static String FILE = "systeminfo.log";
	private final static String COMMAND = "systeminfo";

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
