package io.WebTech.SoundMood;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/songs")
@CrossOrigin(origins = "https://frontend-soundmood.onrender.com")
public class SongController {

    private final io.WebTech.SoundMood.SongRepository repo;

    public SongController(io.WebTech.SoundMood.SongRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Song> getSongs(@RequestParam(required = false) String mood) {
        if (mood != null) {
            return repo.findByMood(mood);
        }
        return repo.findAll();
    }

    @PostMapping
    public Song addSong(@RequestBody Song song) {
        return repo.save(song);
    }
}
