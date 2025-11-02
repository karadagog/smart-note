package main.java;

import main.java.app.ConsoleMenu;
import main.java.repository.NoteRepository;
import main.java.thread.AutoSaveTask;
import main.java.util.ConfigLoader;

public class Main {

    public static void main(String[] args) {

        System.out.println("Başlıyor... " + ConfigLoader.get("app.name") + " v" + ConfigLoader.get("app.version"));

        NoteRepository noteRepository = new NoteRepository();

        Thread autoSaveThread = new Thread(new AutoSaveTask(noteRepository));
        autoSaveThread.setDaemon(true);  // Program kapanınca thread de kapansın
        autoSaveThread.start();

        new ConsoleMenu(noteRepository).start();
    }
}
