package main.java.repository;

import main.java.model.Note;
import main.java.util.ConfigLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileRepository {

    private final String filePath;

    public FileRepository() {
        this.filePath = ConfigLoader.get("data.file", "notes.dat");
    }


    public void save(List<Note> notes) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
            oos.writeObject(new ArrayList<>(notes));
            System.out.println("[File] Notlar kaydedildi: " + filePath);
        } catch (FileNotFoundException e) {
            System.err.println("Dosya okuma hatası " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Note> load() {
        File file = new File(filePath);
        if (!file.exists()) return new ArrayList<>();
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            Object obj = ois.readObject();
            if (obj instanceof List) {
                return (List<Note>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Dosya okuma hatası: " + e.getMessage());
        }
        return new ArrayList<>();
    }


}
