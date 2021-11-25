package com.leonardo.rocha.wedding.steps;

import com.leonardo.rocha.wedding.WeddingRsvpRegistryApiClient;
import com.leonardo.rocha.wedding.config.CucumberConfig;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = { CucumberConfig.class })
public class WeddingRsvpRegistryScenariosSteps {

    @Autowired
    WeddingRsvpRegistryApiClient weddingRsvpRegistryApiClient;

    @Given("that the Wedding Rsvp Registry App is Running")
    public void that_the_wedding_rsvp_registry_app_is_running() {
        // Write code here that turns the phrase above into concrete actions
        weddingRsvpRegistryApiClient.healthCheck();
    }
    @When("the Wedding Rsvp Registry App receives a valid create Guest request")
    public void the_wedding_rsvp_registry_app_receives_a_valid_create_guest_request() {
        // Write code here that turns the phrase above into concrete actions
        weddingRsvpRegistryApiClient.createGuest("Leo", 5);
    }

    @Then("the Wedding Rsvp Registry App responds with a created status")
    public void the_wedding_rsvp_registry_app_responds_with_a_created_status() {
        // Write code here that turns the phrase above into concrete actions

    }
}
