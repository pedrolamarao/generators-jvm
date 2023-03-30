package br.dev.pedrolamarao.generators.ber;

import java.io.InputStream;

public class BerAbstractReaderTest extends BerGeneratorTest
{
    @Override
    BerReader create (InputStream stream)
    {
        return new BerAbstractReader(stream);
    }
}
