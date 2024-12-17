package com.biblebot.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface VerseRepository extends JpaRepository<Verse, Long> {

    Optional<Verse> findByBookNameAndChapterAndVerseNumber(String bookName, int chapter, int verseNumber);

}
