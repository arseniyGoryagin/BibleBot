package com.biblebot.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface VerseRepository extends JpaRepository<Verse, Long> {

    Optional<Verse> findByBookIdAndChapterAndVerseNumber(Long bookId, int chapter, int verseNumber);

    Optional<List<Verse>> findAllByChapterAndBookId(int chapter, Long bookId);

    long countDistinctChapterByBookId(Long bookId);

    long countByBookIdAndChapter(Long bookId, Long chapter);

}
