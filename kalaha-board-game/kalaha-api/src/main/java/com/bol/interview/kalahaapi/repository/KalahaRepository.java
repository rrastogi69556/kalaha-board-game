package com.bol.interview.kalahaapi.repository;

import com.bol.interview.kalahaapi.api.model.BoardGame;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface KalahaRepository extends MongoRepository<BoardGame, String> {
}
