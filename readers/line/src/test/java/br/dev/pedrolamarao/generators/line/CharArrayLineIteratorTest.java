package br.dev.pedrolamarao.generators.line;

import java.util.Iterator;

public class CharArrayLineIteratorTest extends LineParserTest
{
    @Override
    Iterator<String> parse (String text)
    {
        return LineIterator.from(text.toCharArray());
    }
}
