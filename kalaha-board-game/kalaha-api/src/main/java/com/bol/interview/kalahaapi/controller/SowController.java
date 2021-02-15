package com.bol.interview.kalahaapi.controller;

import com.bol.interview.kalahaapi.abstraction.controller.ISowController;
import com.bol.interview.kalahaapi.abstraction.service.ISowService;
import com.bol.interview.kalahaapi.abstraction.service.ICacheService;
import com.bol.interview.kalahaapi.abstraction.service.IValidationService;
import com.bol.interview.kalahaapi.api.model.BoardGame;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bol.interview.kalahaapi.constants.api.LogConstants.INFO_GAME_UPDATED;
import static com.bol.interview.kalahaapi.constants.api.LogConstants.INFO_SOW_API_INVOKED;
import static com.bol.interview.kalahaapi.constants.api.URLMappingConstants.*;

@Slf4j
@RestController
@Api(value = "Kalaha Game. Contains endpoints for moving stone operations")
public class SowController extends BaseController implements ISowController {
    private static final String API_MOVE_STONES_TAG = "KALAHA_MOVE_STONES_FROM_PITS";

    private final IValidationService validationService;
    private final ICacheService cacheService;
    private final ISowService sowService;

    @Autowired
    public SowController(IValidationService validationService,
                         ICacheService cacheService,
                         ISowService sowService) {
        this.validationService = validationService;
        this.cacheService = cacheService;
        this.sowService = sowService;
    }

    @PutMapping(KALAHA_MOVE_STONES_PATH)
    @ApiOperation(value = "Returns updated instance of BoardGame", response = String.class, tags = {API_MOVE_STONES_TAG}, produces = "Application/JSON", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gameId", value = "Represents unique gameId. Mandatory.", required = true, dataType = "java.lang.String", paramType = "path"),
            @ApiImplicitParam(name = "pitIndex", value = "Represents selected pit for moving stones.", required = true, dataType = "java.lang.Integer", paramType = "path")
    })
    @Override
    @CrossOrigin(origins= KALAHA_API_URL, allowedHeaders = "*")
    public ResponseEntity<String> moveStonesFromSelectedPit(@PathVariable(value ="gameId") String gameId,
                                                            @PathVariable(value = "pitIndex") Integer pitIndex) throws Exception {
        log.info(INFO_SOW_API_INVOKED);
        String errorMessage = validationService.validateGameId(gameId);

        if (!errorMessage.isEmpty()) {
            log.error(errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        BoardGame game = cacheService.fetchGameById(gameId);

        game = sowService.sowStonesAndGetGame(game, pitIndex);
        game = cacheService.updateAndGetGame(game);
        log.info(INFO_GAME_UPDATED);
        return ResponseEntity.ok(getJsonMapper().writeValueAsString(game));
    }
}
