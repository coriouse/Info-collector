package app.collector.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.log4j.Logger;

/**
 * Builder plugin
 * 
 * @author Ogarkov.Sergey
 *
 */
public class Builder {

	protected static final Logger LOGGER = Logger.getLogger(Builder.class);

	// name of plugin
	protected String name = "";
	// problem counter
	protected int problem = 0;

	public Builder() {

	}

	protected Collector aggregator;

	/**
	 * init of plugin
	 */
	public void init(String name) {
		this.name = name;
		problemReset();
		try {
			Files.createDirectories(new File(getPluginPath()).toPath());
		} catch (IOException e) {
			LOGGER.error(name, e);
			problem++;
		}
	}

	/**
	 * get plugin path
	 * 
	 * @return
	 */
	public String getPluginPath() {
		return Collector.ROOT_PATH + File.separator + name;
	}

	protected String getStatus(int problem) {
		return problem == 0 ? "OK" : "FAIL";
	}

	protected void problemReset() {
		problem = 0;
	}

	public void report() {
		LOGGER.info(name + " is collected: " + this.getStatus(problem));
	}

}
