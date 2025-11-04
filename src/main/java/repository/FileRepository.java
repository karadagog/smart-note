// src/main/java/main/java/repository/FileRepository.java
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
            System.err.println("Dosyaya kaydetme hatası: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public List<Note> load(String filePath) {
        if (!Files.exists(Paths.get(filePath))) return null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?> list && !list.isEmpty() && list.get(0) instanceof Note) {
                return new ArrayList<>((List<Note>) list);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Dosyadan yükleme hatası: " + e.getMessage());
        }
        return null;
    }
}