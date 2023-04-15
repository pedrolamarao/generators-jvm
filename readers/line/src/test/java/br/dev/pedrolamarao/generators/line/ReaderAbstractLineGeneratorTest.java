package br.dev.pedrolamarao.generators.line;

import java.io.StringReader;
import java.util.function.Supplier;

public class ReaderAbstractLineGeneratorTest extends LineTest
{
    @Override
    Supplier<String> parse (String text)
    {
        return LineGenerators.abstractFrom( new StringReader(text) );
    }
}
