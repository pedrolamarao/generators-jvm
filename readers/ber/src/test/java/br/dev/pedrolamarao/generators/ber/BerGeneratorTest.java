package br.dev.pedrolamarao.generators.ber;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyPairGenerator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.nullValue;

public abstract class BerGeneratorTest
{
    abstract BerReader create (InputStream stream);

    @Test
    public void keys () throws Exception
    {
        final var keys = KeyPairGenerator.getInstance("RSA").generateKeyPair();

        final var subjectPublicKeyInfoReader = create( new ByteArrayInputStream( keys.getPublic().getEncoded() ) );
        // open: subjectPublicKeyInfo
        assertThat( subjectPublicKeyInfoReader.get(), isA(BerOpen.class) );
        // open: subjectPublicKeyInfo.algorithm : AlgorithmIdentifier
        assertThat( subjectPublicKeyInfoReader.get(), isA(BerOpen.class) );
        // algorithmIdentifier.algorithm : OBJECT IDENTIFIER
        assertThat( subjectPublicKeyInfoReader.get(), isA(BerObjectIdentifier.class) );
        // algorithmIdentifier.parameters : ANY
        assertThat( subjectPublicKeyInfoReader.get(), isA(BerNull.class) );
        // close: subjectPublicKeyInfo.algorithm : algorithmIdentifier
        assertThat( subjectPublicKeyInfoReader.get(), isA(BerClose.class) );
        // subjectPublicKeyInfo.subjectPublicKey : BIT STRING
        assertThat( subjectPublicKeyInfoReader.get(), isA(BerBits.class) );
        // close: subjectPublicKeyInfo
        assertThat( subjectPublicKeyInfoReader.get(), isA(BerClose.class) );
        // finish
        assertThat( subjectPublicKeyInfoReader.get(), nullValue() );

        final var privateKeyInfoReader = create( new ByteArrayInputStream( keys.getPrivate().getEncoded() ) );
        // open: privateKeyInfo
        assertThat( privateKeyInfoReader.get(), isA(BerOpen.class) );
        // privateKeyInfo.version : INTEGER
        assertThat( privateKeyInfoReader.get(), isA(BerInteger.class) );
        // open: privateKeyInfo.privateKeyAlgorithm : AlgorithmIdentifier
        assertThat( privateKeyInfoReader.get(), isA(BerOpen.class) );
        // algorithmIdentifier.algorithm : OBJECT IDENTIFIER
        assertThat( privateKeyInfoReader.get(), isA(BerObjectIdentifier.class) );
        // algorithmIdentifier.parameters : ANY
        assertThat( privateKeyInfoReader.get(), isA(BerNull.class) );
        // close: privateKeyInfo.algorithm : AlgorithmIdentifier
        assertThat( privateKeyInfoReader.get(), isA(BerClose.class) );
        // privateKeyInfo.privateKey : OCTET STRING
        assertThat( privateKeyInfoReader.get(), isA(BerBytes.class) );
        // close: privateKeyInfo
        assertThat( privateKeyInfoReader.get(), isA(BerClose.class) );
        // finish
        assertThat( privateKeyInfoReader.get(), nullValue() );

    }
}
