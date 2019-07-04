package virus;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;

class ReplacingInputStream extends FilterInputStream {


    final byte[] search, replacement;
    LinkedList<Integer> inQueue = new LinkedList<Integer>();
    LinkedList<Integer> outQueue = new LinkedList<Integer>();

    protected ReplacingInputStream(InputStream in, byte[] search, byte[] replacement) {
        super(in);

        byte[] lenpart = new byte[]{(byte) search.length};
        this.search = new byte[search.length + 1];
        System.arraycopy(lenpart, 0, this.search, 0, 1);
        System.arraycopy(search, 0, this.search, 1, search.length);

        byte[] lenpart2 = new byte[]{(byte) replacement.length};
        this.replacement = new byte[replacement.length + 1];
        System.arraycopy(lenpart2, 0, this.replacement, 0, 1);
        System.arraycopy(replacement, 0, this.replacement, 1, replacement.length);
    }

    private boolean isMatchFound() {
        Iterator<Integer> inIter = inQueue.iterator();
        for (int i = 0; i < search.length; i++)
            if (!inIter.hasNext() || search[i] != inIter.next())
                return false;
        return true;
    }

    private void readAhead() throws IOException {
        while (inQueue.size() < search.length) {
            int next = super.read();
            inQueue.offer(next);
            if (next == -1)
                break;
        }
    }

    @Override
    public int read() throws IOException {
        if (outQueue.isEmpty()) {
            readAhead();

            if (isMatchFound()) {
                for (int i = 0; i < search.length; i++)
                    inQueue.remove();

                for (byte b : replacement)
                    outQueue.offer((int) b);
            } else
                outQueue.add(inQueue.remove());
        }

        return outQueue.remove();
    }

}