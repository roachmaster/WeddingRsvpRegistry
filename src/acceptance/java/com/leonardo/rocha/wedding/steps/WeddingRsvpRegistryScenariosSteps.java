package com.leonardo.rocha.wedding.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.leonardo.rocha.wedding.client.WeddingRsvpRegistryApiClient;
import com.leonardo.rocha.wedding.config.CucumberConfig;
import com.leonardo.rocha.wedding.data.Guest;
import com.leonardo.rocha.wedding.service.GuestDB;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@CucumberContextConfiguration
@ContextConfiguration(classes = { CucumberConfig.class }, initializers = {ConfigDataApplicationContextInitializer.class})
@TestPropertySource(properties = {"classpath:/"})
@ActiveProfiles("k3s")
public class WeddingRsvpRegistryScenariosSteps {

    @Autowired
    WeddingRsvpRegistryApiClient weddingRsvpRegistryApiClient;

    @Autowired
    ObjectWriter objectWriter;

    @Autowired
    GuestDB guestDB;

    Guest expectedGuest;

    Guest actualGuest;

    List<Guest> expectedGuests;

    List<Guest> actualGuests;

    Scenario scenario;

    @Before
    public void setUp(Scenario scenario){
        this.scenario = scenario;
        this.guestDB.deleteGuests();
        this.actualGuest = null;
        this.expectedGuest = null;
    }

    @Given("that the Wedding Rsvp Registry App is Running")
    public void that_the_wedding_rsvp_registry_app_is_running() {
        assert weddingRsvpRegistryApiClient.healthCheck();
    }

    @When("the Wedding Rsvp Registry App receives a valid create Guest request")
    public void the_wedding_rsvp_registry_app_receives_a_valid_create_guest_request() {
        actualGuest = weddingRsvpRegistryApiClient.createGuest("Leo", 5);
    }

    @Then("the Wedding Rsvp Registry App responds with a created status")
    public void the_wedding_rsvp_registry_app_responds_with_a_created_status() throws JsonProcessingException {
        scenario.log("Received the following response \n" + objectWriter.writeValueAsString(actualGuest));
        assert Objects.nonNull(actualGuest);
    }

    @And("the Wedding Rsvp Registry App saves the Guest")
    public void the_wedding_rsvp_registry_app_saves_the_guest() throws JsonProcessingException {
        Guest savedGuest = this.guestDB.getGuest(actualGuest.getName());
        scenario.log("Received the following response from DB \n" + objectWriter.writeValueAsString(savedGuest));
        assert actualGuest.equals(savedGuest);
    }

    @Given("the Wedding Rsvp Registry App has a saved Guest")
    public void the_wedding_rsvp_registry_app_has_a_saved_guest() throws JsonProcessingException {
        this.expectedGuest = this.guestDB.createGuest("Emily", 3);
        scenario.log("Received the following response from DB \n" + objectWriter.writeValueAsString(expectedGuest));
    }

    @When("the Wedding Rsvp Registry App receives a valid get Guest request")
    public void the_wedding_rsvp_registry_app_receives_a_valid_get_guest_request() {
        this.actualGuest = this.weddingRsvpRegistryApiClient.getGuest("Emily");
    }

    @Then("the Wedding Rsvp Registry App responds with the saved Guest")
    public void the_wedding_rsvp_registry_app_responds_with_the_saved_guest() throws JsonProcessingException {
        scenario.log("Received the following response \n" + objectWriter.writeValueAsString(actualGuest));
        assert expectedGuest.equals(actualGuest);
    }

    @Given("the Wedding Rsvp Registry App has saved Guests")
    public void the_wedding_rsvp_registry_app_has_a_saved_guests() throws JsonProcessingException {
        expectedGuests = new ArrayList<>();
        Guest guest1 = this.guestDB.createGuest("Max", 2);
        Guest guest2 = this.guestDB.createGuest("Kahlua",6);
        expectedGuests.add(guest1);
        expectedGuests.add(guest2);
        scenario.log("Received the following response from DB \n" + objectWriter.writeValueAsString(expectedGuests));
    }

    @When("the Wedding Rsvp Registry App receives a valid get all Guests request")
    public void the_wedding_rsvp_registry_app_receives_a_valid_get_all_guests_request() {
        actualGuests = this.weddingRsvpRegistryApiClient.getGuests();
    }
    
    @Then("the Wedding Rsvp Registry App responds with all the saved Guests")
    public void the_wedding_rsvp_registry_app_responds_with_all_the_saved_guests() throws JsonProcessingException {
        scenario.log("Received the following response \n" + objectWriter.writeValueAsString(actualGuests));
        assert expectedGuests.equals(actualGuests);
    }
}
