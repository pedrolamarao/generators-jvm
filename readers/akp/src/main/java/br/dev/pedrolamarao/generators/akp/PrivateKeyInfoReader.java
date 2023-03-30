package br.dev.pedrolamarao.generators.akp;

import br.dev.pedrolamarao.generators.ber.*;
import br.dev.pedrolamarao.generators.rsa.RsaPrivateKeyReader;

import java.io.ByteArrayInputStream;
import java.security.PrivateKey;

public final class PrivateKeyInfoReader
{
    public static PrivateKey read (BerReader reader)
    {
        if (! (reader.read() instanceof BerOpen privateKeyInfo_open))
            throw new RuntimeException();

        if (! (reader.read() instanceof BerInteger privateKeyInfo_version))
            throw new RuntimeException();

        if (! (reader.read() instanceof BerOpen privateKeyInfo_privateKeyAlgorithm_open))
            throw new RuntimeException();

        if (! (reader.read() instanceof BerObjectIdentifier privateKeyInfo_privateKeyAlgorithm_algorithm))
            throw new RuntimeException();

        if (! (reader.read() instanceof BerNull privateKeyInfo_privateKeyAlgorithm_parameters))
            throw new RuntimeException();

        if (! (reader.read() instanceof BerClose privateKeyInfo_privateKeyAlgorithm_close))
            throw new RuntimeException();

        if (! (reader.read() instanceof BerBytes privateKeyInfo_privateKey))
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
        return RsaPrivateKeyReader.parse( new BerRunnableReader( new ByteArrayInputStream( bytes.value() ) ) );
    }
}
