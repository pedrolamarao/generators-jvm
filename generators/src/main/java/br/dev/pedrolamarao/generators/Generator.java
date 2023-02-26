package br.dev.pedrolamarao.generators;

public interface Generator<T>
{
    <T> T next ();
}
