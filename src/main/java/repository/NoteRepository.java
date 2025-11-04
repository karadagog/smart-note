// src/main/java/main/java/repository/NoteRepository.java
package repository;

import model.Note;
import util.ConfigLoader;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class NoteRepository {
    private final List<Note> notes = new ArrayList<>();
    private final FileRepository fileRepository = new FileRepository();
    private final String filePath = ConfigLoader.get("data.file");

    public NoteRepository() {
        loadFromFile();
    }

    public void add(Note note) {
        Objects.requireNonNull(note, "Note cannot be null!");
        notes.add(note);
        saveToFile();
    }

    public Optional<Note> findById(String id) {
        return notes.stream().filter(n -> n.getId().equals(id)).findFirst();
    }

    public List<Note> findAll() {
        return Collections.unmodifiableList(notes);
    }

    public void deleteById(String id) {
        notes.removeIf(n -> n.getId().equals(id));
        saveToFile();
    }

    public void clear() {
        notes.clear();
        saveToFile();
    }

    public void saveToFile() {
        fileRepository.save(notes, filePath);
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        if (!Files.exists(Paths.get(filePath))) return;

        List<Note> loaded = fileRepository.load(filePath);
        if (loaded != null) {
            notes.clear();
            notes.addAll(loaded);
        }
    }
}