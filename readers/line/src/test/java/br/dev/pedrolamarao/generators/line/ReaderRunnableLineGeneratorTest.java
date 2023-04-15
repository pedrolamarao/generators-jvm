package br.dev.pedrolamarao.generators.line;

import java.io.StringReader;
import java.util.function.Supplier;

public class ReaderRunnableLineGeneratorTest extends LineTest
{
    @Override
    Supplier<String> parse (String text)
    {
        return LineGenerators.runnableFrom( new StringReader(text) );
    }
}
