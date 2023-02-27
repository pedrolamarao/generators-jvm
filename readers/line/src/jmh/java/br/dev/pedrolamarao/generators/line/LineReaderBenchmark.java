package br.dev.pedrolamarao.generators.line;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static java.nio.charset.StandardCharsets.UTF_8;

public class LineReaderBenchmark
{
    @Benchmark
    public void baseline (Blackhole hole) throws Exception
    {
        try (var stream = LineReaderBenchmark.class.getResourceAsStream("/data.csv")) {
            assert stream != null;
            hole.consume( stream.readAllBytes() );
        }
    }

    @Benchmark
    public void reader (Blackhole hole) throws Exception
    {
        try (var stream = LineReaderBenchmark.class.getResourceAsStream("/data.csv")) {
            assert stream != null;
            final var reader = new BufferedReader( new InputStreamReader(stream,UTF_8) );
            while (true) {
                final var line = reader.readLine();
                if (line == null) break;
                hole.consume(line);
            }
        }
    }

    @Benchmark
    public void parser (Blackhole hole) throws Exception
    {
        try (var stream = LineReaderBenchmark.class.getResourceAsStream("/data.csv")) {
            assert stream != null;
            final var reader = new LineParser( new InputStreamReader(stream,UTF_8), 8192 );
            reader.run(hole::consume);
        }
    }

    @Benchmark
    public void generator (Blackhole hole) throws Exception
    {
        try (var stream = LineReaderBenchmark.class.getResourceAsStream("/data.csv")) {
            assert stream != null;
            final var reader = new LineReader( new InputStreamReader(stream,UTF_8), 8192 );
            while (true) {
                final var line = reader.next();
                if (line == null) break;
                hole.consume(line);
            }
        }
    }

    public static void main (String[] args) throws RunnerException
    {
        final var opt = new OptionsBuilder()
            .include(LineReaderBenchmark.class.getSimpleName())
            .forks(1)
            .build();

        new Runner(opt).run();
    }
}
