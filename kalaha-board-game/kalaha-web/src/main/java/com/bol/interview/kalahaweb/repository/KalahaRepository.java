package com.bol.interview.kalahaweb.repository;


import com.bol.interview.kalahaweb.model.BoardGame;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface KalahaRepository extends MongoRepository<BoardGame, String> {
}
