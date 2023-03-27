package br.dev.pedrolamarao.generators.line;

import br.dev.pedrolamarao.generators.AbstractGenerator;

import java.io.IOException;
import java.io.Reader;

public class LineAbstractGenerator extends AbstractGenerator<String>
{
    private final char[] buffer;

    private final Reader reader;

    public LineAbstractGenerator (Reader reader, int capacity)
    {
        this.buffer = new char[capacity];
        this.reader = reader;
    }

    @Override
    protected void run ()
    {
        try { LineParser.parse(reader,buffer,it -> this.yield(it.toString())); }
            catch (IOException e) { throw new RuntimeException(e); }
        this.yield(null);
    }
}
