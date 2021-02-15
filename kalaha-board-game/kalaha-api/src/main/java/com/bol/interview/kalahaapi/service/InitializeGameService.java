package com.bol.interview.kalahaapi.service;

import com.bol.interview.kalahaapi.abstraction.controller.IInitializeGameController;
import com.bol.interview.kalahaapi.abstraction.service.IInitializeService;
import com.bol.interview.kalahaapi.api.model.BoardGame;
import com.bol.interview.kalahaapi.builder.GameBoardBuilder;
import com.bol.interview.kalahaapi.request.JsonRequest;
import org.springframework.stereotype.Service;

import static com.bol.interview.kalahaapi.enums.EPlayer.PLAYER_1;
import static com.bol.interview.kalahaapi.enums.EPlayer.PLAYER_2;

/**
 * This class servers the purpose of initializing the game instances, invoked by {@link IInitializeGameController}
 */
@Service
public class InitializeGameService implements IInitializeService {

    @Override
    public BoardGame initializeGameAndGet(Integer stonesPerPit) {

        return new GameBoardBuilder()
                .setUpBoard(stonesPerPit)
                .setUpPlayer(true, PLAYER_1)
                .setUpPlayer(true, PLAYER_2)
                .build();

    }

    @Override
    public BoardGame initializeGameAndGet(JsonRequest jsonRequest) {
         BoardGame game = initializeGameAndGet(jsonRequest.getStonesPerPit());
         game.setCaptureIfOppositeEmpty(jsonRequest.isCaptureIfOppositeEmpty());
         return game;
    }
}
