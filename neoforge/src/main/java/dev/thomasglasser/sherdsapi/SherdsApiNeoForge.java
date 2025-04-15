package dev.thomasglasser.sherdsapi;

import dev.thomasglasser.sherdsapi.impl.client.renderer.special.StackSensitiveDecoratedPotSpecialRenderer;
import dev.thomasglasser.sherdsapi.impl.data.SherdsApiDataGenerators;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;

@Mod(SherdsApi.MOD_ID)
public class SherdsApiNeoForge {
    public SherdsApiNeoForge(IEventBus eventBus) {
        SherdsApi.init();

        eventBus.addListener(SherdsApiDataGenerators::onGatherData);
        eventBus.addListener((RegisterSpecialModelRendererEvent event) -> event.register(SherdsApi.modLoc("decorated_pot"), StackSensitiveDecoratedPotSpecialRenderer.Unbaked.MAP_CODEC));
    }
}
