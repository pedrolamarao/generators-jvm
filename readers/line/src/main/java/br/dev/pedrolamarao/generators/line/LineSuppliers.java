package br.dev.pedrolamarao.generators.line;

import java.io.Reader;
import java.util.function.Supplier;

public class LineSuppliers
{
    public static Supplier<String> from (char[] chars)
    {
        return new CharArrayLineIterator(chars,0,chars.length);
    }

    public static Supplier<String> from (Reader reader)
    {
        return new ReaderLineIterator(reader);
    }
}
