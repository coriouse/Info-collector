package app.logaggregator.plugins;

import java.io.File;
import app.logaggregator.core.Plugin;
import app.logaggregator.core.Builder;
import app.logaggregator.util.CommandExecutor;
import app.logaggregator.util.Utils;

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
			Utils.saveToFile(this.getPluginPath()+File.separator+FILE, CommandExecutor.launch(COMMAND));
		} catch (Exception e) {
			problem++;
			LOGGER.error(this.name, e);
		}
	}
}
