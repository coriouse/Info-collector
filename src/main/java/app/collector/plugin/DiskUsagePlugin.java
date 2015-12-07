package app.collector.plugin;

import java.io.File;

import app.collector.core.Builder;
import app.collector.core.Plugin;
import app.collector.util.CollectorUtils;
import app.collector.util.CommandExecutor;

/**
 * Plugin class collect disk size usage
 * 
 * @author Ogarkov.Sergey
 *
 */
public class DiskUsagePlugin extends Builder implements Plugin {

	private final static String FILE = "diskusage.log";
	private final static String COMMAND = "wmic logicaldisk get size,freespace,caption";

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
