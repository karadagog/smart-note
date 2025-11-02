package main.java.app;

import main.java.exception.NoteNotFoundException;
import main.java.model.Category;
import main.java.model.Note;
import main.java.repository.NoteRepository;

import java.util.Scanner;

public class ConsoleMenu {

    private final NoteRepository noteRepository;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleMenu(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void start () {
        while (true) {
            printMenu();
            int choice = readInt("Seçiniz...");
            try {
                switch (choice) {
                    case 1 -> addNote();
                    case 2 -> listNotes();
                    case 3 -> searchNotes();
                    case 4 -> deleteNote();
                    case 5 -> {
                        System.out.println("Çıkılıyor...");
                        return;
                    }
                    default -> System.out.println("Geçersiz seçim!");
                }
            } catch (Exception e) {
                System.err.println("Hata: " + e.getMessage());
            }

        }

    }

    private void deleteNote() {
        System.out.print("Silinecek not ID: ");
        String id = scanner.nextLine();
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));

        noteRepository.deleteById(id); // YENİ METOD
        System.out.println("Silindi: " + note.getTitle() + " (" + id + ")");
    }

    private void searchNotes() {
        System.out.println("Arama kelimesi: ");
        String keyword = scanner.nextLine();
        noteRepository.findAll().stream()
                .filter(note -> note.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                        note.getContent().toLowerCase().contains(keyword.toLowerCase()))
                .forEach(System.out::println);

    }

    private void listNotes() {
        noteRepository.findAll().stream()
                .sorted()
                .forEach(System.out::println);
    }

    private void addNote() {
        System.out.print("Başlık: ");
        String title = scanner.nextLine();
        System.out.print("İçerik: ");
        String content = scanner.nextLine();
        System.out.print("Kategori (WORK/PERSONAL/STUDY/URGENT/OTHER): ");
        String cat = scanner.nextLine().toUpperCase();
        Category category = Category.valueOf(cat);

        Note note = new Note(title, content, category);
        noteRepository.add(note);
        System.out.println("Not eklendi " + note.getId());
    }

    private int readInt(String prompt) {
        System.out.println(prompt);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }



    }

    private void printMenu() {
        System.out.println("\n=== SMART NOTE ===");
        System.out.println("1. Not Ekle");
        System.out.println("2. Notları Listele");
        System.out.println("3. Ara");
        System.out.println("4. Sil");
        System.out.println("5. Çıkış");
    }
}
