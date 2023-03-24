package br.dev.pedrolamarao.generators.ber;

import br.dev.pedrolamarao.generators.AbstractGenerator;

import java.io.IOException;
import java.io.InputStream;

public class BerAbstractGenerator extends AbstractGenerator<BerObject>
{
    private final InputStream stream;

    public BerAbstractGenerator (InputStream stream)
    {
        this.stream = stream;
    }

    @Override
    protected void run ()
    {
        try { parse(); }
            catch (IOException e) { throw new RuntimeException(e); }
    }

    void parse () throws IOException
    {
        final int tag;

        {
            final int b = stream.read();
            if (b == -1) {
                this.yield(null);
                return;
            }

            if ((b & 0x1F) != 0x1F)
            {
                // low tag
                tag = (b & 0x1F);
            }
            else
            {
                // #TODO: high tag
                throw new RuntimeException("high tag is unsupported");
            }
        }

        final int length;

        {
            final int b0 = stream.read();
            if (b0 == -1) {
                this.yield(null);
                return;
            }

            if ((b0 & 0x80) == 0)
            {
                // definite short length
                length = b0;
            }
            else
            {
                if (b0 != 0x80)
                {
                    int tmp = 0;
                    for (int i = 0, j = (b0 & 0x7F); i != j; ++i) {
                        final int b1 = stream.read();
                        if (b1 == -1) {
                            this.yield(null);
                            return;
                        }
                        tmp <<= 8;
                        tmp |= (b1 & 0xFF);
                    }
                    length = tmp;
                }
                else
                {
                    // #TODO: indefinite length
                    throw new RuntimeException("indefinite length is unsupported");
                }
            }
        }

        if ((tag & 0x20) == 0)
        {
            // primitive
            // #TODO
            this.yield(new BerNull());
        }
        else
        {
            // constructed

            this.yield(new BerOpen());

            // #TODO
            final byte[] ignored = new byte[length];
            stream.read(ignored);

            this.yield(new BerClose());
        }
    }
}
