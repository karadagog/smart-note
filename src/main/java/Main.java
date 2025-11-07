// src/main/java/Main.java
import app.ConsoleMenu;
import repository.FileRepository;
import thread.AutoSaveTask;
import util.ConfigLoader;

public class Main {

    public static void main(String[] args) {

        System.out.println("Başlıyor... " + ConfigLoader.get("app.name") + " v" + ConfigLoader.get("app.version"));

        String filePath = ConfigLoader.get("notes.file");
        if (filePath == null || filePath.isBlank()) {
            filePath = "notes.dat"; // Güvenlik
            System.err.println("notes.file eksik! Varsayılan: notes.dat");
        }

        FileRepository fileRepo = new FileRepository();

        Thread autoSaveThread = new Thread(new AutoSaveTask(fileRepo, filePath));
        autoSaveThread.setDaemon(true);
        autoSaveThread.start();

        new ConsoleMenu(fileRepo, filePath).start();
    }
}