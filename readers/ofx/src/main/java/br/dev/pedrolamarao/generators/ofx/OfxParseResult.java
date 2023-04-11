package br.dev.pedrolamarao.generators.ofx;

public record OfxParseResult<T> (T value, int remaining)
{
}
