package com.bol.interview.kalahaweb.client.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.bol.interview.kalahaweb.constants.URLMappingConstants.*;

@Component
public class WebClientConfig {

    @Value("${kalaha.api.url:http://localhost:8011}")
    private String kalahaApiUrl;

    public String getCreateGameApiUrl(){
        return kalahaApiUrl.concat(KALAHA_CREATE_GAME_PATH);
    }

    public String getSowStonesApiUrl(String gameId, Integer pitIndex){
        return String.format(SOW_STONES_API_PATH, kalahaApiUrl, gameId, pitIndex);
    }

    public String getFetchGameApiUrl(String gameId){
        return String.format(FETCH_GAME_API_PATH, kalahaApiUrl, gameId) ;
    }

    public String getDeleteGameApiUrl(String gameId){
        return String.format(DELETE_GAME_API_PATH, kalahaApiUrl, gameId) ;
    }
}
