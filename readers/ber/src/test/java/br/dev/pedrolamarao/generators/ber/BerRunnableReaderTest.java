package br.dev.pedrolamarao.generators.ber;

import java.io.InputStream;

public class BerRunnableReaderTest extends BerGeneratorTest
{
    @Override
    BerReader create (InputStream stream)
    {
        return new BerRunnableReader(stream);
    }
}
