package com.vld.dobitnik.cqrs;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DrawRepository extends MongoRepository<Draw, String> {
    Optional<Draw> findByDrawNumber(String drawNumber);
    Optional<Draw> findByDrawDate(String drawDate);

}
