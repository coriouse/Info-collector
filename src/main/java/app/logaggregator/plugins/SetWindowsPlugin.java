package app.logaggregator.plugins;

import java.io.File;
import app.logaggregator.core.Plugin;
import app.logaggregator.core.Builder;
import app.logaggregator.util.CommandExecutor;
import app.logaggregator.util.Utils;

public class SetWindowsPlugin extends Builder implements Plugin {
	
	private final static String FILE = "set.log";
	private final static String COMMAND = "set";

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
