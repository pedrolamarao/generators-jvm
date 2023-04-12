package br.dev.pedrolamarao.generators.line;

import java.util.ArrayList;
import java.util.Iterator;

public class LineParserCharsTest extends LineParserTest
{
    @Override
    Iterator<String> parse (String text)
    {
        final var list = new ArrayList<String>();
        LineParser.parse(text.toCharArray(),list::add);
        list.add(null);
        return list.iterator();
    }
}
