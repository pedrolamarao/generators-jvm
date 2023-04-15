package br.dev.pedrolamarao.generators.line;

import java.util.function.Supplier;

public class CharArrayLineSupplierTest extends LineTest
{
    @Override
    Supplier<String> parse (String text, int capacity)
    {
        return LineSuppliers.from(text.toCharArray());
    }
}
