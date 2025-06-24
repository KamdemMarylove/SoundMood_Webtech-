package io.WebTech.SoundMood;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Entity
public class MoodEntry {
    @Getter
    @Setter
    @Column
    private boolean liked = false;


    // Getter & Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private LocalDate date;

    @Setter
    private String mood;

    @Setter
    @ManyToOne
    private Song song;

    public MoodEntry() {}

    public MoodEntry(LocalDate date, String mood, Song song) {
        this.date = date;
        this.mood = mood;
        this.song = song;

    }

}
