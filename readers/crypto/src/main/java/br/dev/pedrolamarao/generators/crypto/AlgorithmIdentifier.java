package br.dev.pedrolamarao.generators.crypto;

import br.dev.pedrolamarao.generators.ber.BerObjectIdentifier;

public record AlgorithmIdentifier (BerObjectIdentifier algorithm, Object parameters)
{
}