package dev.thomasglasser.sherdsapi.impl.data;

import dev.thomasglasser.sherdsapi.impl.data.models.SherdsApiModelProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class SherdsApiDataGenerators {
    public static void onGatherData(GatherDataEvent.Client event) {
        event.createProvider(SherdsApiModelProvider::new);
    }
}
