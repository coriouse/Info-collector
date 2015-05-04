package app.logaggregator.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Class launcher of windows commands 
 * 
 * @author Ogarkov.Sergey
 *
 */
public class CommandExecutor {

	public static String launch(String command) throws Exception {

		StringBuilder sb = new StringBuilder();
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec("cmd.exe /c" + command);
			// InputStream stderr = proc.getErrorStream();
			InputStream std = proc.getInputStream();
			ExecutorService pool = Executors.newFixedThreadPool(2);
			// sb.append(pool.submit(new StreamReader(stderr)).get());
			sb.append(pool.submit(new StreamReader(std)).get());
			pool.shutdown();
			int exitVal = proc.waitFor();
			if (exitVal == 1) {
				//System.out.println("Error");
			} 
		return sb.toString();
	}

	public static class StreamReader implements Callable<String> {
		private InputStream is;
		private static Semaphore outputSem;

		public StreamReader(InputStream is) {
			this.is = is;
			outputSem = new Semaphore(1);
			try {
				outputSem.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public String call() {
			StringBuilder sb = new StringBuilder();
			try {
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\r\n");
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} finally {
				outputSem.release();
			}
			return sb.toString();
		}
	}
}
