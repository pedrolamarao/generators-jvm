package br.dev.pedrolamarao.generators.ber;

import br.dev.pedrolamarao.generators.Generator;

import java.io.InputStream;

public class BerAbstractGeneratorTest extends BerGeneratorTest
{
    @Override
    Generator<BerObject> create (InputStream stream)
    {
        return new BerAbstractGenerator(stream);
    }
}
