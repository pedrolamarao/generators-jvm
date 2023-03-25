package br.dev.pedrolamarao.generators.ber;

import br.dev.pedrolamarao.generators.Generator;
import br.dev.pedrolamarao.generators.RunnableGenerator;

import java.io.IOException;
import java.io.InputStream;

public class BerRunnableGenerator implements Generator<BerObject>
{
    private final RunnableGenerator<BerObject> generator;

    private final InputStream stream;

    public BerRunnableGenerator (InputStream stream)
    {
        this.generator = new RunnableGenerator<>(this::run);
        this.stream = stream;
    }

    void run ()
    {
        try
        {
            BerParser.parse(stream,RunnableGenerator::yield);
            RunnableGenerator.yield(null);
        }
        catch (IOException e) { throw new RuntimeException(e); }
    }

    @Override
    public BerObject next ()
    {
        return generator.next();
    }
}
