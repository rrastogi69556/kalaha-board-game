package com.bol.interview.kalahaapi.service;

import com.bol.interview.kalahaapi.abstraction.service.ICacheService;
import com.bol.interview.kalahaapi.abstraction.service.IValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.bol.interview.kalahaapi.common.utils.ApiValidationUtils.throwErrorMessageIfInvalidGameId;
import static com.bol.interview.kalahaapi.common.utils.ApiValidationUtils.throwErrorMessageIfInvalidStones;

/*________________________________________________
 |      This class validates API inputs          |
|_______________________________________________| */

@Service
public class ValidationService implements IValidationService {

    private final ICacheService cacheService;

    @Autowired
    public ValidationService(ICacheService cacheService) {
        this.cacheService = cacheService;
    }

    public String validateGameId(String gameId) {
        return throwErrorMessageIfInvalidGameId(gameId, cacheService);
    }

    public String validateStones(Integer stonesPerPit) {
        return  throwErrorMessageIfInvalidStones(stonesPerPit);
    }

}
