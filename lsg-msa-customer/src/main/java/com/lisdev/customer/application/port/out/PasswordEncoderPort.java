package com.lisdev.customer.application.port.out;

@FunctionalInterface
public interface PasswordEncoderPort {

    String encode(String rawPassword);

}
