package br.dev.pedrolamarao.generators.line;

import java.io.StringReader;
import java.util.function.Supplier;

public class LineAbstractReaderTest extends LineTest
{
    @Override
    Supplier<String> parse (String text)
    {
        return new LineAbstractReader( new StringReader(text) );
    }
}
