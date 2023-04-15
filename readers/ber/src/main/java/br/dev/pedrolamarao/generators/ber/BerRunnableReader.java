package br.dev.pedrolamarao.generators.ber;

import br.dev.pedrolamarao.generators.RunnableGenerator;

import java.io.IOException;
import java.io.InputStream;

public final class BerRunnableReader extends RunnableGenerator<BerObject> implements BerReader
{
    public BerRunnableReader (InputStream stream)
    {
        super(() -> run(stream));
    }

    static void run (InputStream stream)
    {
        try
        {
            BerParser.parse(stream,RunnableGenerator::yield);
            RunnableGenerator.yield(null);
        }
        catch (IOException e) { throw new RuntimeException(e); }
    }
}
