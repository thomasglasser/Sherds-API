package dev.thomasglasser.sherdsapi.impl.client;

import dev.thomasglasser.sherdsapi.api.SherdsApiConstants;
import dev.thomasglasser.sherdsapi.impl.client.renderer.special.StackSensitiveDecoratedPotSpecialRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.special.SpecialModelRenderers;

public class SherdsApiFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SpecialModelRenderers.ID_MAPPER.put(SherdsApiConstants.modId("decorated_pot"), StackSensitiveDecoratedPotSpecialRenderer.Unbaked.MAP_CODEC);
    }
}
