package br.dev.pedrolamarao.generators.line;

import java.util.function.Supplier;

public class CharArrayRunnableLineGeneratorTest extends LineTest
{
    @Override
    Supplier<String> parse (String text, int capacity)
    {
        return LineGenerators.runnableFrom( text.toCharArray() );
    }
}
