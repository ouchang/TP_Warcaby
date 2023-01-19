package tp.backend;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple App.
 */
public class MovePieceTest {   
    private final static int WHITE=1;
    private final static int BLACK=2;

    private static final String EMPTY="0";
    private static final String WH_PIECE="1";
    private static final String WH_KING="2";
    private static final String BL_PIECE="3";
    private static final String BL_KING="4";

    public String[][] makeBoard(int boardSize) {
        String[][] board = new String[boardSize+1][boardSize+1];

        for(int i=1; i<=boardSize; i++) {
            for(int j=1 ;j<=boardSize; j++) {
                board[i][j] = EMPTY;
            }
        }

        return board;
    }

    @Test
    public void checkMovePieceTest1() { // white piece regular move
        int boardSize = 8;
        String[][] board = makeBoard(boardSize);
        Position tmp = new Position();
        List<Position> positions = new ArrayList<Position>();

        //preparing arguments
        CzechKind CK = new CzechKind();

        board[6][5] = WH_PIECE;

        tmp.setX(6);
        tmp.setY(5);
        positions.add(tmp);

        tmp = new Position();
        tmp.setX(5);
        tmp.setY(4);
        positions.add(tmp);

        Movement output = CK.checkMovePiece(WHITE, positions, board);

        // preparing expected output
        List<Position> capturedFigures = new ArrayList<Position>();

        //Test
        assertEquals(true, output.isEqual("REGULAR", true, capturedFigures , ""));
    }

    @Test
    public void checkMovePieceTest2() { // black piece incorrect move
        int boardSize = 8;
        String[][] board = makeBoard(boardSize);
        Position tmp = new Position();
        List<Position> positions = new ArrayList<Position>();

        // preparing arguments
        CzechKind CK = new CzechKind();

        board[3][4] = BL_PIECE;

        tmp.setX(3);
        tmp.setY(4);
        positions.add(tmp);

        tmp = new Position();
        tmp.setX(4);
        tmp.setY(6);
        positions.add(tmp);

        Movement output = CK.checkMovePiece(BLACK, positions, board);

        // preparing expected output
        List<Position> capturedFigures = new ArrayList<Position>();

        // Test
        assertEquals(true, output.isEqual("", false, capturedFigures , "ERROR: Incorrect move"));
    }

    @Test
    public void checkMovePieceTest3() { // white piece capture
        int boardSize = 8;
        String[][] board = makeBoard(boardSize);
        Position tmp = new Position();
        List<Position> positions = new ArrayList<Position>();

        // preparing arguments
        CzechKind CK = new CzechKind();

        board[6][5] = WH_PIECE;
        board[5][4] = BL_PIECE;

        tmp.setX(6);
        tmp.setY(5);
        positions.add(tmp);

        tmp = new Position();
        tmp.setX(4);
        tmp.setY(3);
        positions.add(tmp);

        Movement output = CK.checkMovePiece(WHITE, positions, board);

        // preparing expected output
        List<Position> capturedFigures = new ArrayList<Position>();

        tmp = new Position();
        tmp.setX(5);
        tmp.setY(4);
        capturedFigures.add(tmp);

        // Test
        assertEquals(true, output.isEqual("CAPTURE", true, capturedFigures , ""));
    }

    @Test
    public void checkGermanCaptureTest1() { // white piece reversed right capture
        int boardSize = 8;
        String[][] board = makeBoard(boardSize);
        Position tmp = new Position();
        List<Position> positions = new ArrayList<Position>();

        // preparing arguments
        GermanKind GK = new GermanKind();

        board[4][3] = WH_PIECE;
        board[5][4] = BL_PIECE;

        tmp.setX(4);
        tmp.setY(3);
        positions.add(tmp);

        tmp = new Position();
        tmp.setX(6);
        tmp.setY(5);
        positions.add(tmp);

        Movement output = GK.checkMovePiece(WHITE, positions, board);

        // preparing expected output
        List<Position> capturedFigures = new ArrayList<Position>();

        tmp = new Position();
        tmp.setX(5);
        tmp.setY(4);
        capturedFigures.add(tmp);

        // Test
        assertEquals(true, output.isEqual("CAPTURE", true, capturedFigures , ""));
    }

    @Test
    public void checkGermanCaptureTest2() { // white piece reversed left capture
        int boardSize = 8;
        String[][] board = makeBoard(boardSize);
        Position tmp = new Position();
        List<Position> positions = new ArrayList<Position>();

        // preparing arguments
        GermanKind GK = new GermanKind();

        board[3][6] = WH_PIECE;
        board[4][5] = BL_PIECE;

        tmp.setX(3);
        tmp.setY(6);
        positions.add(tmp);

        tmp = new Position();
        tmp.setX(5);
        tmp.setY(4);
        positions.add(tmp);

        Movement output = GK.checkMovePiece(WHITE, positions, board);

        // preparing expected output
        List<Position> capturedFigures = new ArrayList<Position>();

        tmp = new Position();
        tmp.setX(4);
        tmp.setY(5);
        capturedFigures.add(tmp);

        // Test
        assertEquals(true, output.isEqual("CAPTURE", true, capturedFigures , ""));
    }

    @Test
    public void checkGermanCaptureTest3() { // black piece reversed left capture
        int boardSize = 8;
        String[][] board = makeBoard(boardSize);
        Position tmp = new Position();
        List<Position> positions = new ArrayList<Position>();

        // preparing arguments
        GermanKind GK = new GermanKind();

        board[6][7] = BL_PIECE;
        board[5][6] = WH_PIECE;

        tmp.setX(6);
        tmp.setY(7);
        positions.add(tmp);

        tmp = new Position();
        tmp.setX(4);
        tmp.setY(5);
        positions.add(tmp);

        Movement output = GK.checkMovePiece(BLACK, positions, board);

        // preparing expected output
        List<Position> capturedFigures = new ArrayList<Position>();

        tmp = new Position();
        tmp.setX(5);
        tmp.setY(6);
        capturedFigures.add(tmp);

        // Test
        assertEquals(true, output.isEqual("CAPTURE", true, capturedFigures , ""));
    }

    @Test
    public void checkGermanCaptureTest4() { // black piece reversed right capture
        int boardSize = 8;
        String[][] board = makeBoard(boardSize);
        Position tmp = new Position();
        List<Position> positions = new ArrayList<Position>();

        // preparing arguments
        GermanKind GK = new GermanKind();

        board[5][2] = BL_PIECE;
        board[4][3] = WH_PIECE;

        tmp.setX(5);
        tmp.setY(2);
        positions.add(tmp);

        tmp = new Position();
        tmp.setX(3);
        tmp.setY(4);
        positions.add(tmp);

        Movement output = GK.checkMovePiece(BLACK, positions, board);

        // preparing expected output
        List<Position> capturedFigures = new ArrayList<Position>();

        tmp = new Position();
        tmp.setX(4);
        tmp.setY(3);
        capturedFigures.add(tmp);

        // Test
        assertEquals(true, output.isEqual("CAPTURE", true, capturedFigures , ""));
    }
}
