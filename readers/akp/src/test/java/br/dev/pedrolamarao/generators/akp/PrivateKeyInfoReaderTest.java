package br.dev.pedrolamarao.generators.akp;

import br.dev.pedrolamarao.generators.ber.BerAbstractReader;
import br.dev.pedrolamarao.generators.ber.BerRunnableReader;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.security.KeyPairGenerator;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.RSAPrivateKey;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PrivateKeyInfoReaderTest
{
    @Test
    public void abstract_ec () throws Exception
    {
        final var keys = KeyPairGenerator.getInstance("EC").generateKeyPair();
        final var generated = (ECPrivateKey) keys.getPrivate();
        final var encoded = keys.getPrivate().getEncoded();
        final var parsed = (ECPrivateKey) PrivateKeyInfoReader.read( new BerAbstractReader( new ByteArrayInputStream(encoded) ) );
        assertThat( parsed.getParams(), equalTo( generated.getParams() ) );
    }

    @Test
    public void abstract_rsa () throws Exception
    {
        final var keys = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        final var generated = (RSAPrivateKey) keys.getPrivate();
        final var encoded = keys.getPrivate().getEncoded();
        final var parsed = (RSAPrivateKey) PrivateKeyInfoReader.read( new BerAbstractReader( new ByteArrayInputStream(encoded) ) );
        assertThat( parsed.getModulus(), equalTo( generated.getModulus() ) );
        assertThat( parsed.getPrivateExponent(), equalTo( generated.getPrivateExponent() ) );
    }

    @Test
    public void runnable_ec () throws Exception
    {
        final var keys = KeyPairGenerator.getInstance("EC").generateKeyPair();
        final var generated = (ECPrivateKey) keys.getPrivate();
        final var encoded = keys.getPrivate().getEncoded();
        final var parsed = (ECPrivateKey) PrivateKeyInfoReader.read( new BerRunnableReader( new ByteArrayInputStream(encoded) ) );
        assertThat( parsed.getParams(), equalTo( generated.getParams() ) );
    }

    @Test
    public void runnable_rsa () throws Exception
    {
        final var keys = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        final var generated = (RSAPrivateKey) keys.getPrivate();
        final var encoded = keys.getPrivate().getEncoded();
        final var parsed = (RSAPrivateKey) PrivateKeyInfoReader.read( new BerRunnableReader( new ByteArrayInputStream(encoded) ) );
        assertThat( parsed.getModulus(), equalTo( generated.getModulus() ) );
        assertThat( parsed.getPrivateExponent(), equalTo( generated.getPrivateExponent() ) );
    }
}
