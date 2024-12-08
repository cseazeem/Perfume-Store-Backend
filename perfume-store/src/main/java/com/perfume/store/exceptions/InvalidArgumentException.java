package com.perfume.store.exceptions;

public class InvalidArgumentException extends PerfumeException {

    public InvalidArgumentException(String perfumeErrorMessage) {
        super("PERFUME",perfumeErrorMessage);
    }
}
