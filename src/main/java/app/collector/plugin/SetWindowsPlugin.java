package app.collector.plugin;

import java.io.File;

import app.collector.core.Builder;
import app.collector.core.Plugin;
import app.collector.util.CollectorUtils;
import app.collector.util.CommandExecutor;

public class SetWindowsPlugin extends Builder implements Plugin {

	private final static String FILE = "set.log";
	private final static String COMMAND = "set";

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
