package io.WebTech.SoundMood;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MoodEntryRepository extends JpaRepository<MoodEntry, Long> {
    List<MoodEntry> findByDateBetween(LocalDate start, LocalDate end);
    Optional<MoodEntry> findByDate(LocalDate date);
}
// Diese Schnittstelle erweitert JpaRepository und bietet Methoden zum Abrufen von Mood-Entries basierend auf
// Datum und Stimmung.