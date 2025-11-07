// src/main/java/thread/AutoSaveTask.java
package thread;

import model.Note;
import repository.FileRepository;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AutoSaveTask implements Runnable {

    private final FileRepository fileRepo;
    private final String filePath;
    private List<Note> notes;
    private final Timer timer = new Timer(true);

    public AutoSaveTask(FileRepository fileRepo, String filePath) {
        this.fileRepo = fileRepo;
        this.filePath = filePath;
        this.notes = fileRepo.load(filePath);
    }

    @Override
    public void run() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                saveIfChanged();
            }
        }, 30_000, 30_000); // 30 saniyede bir

        // Sonsuz bekle (daemon thread)
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void saveIfChanged() {
        List<Note> current = fileRepo.load(filePath);
        if (!current.equals(notes)) {
            fileRepo.save(current, filePath);
            System.out.println("[AutoSave] Notlar kaydedildi: " + current.size() + " adet");
            notes = current;
        }
    }
}