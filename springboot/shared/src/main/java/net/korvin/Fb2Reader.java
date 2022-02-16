package net.korvin;

import net.korvin.entities.AbstractBook;
import org.jetbrains.annotations.NotNull;

public class Fb2Reader extends StaxReader {
    @NotNull
    AbstractBook getParsingModel() {
        return null; //new Fb2Book();
    }
}
