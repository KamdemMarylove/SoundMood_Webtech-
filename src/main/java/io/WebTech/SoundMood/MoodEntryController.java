package io.WebTech.SoundMood;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @PostMapping
    public MoodEntry saveEntry(@RequestBody MoodEntry entry) {
        // Automatisch heutiges Datum setzen
        entry.setDate(LocalDate.now());
        return entryRepo.save(entry);
    }

    @GetMapping("/week")
    public List<MoodEntry> getThisWeeksEntries() {
        LocalDate today = LocalDate.now();
        LocalDate monday = today.minusDays(today.getDayOfWeek().getValue() - 1);
        return entryRepo.findByDateBetween(monday, today);
    }
}
// Diese Klasse verwaltet die Mood-Entries und bietet Endpunkte zum Speichern und Abrufen der Einträge für die aktuelle Woche.