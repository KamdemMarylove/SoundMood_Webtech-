package io.WebTech.SoundMood;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MoodEntryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MoodEntryRepository moodEntryRepository;


    @InjectMocks
    private MoodEntryController moodEntryController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(moodEntryController).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // For LocalDate serialization
    }






    @Test
    void getAllEntries_ReturnsAllEntries() throws Exception {
        // Arrange
        Song song1 = new Song("Song 1", "Artist 1", "happy");
        Song song2 = new Song("Song 2", "Artist 2", "sad");

        List<MoodEntry> allEntries = Arrays.asList(
                new MoodEntry(LocalDate.now().minusDays(1), "happy", song1),
                new MoodEntry(LocalDate.now(), "sad", song2)
        );

        when(moodEntryRepository.findAll()).thenReturn(allEntries);

        // Act & Assert
        mockMvc.perform(get("/entries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));

        verify(moodEntryRepository, times(1)).findAll();
    }

    @Test
    void deleteAll_DeletesAllEntries() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/entries"))
                .andExpect(status().isOk());

        verify(moodEntryRepository, times(1)).deleteAll();
    }

    @Test
    void deleteOne_ExistingId_DeletesEntry() throws Exception {
        // Arrange
        Long entryId = 1L;

        // Act & Assert
        mockMvc.perform(delete("/entries/{id}", entryId))
                .andExpect(status().isOk());

        verify(moodEntryRepository, times(1)).deleteById(entryId);
    }
}

