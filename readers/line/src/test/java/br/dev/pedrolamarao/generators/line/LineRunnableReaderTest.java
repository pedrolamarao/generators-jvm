package br.dev.pedrolamarao.generators.line;

import java.io.Reader;

public class LineRunnableReaderTest extends LineGeneratorTest
{
    @Override
    LineReader create (Reader reader, int buffer)
    {
        return new LineRunnableReader(reader,buffer);
    }
}
