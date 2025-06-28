package io.WebTech.SoundMood;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SongRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SongRepository songRepository;

    private Song happySong;
    private Song sadSong;
    private Song energeticSong;

    @BeforeEach
    void setUp() {
        happySong = new Song("Pona nini", "Tiakola", "happy");
        sadSong = new Song("Drivers License", "Olivia Rodrigo", "sad");
        energeticSong = new Song("Power", "Kanye West", "energetic");

        entityManager.persistAndFlush(happySong);
        entityManager.persistAndFlush(sadSong);
        entityManager.persistAndFlush(energeticSong);
    }

    @Test
    void findByMood_ExistingMood_ReturnsMatchingSongs() {
        // Act
        List<Song> happySongs = songRepository.findByMood("happy");

        // Assert
        assertEquals(1, happySongs.size());
        assertEquals("Pona nini", happySongs.get(0).getTitle());
        assertEquals("Tiakola", happySongs.get(0).getArtist());
        assertEquals("happy", happySongs.get(0).getMood());
    }

    @Test
    void findByMood_NonExistingMood_ReturnsEmptyList() {
        // Act
        List<Song> nonExistentMoodSongs = songRepository.findByMood("nonexistent");

        // Assert
        assertTrue(nonExistentMoodSongs.isEmpty());
    }

    @Test
    void findByMood_CaseSensitive_ReturnsCorrectResults() {
        // Act
        List<Song> upperCaseSongs = songRepository.findByMood("HAPPY");
        List<Song> lowerCaseSongs = songRepository.findByMood("happy");

        // Assert
        assertTrue(upperCaseSongs.isEmpty(), "Should be case sensitive");
        assertEquals(1, lowerCaseSongs.size());
    }

    @Test
    void findAll_ReturnsAllSongs() {
        // Act
        List<Song> allSongs = songRepository.findAll();

        // Assert
        assertEquals(3, allSongs.size());

        // Verify all moods are present
        List<String> moods = allSongs.stream()
                .map(Song::getMood)
                .toList();
        assertTrue(moods.contains("happy"));
        assertTrue(moods.contains("sad"));
        assertTrue(moods.contains("energetic"));
    }

    @Test
    void save_NewSong_PersistsSuccessfully() {
        // Arrange
        Song newSong = new Song("Weightless", "Marconi Union", "relaxed");

        // Act
        Song savedSong = songRepository.save(newSong);

        // Assert
        assertNotNull(savedSong.getId());
        assertEquals("Weightless", savedSong.getTitle());
        assertEquals("Marconi Union", savedSong.getArtist());
        assertEquals("relaxed", savedSong.getMood());

        // Verify it's actually in the database
        Song foundSong = entityManager.find(Song.class, savedSong.getId());
        assertNotNull(foundSong);
        assertEquals("Weightless", foundSong.getTitle());
    }

    @Test
    void count_ReturnsCorrectNumber() {
        // Act
        long count = songRepository.count();

        // Assert
        assertEquals(3, count);
    }

    @Test
    void deleteById_RemovesSong() {
        // Arrange
        Long songId = happySong.getId();

        // Act
        songRepository.deleteById(songId);

        // Assert
        assertEquals(2, songRepository.count());
        assertFalse(songRepository.findById(songId).isPresent());
    }

    @Test
    void findByMood_MultipleSongsWithSameMood_ReturnsAllMatching() {
        // Arrange
        Song anotherHappySong = new Song("Good Vibes", "Test Artist", "happy");
        entityManager.persistAndFlush(anotherHappySong);

        // Act
        List<Song> happySongs = songRepository.findByMood("happy");

        // Assert
        assertEquals(2, happySongs.size());

        List<String> titles = happySongs.stream()
                .map(Song::getTitle)
                .toList();
        assertTrue(titles.contains("Pona nini"));
        assertTrue(titles.contains("Good Vibes"));
    }
}

