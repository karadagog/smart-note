package exception;

public class NoteNotFoundException extends RuntimeException{

    public NoteNotFoundException(String id) {
        super("Not bulunamadı: " + id );
    }
}
