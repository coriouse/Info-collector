package app.logaggregator.plugins;

import java.io.File;
import app.logaggregator.core.Plugin;
import app.logaggregator.core.Builder;
import app.logaggregator.util.CommandExecutor;
import app.logaggregator.util.Utils;

/**
 * Plugin class collect time zone info
 * 
 * @author Ogarkov.Sergey
 *
 */
public class ChekingTimeZonePlugin extends Builder implements Plugin{

	private final static String FILE = "tz.log";
	private final static String COMMAND = "java -jar "+ChekingTimeZonePlugin.class.getResource("/tzupdater.jar").getPath().substring(1, ChekingTimeZonePlugin.class.getResource("/tzupdater.jar").getPath().length())+" -t";
	
	@Override
	public void make() {
		execute();
	}
	
	private void  execute(){
		try {
			Utils.saveToFile(this.getPluginPath()+File.separator+FILE, CommandExecutor.launch(COMMAND));
		} catch (Exception e) {
			problem++;
			LOGGER.error(this.name, e);
		}
	}
}
