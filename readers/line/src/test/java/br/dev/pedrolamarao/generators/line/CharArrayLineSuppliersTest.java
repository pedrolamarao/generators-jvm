package br.dev.pedrolamarao.generators.line;

import java.util.function.Supplier;

public class CharArrayLineSuppliersTest extends LineTest
{
    @Override
    Supplier<String> parse (String text)
    {
        return LineSuppliers.from(text.toCharArray());
    }
}
