package com.bol.interview.kalahaweb.client.helper;

import com.bol.interview.kalahaweb.exceptions.KalahaGameException;
import com.bol.interview.kalahaweb.model.JsonRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.bol.interview.kalahaweb.constants.LogConstants.ERROR_NOT_OK_RESPONSE;
import static com.bol.interview.kalahaweb.constants.LogConstants.ERROR_RESPONSE_NULL;

@Component
@Slf4j
public class WebClientHelper {

    public String getJsonPrettyResponse(ResponseEntity<String> gameResponse, ObjectMapper mapper) throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().
                writeValueAsString(gameResponse.getBody());
    }

    public ResponseEntity<String> postAndFetchResponseFromCreateGameApi(String url, HttpEntity<String> httpEntityRequest, RestTemplate restTemplate) throws JsonProcessingException {
        return restTemplate.postForEntity(url, httpEntityRequest, String.class);
    }

    public void throwErrorIfInvalidResponse(ResponseEntity<String> responseEntity) throws KalahaGameException,JsonProcessingException {
        KalahaGameException exception = null;
        if(responseEntity == null) {
            exception =  new KalahaGameException(ERROR_RESPONSE_NULL);
            log.error(String.format(ERROR_RESPONSE_NULL),exception);
            throw exception;
        }

        if(!responseEntity.getStatusCode().is2xxSuccessful()) {
            exception = new KalahaGameException(responseEntity.getBody());
            log.error(String.format(ERROR_NOT_OK_RESPONSE, responseEntity.getStatusCode()), exception);
            throw exception;
        }
    }

    public HttpEntity<String> convertJsonRequestToHttpEntityRequest(JsonRequest jsonRequest, ObjectMapper mapper) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(mapper.writeValueAsString(jsonRequest), headers);
    }

}
