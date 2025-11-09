// src/test/java/com/smartnote/app/ConsoleMenuIT.java
package com.smartnote.model.test.java.com.smartnote.app;

import app.ConsoleMenu;
import model.Category;
import model.Note;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import repository.FileRepository;

import java.io.*;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ConsoleMenu - Full User Flow Integration Test")
class ConsoleMenuIT {

    private FileRepository fileRepo;
    private ConsoleMenu menu;

    @TempDir
    private Path tempDir;

    private Path testFile;

    @BeforeEach
    void setUp() {
        testFile = tempDir.resolve("notes.dat");
        fileRepo = new FileRepository();

        // ConsoleMenu → FileRepository + filePath alır
        menu = new ConsoleMenu(fileRepo, testFile.toString());
    }

    @Test
    @DisplayName("should add note, save to file, list it, and exit")
    void shouldAddNoteSaveToFileAndListIt() throws IOException {
        // GIVEN
        String input = """
            1
            Meeting Note
            Discuss project timeline
            WORK
            2
            5
            """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));

        try {
            // WHEN
            menu.start();
        } finally {
            System.setOut(original);
        }

        // THEN: File exists
        assertTrue(java.nio.file.Files.exists(testFile), "File should be created");

        // THEN: Load and verify
        List<Note> loaded = fileRepo.load(testFile.toString());
        assertEquals(1, loaded.size(), "One note should be saved");
        Note note = loaded.get(0);
        assertEquals("Meeting Note", note.getTitle());
        assertEquals("Discuss project timeline", note.getContent());
        assertEquals(Category.WORK, note.getCategory());

        // THEN: Console output
        String output = out.toString();
        assertTrue(output.contains("Meeting Note"));
        assertTrue(output.contains("WORK"));
        assertTrue(output.contains("eklendi"));
    }

    @Test
    @DisplayName("should handle empty file on startup")
    void shouldHandleEmptyFileOnStartup() {
        String input = "2\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> menu.start());
    }
}