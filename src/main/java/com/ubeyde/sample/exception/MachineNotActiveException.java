package com.ubeyde.sample.exception;

public class MachineNotActiveException extends RuntimeException {

    public MachineNotActiveException(String message) {
        super(message);
    }
}
