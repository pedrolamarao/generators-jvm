package br.dev.pedrolamarao.generators.akp;

import br.dev.pedrolamarao.generators.ber.BerAbstractReader;
import br.dev.pedrolamarao.generators.ber.BerRunnableReader;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PrivateKeyInfoReaderTest
{
    @Test
    public void abstract_ () throws Exception
    {
        final var keys = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        final var generated = (RSAPrivateKey) keys.getPrivate();
        final var encoded = keys.getPrivate().getEncoded();
        final var parsed = (RSAPrivateKey) PrivateKeyInfoReader.read( new BerAbstractReader( new ByteArrayInputStream(encoded ) ) );
        assertThat( parsed.getModulus(), equalTo( generated.getModulus() ) );
        assertThat( parsed.getPrivateExponent(), equalTo( generated.getPrivateExponent() ) );
    }

    @Test
    public void runnable () throws Exception
    {
        final var keys = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        final var generated = (RSAPrivateKey) keys.getPrivate();
        final var encoded = keys.getPrivate().getEncoded();
        final var parsed = (RSAPrivateKey) PrivateKeyInfoReader.read( new BerRunnableReader( new ByteArrayInputStream(encoded ) ) );
        assertThat( parsed.getModulus(), equalTo( generated.getModulus() ) );
        assertThat( parsed.getPrivateExponent(), equalTo( generated.getPrivateExponent() ) );
    }
}
