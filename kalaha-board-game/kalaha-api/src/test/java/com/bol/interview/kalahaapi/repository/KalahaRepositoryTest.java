package com.bol.interview.kalahaapi.repository;

import com.bol.interview.kalahaapi.api.model.Board;
import com.bol.interview.kalahaapi.api.model.BoardGame;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@DataMongoTest
@RunWith(SpringRunner.class)
public class KalahaRepositoryTest {

    @Autowired KalahaRepository repository;


    @DisplayName("given the board game is setup" +
            " when object is saved in repository" +
            " then expect object exists in the repository")
    @Test
    public void when_savingGameUsingRepository_then_expectGameSaved() {
        //given
        Board board = new Board(4);
        BoardGame boardGame = new BoardGame("board-id-unique-string", board);

        //when
        repository.save(boardGame);

        //then
        assertTrue(repository.existsById("board-id-unique-string"));
    }
}
