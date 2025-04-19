package io.WebTech.SoundMood;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    @GetMapping("/mood")
    public List<SongVorschlag> getSongs() {
        return List.of(
                new SongVorschlag("Pona nini", "Tiakola", "happy")
    );
    }


}
