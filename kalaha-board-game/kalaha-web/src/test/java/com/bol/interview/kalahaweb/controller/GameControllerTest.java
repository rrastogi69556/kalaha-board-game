package com.bol.interview.kalahaweb.controller;

import com.bol.interview.kalahaweb.client.WebClient;
import com.bol.interview.kalahaweb.client.configuration.WebClientConfig;
import com.bol.interview.kalahaweb.client.helper.WebClientHelper;
import com.bol.interview.kalahaweb.exceptions.KalahaGameException;
import com.bol.interview.kalahaweb.model.BoardGame;
import com.bol.interview.kalahaweb.model.JsonRequest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static com.bol.interview.kalahaweb.constants.KalahaWebConstants.DEFAULT_STONES;
import static com.bol.interview.kalahaweb.constants.KalahaWebConstants.PIT_1;
import static com.bol.interview.kalahaweb.constants.LogConstants.ERROR_RESPONSE_NULL;
import static com.bol.interview.kalahaweb.constants.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
public class GameControllerTest {

    @Mock
    private WebClientConfig webClientConfig;

    private ObjectMapper mapper;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private WebClientHelper webClientHelper;
    @InjectMocks
    private WebClient webClient;

    @Before
    public void setUp(){
        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    }

    @Test
    public void when_StartNewGame_expect_BoardGameReturned() throws IOException {

        BoardGame expectedGame = mapper.readValue(EXPECTED_RESPONSE_STRING, BoardGame.class);
        ResponseEntity<String> gameResponse = ResponseEntity.ok(EXPECTED_RESPONSE_STRING);

        when(webClientConfig.getCreateGameApiUrl()).thenReturn(KALAHA_CREATE_API_URL_STRING);
        when(webClientHelper.postAndFetchResponseFromCreateGameApi(anyString(), any(), eq(restTemplate))).thenReturn(gameResponse);
        when(restTemplate.postForEntity(anyString(), any(), eq(String.class))).thenReturn(gameResponse);

        BoardGame game = webClient.startGame(new JsonRequest(DEFAULT_STONES, !SHOULD_CAPTURE_STONES_IF_OPPOSITE_PIT_EMPTY));

        assertThat(game).isNotNull();
        assertThat(game.getId()).isEqualTo(expectedGame.getId());

    }

    @SneakyThrows
    @Test(expected = KalahaGameException.class)
    public void when_gameIsNull_expect_Exception() {
        KalahaGameException nullException= new KalahaGameException(ERROR_RESPONSE_NULL);

        when(webClientConfig.getCreateGameApiUrl()).thenReturn(KALAHA_CREATE_API_URL_STRING);
        when(webClientHelper.postAndFetchResponseFromCreateGameApi(anyString(), any(), eq(restTemplate))).thenReturn(null);

        doThrow(nullException).when(webClientHelper).throwErrorIfInvalidResponse(eq(null));
        webClientHelper.throwErrorIfInvalidResponse(null);
    }

    @Test
    public void when_SowGame_expect_UpdatedBoardReturnedWithCorrectStonesPlacement() throws IOException {

        BoardGame expectedGame = mapper.readValue(EXPECTED_RESPONSE_STRING, BoardGame.class);
        ResponseEntity<String> gameResponseString = ResponseEntity.ok(EXPECTED_RESPONSE_STRING);

        when(webClientConfig.getSowStonesApiUrl(anyString(), anyInt())).thenReturn(KALAHA_SOW_API_URL_STRING);
        when(restTemplate.exchange(anyString(), Mockito.eq(HttpMethod.PUT), any(), eq(String.class))).thenReturn(gameResponseString);

        BoardGame game = webClient.sowStones(GAME_ID, PIT_1);

        assertThat(game).isNotNull();
        assertThat(game.getId()).isEqualTo(expectedGame.getId());
        assertThat(mapper.writeValueAsString(game)).isEqualTo(EXPECTED_RESPONSE_STRING);

    }

    @Test
    public void when_deleteGame_expect_gameDeletedResponse() {
        ResponseEntity<String> gameResponseString = ResponseEntity.ok(DELETED_FROM_DATABASE);


        when(webClientConfig.getDeleteGameApiUrl(anyString())).thenReturn(KALAHA_DELETE_API_URL_STRING);
        when(restTemplate.exchange(anyString(), Mockito.eq(HttpMethod.DELETE), any(), eq(String.class))).thenReturn(gameResponseString);

        String gameDeletedResponseString = webClient.deleteGame(GAME_ID);

        assertThat(gameDeletedResponseString).isNotNull();
        assertThat(gameDeletedResponseString).isEqualTo(DELETED_FROM_DATABASE);
    }
}
