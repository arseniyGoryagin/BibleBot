package com.biblebot.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VerseRepository extends JpaRepository<Verse, Long> {

    Verse findByBookNameAndChapterAndVerseNumber(String bookName, int chapter, int verseNumber);

}
