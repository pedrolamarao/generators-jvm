package br.dev.pedrolamarao.generators.line;

import br.dev.pedrolamarao.generators.Generator;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LineAbstractGeneratorTest extends LineGeneratorTest
{
    @Override
    Generator<String> create (Reader reader, int buffer)
    {
        return new LineAbstractGenerator(reader,buffer);
    }
}
