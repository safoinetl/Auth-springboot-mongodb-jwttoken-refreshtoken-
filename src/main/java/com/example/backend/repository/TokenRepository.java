package com.example.backend.repository;

import com.example.backend.Token.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token,Integer> {

    @Query("{ 'user.id' : ?0, $or: [ { 'expired' : false }, { 'revoked' : false } ] }")
    List<Token> findAllValidTokenByUser(String id);
    Optional<Token> findByToken(String token);
}
