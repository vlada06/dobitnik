package com.vld.dobitnik.validate;


import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Validate JSON schema for REST API request
 *
 * <p>
 * Temporary validator, until figuring out how to implement
 *
 * @author: Vladimir Davidovic
 * date: 28/01/2021
 * time: 20:22
 */

@Service
public class SchemaValidation {
    private static final List<String> MANDATORY_FIELDS
            = Arrays.asList("mainGamePool", "mainGameSize", "mainSystemSize", "mainNumbersCombination");

    public boolean validSchema(Set<String> requestKeys) {
        return requestKeys.containsAll(MANDATORY_FIELDS);
    }
}