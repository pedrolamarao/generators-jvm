package br.dev.pedrolamarao.generators.x500;

import br.dev.pedrolamarao.generators.ber.BerObjectIdentifier;

public record AlgorithmIdentifier (BerObjectIdentifier algorithm, Object parameters)
{
}