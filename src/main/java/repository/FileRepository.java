// src/main/java/repository/FileRepository.java
package repository;

import model.Note;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileRepository {

    public void save(List<Note> notes, String filePath) {
        try {
            Files.createDirectories(Paths.get(filePath).getParent());
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                oos.writeObject(new ArrayList<>(notes));
            }
        } catch (IOException e) {
            System.err.println("Save error: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public List<Note> load(String filePath) {
        if (!Files.exists(Paths.get(filePath))) {
            return new ArrayList<>(); // NULL YERİNE BOŞ LİSTE!
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?> list && !list.isEmpty() && list.get(0) instanceof Note) {
                return new ArrayList<>((List<Note>) list);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Load error: " + e.getMessage());
        }
        return new ArrayList<>(); // HATA DURUMUNDA DA BOŞ LİSTE!
    }
}