package com.bodhivruksha;

import cucumber.api.event.*;

import io.restassured.RestAssured;

public class CucumberEventListener implements ConcurrentEventListener {

    /* Execution priority for cucumber and testng :
    * TestNG’s @BeforeClass
    * → Cucumber’s @Before
    * → Cucumber Background
    * → Cucumber Scenario
    * → Cucumber’s @After
    * → TestNG’s @AfterClass
    * */

    private EventHandler<TestRunStarted> testRunStarted = event -> {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        System.out.println("Cucumber test run started");
    };

    private EventHandler<TestRunFinished> testRunFinished = event -> {
        System.out.println("Cucumber test run finished");
    };


    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestRunStarted.class, testRunStarted);
        eventPublisher.registerHandlerFor(TestRunFinished.class, testRunFinished);
    }
}
