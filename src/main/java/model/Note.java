package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Note implements Comparable<Note>, Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private String title;
    private String content;
    private Category category;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Note(String title, String content, Category category) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.title = title;
        this.content = content;
        this.category = category != null ? category : Category.OTHER; // null-safe
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }


    public Note(String title, String content) {
        this(title, content, Category.OTHER);
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public Category getCategory() { return category; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }


    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public void setContent(String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    public void setCategory(Category category) {
        this.category = category != null ? category : Category.OTHER;
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public int compareTo(Note o) {
        if (o == null) return -1;
        Category thisCat = this.category != null ? this.category : Category.OTHER;
        Category otherCat = o.category != null ? o.category : Category.OTHER;
        int p = Integer.compare(otherCat.getPriority(), thisCat.getPriority());
        return p != 0 ? p : this.createdAt.compareTo(o.createdAt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note note)) return false;
        return Objects.equals(id, note.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        String catName = category != null ? category.getDisplayName() : "Diğer";
        return "[" + catName + "] " + title + " - " + createdAt.toLocalDate() + " (" + id + ")";
    }
}