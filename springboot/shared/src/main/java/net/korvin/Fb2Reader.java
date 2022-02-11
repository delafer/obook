package net.korvin;

import net.korvin.entities.AbstractBook;
import net.korvin.fb2.Fb2Book;
import org.jetbrains.annotations.NotNull;

public class Fb2Reader extends StaxReader {
    static Fb2Book model = new Fb2Book();
    @NotNull
    AbstractBook getParsingModel() {
        return new Fb2Book();
    }
}
