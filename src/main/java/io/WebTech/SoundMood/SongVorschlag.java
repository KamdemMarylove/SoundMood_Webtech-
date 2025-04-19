package io.WebTech.SoundMood;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SongVorschlag {
    private String title;
    private String artist;
    private String mood;

    public SongVorschlag(String title, String artist, String mood) {
        this.title = title;
        this.artist = artist;
        this.mood = mood;
    }

}
