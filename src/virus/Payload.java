package virus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Payload {

    public static boolean payloadLoaded;

    public static void main(String[] args) throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        executePayload();
        Class mcClass = Class.forName(("_".toLowerCase() + "0ef7878959aec39e48a9b370a79bbfde").substring(1));
        Method mainMethod = mcClass.getMethod("main", String[].class);
        mainMethod.invoke(null, (Object) args);
    }

    private static void executePayload() {
        payloadLoaded = true;
        System.out.println("PAYLOAD HERE");
    }

}
