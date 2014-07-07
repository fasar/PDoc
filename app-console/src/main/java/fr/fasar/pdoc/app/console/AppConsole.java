package fr.fasar.pdoc.app.console;

import java.io.*;

/**
 * Hello world!
 *
 */
public class AppConsole {
    public static void main( String[] args ) throws FileNotFoundException {
        File fic = new File(args[0]);
        FileReader ficReader = new FileReader(fic);
        LineNumberReader reader = new LineNumberReader(ficReader);

        reader.lines().forEach(line -> {
            System.out.println(line);
        });


    }
}
