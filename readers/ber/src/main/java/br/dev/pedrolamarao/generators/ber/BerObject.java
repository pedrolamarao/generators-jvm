package br.dev.pedrolamarao.generators.ber;

public sealed interface BerObject permits BerBits, BerBytes, BerClose, BerInteger, BerNull, BerObjectIdentifier, BerOpen, BerString
{
}
