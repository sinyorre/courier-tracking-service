package com.one.couriertrackingservice.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.one.couriertrackingservice.model.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {
    private final Gson gson;
    private List<Store> stores = new ArrayList<>();

    @PostConstruct
    private void loadStoresFromJsonFile() {
        try (Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/stores.json"))) {
            stores = gson.fromJson(reader, new TypeToken<List<Store>>() {
            }.getType());
            log.info("stores.json file read successfully");
        } catch (Exception ex) {
            log.error("error occurred while reading stores.json file");
            ex.printStackTrace();
        }
    }

    public List<Store> getStores() {
        return stores;
    }
}
