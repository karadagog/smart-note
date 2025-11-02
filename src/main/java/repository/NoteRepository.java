package main.java.repository;

import main.java.cache.LRUCache;
import main.java.model.Note;
import main.java.util.ConfigLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class NoteRepository {

    private final List<Note> notes = new CopyOnWriteArrayList<>();
    private final LRUCache<String, Note> cache;
    private final FileRepository fileRepository;

    public NoteRepository() {
        int cacheSize = ConfigLoader.getInt("max.cache.size", 5);
        this.cache = new LRUCache<>(cacheSize);
        this.fileRepository = new FileRepository();
        loadFromFile();
    }



    public void add(Note note){
 /*       if (note == null) {
            throw new IllegalArgumentException();
        }

  */
        Objects.requireNonNull(note, "Note cannot be null!");
        notes.add(note);
        cache.put(note.getId(), note);
    }



    public Optional<Note> findById(String id) {
        Note cached = cache.get(id);
        if (cached != null) return Optional.of(cached);

        return notes.stream()
                .filter(note -> note.getId().equals(id))
                .findFirst();
    }

    public List<Note> findAll() {
        return new ArrayList<>(notes);
    }

    public void saveToFile() {
        fileRepository.save(notes);
    }

    private void loadFromFile() {
        List<Note> loaded = fileRepository.load();
        notes.clear();
        notes.addAll(loaded);
        loaded.forEach(note -> cache.put(note.getId(), note));
    }

    public void clear() {
        notes.clear();
        cache.clear();
    }

    public void deleteById(String id) {
        notes.removeIf(note -> note.getId().equals(id));
        cache.remove(id);
    }
}
