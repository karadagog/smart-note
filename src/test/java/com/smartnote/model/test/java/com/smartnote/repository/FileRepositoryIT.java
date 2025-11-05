// src/test/java/repository/FileRepositoryIT.java
package test.java.com.smartnote.repository;

import model.Note;
import model.Category;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import repository.FileRepository;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FileRepository - Real File Save/Load Integration Tests")
class FileRepositoryIT {

    private FileRepository repo;

    @TempDir
    private Path tempDir; // FIELD OLARAK!

    private Path testFile;

    @BeforeEach
    void setUp() {
        testFile = tempDir.resolve("notes.dat"); // tempDir artık null değil
        repo = new FileRepository();
    }

    @Test
    @DisplayName("should save and load a list of notes correctly")
    void shouldSaveAndLoadNotesCorrectly() {
        // GIVEN: Prepare a list of notes to save
        List<Note> notesToSave = List.of(
                new Note("Meeting", "Project discussion", Category.WORK),
                new Note("Personal", "Grocery list", Category.PERSONAL)
        );

        // WHEN: Save the list to a real file
        repo.save(notesToSave, testFile.toString());

        // THEN: File should exist
        assertTrue(java.nio.file.Files.exists(testFile), "File should be created");

        // WHEN: Load from the same file
        List<Note> loadedNotes = repo.load(testFile.toString());

        // THEN: Data should match
        assertEquals(2, loadedNotes.size(), "Should load 2 notes");
        assertEquals("Meeting", loadedNotes.get(0).getTitle(), "First note title should match");
        assertEquals("Grocery list", loadedNotes.get(1).getContent(), "Second note content should match");
        assertEquals(Category.WORK, loadedNotes.get(0).getCategory(), "Category should be preserved");
    }

    @Test
    @DisplayName("should return empty list when file does not exist")
    void shouldReturnEmptyListWhenFileNotFound() {
        // GIVEN: A non-existent file path
        String invalidPath = tempDir.resolve("nonexistent.dat").toString();

        // WHEN: Attempt to load from the non-existent file
        List<Note> result = repo.load(invalidPath);

        // THEN: Should return an empty list
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Should return empty list for missing file");
    }
}