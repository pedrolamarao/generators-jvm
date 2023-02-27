package br.dev.pedrolamarao.generators.csv;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static java.nio.charset.StandardCharsets.UTF_8;

public class CsvReaderBenchmark
{
    @Benchmark
    public void readAllBytes (Blackhole hole) throws Exception
    {
        try (var stream = CsvReaderBenchmark.class.getResourceAsStream("/data.csv")) {
            assert stream != null;
            hole.consume( stream.readAllBytes() );
        }
    }

    @Benchmark
    public void readLineThenSplit (Blackhole hole) throws Exception
    {
        try (var stream = CsvReaderBenchmark.class.getResourceAsStream("/data.csv")) {
            assert stream != null;
            final var reader = new BufferedReader( new InputStreamReader(stream,UTF_8) );
            while (true) {
                final var line = reader.readLine();
                if (line == null) break;
                final var record = line.split(",");
                for (var field : record)
                    hole.consume(field);
            }
        }
    }

    @Benchmark
    public void opencsvReadNext (Blackhole hole) throws Exception
    {
        try (var stream = CsvReaderBenchmark.class.getResourceAsStream("/data.csv")) {
            assert stream != null;
            final var reader = new com.opencsv.CSVReader( new BufferedReader( new InputStreamReader(stream,UTF_8) ) );
            while (true) {
                final var record = reader.readNext();
                if (record == null) break;
                for (var field : record)
                    hole.consume(field);
            }
        }
    }

    @Benchmark
    public void generatorNext (Blackhole hole) throws Exception
    {
        try (var stream = CsvReaderBenchmark.class.getResourceAsStream("/data.csv")) {
            assert stream != null;
            final var reader = new CsvReader( new BufferedReader( new InputStreamReader(stream,UTF_8) ) );
            while (true) {
                final var field = reader.next();
                if (field == CsvReader.SENTINEL) break;
                hole.consume(field);
            }
        }
    }

    public static void main (String[] args) throws RunnerException
    {
        final var opt = new OptionsBuilder()
            .include(CsvReaderBenchmark.class.getSimpleName())
            .forks(1)
            .build();

        new Runner(opt).run();
    }
}
