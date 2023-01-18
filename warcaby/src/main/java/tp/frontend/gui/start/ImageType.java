package tp.frontend.gui.start;

public enum ImageType {
    EMPTY(""),
    WHITE_SIMPLE("simpleWhitePiece.png"),
    WHITE_KING("kingWhitePiece.png"),
    BLACK_SIMPLE("simpleBlackPiece.png"),
    BLACK_KING("kingBlackPiece.png");

    private final String name;

    private ImageType(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }
}
