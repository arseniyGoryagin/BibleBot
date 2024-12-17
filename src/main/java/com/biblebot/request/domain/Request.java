package com.biblebot.request.domain;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Request {

    private String bookName;
    private int chapter;
    private int verse;
}
