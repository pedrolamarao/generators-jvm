package br.dev.pedrolamarao.generators.ber;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1StreamParser;
import org.openjdk.jmh.annotations.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

public class BerBenchmark
{
    static final SecureRandom secureRandom = new SecureRandom( new byte[0] );

    @State(Scope.Benchmark)
    public static class Common
    {
        byte[] data;

        @Setup(Level.Trial)
        public void setup () throws Exception
        {
            final var generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048,secureRandom);
            data = generator.generateKeyPair().getPrivate().getEncoded();
        }
    }

    @Benchmark
    public long abstractGenerator (Common it)
    {
        final var reader = new BerAbstractReader( new ByteArrayInputStream( it.data ) );
        long counter = 0;
        while (true) {
            final var next = reader.read();
            if (next == null) break;
            ++counter;
        }
        return counter;
    }

    @Benchmark
    public long bouncyCastleParser (Common it) throws IOException
    {
        final var parser = new ASN1InputStream( new ByteArrayInputStream( it.data ) );
        long counter = 0;
        while (true) {
            final var next = parser.readObject();
            if (next == null) break;
            ++counter;
        }
        parser.close();
        return counter;
    }

    @Benchmark
    public long bouncyCastleStreamParser (Common it) throws IOException
    {
        final var parser = new ASN1StreamParser( new ByteArrayInputStream( it.data ) );
        long counter = 0;
        while (true) {
            final var next = parser.readObject();
            if (next == null) break;
            ++counter;
        }
        return counter;
    }

    @Benchmark
    public long parser (Common it) throws IOException
    {
        final var stream = new ByteArrayInputStream( it.data );
        final long[] counter = { 0 };
        BerParser.parse(stream,next -> ++counter[0]);
        return counter[0];
    }

    @Benchmark
    public long runnableGenerator (Common it)
    {
        final var reader = new BerRunnableReader( new ByteArrayInputStream( it.data ) );
        long counter = 0;
        while (true) {
            final var next = reader.read();
            if (next == null) break;
            ++counter;
        }
        return counter;
    }
}
