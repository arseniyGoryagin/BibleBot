package com.biblebot.domain;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "rst_bible_books")
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book")
    private String bookName;

    private String alt;

    private String abbr;

    private int idx;
}
