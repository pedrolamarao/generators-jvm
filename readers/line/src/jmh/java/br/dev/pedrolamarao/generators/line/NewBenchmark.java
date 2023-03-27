package br.dev.pedrolamarao.generators.line;

import org.openjdk.jmh.annotations.*;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.stream.IntStream;

@State(Scope.Benchmark)
public class NewBenchmark
{
    static final int lines = 10240;

    @Benchmark
    public Object reader ()
    {
        return new BufferedReader(new StringReader(data()),8192);
    }

    @Benchmark
    public Object abstractGenerator ()
    {
        return new LineAbstractGenerator(new StringReader(data()),8192);
    }

    @Benchmark
    public Object runnableGenerator ()
    {
        return new LineRunnableGenerator(new StringReader(data()),8192);
    }

    public static String data ()
    {
        final var builder = new StringBuilder();
        IntStream.range(0,lines).forEach(_1 -> builder.append("2022-02-02,foo ,bar, 1.0, Description of this record\n"));
        return builder.toString();
    }
}
