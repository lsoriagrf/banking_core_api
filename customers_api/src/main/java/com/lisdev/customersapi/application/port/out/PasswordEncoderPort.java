package com.lisdev.customersapi.application.port.out;

@FunctionalInterface
public interface PasswordEncoderPort {

    String encode(String rawPassword);

}
