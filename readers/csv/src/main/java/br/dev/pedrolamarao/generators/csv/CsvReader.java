package br.dev.pedrolamarao.generators.csv;

import br.dev.pedrolamarao.generators.AbstractGenerator;

import java.io.IOException;
import java.io.Reader;

public class CsvReader extends AbstractGenerator<String>
{
    private final Reader reader;

    private final char separator;

    public CsvReader (Reader reader, char separator)
    {
        this.reader = reader;
        this.separator = separator;
    }

    public CsvReader (Reader reader)
    {
        this(reader,',');
    }

    @Override
    protected void run ()
    {
        try
        {
            final var builder = new StringBuilder();

            int c = reader.read();
            while (c != -1) {
                if (c == separator || c == '\n') {
                    final var next = builder.toString();
                    this.yield(next);
                    builder.setLength(0);
                }
                else if (c != '\r') {
                    builder.append((char) c);
                }
                c = reader.read();
            }

            while (true) {
                this.yield(null);
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
