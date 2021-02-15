/**
 * Copyright 2019 Esfandiyar Talebi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bol.interview.kalahaweb.controller;

import com.bol.interview.kalahaweb.abstraction.ICacheService;
import com.bol.interview.kalahaweb.client.WebClient;
import com.bol.interview.kalahaweb.events.SowEvent;
import com.bol.interview.kalahaweb.exceptions.KalahaGameException;
import com.bol.interview.kalahaweb.model.BoardGame;
import com.bol.interview.kalahaweb.model.JsonRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import static com.bol.interview.kalahaweb.constants.ErrorConstants.*;
import static com.bol.interview.kalahaweb.constants.LogConstants.ERROR_NO_GAME_INSTANCE_FOUND;
import static com.bol.interview.kalahaweb.constants.LogConstants.ERROR_WHILE_DELETING_GAME_INSTANCE;


@Component
public class GameController {

    private BoardGame game;

    private final ApplicationEventPublisher eventPublisher;
    private final ICacheService cacheService;
    private final WebClient webClient;

    @Autowired
    public GameController(ApplicationEventPublisher eventPublisher,
                          ICacheService cacheService,
                          WebClient webClient
    ) {
        this.eventPublisher = eventPublisher;
        this.cacheService = cacheService;
        this.webClient = webClient;
    }

    public BoardGame startNewGame(JsonRequest jsonRequest) throws KalahaGameException {
        try {

            this.game = webClient.startGame(jsonRequest) ;
            throwErrorIfNoGameInstanceFound(game,  NO_GAME_INSTANCE);
            return this.game;

        }catch (Exception e){
            throw new KalahaGameException(CREATE_GAME_ERROR);
        }
    }

    public void sow(Integer pitIndex) throws KalahaGameException {
        try {
            this.game = webClient.sowStones(String.valueOf(this.game.getId()), pitIndex);

            throwErrorIfNoGameInstanceFound(this.game, NO_GAME_INSTANCE);

            this.eventPublisher.publishEvent(new SowEvent(this, this.game, pitIndex));

        }catch (Exception ex){
            throw new KalahaGameException(SOW_STONES_GAME_ERROR);
        }
    }


    public boolean hasGameStarted () {
        return this.game.getId() != null;
    }

    public BoardGame getGame(String gameId) {
        try {
            //check cache -> DB
            this.game = cacheService.fetchGame(gameId);

            //fallback to API
            if(null == game) {
                this.game = webClient.fetchGame(String.valueOf(this.game.getId()));
            }

            throwErrorIfNoGameInstanceFound( this.game, NO_GAME_INSTANCE);

            this.eventPublisher.publishEvent(new SowEvent(this, this.game));

        }catch (Exception ex){
            throw new KalahaGameException("Error connecting with Kalaha api for fetching game");
        }
        return null;
    }

    public String deleteGame(String gameId) throws KalahaGameException {
        try {

            String response = webClient.deleteGame(gameId) ;

            throwErrorIfUnableToDeleteGame(response);

            return response;

        }catch (Exception e){
            throw new KalahaGameException(DELETE_GAME_ERROR);
        }
    }

    private void throwErrorIfUnableToDeleteGame(String response) {
        if(StringUtils.isEmpty(response) ||
                response.equalsIgnoreCase(ERROR_NO_GAME_INSTANCE_FOUND) ||
                response.equalsIgnoreCase(ERROR_WHILE_DELETING_GAME_INSTANCE)) {
            throw new KalahaGameException(DELETE_GAME_ERROR);
        }
    }

    private void throwErrorIfNoGameInstanceFound(BoardGame game, String noGameInstance) {
        if (null == game) {
            throw new KalahaGameException(noGameInstance);
        }
    }

}

