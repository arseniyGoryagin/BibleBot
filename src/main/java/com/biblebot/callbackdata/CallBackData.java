package com.biblebot.callbackdata;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallBackData {

    private Pages page;

    private String data;


    public static String  toJson(CallBackData data) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(data);
    }

    public static CallBackData fromJson(String data) throws JsonProcessingException {
        return new ObjectMapper().readValue(data, CallBackData.class);
    }

}
