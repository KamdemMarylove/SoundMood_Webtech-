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

    @PostMapping
    public MoodEntry saveOrUpdateEntry(@RequestBody MoodEntry entry) {
        LocalDate today = LocalDate.now();
        entry.setDate(today);

        Optional<MoodEntry> existing = entryRepo.findByDate(today);
        if (existing.isPresent()) {
            MoodEntry update = existing.get();
            update.setMood(entry.getMood());
            update.setSong(entry.getSong());
            return entryRepo.save(update);
        }

        return entryRepo.save(entry);
    }

    @GetMapping("/week")
    public List<MoodEntry> getThisWeeksEntries() {
        LocalDate today = LocalDate.now();
        LocalDate monday = today.minusDays(today.getDayOfWeek().getValue() - 1);
        return entryRepo.findByDateBetween(monday, today);
    }

    @GetMapping
    public List<MoodEntry> getAllEntries() {
        return entryRepo.findAll();
    }

    // Optional: Alles löschen (nur für Tests!)
    @DeleteMapping
    public void deleteAll() {
        entryRepo.deleteAll();
    }

    // Optional: Einzeln löschen
    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable Long id) {
        entryRepo.deleteById(id);
    }
}
