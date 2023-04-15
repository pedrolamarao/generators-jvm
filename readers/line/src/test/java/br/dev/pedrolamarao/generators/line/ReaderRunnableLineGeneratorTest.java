package br.dev.pedrolamarao.generators.line;

import java.io.StringReader;
import java.util.function.Supplier;

public class ReaderRunnableLineGeneratorTest extends LineTest
{
    @Override
    Supplier<String> parse (String text, int capacity)
    {
        return LineGenerators.runnableFrom( new StringReader(text), capacity );
    }
}
