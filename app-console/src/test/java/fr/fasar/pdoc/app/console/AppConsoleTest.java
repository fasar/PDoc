package fr.fasar.pdoc.app.console;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fabien_s on 07/07/2014.
 */
public class AppConsoleTest {
    public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("articleLeMonde.txt");
        File fic = new File(url.toURI());

        List<String> args2 = Arrays.asList(fic.getAbsolutePath());
        AppConsole.main((String[])args2.toArray());
    }
}
