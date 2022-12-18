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
        //System.out.println( "Hello World!" );
        CoderDecoder CD = new CoderDecoder();
        Command command = CD.decodeCommand(new File("/home/ola/WORKSPACE/pwr/warcaby_tp/warcaby/src/main/java/tp/protocol/newGame.json"), "newGame");
        System.out.println( "ZDEKODOWANO: " + command.showView());
        String output;
        CD.codeCommand(command);
        //System.out.println( "ZAKODOWANO: " + output);

    }
}
