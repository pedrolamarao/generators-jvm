package br.dev.pedrolamarao.generators.line;

import br.dev.pedrolamarao.generators.Generator;

import java.io.Reader;

public class LineRunnableGeneratorTest extends LineGeneratorTest
{
    @Override
    Generator<String> create (Reader reader, int buffer)
    {
        return new LineRunnableGenerator(reader,buffer);
    }
}
