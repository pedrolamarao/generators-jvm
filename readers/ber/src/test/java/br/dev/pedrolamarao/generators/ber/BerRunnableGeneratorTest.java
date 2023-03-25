package br.dev.pedrolamarao.generators.ber;

import br.dev.pedrolamarao.generators.Generator;

import java.io.InputStream;

public class BerRunnableGeneratorTest extends BerGeneratorTest
{
    @Override
    Generator<BerObject> create (InputStream stream)
    {
        return new BerRunnableGenerator(stream);
    }
}
