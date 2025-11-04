// src/main/java/main/java/app/ConsoleMenu.java
package app;


import exception.NoteNotFoundException;
import model.Category;
import model.Note;
import repository.NoteRepository;

import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {

    private final NoteRepository noteRepository;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleMenu(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void start() {
        while (true) {
            printMenu();
            int choice = readInt("Seçiniz: ");
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
        String id = scanner.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("ID boş olamaz!");
            return;
        }

        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));

        noteRepository.deleteById(id);
        System.out.println("Silindi: " + note.getTitle() + " (" + id + ")");
    }

    private void searchNotes() {
        System.out.print("Arama kelimesi: ");
        String keyword = scanner.nextLine().trim().toLowerCase();
        if (keyword.isEmpty()) {
            System.out.println("Arama kelimesi boş!");
            return;
        }

        noteRepository.findAll().stream()
                .filter(note -> (note.getTitle() != null && note.getTitle().toLowerCase().contains(keyword)) ||
                        (note.getContent() != null && note.getContent().toLowerCase().contains(keyword)))
                .sorted()
                .forEach(System.out::println);
    }

    private void listNotes() {
        List<Note> all = noteRepository.findAll();
        if (all.isEmpty()) {
            System.out.println("Henüz not yok.");
            return;
        }
        System.out.println("\n--- Notlar ---");
        all.stream().sorted().forEach(System.out::println);
    }

    private void addNote() {
        System.out.print("Başlık: ");
        String title = scanner.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("Başlık boş olamaz!");
            return;
        }

        System.out.print("İçerik: ");
        String content = scanner.nextLine();

        System.out.print("Kategori (WORK/PERSONAL/STUDY/URGENT/OTHER): ");
        String catInput = scanner.nextLine().trim().toUpperCase();
        Category category;
        try {
            category = Category.valueOf(catInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Geçersiz kategori! Varsayılan: OTHER");
            category = Category.OTHER;
        }

        Note note = new Note(title, content, category);
        noteRepository.add(note);
        System.out.println("Not eklendi: " + note.getId());
    }

    private int readInt(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Lütfen sayı girin!");
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