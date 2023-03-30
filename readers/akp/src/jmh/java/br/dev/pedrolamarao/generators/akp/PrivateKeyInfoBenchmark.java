package br.dev.pedrolamarao.generators.akp;

import br.dev.pedrolamarao.generators.ber.BerAbstractReader;
import br.dev.pedrolamarao.generators.ber.BerRunnableReader;
import org.openjdk.jmh.annotations.*;

import java.io.ByteArrayInputStream;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;

public class PrivateKeyInfoBenchmark
{
    static final SecureRandom secureRandom = new SecureRandom( new byte[0] );

    @State(Scope.Benchmark)
    public static class Common
    {
        byte[] data;

        @Param({"1024","2048","4096"})
        int size;

        @Setup(Level.Trial)
        public void setup () throws Exception
        {
            final var generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(size,secureRandom);
            data = generator.generateKeyPair().getPrivate().getEncoded();
        }
    }

    @Benchmark
    public PrivateKey abstractGenerator (Common it)
    {
        return PrivateKeyInfoReader.read( new BerAbstractReader( new ByteArrayInputStream( it.data ) ) );
    }

    @Benchmark
    public PrivateKey jca (Common it) throws Exception
    {
        return KeyFactory.getInstance("RSA").generatePrivate( new PKCS8EncodedKeySpec( it.data ) );
    }

    @Benchmark
    public PrivateKey runnableGenerator (Common it)
    {
        return PrivateKeyInfoReader.read( new BerRunnableReader( new ByteArrayInputStream( it.data ) ) );
    }
}
