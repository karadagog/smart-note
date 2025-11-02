package main.java.thread;

import main.java.repository.NoteRepository;
import main.java.util.ConfigLoader;

public class AutoSaveTask implements Runnable{

    private final NoteRepository noteRepository;
    private final long interval;

    public AutoSaveTask(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
        this.interval = ConfigLoader.getInt("autosave.interval", 30000);
    }


    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(interval);
                noteRepository.saveToFile();
                System.out.println("[Autosave] Notlar Kaydedildi.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("[Autosave] Durduruldu");
                break;
            }
        }

    }
}
