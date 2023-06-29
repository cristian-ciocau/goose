package com.cciocau.goose.efb;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class EFBRepository {
    private final Cache<EFB, EFB> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(30, TimeUnit.SECONDS)
            .build();

    public void add(EFB efb) {
        cache.put(efb, efb);
    }

    public List<EFB> getAll() {
        return new ArrayList<>(cache.asMap().values());
    }

    public List<EFB> getByType(EFBType type) {
        return getAll().stream()
                .filter(efb -> efb.getType().equals(type))
                .collect(Collectors.toList());
    }
}
