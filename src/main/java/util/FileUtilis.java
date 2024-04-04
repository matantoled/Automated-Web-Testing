package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.log4j.Logger;
import org.testng.Assert;

import applogic.ApplicationManager1;

public class FileUtilis {

	private ApplicationManager1 app;
	private Logger log = Logger.getLogger("SeleniumLog");



	public FileUtilis(ApplicationManager1 app) {
		this.app = app;
	}

	public void initLogger() {
		log = app.getLogger();
	}

	public void createDirectories(String absolutePath) {
		log.info("Created dir: " + absolutePath);
		try {
			File dirs = new File(absolutePath);
			if (!dirs.exists()) {
				dirs.mkdirs();
			}
		} catch (Exception e) {
			log.error("log.error: " + e.getMessage());
		}

	}

	public synchronized static void createFile(String fileName, String fileText) {
		FileWriterWithEncoding fstream;
		try {
			fstream = new FileWriterWithEncoding(fileName, StandardCharsets.UTF_8);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(fileText);
			out.close();
		} catch (IOException e) {
			Assert.fail("Failed to create file: " + fileName);
			e.printStackTrace();
		}
	}


	public  void writeSimpleTextFile(String fileFullPath, String line, boolean append) {

		try {
			FileWriterWithEncoding writer;
			writer = new FileWriterWithEncoding(fileFullPath, StandardCharsets.UTF_8, append);
			writer.write(line + "\n");

			writer.flush();
			writer.close();
		} catch (IOException e) {
			Assert.fail("Failed to write row to file: " + fileFullPath);
			e.printStackTrace();
		}

	}



}