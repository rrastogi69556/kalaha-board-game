package com.bol.interview.kalahaapi.controller;

import com.bol.interview.kalahaapi.abstraction.controller.ICacheController;
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

import static com.bol.interview.kalahaapi.constants.api.URLMappingConstants.KALAHA_DELETE_GAME;
import static com.bol.interview.kalahaapi.constants.api.URLMappingConstants.KALAHA_FETCH_GAME;

@Slf4j
@RestController
@Api(value = "Kalaha Game. Contains endpoints for updating game operations")
public class CacheController extends BaseController implements ICacheController {
    private static final String API_FETCH_TAG = "KALAHA_FETCH_GAME";
    private static final String API_DELETE_TAG = "KALAHA_DELETE_GAME";

    private final IValidationService validationService;
    private final ICacheService cacheService;

    @Autowired
    public CacheController(IValidationService validationService,
                           ICacheService cacheService) {
        this.validationService = validationService;
        this.cacheService = cacheService;
    }


    @ApiOperation(value = "Returns existing instance of BoardGame", response = String.class, tags = {API_FETCH_TAG}, produces = "Application/JSON", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gameId", value = "Represents existing gameId. Mandatory.", required = true, dataType = "java.lang.String", paramType = "path"),
    })
    @CrossOrigin(origins= "http://kalaha-api:8011/", allowedHeaders = "*")
    @GetMapping(KALAHA_FETCH_GAME)
    @Override
    public ResponseEntity<String> fetchGame(@PathVariable("gameId") String gameId) throws Exception{

        String errorMessage = validationService.validateGameId(gameId);
        if (!errorMessage.isEmpty()) {
            log.error(errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        BoardGame game = cacheService.fetchGameById(gameId);

        return ResponseEntity.ok(getJsonMapper().writeValueAsString(game));
    }



    @ApiOperation(value = "Returns status of the game deletion", response = String.class, tags = {API_DELETE_TAG}, produces = "Application/JSON", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gameId", value = "Represents existing gameId. Mandatory.", required = true, dataType = "java.lang.String", paramType = "path"),
    })
    @CrossOrigin(origins= "http://kalaha-api:8011/", allowedHeaders = "*")
    @DeleteMapping(KALAHA_DELETE_GAME)
    @Override
    public ResponseEntity<String> deleteGame(@PathVariable("gameId") String gameId) throws Exception{

        String errorMessage = validationService.validateGameId(gameId);
        if (!errorMessage.isEmpty()) {
            log.error(errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        String result = cacheService.deleteGameById(gameId);

        return ResponseEntity.ok(getJsonMapper().writeValueAsString(result));
    }

}
