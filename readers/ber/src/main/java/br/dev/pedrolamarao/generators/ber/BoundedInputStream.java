package br.dev.pedrolamarao.generators.ber;

import java.io.IOException;
import java.io.InputStream;

final class BoundedInputStream extends InputStream
{
    private int remaining;

    private final InputStream stream;

    public BoundedInputStream (InputStream stream, int bound)
    {
        this.remaining = bound;
        this.stream = stream;
    }

    @Override
    public int read () throws IOException
    {
        if (remaining == 0) return -1;
        final int next = stream.read();
        if (next == -1) return -1;
        remaining--;
        return next;
    }

    public int remaining ()
    {
        return remaining;
    }
}
