// src/test/java/model/NoteTest.java
package com.smartnote.model.test.java.com.smartnote.model;


import model.Category;
import model.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Note Sınıfı Testleri")
class NoteTest {

    private Note note;

    @BeforeEach
    void setUp() {
        note = new Note("Toplantı", "Proje detayları", Category.WORK);
    }

    @Test
    void shouldAssignIdOnCreation() {
        assertNotNull(note.getId());
        assertEquals(8, note.getId().length());
    }

    @Test
    void shouldSetTitleAndContent() {
        assertEquals("Toplantı", note.getTitle());
        assertEquals("Proje detayları", note.getContent());
    }

    @Test
    void shouldSetCategory() {
        assertEquals(Category.WORK, note.getCategory());
        assertEquals("İş", note.getCategory().getDisplayName());
    }

    @Test
    void shouldUpdateTimestampOnModify() {
        LocalDateTime old = note.getUpdatedAt();

        // Thread.sleep yerine: 1 ms beklet (daha güvenli)
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        note.setTitle("Yeni Başlık");
        assertTrue(note.getUpdatedAt().isAfter(old), "updatedAt güncellenmedi!");
    }

    @Test
    void shouldUseDefaultCategoryWhenNull() {
        Note note2 = new Note("Sadece başlık", "İçerik", null);
        assertEquals(Category.OTHER, note2.getCategory());
    }
}