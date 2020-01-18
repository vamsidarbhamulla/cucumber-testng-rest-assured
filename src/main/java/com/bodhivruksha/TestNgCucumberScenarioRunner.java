package com.bodhivruksha;

import io.cucumber.testng.CucumberFeatureWrapper;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.PickleEventWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@CucumberOptions(
        features = "features",
        glue = {"com.bodhivruksha.stepdefs"},
        plugin = { "com.bodhivruksha.CucumberEventListener",
                "html:target/cucumberHtmlReport",
                "json:target/cucumber-report.json" }
)
public class TestNgCucumberScenarioRunner {
    private TestNGCucumberRunner testNGCucumberRunner;

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "scenarios")
    public void feature(PickleEventWrapper pickelEventWrapper,
                        CucumberFeatureWrapper cucumberFeature) throws Throwable {
        testNGCucumberRunner.runScenario(pickelEventWrapper.getPickleEvent());
    }

    @DataProvider
    public Object[][] scenarios() {
        return testNGCucumberRunner.provideScenarios() ;
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        testNGCucumberRunner.finish();
    }

}
