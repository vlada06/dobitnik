package com.vld.dobitnik.cqrs;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date; // TODO - better class for dates

@Document("draws")
@Builder
public record Draw(
    @Id
    String drawNumber,
    @NotNull(message = "Must have at least 5 numbers")
    Combination drawNumbers
//    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
//    LocalDate drawDate
) {
}