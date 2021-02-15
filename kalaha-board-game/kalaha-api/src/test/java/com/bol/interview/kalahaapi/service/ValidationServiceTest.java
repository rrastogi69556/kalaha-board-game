package com.bol.interview.kalahaapi.service;

import com.bol.interview.kalahaapi.api.model.Board;
import com.bol.interview.kalahaapi.api.model.BoardGame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;

import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class ValidationServiceTest {

    public static final int VALID_STONES_PER_PIT = 6;
    public static final int INVALID_STONES_PER_PIT = -1;
    @Mock
    CacheService cacheService;

    @InjectMocks
    ValidationService validationService;

    private BoardGame game;

    @PostConstruct
    public void init() {
        //given
        game = new BoardGame("sample-unique-id-string", new Board(4) );
    }


    @Test
    public void when_gamePresentInCache_expect_gameToReturn() {

        //when
        when(cacheService.fetchGameById(game.getId())).thenReturn(game);
        String returnResponse = validationService.validateGameId(game.getId());

        //then
        assertThat(returnResponse).isEqualTo(EMPTY);

    }

    @Test
    public void when_gameNotPresentInCache_expect_errorMessage() {
        //given - no game present
        String uniqueId = "sample-unique-id-string";
        String expectedResponse = "Either Game id is invalid, non-existing, null/empty or negative.";

        //when
        when(cacheService.fetchGameById(anyString())).thenReturn(null);
        String returnResponse = validationService.validateGameId(uniqueId);

        //then
        assertThat(returnResponse).isEqualTo(expectedResponse);

    }
    @Test
    public void when_validNumberOfStonesProvided_expect_noErrorMessageToProceed() {

        //when
        String returnResponse = validationService.validateStones(VALID_STONES_PER_PIT);

        //then
        assertThat(returnResponse).isEqualTo(EMPTY);

    }

    @Test
    public void when_invalidStonesProvided_expect_errorMessage() {
        String invalidResponse = "Invalid number of stones passed. Required non-negative stones or too large value.";

        //when
        String returnResponse = validationService.validateStones(INVALID_STONES_PER_PIT);

        //then
        assertThat(returnResponse).isEqualTo(invalidResponse);

    }
}
