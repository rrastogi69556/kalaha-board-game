package com.bol.interview.kalahaapi.service;

import com.bol.interview.kalahaapi.api.model.Board;
import com.bol.interview.kalahaapi.api.model.BoardGame;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Objects;
import java.util.Optional;

import static com.bol.interview.kalahaapi.constants.api.TestConstants.STONES_PER_PIT_4;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class CacheServiceTest {

    public static final int INVOKE_ONCE = 1;
    public static final String SAMPLE_UNIQUE_ID_STRING = "sample-unique-id-string";

    interface KalahaRepo extends MongoRepository<BoardGame, String> {

        @Cacheable("kalahaCacheSample")
        Optional<BoardGame> findById(String gameId);
    }


    @Configuration
    @EnableCaching
    static class Config {

        // Simulating caching configuration
        @Bean
        CacheManager cacheManagerMock() {
            return new ConcurrentMapCacheManager("kalahaCacheSample");
        }

        @Bean
        KalahaRepo kalahaRepositoryMock() {
            return Mockito.mock(KalahaRepo.class);
        }
    }

    @Autowired
    KalahaRepo kalahaRepositoryMock;

    private BoardGame game;

    @Autowired
    private CacheManager cacheManagerMock;

    @Before
    public void setUp() {
        game = new BoardGame(SAMPLE_UNIQUE_ID_STRING, new Board(STONES_PER_PIT_4) );

    }
    @Test
    public void given_2GameInstances_when_gameRequested2Times_expect_DBCallFirstTimeAndCacheCallSecondTime() {

        //given - 2 instances created for mock to return in subsequent calls
        game = new BoardGame(SAMPLE_UNIQUE_ID_STRING, new Board(STONES_PER_PIT_4) );
        BoardGame game_2 = new BoardGame(SAMPLE_UNIQUE_ID_STRING,  new Board(STONES_PER_PIT_4));

       //when
        when(kalahaRepositoryMock.findById(anyString()))
                .thenReturn(Optional.of(game))
                .thenReturn(Optional.of(game_2));
        //#1 - first time object
        Optional<BoardGame> game3 = kalahaRepositoryMock.findById(SAMPLE_UNIQUE_ID_STRING);
        assertThat(game3.get()).isEqualTo(game);

        //#2 - return same object (cached)
        game3 = kalahaRepositoryMock.findById(SAMPLE_UNIQUE_ID_STRING);
        assertThat(game3.get()).isEqualTo(game);

        //then
        verify(kalahaRepositoryMock, times(INVOKE_ONCE)).findById(SAMPLE_UNIQUE_ID_STRING);
        assertThat(Objects.requireNonNull(cacheManagerMock.getCache("kalahaCacheSample")).get(SAMPLE_UNIQUE_ID_STRING)).isNotNull();

    }

}
