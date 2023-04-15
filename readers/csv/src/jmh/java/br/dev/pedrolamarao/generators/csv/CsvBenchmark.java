package br.dev.pedrolamarao.generators.csv;

import com.opencsv.CSVReaderBuilder;
import org.openjdk.jmh.annotations.*;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.util.Arrays;
import java.util.stream.IntStream;

@State(Scope.Benchmark)
public class CsvBenchmark
{
    @Param({"8"})
    int fields;

    @Param({"1024"})
    int length;

    @Param({"1024"})
    int records;

    char[] chars;

    static final char separator = ',';

    @Benchmark
    public long base () throws Exception
    {
        final var separator_ = String.valueOf(separator);
        final var reader = new BufferedReader( new CharArrayReader(chars) );
        int counter = 0;
        while (true) {
            final var line = reader.readLine();
            if (line == null) break;
            final var fields = line.split(separator_,-1);
            counter += fields.length;
        }
        if (counter != records*fields) throw new RuntimeException("unexpected counter: " + counter);
        return counter;
    }

    @Benchmark
    public long opencsv () throws Exception
    {
        final var reader = new CSVReaderBuilder( new CharArrayReader(chars) ).build();
        int counter = 0;
        while (true) {
            final var fields = reader.readNext();
            if (fields == null) break;
            counter += fields.length;
        }
        if (counter != records*fields) throw new RuntimeException("unexpected counter: " + counter);
        return counter;
    }

    @Benchmark
    public long supplierFromCharArray ()
    {
        final var reader = CsvSuppliers.from(chars,separator);
        int counter = 0;
        while (true) {
            final var fields = reader.get();
            if (fields == null) break;
            counter += fields.length;
        }
        if (counter != records*fields) throw new RuntimeException("unexpected counter: " + counter);
        return counter;
    }

    @Benchmark
    public long supplierFromReader ()
    {
        final var reader = CsvSuppliers.from( new CharArrayReader(chars), separator );
        int counter = 0;
        while (true) {
            final var fields = reader.get();
            if (fields == null) break;
            counter += fields.length;
        }
        if (counter != records*fields) throw new RuntimeException("unexpected counter: " + counter);
        return counter;
    }

    @Benchmark
    public long abstractGeneratorFromCharArray ()
    {
        final var reader = CsvGenerators.abstractFrom(chars,separator);
        int counter = 0;
        while (true) {
            final var fields = reader.get();
            if (fields == null) break;
            counter += fields.length;
        }
        if (counter != records*fields) throw new RuntimeException("unexpected counter: " + counter);
        return counter;
    }

    @Benchmark
    public long abstractGeneratorFromReader ()
    {
        final var reader = CsvGenerators.abstractFrom( new CharArrayReader(chars), separator );
        int counter = 0;
        while (true) {
            final var fields = reader.get();
            if (fields == null) break;
            counter += fields.length;
        }
        if (counter != records*fields) throw new RuntimeException("unexpected counter: " + counter);
        return counter;
    }

    @Benchmark
    public long runnableGeneratorFromCharArray ()
    {
        final var reader = CsvGenerators.runnableFrom(chars,separator);
        int counter = 0;
        while (true) {
            final var fields = reader.get();
            if (fields == null) break;
            counter += fields.length;
        }
        if (counter != records*fields) throw new RuntimeException("unexpected counter: " + counter);
        return counter;
    }

    @Benchmark
    public long runnableGeneratorFromReader ()
    {
        final var reader = CsvGenerators.runnableFrom( new CharArrayReader(chars), separator );
        int counter = 0;
        while (true) {
            final var fields = reader.get();
            if (fields == null) break;
            counter += fields.length;
        }
        if (counter != records*fields) throw new RuntimeException("unexpected counter: " + counter);
        return counter;
    }

    @Setup
    public void generate ()
    {
        final var builder = new StringBuilder();
        IntStream.range(0,records).forEach(_1 -> {
            final var record = new StringBuilder();
            IntStream.range(0,fields).forEach(_2 -> {
                final char[] field = new char[length];
                Arrays.fill(field,'x');
                field[length-1] = separator;
                record.append(field);
            });
            record.setCharAt(record.length()-1,'\n');
            builder.append(record);
        });
        chars = builder.toString().toCharArray();
    }
}
