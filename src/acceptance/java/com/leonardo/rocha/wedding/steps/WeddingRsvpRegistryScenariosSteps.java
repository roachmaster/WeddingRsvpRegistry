package com.leonardo.rocha.wedding.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.leonardo.rocha.wedding.client.WeddingRsvpRegistryApiClient;
import com.leonardo.rocha.wedding.config.CucumberConfig;
import com.leonardo.rocha.wedding.data.DeleteAllResponse;
import com.leonardo.rocha.wedding.data.DeleteGuestResponse;
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
import static org.junit.Assert.*;
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

    DeleteGuestResponse actualDeleteGuestResponse;

    DeleteAllResponse actualDeleteAllResponse;

    Scenario scenario;

    @Before
    public void setUp(Scenario scenario){
        this.scenario = scenario;
        this.guestDB.deleteGuests();
        this.actualGuest = null;
        this.actualGuests = null;
        this.expectedGuest = null;
        this.expectedGuests = null;
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
        assertNotNull(actualGuest);
    }

    @And("the Wedding Rsvp Registry App saves the Guest")
    public void the_wedding_rsvp_registry_app_saves_the_guest() throws JsonProcessingException {
        Guest savedGuest = this.guestDB.getGuest(actualGuest.getName());
        scenario.log("Received the following response from DB \n" + objectWriter.writeValueAsString(savedGuest));
        assertEquals(savedGuest,actualGuest);
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
        assertEquals(expectedGuest, actualGuest);
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
        assertEquals(expectedGuests,actualGuests);
    }

    @When("the Wedding Rsvp Registry App receives a valid update Guest request")
    public void the_wedding_rsvp_registry_app_receives_a_valid_update_guest_request() {
        actualGuest = this.weddingRsvpRegistryApiClient.updateGuest("Emily",true,2);
    }

    @Then("the Wedding Rsvp Registry App responds with the updated Guest")
    public void the_wedding_rsvp_registry_app_responds_with_the_updated_guest() throws JsonProcessingException {
        expectedGuest = this.guestDB.getGuest("Emily");
        scenario.log("Received the following response \n" + objectWriter.writeValueAsString(actualGuest));
        assertEquals(expectedGuest,actualGuest);
    }

    @When("the Wedding Rsvp Registry App receives a valid delete Guest request")
    public void the_wedding_rsvp_registry_app_receives_a_valid_delete_guest_request() {
        actualDeleteGuestResponse = this.weddingRsvpRegistryApiClient.deleteGuest("Emily");
    }

    @Then("the Wedding Rsvp Registry App responds with the Guest deleted")
    public void the_wedding_rsvp_registry_app_responds_with_the_guest_deleted() throws JsonProcessingException {
        scenario.log("Received the following response \n" + objectWriter.writeValueAsString(actualDeleteGuestResponse));
        assertEquals(1L, actualDeleteGuestResponse.getNumOfGuestsDeleted());
    }

    @When("the Wedding Rsvp Registry App receives a valid delete all Guests request")
    public void the_wedding_rsvp_registry_app_receives_a_valid_delete_all_guests_request() {
        actualDeleteAllResponse = this.weddingRsvpRegistryApiClient.deleteGuests();
    }

    @Then("the Wedding Rsvp Registry App responds with the delete all guests")
    public void the_wedding_rsvp_registry_app_responds_with_the_delete_all_guests() throws JsonProcessingException {
        scenario.log("Received the following response \n" + objectWriter.writeValueAsString(actualDeleteAllResponse));
        assertEquals(0L,actualDeleteAllResponse.getNumOfGuests());
    }
}
