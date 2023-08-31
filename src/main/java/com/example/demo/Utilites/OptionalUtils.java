package com.example.demo.Utilites;

import com.example.demo.Exceptions.RecordNotFoundException;

import java.util.Optional;

public class OptionalUtils {
    public static <T> T getValueOrThrow(Optional<T> optional, String errorMessage) {
        return optional.orElseThrow(() -> new RecordNotFoundException(errorMessage));
    }
}
