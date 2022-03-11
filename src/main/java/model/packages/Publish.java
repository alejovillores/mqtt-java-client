package model.packages;

import java.util.ArrayList;
import java.util.Collection;

public interface Publish {
    int getId();

    boolean isRetained();

    boolean isDuplicated();

    Byte flagByte();

    Collection<Byte> lengthAlgorithm();

    Collection<Byte> variableHeaderBytes();

    Collection<Byte> payloadBytes();

    ArrayList<Byte> toBytes();
}
