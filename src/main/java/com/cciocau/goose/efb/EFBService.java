package com.cciocau.goose.efb;

import com.cciocau.goose.efb.foreflight.ForeFlightListener;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EFBService {
    private final ExecutorService executorService;
    private final EFBRepository repository = new EFBRepository();

    public EFBService() {
        this(Executors.newFixedThreadPool(1));
    }

    EFBService(ExecutorService executorService) {
        this.executorService = executorService;

        var listener = new ForeFlightListener(repository);

        this.executorService.submit(listener::listen);
    }

    public List<EFB> getFlightBags() {
        return repository.getAll();
    }

    public List<EFB> getFlightBags(EFBType type) {
        return repository.getByType(type);
    }
}
