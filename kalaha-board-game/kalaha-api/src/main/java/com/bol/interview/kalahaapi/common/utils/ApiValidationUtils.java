package com.bol.interview.kalahaapi.common.utils;

import com.bol.interview.kalahaapi.abstraction.service.ICacheService;
import com.bol.interview.kalahaapi.exception.KalahaGameException;

import static com.bol.interview.kalahaapi.constants.api.KalahaApiConstants.NO_ERROR_MESSAGE;
import static com.bol.interview.kalahaapi.constants.api.LogConstants.*;
import static org.apache.logging.log4j.util.Strings.isEmpty;

/**
 * An Utility class which deals with validations of the provided inputs.
 */
public class ApiValidationUtils {

    private ApiValidationUtils() {}

    public static String throwErrorMessageIfInvalidGameId(Object gameId, ICacheService cache) {
        String errorMessage = NO_ERROR_MESSAGE;
        try {
            if (gameId instanceof String) {
                String id = (String) gameId;
                if (cache == null || null == cache.fetchGameById(id) || isEmpty(id)) {
                    errorMessage = ERROR_INVALID_GAME_ID;
                }
            }
        }catch(KalahaGameException kge) {
            errorMessage = ERROR_GAME_NOT_FOUND_IN_CACHE;
        }
        return errorMessage;
    }

    public static String throwErrorMessageIfInvalidStones(Integer stonesPerPit) {
        String errorMessage = NO_ERROR_MESSAGE;
        if (stonesPerPit == null || stonesPerPit <= 0 || stonesPerPit >= Integer.MAX_VALUE) {
            errorMessage = ERROR_INVALID_STONES;
        }
        return errorMessage;
    }
}
