package com.ubeyde.sample.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(int neededAmount) {
        super("BAKİYE YETERSİZ. YÜKLEMENİZ GEREKEN TUTAR: " + neededAmount + " TRY");
    }
}
