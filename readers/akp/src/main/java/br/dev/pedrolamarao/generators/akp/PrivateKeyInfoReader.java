package br.dev.pedrolamarao.generators.akp;

import br.dev.pedrolamarao.generators.Generator;
import br.dev.pedrolamarao.generators.ber.*;
import br.dev.pedrolamarao.generators.rsa.RsaPrivateKeyParser;

import java.io.ByteArrayInputStream;
import java.security.PrivateKey;

public class PrivateKeyInfoReader
{
    public static PrivateKey read (Generator<BerObject> reader)
    {
        if (! (reader.next() instanceof BerOpen privateKeyInfo_open))
            throw new RuntimeException();

        if (! (reader.next() instanceof BerInteger privateKeyInfo_version))
            throw new RuntimeException();

        if (! (reader.next() instanceof BerOpen privateKeyInfo_privateKeyAlgorithm_open))
            throw new RuntimeException();

        if (! (reader.next() instanceof BerObjectIdentifier privateKeyInfo_privateKeyAlgorithm_algorithm))
            throw new RuntimeException();

        if (! (reader.next() instanceof BerNull privateKeyInfo_privateKeyAlgorithm_parameters))
            throw new RuntimeException();

        if (! (reader.next() instanceof BerClose privateKeyInfo_privateKeyAlgorithm_close))
            throw new RuntimeException();

        if (! (reader.next() instanceof BerBytes privateKeyInfo_privateKey))
            throw new RuntimeException();

        // #TODO: finish consuming reader

        return read(
            privateKeyInfo_privateKeyAlgorithm_algorithm,
            privateKeyInfo_privateKey
        );
    }

    static PrivateKey read(BerObjectIdentifier ignored, BerBytes bytes)
    {
        // #TODO: actually consider identifier
        return RsaPrivateKeyParser.parse( new BerRunnableGenerator( new ByteArrayInputStream( bytes.value() ) ) );
    }
}
