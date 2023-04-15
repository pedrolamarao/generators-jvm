package br.dev.pedrolamarao.generators.line;

import java.io.StringReader;
import java.util.function.Supplier;

public class ReaderLineSupplierTest extends LineTest
{
    @Override
    Supplier<String> parse (String text)
    {
        return LineSuppliers.from( new StringReader(text) );
    }
}
