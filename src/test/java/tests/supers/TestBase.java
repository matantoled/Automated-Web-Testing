package tests.supers;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_AVI;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE;
import static org.monte.media.VideoFormatKeys.QualityKey;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import applogic.ApplicationManager1;
import util.GenUtils;
import util.ParamsUtils;
import util.SelUtils;

public class TestBase {

	protected ApplicationManager1 app;
	protected SelUtils su;
	public Logger log = Logger.getLogger("SeleniumLog");

	protected final String LOG_DIR = "Logs";
	protected final String SCREENSHOT_DIR = "Screenshots";
	protected final String RECORDING_DIR = "Recordings";
	protected final String DOWNLOADS_DIR = "Downloads";
	protected String testName, section, pkg, dirPath, downloadsFolder, externalDataFolder;
	protected String recordingFolder;
	protected Date startTime, endTime;
	protected ScreenRecorder screenRecorder;

	@BeforeMethod
	public void init() throws IOException, AWTException {

		initDriverPath();
		app = new ApplicationManager1(true, testName, pkg);
		initLog4j();
		app.initResources(log);
		startRecording();
		su = app.su;
		startTest();
	}


	protected void initLog4j() {
		log = Logger.getLogger(testName);
		log.removeAllAppenders();

		String ts = GenUtils.getCurrentTimestamp(GenUtils.TIME_FORMAT_SSS);


		String path = app.getParamsUtils().getPropValue(ParamsUtils.WORKSPACE) + File.separator + LOG_DIR + File.separator + pkg + File.separator
				+ testName + ts + ".log";

		URL loggerprops = Thread.currentThread().getContextClassLoader().getResource("log4j.properties");
		PropertyConfigurator.configure(loggerprops);

		try {
			FileAppender appender = new FileAppender(new PatternLayout("%-5p %d{HH:mm:ss} %x (%F:%L) - %m%n"), path, false);
			log.addAppender(appender);
		} catch (IOException ioe) {
			Assert.fail("Failed to open log file: " + path + "\n" + ioe);
		}

		ConsoleAppender ca = new ConsoleAppender();
		ca.setWriter(new OutputStreamWriter(System.out));
		ca.setLayout(new PatternLayout("%-5p %d{HH:mm:ss} %x (%F:%L) - %m%n"));
		log.addAppender(ca);

		log.info("Writing debug log to: " + path);
	}

	protected void initDriverPath() {
		testName = this.getClass().getName().replaceAll(".*\\.", "");
		pkg = this.getClass().getPackage().toString().replaceAll(".*\\.", "");
		String[] pkgArray = this.getClass().getPackage().toString().split("\\.");
		section = pkgArray.length > 1 ? pkgArray[1] : pkgArray[0].substring(8);

		if (System.getProperty("os.name").toLowerCase().matches(".*windows.*")) {
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\Profile\\Windows\\IEDriverServer.exe");
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\Profile\\Windows\\chromedriver.exe");
			System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "\\Profile\\Windows\\MicrosoftWebDriver.exe");
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\Profile\\Windows\\geckodriver.exe");

		} else {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/Profile/Linux/chromedriver");
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/Profile/Linux/geckodriver");
		}
	}

	@AfterMethod
	public synchronized void tearDown() {

		app.stop();

	}

	protected void onTestFailure(Throwable t) {
		log.fatal(t.getMessage());

		captureScreenShot();
		stopRecording();
		try {
			cleanUp();
		} catch (Throwable tNew) {
			log.debug("The test has also failed during clean up!");
			log.fatal(tNew);
			endTestFailure();
			Assert.fail(tNew.getMessage());
		}
		endTestFailure();
		
		throw new RuntimeException(t);
	}

	protected void cleanUp() {

	}

	public void captureScreenShot() {

		String fileName = "Failure screenshot for " + testName + GenUtils.getCurrentTimestamp(GenUtils.TIME_FORMAT_ddMMyyHHmmSSS) + ".png";
		String path = app.getParamsUtils().getPropValue(ParamsUtils.WORKSPACE) + File.separator + SCREENSHOT_DIR + File.separator + pkg
				+ File.separator + fileName;
		app.captureScreenShot(path);

	}

	protected void startTest() {
		startTime = new Date();
		log.info("*************************************************************************");
		log.info("The '" + testName + "' test has started.");
		log.info("Start time: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(startTime.getTime())));
		log.info("*************************************************************************");

	}

	protected void endTestSuccess() {
		stopRecording();

		endTime = new Date();
		log.info("*************************************************************************");
		log.info("The '" + testName + "' test has ended successfully.");
		log.info("Start time: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(startTime.getTime())));
		log.info("End time:       " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(endTime.getTime())) + ".");
		log.info("-------------------------------------------------------------------------");
		log.info("Total run time: " + getTestRunTime());
		log.info("*************************************************************************");

	}

	protected void endTestFailure() {
		endTime = new Date();
		log.info("*************************************************************************");
		log.info("The '" + testName + "' test has ended with error.");
		log.info("Start time: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(startTime.getTime())));
		log.info("End time:       " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(endTime.getTime())) + ".");
		log.info("-------------------------------------------------------------------------");
		log.info("Total run time: " + getTestRunTime());
		log.info("*************************************************************************");
	}

	protected String getTestRunTime() {

		long diff = endTime.getTime() - startTime.getTime();

		long secondInMillis = 1000;
		long minuteInMillis = secondInMillis * 60;
		long hourInMillis = minuteInMillis * 60;
		long dayInMillis = hourInMillis * 24;

		long elapsedDays = diff / dayInMillis;
		diff = diff % dayInMillis;
		long elapsedHours = diff / hourInMillis;
		diff = diff % hourInMillis;
		long elapsedMinutes = diff / minuteInMillis;
		diff = diff % minuteInMillis;
		long elapsedSeconds = diff / secondInMillis;
		diff = diff % secondInMillis;

		return (elapsedDays + " days, " + elapsedHours + " hours, " + elapsedMinutes + " minutes, " + elapsedSeconds + "." + diff + " seconds.");

	}

	// Recordings methods:
	public void startRecording() throws IOException, AWTException {

		if (Boolean.parseBoolean(app.paramsUtils.getPropValue(ParamsUtils.RECORDING))) {

			log.info("Starting to record the test");

			recordingFolder = app.getParamsUtils().getPropValue(ParamsUtils.WORKSPACE) + File.separator + RECORDING_DIR + File.separator + pkg
					+ File.separator + testName;

			app.fileUtilis.createDirectories(recordingFolder);
			log.info("Saving recording to: '" + recordingFolder + "'.");

			GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
			this.screenRecorder = new ScreenRecorder(gc, null, new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
					new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, CompressorNameKey,
							ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey, Rational.valueOf(15), QualityKey, 1.0f,
							KeyFrameIntervalKey, 15 * 60),
					new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)), null,
					new File(recordingFolder));

			this.screenRecorder.start();
		}
	}


	public void stopRecording() {
		if (Boolean.parseBoolean(app.paramsUtils.getPropValue(ParamsUtils.RECORDING))) {

			log.info("Stop the recording of the test");
			try {
				this.screenRecorder.stop();
			} catch (IOException e) {
				e.printStackTrace();
			}
			changeRecordedFileName();
		}
	}

	public void changeRecordedFileName() {
		File dir = new File(recordingFolder);

		File[] files = dir.listFiles();
		if (files.length == 0) {
			return;
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}

		String currTS = GenUtils.getCurrentTimestamp("ddMMyyHHmm");
		File newfile = new File(recordingFolder + File.separator + testName + currTS + ".avi");

		lastModifiedFile.renameTo(newfile);
		log.info("Recording is saved in: " + newfile.getAbsolutePath());
	}
}
