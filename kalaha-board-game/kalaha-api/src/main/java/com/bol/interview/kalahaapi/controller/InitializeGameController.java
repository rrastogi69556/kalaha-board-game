package com.bol.interview.kalahaapi.controller;

import com.bol.interview.kalahaapi.abstraction.controller.IInitializeGameController;
import com.bol.interview.kalahaapi.abstraction.service.ICacheService;
import com.bol.interview.kalahaapi.abstraction.service.IInitializeService;
import com.bol.interview.kalahaapi.abstraction.service.IValidationService;
import com.bol.interview.kalahaapi.api.model.BoardGame;
import com.bol.interview.kalahaapi.request.JsonRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.bol.interview.kalahaapi.constants.api.KalahaApiConstants.DEFAULT_STONES;
import static com.bol.interview.kalahaapi.constants.api.KalahaApiConstants.SHOULD_CAPTURE_IF_OPPOSITE_PIT_EMPTY;
import static com.bol.interview.kalahaapi.constants.api.LogConstants.*;
import static com.bol.interview.kalahaapi.constants.api.URLMappingConstants.KALAHA_CREATE_GAME;
import static java.util.Objects.nonNull;

@Slf4j
@RestController
@Api(value = "Kalaha Game. Contains endpoints for creating game instances.")
public class InitializeGameController extends BaseController implements IInitializeGameController {

    private static final String API_CREATE_TAG = "KALAHA_CREATE_GAME";

    private final IInitializeService initializeService;

    private final IValidationService validationService;

    private final ICacheService cacheService;

    @Autowired
    public InitializeGameController(IInitializeService initializeService,
                                    IValidationService validationService,
                                    ICacheService cacheService){
        this.initializeService = initializeService;
        this.validationService = validationService;
        this.cacheService = cacheService;
    }

    @ApiOperation(value = "Returns new instance of BoardGame", response = String.class, tags = {API_CREATE_TAG}, produces = "Application/JSON", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stonesPerPit", value = "Represents number of stones per pit. Default is 6. Optional", required = false, dataType = "java.lang.Integer", paramType = "query"),
    })
    @PostMapping(KALAHA_CREATE_GAME)

    @CrossOrigin(origins= "http://kalaha-api:8011/", allowedHeaders = "*")
    @Override
    public ResponseEntity<String> createGame(@RequestBody(required = false) JsonRequest jsonRequest) throws Exception {

        if(jsonRequest == null) jsonRequest = new JsonRequest(DEFAULT_STONES, SHOULD_CAPTURE_IF_OPPOSITE_PIT_EMPTY);

        if(nonNull(jsonRequest.getStonesPerPit())) {

            String errorMessage = validationService.validateStones(jsonRequest.getStonesPerPit());
            if (!errorMessage.isEmpty()) {
                log.error(errorMessage);
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorMessage);
            }
        }

        log.info(INFO_GAME_ID_GENERATING);

        BoardGame game = initializeService.initializeGameAndGet(jsonRequest);
        log.info(String.format(INFO_GAME_INITIALIZED, game.getId()));

        game = cacheService.saveAndGetGame(game);

        log.info(String.format(INFO_GAME_INSTANCE, game));

        return ResponseEntity.ok(getJsonMapper().writeValueAsString(game));
    }


}
