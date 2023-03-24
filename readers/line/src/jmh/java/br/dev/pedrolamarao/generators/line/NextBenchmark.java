package br.dev.pedrolamarao.generators.line;

import org.openjdk.jmh.annotations.*;

import java.io.*;
import java.util.stream.IntStream;

@State(Scope.Benchmark)
public class NextBenchmark
{
    static final int lines = 10240;

    @State(Scope.Benchmark)
    public static class ReaderState
    {
        int counter;

        BufferedReader reader;

        @Setup(Level.Invocation)
        public void setup ()
        {
            counter = 0;
            reader = new BufferedReader(new StringReader(data()),8192);
        }
    }

    @Benchmark
    public long reader (ReaderState it) throws Exception
    {
        while (true) {
            final var line = it.reader.readLine();
            if (line == null) break;
            ++it.counter;
        }
        if (it.counter != lines) throw new RuntimeException("unexpected counter: " + it.counter);
        return it.counter;
    }

    @State(Scope.Benchmark)
    public static class ParserState
    {
        int counter;

        LineParser parser;

        @Setup(Level.Invocation)
        public void setup ()
        {
            counter = 0;
            parser = new LineParser(new StringReader(data()),8192);
        }
    }

    @Benchmark
    public long parser (ParserState it)
    {
        it.parser.run(line -> ++it.counter);
        if (it.counter != lines) throw new RuntimeException("unexpected counter: " + it.counter);
        return it.counter;
    }

    @State(Scope.Benchmark)
    public static class AbstractGeneratorState
    {
        int counter;

        LineAbstractGenerator generator;

        @Setup(Level.Invocation)
        public void setup ()
        {
            counter = 0;
            generator = new LineAbstractGenerator(new StringReader(data()),8192);
        }
    }

    @Benchmark
    public long abstractGenerator (AbstractGeneratorState it)
    {
        while (true) {
            final var line = it.generator.next();
            if (line == null) break;
            ++it.counter;
        }
        if (it.counter != lines) throw new RuntimeException("unexpected counter: " + it.counter);
        return it.counter;
    }

    @State(Scope.Benchmark)
    public static class RunnableGeneratorState
    {
        int counter;

        LineRunnableGenerator generator;

        @Setup(Level.Invocation)
        public void setup ()
        {
            counter = 0;
            generator = new LineRunnableGenerator(new StringReader(data()),8192);
        }
    }

    @Benchmark
    public long runnableGenerator (RunnableGeneratorState it)
    {
        while (true) {
            final var line = it.generator.next();
            if (line == null) break;
            ++it.counter;
        }
        if (it.counter != lines) throw new RuntimeException("unexpected counter: " + it.counter);
        return it.counter;
    }

    public static String data ()
    {
        final var builder = new StringBuilder();
        IntStream.range(0,lines).forEach(_1 -> builder.append("2022-02-02,foo ,bar, 1.0, Description of this record\n"));
        return builder.toString();
    }
}
