package net.korvin.entities;

public abstract class PoolObject {
    public abstract void release();

    @Override
    public final int hashCode() {
        return super.hashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        return super.equals(obj);
    }
}
