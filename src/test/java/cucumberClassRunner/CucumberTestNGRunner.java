package cucumberClassRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/FeatureFiles"
        , glue = "cucumberstepDefinitions"
        , plugin = {"pretty","io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"})

public class CucumberTestNGRunner extends AbstractTestNGCucumberTests {
}
