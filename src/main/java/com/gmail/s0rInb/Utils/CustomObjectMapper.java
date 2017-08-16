package com.gmail.s0rInb.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.time.LocalDate;

public class CustomObjectMapper extends ObjectMapper {
    public CustomObjectMapper(){
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        simpleModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        this.registerModule(simpleModule);
        this.enable(SerializationFeature.INDENT_OUTPUT);
    }
}
