package dev.thomasglasser.sherdsapi.impl;

import dev.thomasglasser.sherdsapi.api.SherdsApiConstants;
import dev.thomasglasser.sherdsapi.impl.data.SherdsApiDataGenerators;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(SherdsApiConstants.MOD_ID)
public class SherdsApiNeoForge {
    public SherdsApiNeoForge(IEventBus modBus) {
        SherdsApi.init();

        modBus.addListener(SherdsApiDataGenerators::onGatherData);
    }
}
