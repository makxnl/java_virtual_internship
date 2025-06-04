//package org.javaguru.travel.insurance.core;
//
//import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
//import org.javaguru.travel.insurance.dto.TravelCalculatePremiumResponse;
//import org.javaguru.travel.insurance.dto.ValidationError;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class TravelCalculatePremiumServiceImplTest {
//
//    @Mock
//    private static TravelCalculatePremiumRequestValidator validator;
//    @Mock
//    private static TravelPremiumUnderwriting underwriting;
//
//    @InjectMocks
//    private static TravelCalculatePremiumServiceImpl service;
//
//
//    private List<ValidationError> buildValidationErrorList() {
//        return List.of(
//                new ValidationError("field", "errorMessage")
//        );
//    }
//
//    private Date createDate(String dateStr) {
//        try {
//            return new SimpleDateFormat("dd.MM.yyyy").parse(dateStr);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void shouldCreateSuccessResponse() {
//        TravelCalculatePremiumRequest request = mock(TravelCalculatePremiumRequest.class);
//        when(request.getPersonFirstName()).thenReturn("Max");
//        when(request.getPersonLastName()).thenReturn("Smirnov");
//        when(request.getAgreementDateFrom()).thenReturn(createDate("01.01.2023"));
//        when(request.getAgreementDateTo()).thenReturn(createDate("10.01.2023"));
//        when(validator.validate(request)).thenReturn(List.of());
//        when(underwriting.calculatePremium(request)).thenReturn(BigDecimal.valueOf(9));
//        TravelCalculatePremiumResponse response = service.calculatePremium(request);
//        assertAll(
//                () -> assertFalse(response.hasErrors()),
//                () -> assertEquals(request.getPersonFirstName(), response.getPersonFirstName()),
//                () -> assertEquals(request.getPersonLastName(), response.getPersonLastName()),
//                () -> assertEquals(request.getAgreementDateFrom(), response.getAgreementDateFrom()),
//                () -> assertEquals(request.getAgreementDateTo(), response.getAgreementDateTo())
//                , () -> assertEquals(BigDecimal.valueOf(9), response.getAgreementPrice())
//        );
//        System.out.println(response.getAgreementPrice());
//    }
//
//    @Test
//    void shouldReturnErrors() {
//        TravelCalculatePremiumRequest request = mock(TravelCalculatePremiumRequest.class);
//        when(validator.validate(request)).thenReturn(buildValidationErrorList());
//        TravelCalculatePremiumResponse response = service.calculatePremium(request);
//        assertTrue(response.hasErrors());
//    }
//
//}

package org.javaguru.travel.insurance.core;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumResponse;
import org.javaguru.travel.insurance.dto.ValidationError;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TravelCalculatePremiumServiceImplTest {

    @Mock private TravelCalculatePremiumRequestValidator requestValidator;
    @Mock private TravelPremiumUnderwriting premiumUnderwriting;

    @InjectMocks
    private TravelCalculatePremiumServiceImpl service;

    @Test
    public void shouldReturnResponseWithErrors() {
        TravelCalculatePremiumRequest request = mock(TravelCalculatePremiumRequest.class);
        List<ValidationError> errors = buildValidationErrorList();
        when(requestValidator.validate(request)).thenReturn(errors);
        TravelCalculatePremiumResponse response = service.calculatePremium(request);
        assertTrue(response.hasErrors());
    }

    @Test
    public void shouldReturnResponseWithValidationErrors() {
        TravelCalculatePremiumRequest request = mock(TravelCalculatePremiumRequest.class);
        List<ValidationError> errors = buildValidationErrorList();
        when(requestValidator.validate(request)).thenReturn(errors);
        TravelCalculatePremiumResponse response = service.calculatePremium(request);
        assertEquals(response.getErrors().size(), 1);
        assertEquals(response.getErrors().get(0).getField(), "field");
        assertEquals(response.getErrors().get(0).getMessage(), "errorMessage");
    }

    @Test
    public void shouldNotInvokeDateTimeUtilWhenValidationErrors() {
        TravelCalculatePremiumRequest request = mock(TravelCalculatePremiumRequest.class);
        List<ValidationError> errors = buildValidationErrorList();
        when(requestValidator.validate(request)).thenReturn(errors);
        TravelCalculatePremiumResponse response = service.calculatePremium(request);
        assertEquals(response.getErrors().size(), 1);
        assertEquals(response.getErrors().get(0).getField(), "field");
        assertEquals(response.getErrors().get(0).getMessage(), "errorMessage");
    }

    @Test
    public void shouldReturnResponseWithCorrectPersonFirstName() {
        TravelCalculatePremiumRequest request = mock(TravelCalculatePremiumRequest.class);
        when(request.getPersonFirstName()).thenReturn("personFirstName");
        when(requestValidator.validate(request)).thenReturn(List.of());
        TravelCalculatePremiumResponse response = service.calculatePremium(request);
        assertEquals(response.getPersonFirstName(), "personFirstName");
    }

    @Test
    public void shouldReturnResponseWithCorrectPersonLastName() {
        TravelCalculatePremiumRequest request = mock(TravelCalculatePremiumRequest.class);
        when(request.getPersonLastName()).thenReturn("personLastName");
        when(requestValidator.validate(request)).thenReturn(List.of());
        TravelCalculatePremiumResponse response = service.calculatePremium(request);
        assertEquals(response.getPersonLastName(), "personLastName");
    }

    @Test
    public void shouldReturnResponseWithCorrectAgreementDateFrom() {
        TravelCalculatePremiumRequest request = mock(TravelCalculatePremiumRequest.class);
        Date dateFrom = new Date();
        when(request.getAgreementDateFrom()).thenReturn(dateFrom);
        when(requestValidator.validate(request)).thenReturn(List.of());
        TravelCalculatePremiumResponse response = service.calculatePremium(request);
        assertEquals(response.getAgreementDateFrom(), dateFrom);
    }

    @Test
    public void shouldReturnResponseWithCorrectAgreementDateTo() {
        TravelCalculatePremiumRequest request = mock(TravelCalculatePremiumRequest.class);
        Date dateTo = new Date();
        when(request.getAgreementDateTo()).thenReturn(dateTo);
        when(requestValidator.validate(request)).thenReturn(List.of());
        TravelCalculatePremiumResponse response = service.calculatePremium(request);
        assertEquals(response.getAgreementDateTo(), dateTo);
    }

    @Test
    public void shouldReturnResponseWithCorrectAgreementPrice() {
        TravelCalculatePremiumRequest request = mock(TravelCalculatePremiumRequest.class);
        when(request.getAgreementDateFrom()).thenReturn(createDate("01.01.2023"));
        when(request.getAgreementDateTo()).thenReturn(createDate("10.01.2023"));
        when(requestValidator.validate(request)).thenReturn(List.of());
        when(premiumUnderwriting.calculatePremium(request)).thenReturn(new BigDecimal(9));
        TravelCalculatePremiumResponse response = service.calculatePremium(request);
        assertEquals(response.getAgreementPrice(), new BigDecimal(9));
    }

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

}