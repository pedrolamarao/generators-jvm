package br.dev.pedrolamarao.generators.line;

import java.util.Iterator;

public class LineIterator
{
    public static Iterator<String> from (char[] chars)
    {
        return new CharArrayLineIterator(chars,0,chars.length);
    }
}
