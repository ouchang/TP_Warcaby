package tp.backend;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple App.
 */
public class AppTest 
{   
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
    public void checkCoderDecoder() {
        int boardSize = 8;
        String[][] board = makeBoard(boardSize);
        GameStatusClass gameStatus = new GameStatusClass();
        CzechKind CK = new CzechKind();

        CoderDecoder CD = new CoderDecoder();
        
        //Temporary inicializing statusCommand
        gameStatus.setId("12");
        gameStatus.setFriendly_name("testGame1");
        gameStatus.setGameKind(CK.getName());
        gameStatus.setPlayer1("Ola");
        gameStatus.setPlayer2("Aga");
        gameStatus.setTurn(String.valueOf(CK.whoStarts()));
        gameStatus.setStatus("ongoing");
        gameStatus.setError("");
        gameStatus.setBoard(board);

        String json = CD.codeCommand(gameStatus);
        GameStatusClass outputStatus = (GameStatusClass) CD.decodeCommand(json);

        assertEquals(gameStatus.getId(), outputStatus.getId());
        assertEquals(gameStatus.getFriendly_name(), outputStatus.getFriendly_name());
        assertEquals(gameStatus.getGameKind(), outputStatus.getGameKind());
        assertEquals(gameStatus.getPlayer1(), outputStatus.getPlayer1());
        assertEquals(gameStatus.getPlayer2(), outputStatus.getPlayer2());
        assertEquals(gameStatus.getTurn(), outputStatus.getTurn());
        assertEquals(gameStatus.getStatus(), outputStatus.getStatus());
        assertEquals(gameStatus.getError(), outputStatus.getError());

        String gameStatusBoard[][] = gameStatus.getBoard();
        String outputStatusBoard[][] = outputStatus.getBoard();
        for(int i=0; i<=boardSize; i++) {
            for(int j=0; j<=boardSize; j++) {
                assertEquals(gameStatusBoard[i][j], outputStatusBoard[i][j]);
            }
        }
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
    public void checkMoveKingTest1() { // black king regular move
        int boardSize = 8;
        String[][] board = makeBoard(boardSize);
        Position tmp = new Position();
        List<Position> positions = new ArrayList<Position>();

        // preparing arguments
        CzechKind CK = new CzechKind();

        board[1][4] = BL_KING;

        tmp.setX(1);
        tmp.setY(4);
        positions.add(tmp);

        tmp = new Position();
        tmp.setX(5);
        tmp.setY(8);
        positions.add(tmp);

        Movement output = CK.checkMoveKing(BLACK, positions, board);

        // preparing expected output
        List<Position> capturedFigures = new ArrayList<Position>();

        // Test
        assertEquals(true, output.isEqual("REGULAR", true, capturedFigures , ""));
    }

    @Test
    public void checkMoveKingTest2() { // black king capture
        int boardSize = 8;
        String[][] board = makeBoard(boardSize);
        Position tmp = new Position();
        List<Position> positions = new ArrayList<Position>();

        // preparing arguments
        CzechKind CK = new CzechKind();

        board[4][7] = BL_KING;
        board[6][5] = WH_PIECE;

        tmp.setX(4);
        tmp.setY(7);
        positions.add(tmp);

        tmp = new Position();
        tmp.setX(7);
        tmp.setY(4);
        positions.add(tmp);

        Movement output = CK.checkMoveKing(BLACK, positions, board);

        // preparing expected output
        List<Position> capturedFigures = new ArrayList<Position>();

        tmp = new Position();
        tmp.setX(6);
        tmp.setY(5);
        capturedFigures.add(tmp);

        // Test
        assertEquals(true, output.isEqual("CAPTURE", true, capturedFigures , ""));
    }

    @Test
    public void checkMoveKingTest3() { // white king incorrect capture
        int boardSize = 8;
        String[][] board = makeBoard(boardSize);
        Position tmp = new Position();
        List<Position> positions = new ArrayList<Position>();

        // preparing arguments
        CzechKind CK = new CzechKind();

        board[3][8] = WH_KING;
        board[6][5] = BL_PIECE;
        board[4][7] = WH_PIECE;

        tmp.setX(3);
        tmp.setY(8);
        positions.add(tmp);

        tmp = new Position();
        tmp.setX(7);
        tmp.setY(4);
        positions.add(tmp);

        Movement output = CK.checkMoveKing(WHITE, positions, board);

        // preparing expected output
        List<Position> capturedFigures = new ArrayList<Position>();

        tmp = new Position();
        tmp.setX(6);
        tmp.setY(5);
        capturedFigures.add(tmp);

        // Test
        assertEquals(true, output.isEqual("", false, capturedFigures , "ERROR: Incorrect move"));
    }

    @Test
    public void checkMultiCapturePieceTest1() { // black piece multi-capture
        int boardSize = 8;
        String[][] board = makeBoard(boardSize);
        Position tmp = new Position();
        List<Position> positions = new ArrayList<Position>();

        // preparing arguments
        CzechKind CK = new CzechKind();

        board[3][6] = BL_PIECE;
        board[4][5] = WH_PIECE;
        board[6][5] = WH_PIECE;

        tmp.setX(3);
        tmp.setY(6);
        positions.add(tmp);

        tmp = new Position();
        tmp.setX(5);
        tmp.setY(4);
        positions.add(tmp);

        tmp = new Position();
        tmp.setX(7);
        tmp.setY(6);
        positions.add(tmp);

        Movement output = CK.checkMultiCapturePiece(BLACK, positions, board);

        // preparing expected output
        List<Position> capturedFigures = new ArrayList<Position>();

        tmp = new Position();
        tmp.setX(4);
        tmp.setY(5);
        capturedFigures.add(tmp);

        tmp = new Position();
        tmp.setX(6);
        tmp.setY(5);
        capturedFigures.add(tmp);

        // Test
        assertEquals(true, output.isEqual("CAPTURE", true, capturedFigures , ""));
    }

    @Test
    public void checkMultiCaptureKingTest1() { // white king multi-capture
        int boardSize = 8;
        String[][] board = makeBoard(boardSize);
        Position tmp = new Position();
        List<Position> positions = new ArrayList<Position>();

        // preparing arguments
        CzechKind CK = new CzechKind();

        board[1][8] = WH_KING;
        board[4][5] = BL_KING;
        board[7][4] = BL_PIECE;

        tmp = new Position();
        tmp.setX(1);
        tmp.setY(8);
        positions.add(tmp);

        tmp = new Position();
        tmp.setX(6);
        tmp.setY(3);
        positions.add(tmp);

        tmp = new Position();
        tmp.setX(8);
        tmp.setY(5);
        positions.add(tmp);

        Movement output = CK.checkMultiCaptureKing(WHITE, positions, board);

        // preparing expected output
        List<Position> capturedFigures = new ArrayList<Position>();

        tmp = new Position();
        tmp.setX(4);
        tmp.setY(5);
        capturedFigures.add(tmp);

        tmp = new Position();
        tmp.setX(7);
        tmp.setY(4);
        capturedFigures.add(tmp);

        // Test
        assertEquals(true, output.isEqual("CAPTURE", true, capturedFigures , ""));
    }

    @Test
    public void isCapturePossibleTest1() { // possible capture
        int boardSize = 8;
        String[][] board = makeBoard(boardSize);

        // preparing arguments
        CzechKind CK = new CzechKind();
        board[3][6] = BL_PIECE;
        board[4][5] = WH_PIECE;
        board[6][5] = WH_PIECE;

        // Test
        assertEquals(true, CK.isCapturePossible(BLACK, board));
    }

    @Test
    public void isCapturePossibleTest2() { // no capture is possible
        int boardSize = 8;
        String[][] board = makeBoard(boardSize);

        // preparing arguments
        CzechKind CK = new CzechKind();
        board[4][5] = WH_PIECE;
        board[6][7] = BL_PIECE;

        // Test
        assertEquals(false, CK.isCapturePossible(BLACK, board));
    }

    //TO DO: Preapare tests for GermanKind
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
