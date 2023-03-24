package br.dev.pedrolamarao.generators.ber;

import java.math.BigInteger;

public record BerInteger(BigInteger value) implements BerObject
{
}
