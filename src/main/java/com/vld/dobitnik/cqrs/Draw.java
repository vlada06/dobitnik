package com.vld.dobitnik.cqrs;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * A single lottery draw from the past. Data object for storing lottery draws
 *
 * @param drawNumber  Draw Number
 * @param drawNumbers Lottery numbers drawn
 * @param drawDate Draw Date
 */
@Document("draws")
@Builder
public record Draw(
    @Id
    String drawNumber,
    @NotNull(message = "Must have at least 5 numbers")
    Combination drawNumbers,
    @JsonFormat(pattern = "dd/MM/yyyy")
    String drawDate
) {
}