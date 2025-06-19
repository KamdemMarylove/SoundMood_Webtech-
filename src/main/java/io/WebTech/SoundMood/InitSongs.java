package io.WebTech.SoundMood;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class InitSongs {

    private final SongRepository repo;

    public InitSongs(SongRepository repo) {
        this.repo = repo;
    }

    @PostConstruct
    public void init() {
        if (repo.count() == 0) {
            repo.save(new Song("Pona nini", "Tiakola", "happy"));
            repo.save(new Song("Drivers License", "Olivia Rodrigo", "sad"));
            repo.save(new Song("Power", "Kanye West", "energetic"));
            repo.save(new Song("Weightless", "Marconi Union", "relaxed"));
            repo.save(new Song("Party Rock Anthem", "LMFAO", "outgoing"));
            System.out.println("Testdaten in die Datenbank eingefügt.");
        }
    }
}
