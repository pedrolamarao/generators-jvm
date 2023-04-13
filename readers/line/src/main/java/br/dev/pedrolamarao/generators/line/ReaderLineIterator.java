package br.dev.pedrolamarao.generators.line;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Supplier;

final class ReaderLineIterator implements Supplier<String>
{
    private final StringBuilder buffer = new StringBuilder();

    private boolean hasNext = true;

    private final Reader reader;

    ReaderLineIterator (Reader reader)
    {
        this.reader = reader;
    }

    @Override
    public String get ()
    {
        if (! hasNext) return null;

        try
        {
            int c = -1;
            while ((c = reader.read()) != -1)
            {
                if (c == '\r' || c == '\n') {
                    final var line = buffer.toString();
                    buffer.setLength(0);
                    return line;
                }
                else {
                    buffer.append((char)c);
                }
            }
        }
        catch (IOException e) { throw new RuntimeException(e); }

        hasNext = false;

        if (! buffer.isEmpty()) {
            final var line = buffer.toString();
            buffer.setLength(0);
            return line;
        }

        return null;
    }
}
