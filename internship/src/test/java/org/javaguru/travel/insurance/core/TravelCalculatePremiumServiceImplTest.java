package org.javaguru.travel.insurance.core;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumResponse;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TravelCalculatePremiumServiceImplTest {

    @Mock
    private static TravelCalculatePremiumRequestValidator validator;
    @Mock
    private static TravelPremiumUnderwriting underwriting;

    @InjectMocks
    private static TravelCalculatePremiumServiceImpl service;

    private static TravelCalculatePremiumRequest request;

    private List<ValidationError> buildValidationErrorList() {
        return List.of(
                new ValidationError("field", "errorMessage")
        );
    }

    private Date createDate(String dateStr) {
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    public static void setUp() {
        request = mock(TravelCalculatePremiumRequest.class);
//        MockitoAnnotations.openMocks(this); -> @ExtendWith(MockitoExtension.class);
    }

    @Test
    void shouldCreateSuccessResponse() {
        when(validator.validate(request)).thenReturn(List.of());
        when(underwriting.calculatePremium(request)).thenReturn(BigDecimal.valueOf(0));
        TravelCalculatePremiumResponse response = service.calculatePremium(request);
        assertFalse(response.hasErrors());
    }

    @Test
    void shouldReturnErrors() {
        when(validator.validate(request)).thenReturn(buildValidationErrorList());
        TravelCalculatePremiumResponse response = service.calculatePremium(request);
        assertTrue(response.hasErrors());
    }

    @Test
    void checkErrorMessage() {
        when(validator.validate(request)).thenReturn(buildValidationErrorList());
        TravelCalculatePremiumResponse response = service.calculatePremium(request);
        assertEquals("field", response.getErrors().get(0).getField());
        assertEquals("errorMessage", response.getErrors().get(0).getMessage());
    }

    @AfterAll
    public static void shutDown() {
        request = null;
    }

}