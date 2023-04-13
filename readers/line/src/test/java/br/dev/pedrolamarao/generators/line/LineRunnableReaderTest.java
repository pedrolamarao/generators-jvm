package br.dev.pedrolamarao.generators.line;

import java.io.StringReader;
import java.util.function.Supplier;

public class LineRunnableReaderTest extends LineTest
{
    @Override
    Supplier<String> parse (String text)
    {
        return new LineRunnableReader( new StringReader(text) );
    }
}
