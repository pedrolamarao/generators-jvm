package br.dev.pedrolamarao.generators.line;

import br.dev.pedrolamarao.generators.RunnableGenerator;

import java.io.IOException;
import java.io.Reader;

public final class LineRunnableReader implements LineReader
{
    private final char[] buffer;

    private final RunnableGenerator<String> generator;

    private final Reader reader;

    public LineRunnableReader (Reader reader, int capacity)
    {
        this.buffer = new char[capacity];
        this.generator = new RunnableGenerator<>(this::run);
        this.reader = reader;
    }

    @Override
    public String read ()
    {
        return generator.next();
    }

    void run ()
    {
        try { LineParser.parse(reader,buffer, it -> RunnableGenerator.yield(it.toString())); }
            catch (IOException e) { throw new RuntimeException(e); }
        RunnableGenerator.yield(null);
    }
}
