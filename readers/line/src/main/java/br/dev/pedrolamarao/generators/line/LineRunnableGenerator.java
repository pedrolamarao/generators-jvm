package br.dev.pedrolamarao.generators.line;

import br.dev.pedrolamarao.generators.Generator;
import br.dev.pedrolamarao.generators.RunnableGenerator;

import java.io.IOException;
import java.io.Reader;

public class LineRunnableGenerator implements Generator<String>
{
    private final char[] buffer;

    private final RunnableGenerator<String> generator;

    private int position = 0;

    private int limit = 0;

    private final Reader reader;

    public LineRunnableGenerator (Reader reader, int capacity)
    {
        this.buffer = new char[capacity];
        this.generator = new RunnableGenerator<>(this::run);
        this.reader = reader;
    }

    public String next ()
    {
        return generator.next();
    }

    int read () throws IOException
    {
        final int read = reader.read(buffer,0,buffer.length);
        if (read == -1) return read;
        position = 0;
        limit = read;
        return read;
    }

    void run ()
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
                        final var next = builder.toString();
                        RunnableGenerator.yield(next);
                        builder.setLength(0);
                    }
                    else {
                        builder.append(buffer,position,limit - position);
                        position = limit;
                    }
                }
            }

            RunnableGenerator.yield(null);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
