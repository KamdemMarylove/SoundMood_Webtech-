package io.WebTech.SoundMood;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MoodEntryRepository extends JpaRepository<MoodEntry, Long> {
    List<MoodEntry> findByDate(LocalDate date); // ✅ richtig
    List<MoodEntry> findByDateBetween(LocalDate start, LocalDate end);
}