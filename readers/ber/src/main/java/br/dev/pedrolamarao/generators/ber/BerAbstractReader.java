package br.dev.pedrolamarao.generators.ber;

import br.dev.pedrolamarao.generators.AbstractGenerator;

import java.io.IOException;
import java.io.InputStream;

public final class BerAbstractReader extends AbstractGenerator<BerObject> implements BerReader
{
    private final InputStream stream;

    public BerAbstractReader (InputStream stream)
    {
        this.stream = stream;
    }

    @Override
    public BerObject read ()
    {
        return this.get();
    }

    @Override
    protected void run ()
    {
        try
        {
            BerParser.parse(stream,this::yield);
            this.yield(null);
        }
        catch (IOException e) { throw new RuntimeException(e); }
    }
}
