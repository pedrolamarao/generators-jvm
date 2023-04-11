package br.dev.pedrolamarao.generators.ofx;

import java.util.Map;

public record OfxHeader (Map<String,String> properties) implements OfxObject
{
}
