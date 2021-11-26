package com.leonardo.rocha.wedding.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.leonardo.rocha.wedding.client.WeddingRsvpRegistryApiClient;
import com.leonardo.rocha.wedding.config.CucumberConfig;
import com.leonardo.rocha.wedding.data.Guest;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@CucumberContextConfiguration
@ContextConfiguration(classes = { CucumberConfig.class })
public class WeddingRsvpRegistryScenariosSteps {

    @Autowired
    WeddingRsvpRegistryApiClient weddingRsvpRegistryApiClient;

    @Autowired
    ObjectWriter objectWriter;

    Guest guest;

    Scenario scenario;

    @Before
    public void setUp(Scenario scenario){
        this.scenario = scenario;
    }

    @Given("that the Wedding Rsvp Registry App is Running")
    public void that_the_wedding_rsvp_registry_app_is_running() {
        // Write code here that turns the phrase above into concrete actions
        assert weddingRsvpRegistryApiClient.healthCheck();
    }
    @When("the Wedding Rsvp Registry App receives a valid create Guest request")
    public void the_wedding_rsvp_registry_app_receives_a_valid_create_guest_request() {
        // Write code here that turns the phrase above into concrete actions
        guest = weddingRsvpRegistryApiClient.createGuest("Leo", 5);

    }

    @Then("the Wedding Rsvp Registry App responds with a created status")
    public void the_wedding_rsvp_registry_app_responds_with_a_created_status() throws JsonProcessingException {
        // Write code here that turns the phrase above into concrete actions
        scenario.log("Received the following response \n" + objectWriter.writeValueAsString(guest));
        assert Objects.nonNull(guest);
    }
}
