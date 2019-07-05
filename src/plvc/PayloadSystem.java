package plvc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("PAYLOAD TIME!");
    }

}
