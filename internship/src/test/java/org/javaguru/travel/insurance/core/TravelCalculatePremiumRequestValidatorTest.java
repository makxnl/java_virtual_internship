package org.javaguru.travel.insurance.core;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TravelCalculatePremiumRequestValidatorTest {

    TravelCalculatePremiumRequestValidator validator;
    DateTimeService dateTimeService = new DateTimeService();


    @BeforeEach
    public void setUp() {
        validator = new TravelCalculatePremiumRequestValidator(dateTimeService);
    }

    @Test
    void allFieldAsOk() {
        TravelCalculatePremiumRequest request = new TravelCalculatePremiumRequest("Max",
                "Smirnov", new Date(1212121212121L), new Date(1212121212222L));
        assertEquals(true, validator.validate(request).isEmpty());
    }

    @Test
    void firstNameIsNull() {
        TravelCalculatePremiumRequest request = new TravelCalculatePremiumRequest(null,
                "Smirnov", new Date(1212121212121L), new Date(1212121212222L));
        assertEquals("personFirstName", validator.validate(request).get(0).getField());
        assertEquals("Must not be empty!", validator.validate(request).get(0).getMessage());
    }

    @Test
    void firstNameIsEmpty() {
        TravelCalculatePremiumRequest request = new TravelCalculatePremiumRequest("",
                "Smirnov", new Date(1212121212121L), new Date(1212121212222L));
        assertEquals("personFirstName", validator.validate(request).get(0).getField());
        assertEquals("Must not be empty!", validator.validate(request).get(0).getMessage());
    }


    @Test
    void firstAndLastNamesIsEmpty() {
        TravelCalculatePremiumRequest request = new TravelCalculatePremiumRequest("",
                "", new Date(1212121212121L), new Date(1212121212222L));
        assertEquals("personFirstName", validator.validate(request).get(0).getField());
        assertEquals("Must not be empty!", validator.validate(request).get(0).getMessage());
        assertEquals("personLastName", validator.validate(request).get(1).getField());
        assertEquals("Must not be empty!", validator.validate(request).get(1).getMessage());
    }


    @Test
    void dateFromIsNull() {
        TravelCalculatePremiumRequest request = new TravelCalculatePremiumRequest("Max",
                "Smirnov", null, new Date(1212121212222L));
        assertEquals("agreementDateFrom", validator.validate(request).get(0).getField());
        assertEquals("Must not be empty!", validator.validate(request).get(0).getMessage());
    }
}
