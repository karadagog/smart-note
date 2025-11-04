package model;

public enum Category {

    WORK("İş", 3),
    PERSONAL("Kişisel" ,2),
    STUDY("Eğitim", 2),
    URGENT("Acil", 1),
    OTHER("Diğer", 0);

    private final String displayName;
    private final int priority;

    Category(String displayName, int priority) {
        this.displayName=displayName;
        this.priority=priority;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
