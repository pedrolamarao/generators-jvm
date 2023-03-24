package br.dev.pedrolamarao.generators.ber;

public sealed interface BerObject permits BerBytes, BerClose, BerInteger, BerNull, BerObjectIdentifier, BerOpen, BerString
{
}
