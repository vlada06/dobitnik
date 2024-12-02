package com.vld.dobitnik.cqrs;


import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.repository.MongoRepository;

@Component
public interface DrawRepository extends MongoRepository<Draw, String> {
}
