package tp;

import java.io.File;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        CoderDecoder CD = new CoderDecoder();
        //Command command = CD.decodeCommand(new File("/home/ola/WORKSPACE/pwr/warcaby_tp/warcaby/src/main/java/tp/protocol/gameStatus.json"), "gameStatus");
        //System.out.println( "DESERIALIZED: " + command.showView());
        gameCommandClass moveCommand = new gameCommandClass();
        moveCommand.setActorId(1);
        moveCommand.fromX=6; // TEST
        moveCommand.fromY=2; // TEST
        moveCommand.toX=5; // TEST
        moveCommand.toY=4; // TEST

        String output;
        output = CD.codeCommand(moveCommand);
        System.out.println( "SERIALIZED: " + output);

    }
}
