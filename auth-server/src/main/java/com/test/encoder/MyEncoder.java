package com.test.encoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MyEncoder implements Function<String,String> {
    private static final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
    @Override
    public String apply(String s) {
        return encoder.encode(s);
    }
}
