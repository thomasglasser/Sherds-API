package dev.thomasglasser.sherdsapi.impl.client;

import dev.thomasglasser.sherdsapi.api.SherdsApiConstants;
import dev.thomasglasser.sherdsapi.impl.data.SherdsApiDataGenerators;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(value = SherdsApiConstants.MOD_ID, dist = Dist.CLIENT)
public class SherdsApiNeoForgeClient {
    public SherdsApiNeoForgeClient(IEventBus eventBus) {
        eventBus.addListener(SherdsApiDataGenerators::onGatherData);
        eventBus.addListener(SherdsApiNeoForgeClientEvents::onRegisterSpecialModelRenderers);
    }
}
