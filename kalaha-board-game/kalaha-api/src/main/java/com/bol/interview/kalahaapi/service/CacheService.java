package com.bol.interview.kalahaapi.service;

import com.bol.interview.kalahaapi.abstraction.controller.ICacheController;
import com.bol.interview.kalahaapi.abstraction.service.ICacheService;
import com.bol.interview.kalahaapi.exception.KalahaGameException;
import com.bol.interview.kalahaapi.api.model.BoardGame;
import com.bol.interview.kalahaapi.repository.KalahaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.bol.interview.kalahaapi.constants.api.LogConstants.*;
import static org.apache.logging.log4j.util.Strings.isEmpty;

/**
 * This class serves the purpose of saving,fetching the game instances from cache,DB. Invoked by {@link ICacheController}
 */
@Service
@Slf4j
public class CacheService implements ICacheService {

    private final KalahaRepository repository;

    @Autowired
    public CacheService(KalahaRepository repository) {
        this.repository = repository;
    }


    @Cacheable(cacheNames  = "kalahaCache", key = "#gameId" , unless = "#result  == null")
    @Override
    public BoardGame fetchGameById(String gameId) throws KalahaGameException{

        Optional<BoardGame> game = repository.findById(gameId);

        log.info(String.format(INFO_GAME_FOUND, gameId));

        if (!game.isPresent()) {
            throw new KalahaGameException(ERROR_NO_GAME_INSTANCE_FOUND);
        }

        return game.get();
    }



    @CachePut(value = "kalahaCache", key = "#game.id")
    @Override
    public BoardGame updateAndGetGame(BoardGame game) {
        game = repository.save(game);
        log.info(String.format(INFO_GAME_UPDATED, game.getId()));
        return game;
    }

    @Override
    public BoardGame saveAndGetGame(BoardGame game) {
        return repository.save(game);

    }

    @CacheEvict(cacheNames = "kalahaCache", key = "#gameId")
    @Override
    public String deleteGameById(String gameId) {
        try {
            if (!isEmpty(gameId)) {
                Optional<BoardGame> game = repository.findById(gameId);
                game.ifPresent(existingGame -> deleteById(existingGame.getId()));
                return INFO_GAME_DELETED;
            } else {
                throw new KalahaGameException(ERROR_NO_GAME_INSTANCE_FOUND);
            }
        }catch(KalahaGameException kge) {
            throw new KalahaGameException(ERROR_WHILE_DELETING_GAME_INSTANCE);
        }
    }

    private void deleteById(String id) {
        try {
            repository.deleteById(id);
        }catch (KalahaGameException kge) {
            throw new KalahaGameException(ERROR_WHILE_DELETING_GAME_INSTANCE, kge);
        }

    }
}
