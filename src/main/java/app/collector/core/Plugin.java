package app.collector.core;

public interface Plugin {
	/**
	 * execute some plugin task
	 */
	public void make();

	/**
	 * report of the status finished
	 */
	public void report();

	/**
	 * plugin's initialization.
	 * 
	 * @param name
	 *            - name of plugin
	 */
	public void init(String name);

}
