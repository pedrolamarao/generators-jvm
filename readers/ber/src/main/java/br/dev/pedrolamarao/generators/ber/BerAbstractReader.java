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
    protected void run ()
    {
        try
        {
            BerParser.parse(stream,this::jield);
            this.jield(null);
        }
        catch (IOException e) { throw new RuntimeException(e); }
    }
}
