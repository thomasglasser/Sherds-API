package dev.thomasglasser.sherdsapi;

import dev.thomasglasser.sherdsapi.impl.SherdsApiForgeEvents;
import dev.thomasglasser.sherdsapi.impl.data.SherdsApiDataGenerators;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;

@Mod(SherdsApi.MOD_ID)
public class SherdsApiNeoForge
{
    public SherdsApiNeoForge() {
        SherdsApi.init();

        IEventBus modBus = ModLoadingContext.get().getActiveContainer().getEventBus();
        if (modBus != null)
        {
            modBus.addListener(SherdsApiDataGenerators::gatherData);
            modBus.addListener(SherdsApiForgeEvents::onNewDatapackRegistry);
        }
    }
}