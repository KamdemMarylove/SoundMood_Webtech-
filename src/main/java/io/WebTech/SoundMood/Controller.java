package io.WebTech.SoundMood;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(
        origins = "https://frontend-soundmood.onrender.com",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS}
)
@RestController
public class Controller {

    @GetMapping("/mood")
    public List<SongVorschlag> getSongs() {
        return List.of(
                new SongVorschlag("Pona nini", "Tiakola", "happy")
        );
    }
}
