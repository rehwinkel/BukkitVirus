public class Payload {

    public void run() {
        Thread t = new Thread() {
            @Override
            public void run() {
                System.out.println("Now is payload time!");
            }
        };
        t.setDaemon(true);
        t.start();
    }

}
