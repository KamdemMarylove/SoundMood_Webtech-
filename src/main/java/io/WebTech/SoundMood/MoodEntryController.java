package io.WebTech.SoundMood;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entries")
@CrossOrigin(origins = "https://frontend-soundmood.onrender.com")
public class MoodEntryController {

    private final MoodEntryRepository entryRepo;
    private final SongRepository songRepo;

    public MoodEntryController(MoodEntryRepository entryRepo, SongRepository songRepo) {
        this.entryRepo = entryRepo;
        this.songRepo = songRepo;
    }

    // ✅ Speichern oder überschreiben – nur ein Eintrag pro Tag
    @PostMapping
    public MoodEntry saveOrUpdateEntry(@RequestBody MoodEntry entry) {
        LocalDate today = LocalDate.now();
        entry.setDate(today);

        // Alle Einträge von heute holen
        List<MoodEntry> todaysEntries = entryRepo.findByDate(today);

        // Falls heute schon etwas gespeichert wurde: löschen
        if (!todaysEntries.isEmpty()) {
            entryRepo.deleteAll(todaysEntries);
        }

        return entryRepo.save(entry);
    }

    // ✅ Einträge dieser Woche abrufen
    @GetMapping("/week")
    public List<MoodEntry> getThisWeeksEntries() {
        LocalDate today = LocalDate.now();
        LocalDate monday = today.minusDays(today.getDayOfWeek().getValue() - 1);
        return entryRepo.findByDateBetween(monday, today);
    }
    @PatchMapping("/{id}/like")
    public MoodEntry markAsLiked(@PathVariable Long id) {
        MoodEntry entry = entryRepo.findById(id).orElseThrow();
        entry.setLiked(true);
        return entryRepo.save(entry);
    }

    // ✅ Alle Einträge abrufen (Test & Übersicht)
    @GetMapping
    public List<MoodEntry> getAllEntries() {
        return entryRepo.findAll();
    }

    // 🧹 Alles löschen (nur für Entwickler/Testzwecke)
    @DeleteMapping
    public void deleteAll() {
        entryRepo.deleteAll();
    }

    // Optional: Einzelne löschen (falls nötig)
    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable Long id) {
        entryRepo.deleteById(id);
    }
}
