package com.bol.interview.kalahaweb.service;

import com.bol.interview.kalahaweb.abstraction.ICacheService;
import com.bol.interview.kalahaweb.exceptions.KalahaGameException;
import com.bol.interview.kalahaweb.model.BoardGame;
import com.bol.interview.kalahaweb.repository.KalahaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.bol.interview.kalahaweb.constants.LogConstants.INFO_GAME_FOUND;
import static com.bol.interview.kalahaweb.constants.LogConstants.INFO_GAME_UPDATED;

/**
 * For fetching values from the cache, if not found, then DB
 */
@Service
@Slf4j
public class CacheService implements ICacheService {

    private final KalahaRepository repository;

    @Autowired
    public CacheService(KalahaRepository repository) {
        this.repository = repository;
    }

    @Override
    @Cacheable(cacheNames  = "kalahaCache", key = "#gameId" , unless = "#result  == null")
    public BoardGame fetchGame(String gameId) throws KalahaGameException {
        Optional<BoardGame> game = repository.findById(gameId);

        log.info(String.format(INFO_GAME_FOUND, gameId));

        if (!game.isPresent()) {
            throw new KalahaGameException("Game is not present in the database");
        }
        return game.get();
    }

    @Override
    @CachePut(value = "kalahaCache", key = "#game.id")
    public BoardGame updateAndGetGame(BoardGame game) {
        game = repository.save(game);
        log.info(String.format(INFO_GAME_UPDATED, game.getId()));
        return game;
    }

}
