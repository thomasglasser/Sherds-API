package dev.thomasglasser.sherdsapi;

import dev.thomasglasser.sherdsapi.impl.SherdsApi;
import net.fabricmc.api.ModInitializer;

public class SherdsApiFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        SherdsApi.init();
    }
}
