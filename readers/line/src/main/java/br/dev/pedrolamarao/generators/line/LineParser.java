package br.dev.pedrolamarao.generators.line;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Consumer;

public class LineParser
{
    private final char[] buffer;

    private int position = 0;

    private int limit = 0;

    private final Reader reader;

    public LineParser (Reader reader, int capacity)
    {
        this.buffer = new char[capacity];
        this.reader = reader;
    }

    int read () throws IOException
    {
        final int read = reader.read(buffer,0,buffer.length);
        if (read == -1) return read;
        position = 0;
        limit = read;
        return read;
    }

    public void run (Consumer<String> consumer)
    {
        try
        {
            final var builder = new StringBuilder();

            while (read() != -1) {
                while (position < limit) {
                    int mark = -1;
                    int markPosition = -1;
                    for (int i = position; i != limit; ++i) {
                        final int c = buffer[i];
                        if (c == '\n') {
                            mark = c;
                            markPosition = i;
                            break;
                        }
                    }

                    if (mark == '\n') {
                        builder.append(buffer,position,markPosition - position);
                        position = markPosition + 1;
                        consumer.accept( builder.toString() );
                        builder.setLength(0);
                    }
                    else {
                        builder.append(buffer,position,limit - position);
                        position = limit;
                    }
                }
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
