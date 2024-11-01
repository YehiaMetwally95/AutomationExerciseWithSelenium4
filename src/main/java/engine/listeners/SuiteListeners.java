package engine.listeners;

import engine.managers.PropertiesManager;
import lombok.SneakyThrows;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.TestNG;

import java.io.File;

import static engine.loggers.AllureReportLogger.generateAllureReport;
import static engine.utilities.DeleteDirectoryFiles.deleteFiles;

public class SuiteListeners implements ISuiteListener {

    public void onStart (ISuite suite) {

        //Clear Old Screenshots & Allure Results before Every Run
        File file1 = new File("src/main/resources/screenshots");
        File file2 = new File("target/allure-results");
        deleteFiles(file1);
        deleteFiles(file2);
    }

    public void onFinish (ISuite suite) {
        //Genarate the Allure Report after Suite Run
        generateAllureReport();
    }

}
