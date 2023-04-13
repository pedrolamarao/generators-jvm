package br.dev.pedrolamarao.generators.line;

import br.dev.pedrolamarao.generators.AbstractGenerator;

import java.io.Reader;
import java.util.function.Supplier;

public final class LineAbstractReader extends AbstractGenerator<String> implements Supplier<String>
{
    private final Supplier<String> supplier;

    public LineAbstractReader (Reader reader)
    {
        this.supplier = LineSuppliers.from(reader);
    }

    @Override
    public String get ()
    {
        return this.next();
    }

    @Override
    protected void run ()
    {
        while (true) {
            final var line = supplier.get();
            if (line == null) break;
            this.yield(line);
        }
        this.yield(null);
    }
}
