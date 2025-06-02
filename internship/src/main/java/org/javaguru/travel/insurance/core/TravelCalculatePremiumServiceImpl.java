package org.javaguru.travel.insurance.core;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumResponse;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class TravelCalculatePremiumServiceImpl implements TravelCalculatePremiumService {

    private final TravelCalculatePremiumRequestValidator validator;
    private final TravelPremiumUnderwriting underwriting;


    TravelCalculatePremiumServiceImpl(TravelCalculatePremiumRequestValidator validator
                                      , TravelPremiumUnderwriting underwriting) {
        this.validator = validator;
        this.underwriting = underwriting;
            }


    @Override
    public TravelCalculatePremiumResponse calculatePremium(TravelCalculatePremiumRequest request) {
        List<ValidationError> errors = validator.validate(request);
        return errors.isEmpty()
                ? createSuccessResponse(request)
                : new TravelCalculatePremiumResponse(errors);
    }

    private TravelCalculatePremiumResponse createSuccessResponse(TravelCalculatePremiumRequest request) {
        return new TravelCalculatePremiumResponse(
                request.getPersonFirstName(),
                request.getPersonLastName(),
                request.getAgreementDateFrom(),
                request.getAgreementDateTo(),
                underwriting.calculatePremium(request));
    }
}
