package com.bol.interview.kalahaapi.controller;

import com.bol.interview.kalahaapi.abstraction.service.ICacheService;
import com.bol.interview.kalahaapi.abstraction.service.ISowService;
import com.bol.interview.kalahaapi.abstraction.service.IValidationService;
import com.bol.interview.kalahaapi.api.model.Board;
import com.bol.interview.kalahaapi.api.model.BoardGame;
import com.bol.interview.kalahaapi.api.model.HumanPlayer;
import com.bol.interview.kalahaapi.service.SowService;
import com.bol.interview.kalahaapi.service.helper.SowHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.bol.interview.kalahaapi.common.utils.CommonUtils.readFileToString;
import static com.bol.interview.kalahaapi.constants.api.KalahaApiConstants.NO_PIT_SELECTED;
import static com.bol.interview.kalahaapi.constants.api.TestConstants.GAME_ID;
import static com.bol.interview.kalahaapi.constants.api.TestConstants.STONES_PER_PIT_4;
import static com.bol.interview.kalahaapi.constants.api.URLMappingConstants.KALAHA_PATH;
import static com.bol.interview.kalahaapi.constants.api.URLMappingConstants.KALAHA_PITS_PATH;
import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class SowControllerTest {

    private static final String SOW_GAME_RESPONSE_PATH = "response/sow-game-response.json";
    public static final int SELECTED_PIT_1 = 1;

    @Mock
    private IValidationService validationService;

    @Mock
    private ICacheService cacheService;

    @InjectMocks
    private SowController sowController;
    @Mock
    private ISowService sowService;
    private BoardGame game;
    private Resource jsonResource;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception{
        Board board = new Board(STONES_PER_PIT_4);
        HumanPlayer player1 = new HumanPlayer(board.getPlayer1Pits(), true);
        HumanPlayer player2 = new HumanPlayer(board.getPlayer2Pits(), false);
        game = new BoardGame(player1, player2, board, NO_PIT_SELECTED, GAME_ID);
        jsonResource = new ClassPathResource(SOW_GAME_RESPONSE_PATH);
        mockMvc = MockMvcBuilders.standaloneSetup(sowController)
                .build();
    }


    @Test
    public void when_firstPitSelected_expect_correctStonesPlacementInAllPits() throws Exception {
        when(validationService.validateGameId(game.getId())).thenReturn(EMPTY);
        when(cacheService.fetchGameById(game.getId())).thenReturn(game);
        //get an instance of game when some moves are already played by selecting pit 1
        SowService sow = new SowService(new SowHelper());
        game = sow.sowStonesAndGetGame(game, SELECTED_PIT_1);

        when(sowService.sowStonesAndGetGame(game, SELECTED_PIT_1)).thenReturn(game);
        when(cacheService.updateAndGetGame(game)).thenReturn(game);

         mockMvc.perform(put(KALAHA_PATH  + "/9edff32b-7aa2-427a-ab98-14c4482bf86e"  + KALAHA_PITS_PATH + "/" + SELECTED_PIT_1))
                 .andExpect(status().isOk())
                 .andExpect(content().string(readFileToString(jsonResource.getFile().getAbsolutePath())));
    }
}
