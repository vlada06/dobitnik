package com.vld.dobitnik.validate;


import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class SchemaValidation {
    private static final List<String> MANDATORY_FIELDS
            = Arrays.asList("mainGamePool", "mainGameSize", "mainSystemSize", "mainNumbersCombination");

    public boolean validSchema(Set<String> requestKeys) {
        return requestKeys.containsAll(MANDATORY_FIELDS);
    }
}