package com.leonardo.rocha.wedding;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/acceptance/resources/features/WeddingRsvpRegistryScenarios.feature",
                 plugin = {"pretty",
                           "html:build/reports/tests/acceptanceTest/Cucumber-WeddingRsvpRegistry.html",
                           "json:build/reports/tests/acceptanceTest/Cucumber-WeddingRsvpRegistry.json"},
                 monochrome = true)
public class RsvpAcceptanceTest {
}
