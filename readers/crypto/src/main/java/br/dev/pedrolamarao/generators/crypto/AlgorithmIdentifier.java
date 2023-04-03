package br.dev.pedrolamarao.generators.crypto;

import br.dev.pedrolamarao.generators.ber.BerObjectIdentifier;

public record AlgorithmIdentifier (BerObjectIdentifier algorithm, Object parameters)
{
    static final byte[] RSA = { 42, -122, 72, -122, -9, 13, 1, 1, 1 };

    static final byte[] EC = { (byte) 0x2A, (byte) 0x86, (byte) 0x48, (byte) 0xCE, (byte) 0x3D, (byte) 0x02, (byte) 0x01 };
}