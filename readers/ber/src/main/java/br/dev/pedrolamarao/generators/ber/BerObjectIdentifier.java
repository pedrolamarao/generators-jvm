package br.dev.pedrolamarao.generators.ber;

public record BerObjectIdentifier(byte[] bytes) implements BerObject
{
}
