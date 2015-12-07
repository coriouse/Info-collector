package app.collector.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * Class executor of plugins
 * 
 * @author Ogarkov.Sergey
 *
 */
public class Collector {

	private static final Logger LOGGER = Logger.getLogger(Collector.class);

	private final static List<Plugin> plugins = new ArrayList<Plugin>();

	public final static String ROOT_PATH = Paths.get("").toAbsolutePath().toString() + File.separator + "statictics";
	public final static String ZIP_PATH = Paths.get("").toAbsolutePath().toString() + File.separator + "statictics.zip";

	public Collector() {
		// before delete files
		clean();
		try {
			Files.createDirectories(new File(ROOT_PATH).toPath());
		} catch (IOException e) {
			LOGGER.error(e);
		}
		prepare();
	}

	private void clean() {
		File dir = new File(ROOT_PATH);
		File zip = new File(ZIP_PATH);

		if (dir.exists()) {
			try {
				Path directory = dir.toPath();
				Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						Files.delete(file);
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
						Files.delete(dir);
						return FileVisitResult.CONTINUE;
					}
				});
			} catch (IOException e) {
				LOGGER.error(e);
			}
		}

		if (zip.exists()) {
			try {
				Files.delete(zip.toPath());
			} catch (IOException e) {
				LOGGER.error(e);
			}
		}
	}

	private void addPlugin(Plugin plugin) {
		plugins.add(plugin);
	}

	public void collect() {
		for (Plugin plugin : plugins) {
			plugin.make();
			plugin.report();
		}
		zip();
	}

	private void zip() {
		String destination = ROOT_PATH + ".zip";
		String source = ROOT_PATH;
		try {
			ZipFile zipFile = new ZipFile(destination);
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			zipFile.addFolder(source, parameters);
		} catch (ZipException e) {
			LOGGER.error(e);
		}
	}

	private void prepare() {
		DocumentBuilderFactory factory = null;
		;
		DocumentBuilder builder = null;
		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			LOGGER.error(e);
		}

		try {
			Document document = builder.parse(ClassLoader.getSystemResourceAsStream("plugins.xml"));
			NodeList nodeListPlugins = document.getElementsByTagName("plugin");
			for (int i = 0; i < nodeListPlugins.getLength(); i++) {
				try {

					Class<?> clazz = Class.forName(nodeListPlugins.item(i).getTextContent());
					Object o = clazz.newInstance();
					Plugin p = (Plugin) o;
					p.init(nodeListPlugins.item(i).getAttributes().getNamedItem("name").getNodeValue());
					addPlugin(p);
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | DOMException e) {
					LOGGER.error(e);
				}
			}
		} catch (SAXException | IOException e) {
			LOGGER.error(e);
		}
	}
}
