package br.dev.pedrolamarao.generators.ber;

import java.math.BigInteger;

public record BerInteger(byte[] value) implements BerObject
{
}
