package com.bol.interview.kalahaweb.client;

import com.bol.interview.kalahaweb.client.configuration.WebClientConfig;
import com.bol.interview.kalahaweb.client.helper.WebClientHelper;
import com.bol.interview.kalahaweb.exceptions.KalahaGameException;
import com.bol.interview.kalahaweb.model.BoardGame;
import com.bol.interview.kalahaweb.model.JsonRequest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.bol.interview.kalahaweb.constants.LogConstants.*;

@Component
@Slf4j
public class WebClient {

    private final RestTemplate restTemplate;
    private final WebClientConfig webClientConfig;
    private final ObjectMapper jsonMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    private final WebClientHelper webClientHelper;

    @Autowired
    public WebClient(RestTemplate restTemplate,
                     WebClientConfig webClientConfig,
                     WebClientHelper webClientHelper) {
        this.restTemplate = restTemplate;
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        this.webClientConfig = webClientConfig;

        this.webClientHelper = webClientHelper;
    }


    public BoardGame startGame(JsonRequest jsonRequest)  {
        String url = webClientConfig.getCreateGameApiUrl();

        try {
            HttpEntity<String> httpEntityRequest = webClientHelper.convertJsonRequestToHttpEntityRequest(jsonRequest, jsonMapper);

            log.info(String.format(INFO_INVOKING_API, url));

            ResponseEntity<String> gameResponse = webClientHelper.postAndFetchResponseFromCreateGameApi(url, httpEntityRequest, restTemplate);

            webClientHelper.throwErrorIfInvalidResponse(gameResponse);

            log.info(String.format(INFO_RESPONSE_BODY, webClientHelper.getJsonPrettyResponse(gameResponse, jsonMapper)));

            return jsonMapper.readValue(gameResponse.getBody(), BoardGame.class);

        }catch(RestClientException | KalahaGameException | IOException e) {
            log.error(ERROR_INVOKING_API, e);
        }
        return null;
    }

    public BoardGame sowStones(String gameId, Integer pitIndex) {

        String url = webClientConfig.getSowStonesApiUrl(gameId, pitIndex);
        try {
            log.info(String.format(INFO_INVOKING_API, url));

            ResponseEntity<String> sowStonesResponse = restTemplate.exchange(url, HttpMethod.PUT, null, String.class);

            webClientHelper.throwErrorIfInvalidResponse(sowStonesResponse);

            log.info(String.format(INFO_RESPONSE_BODY, webClientHelper.getJsonPrettyResponse(sowStonesResponse, jsonMapper)));

            return  jsonMapper.readValue(sowStonesResponse.getBody(), BoardGame.class);

        }catch(RestClientException | KalahaGameException | IOException e) {
            log.error(ERROR_INVOKING_API, e);
        }
        return null;
    }

    public BoardGame fetchGame(String gameId) {

        String url = webClientConfig.getFetchGameApiUrl(gameId);
        try {
            log.info(String.format(INFO_INVOKING_API, url));

            ResponseEntity<String> fetchGameResponse = restTemplate.exchange(url, HttpMethod.GET, null, String.class);

            webClientHelper.throwErrorIfInvalidResponse(fetchGameResponse);

            log.info(String.format(INFO_RESPONSE_BODY, webClientHelper.getJsonPrettyResponse(fetchGameResponse, jsonMapper)));

           return  jsonMapper.readValue(fetchGameResponse.getBody(), BoardGame.class);

        }catch(RestClientException | KalahaGameException | IOException e) {
            log.error(ERROR_INVOKING_API, e);
        }
        return null;
    }


    public String deleteGame(String gameId) {

        String url = webClientConfig.getDeleteGameApiUrl(gameId);
        try {
            log.info(String.format(INFO_INVOKING_API, url));

            ResponseEntity<String> deleteGameResponse = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);

            webClientHelper.throwErrorIfInvalidResponse(deleteGameResponse);

            log.info(String.format(INFO_RESPONSE_BODY, webClientHelper.getJsonPrettyResponse(deleteGameResponse, jsonMapper)));

            return deleteGameResponse.getBody();

        }catch(RestClientException | KalahaGameException | IOException e){
            log.error(ERROR_INVOKING_API, e);
        }
        return null;
    }


}
