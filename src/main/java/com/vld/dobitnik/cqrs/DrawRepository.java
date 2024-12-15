package com.vld.dobitnik.cqrs;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrawRepository extends MongoRepository<Draw, String> {
}
