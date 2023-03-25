package br.dev.pedrolamarao.generators.ber;

import br.dev.pedrolamarao.generators.AbstractGenerator;

import java.io.IOException;
import java.io.InputStream;

public class BerAbstractGenerator extends AbstractGenerator<BerObject>
{
    private final InputStream stream;

    public BerAbstractGenerator (InputStream stream)
    {
        this.stream = stream;
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
