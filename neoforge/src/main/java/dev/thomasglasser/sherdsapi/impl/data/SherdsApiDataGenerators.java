package dev.thomasglasser.sherdsapi.impl.data;

import dev.thomasglasser.sherdsapi.impl.data.loot.SherdsApiLootTableProvider;
import dev.thomasglasser.sherdsapi.impl.data.models.SherdsApiModelProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class SherdsApiDataGenerators {
    public static void onGatherData(GatherDataEvent.Client event) {
        // Server
        event.createProvider(SherdsApiLootTableProvider::new);

        // Client
        event.createProvider(SherdsApiModelProvider::new);
    }
}
