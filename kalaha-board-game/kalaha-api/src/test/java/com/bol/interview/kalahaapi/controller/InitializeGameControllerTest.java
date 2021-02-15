package com.bol.interview.kalahaapi.controller;

import com.bol.interview.kalahaapi.abstraction.service.ICacheService;
import com.bol.interview.kalahaapi.api.model.Board;
import com.bol.interview.kalahaapi.api.model.BoardGame;
import com.bol.interview.kalahaapi.request.JsonRequest;
import com.bol.interview.kalahaapi.service.InitializeGameService;
import com.bol.interview.kalahaapi.service.ValidationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.bol.interview.kalahaapi.common.utils.CommonUtils.readFileToString;
import static com.bol.interview.kalahaapi.constants.api.TestConstants.STONES_PER_PIT_4;
import static com.bol.interview.kalahaapi.constants.api.URLMappingConstants.KALAHA_CREATE_GAME_PATH;
import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class InitializeGameControllerTest {

    private static final String CREATE_GAME_RESPONSE_PATH = "response/create-fetch-game-response.json";
    public static final String STONES_PER_PIT_4_CAPTURE_FALSE = "{\"stonesPerPit\":4,\"captureIfOppositeEmpty\":false}";
    private MockMvc mockMvc;

    @Mock
    private InitializeGameService initializeService;

    @Mock
    private ICacheService cacheService;

    @Mock
    private BaseController controller;
    @Mock
    private ValidationService validationService;

    @InjectMocks
    private InitializeGameController gameController;
    private BoardGame game;
    private Resource jsonResource;
    @Before
    public void setUp(){
        game = new BoardGame("9edff32b-7aa2-427a-ab98-14c4482bf86e", new Board(STONES_PER_PIT_4));
        jsonResource = new ClassPathResource(CREATE_GAME_RESPONSE_PATH);
        mockMvc = MockMvcBuilders.standaloneSetup(gameController)
                .build();

    }

    @Test
    public void when_invokeCreateGameURI_expect_gameInstanceInitialized() throws Exception {
        when(validationService.validateStones(STONES_PER_PIT_4)).thenReturn(EMPTY);
        when(initializeService.initializeGameAndGet(any(JsonRequest.class))).thenReturn(game);
        when(cacheService.saveAndGetGame(game)).thenReturn(game);

        mockMvc.perform(
                post(KALAHA_CREATE_GAME_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(STONES_PER_PIT_4_CAPTURE_FALSE)
                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(readFileToString(jsonResource.getFile().getAbsolutePath())));
    }
}
