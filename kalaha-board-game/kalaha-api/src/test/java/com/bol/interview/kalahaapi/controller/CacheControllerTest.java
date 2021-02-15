package com.bol.interview.kalahaapi.controller;

import com.bol.interview.kalahaapi.abstraction.service.ICacheService;
import com.bol.interview.kalahaapi.api.model.Board;
import com.bol.interview.kalahaapi.api.model.BoardGame;
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

import static com.bol.interview.kalahaapi.common.utils.CommonUtils.fileToString;
import static com.bol.interview.kalahaapi.constants.api.TestConstants.STONES_PER_PIT_4;
import static com.bol.interview.kalahaapi.constants.api.URLMappingConstants.KALAHA_PATH;
import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class CacheControllerTest {
    private static final String CREATE_GAME_RESPONSE_PATH = "response/create-fetch-game-response.json";
    private MockMvc mockMvc;

    @Mock
    private ICacheService cacheService;

    @Mock
    private BaseController controller;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private CacheController cacheController;
    private BoardGame game;
    private Resource jsonResource;
    @Before
    public void setUp(){
        game = new BoardGame("9edff32b-7aa2-427a-ab98-14c4482bf86e", new Board(STONES_PER_PIT_4));
        jsonResource = new ClassPathResource(CREATE_GAME_RESPONSE_PATH);
        mockMvc = MockMvcBuilders.standaloneSetup(cacheController)
                .build();

    }

    @Test
    public void testFetchGameResource() throws Exception {
        when(validationService.validateGameId(game.getId())).thenReturn(EMPTY);
        when(cacheService.fetchGameById(game.getId())).thenReturn(game);
        mockMvc.perform(get(KALAHA_PATH + "/fetch-game/9edff32b-7aa2-427a-ab98-14c4482bf86e")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(fileToString(jsonResource.getFile().getAbsolutePath())));
    }

}
