package com.lisdev.customers_api.application.port.out;

@FunctionalInterface
public interface PasswordEncoderPort {

    String encode(String rawPassword);

}
