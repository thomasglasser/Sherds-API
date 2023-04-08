package dev.thomasglasser.shardsapi;

import net.fabricmc.api.ModInitializer;

public class ShardsApiFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        ShardsApi.init();
    }
}
