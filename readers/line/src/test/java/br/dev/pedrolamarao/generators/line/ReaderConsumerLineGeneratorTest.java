package br.dev.pedrolamarao.generators.line;

import java.io.StringReader;
import java.util.function.Supplier;

public class ReaderConsumerLineGeneratorTest extends LineTest
{
    @Override
    Supplier<String> parse (String text, int capacity)
    {
        return LineGenerators.consumerFrom( new StringReader(text), capacity );
    }
}
