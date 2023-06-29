package com.cciocau.goose.efb;

import java.net.InetSocketAddress;
import java.util.Objects;

public class EFB {
    private final String name;
    private final InetSocketAddress address;
    private final EFBType type;

    public EFB(String name, InetSocketAddress address, EFBType type) {
        this.name = name;
        this.address = address;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public EFBType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EFB efb = (EFB) o;
        return Objects.equals(address, efb.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    @Override
    public String toString() {
        return "EFB{" +
                "name='" + name + '\'' +
                ", address=" + address +
                ", type=" + type +
                '}';
    }
}
