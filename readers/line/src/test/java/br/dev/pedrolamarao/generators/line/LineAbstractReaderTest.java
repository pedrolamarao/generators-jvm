package br.dev.pedrolamarao.generators.line;

import java.io.Reader;

public class LineAbstractReaderTest extends LineGeneratorTest
{
    @Override
    LineReader create (Reader reader, int buffer)
    {
        return new LineAbstractReader(reader);
    }
}
