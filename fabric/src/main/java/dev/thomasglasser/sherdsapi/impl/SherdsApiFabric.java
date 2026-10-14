package dev.thomasglasser.sherdsapi.impl;

import net.fabricmc.api.ModInitializer;

public class SherdsApiFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        SherdsApi.init();
    }
}
