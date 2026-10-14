package dev.thomasglasser.sherdsapi.impl.data;

import dev.thomasglasser.sherdsapi.impl.data.loot.SherdsApiLootTableProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class SherdsApiDataGenerators {
    public static void onGatherData(GatherDataEvent event) {
        // Server
        event.createProvider(SherdsApiLootTableProvider::new);
    }
}
