package br.dev.pedrolamarao.generators.line;

import br.dev.pedrolamarao.generators.RunnableGenerator;

import java.io.Reader;
import java.util.function.Supplier;

public final class LineRunnableReader implements Supplier<String>
{
    private final RunnableGenerator<String> generator;

    private final Supplier<String> supplier;

    public LineRunnableReader (Reader reader)
    {
        this.generator = new RunnableGenerator<>(this::run);
        this.supplier = LineSuppliers.from(reader);
    }

    @Override
    public String get ()
    {
        return generator.next();
    }

    void run ()
    {
        while (true) {
            final var line = supplier.get();
            if (line == null) break;
            RunnableGenerator.yield(line);
        }
        RunnableGenerator.yield(null);
    }
}
