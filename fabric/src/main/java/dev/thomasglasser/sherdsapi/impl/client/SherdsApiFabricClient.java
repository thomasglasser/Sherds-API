package dev.thomasglasser.sherdsapi.impl.client;

import dev.thomasglasser.sherdsapi.SherdsApi;
import dev.thomasglasser.sherdsapi.impl.client.renderer.special.StackSensitiveDecoratedPotSpecialRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.special.SpecialModelRenderers;

public class SherdsApiFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SpecialModelRenderers.ID_MAPPER.put(SherdsApi.modLoc("decorated_pot"), StackSensitiveDecoratedPotSpecialRenderer.Unbaked.MAP_CODEC);
    }
}
