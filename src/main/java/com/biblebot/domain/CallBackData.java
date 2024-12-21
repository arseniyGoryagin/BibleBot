package com.biblebot.domain;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CallBackData {


    private String type;
    private Integer book;
    private Integer chapter;
    private Integer verse;
}
