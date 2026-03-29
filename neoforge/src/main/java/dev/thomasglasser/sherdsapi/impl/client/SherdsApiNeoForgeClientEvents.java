package dev.thomasglasser.sherdsapi.impl.client;

import dev.thomasglasser.sherdsapi.api.SherdsApiConstants;
import dev.thomasglasser.sherdsapi.impl.client.renderer.special.StackSensitiveDecoratedPotSpecialRenderer;
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;

public class SherdsApiNeoForgeClientEvents {
    public static void onRegisterSpecialModelRenderers(RegisterSpecialModelRendererEvent event) {
        event.register(SherdsApiConstants.modId("decorated_pot"), StackSensitiveDecoratedPotSpecialRenderer.Unbaked.MAP_CODEC);
    }
}
