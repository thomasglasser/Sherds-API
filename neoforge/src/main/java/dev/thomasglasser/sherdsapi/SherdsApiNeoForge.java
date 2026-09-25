package dev.thomasglasser.sherdsapi;

import dev.thomasglasser.sherdsapi.api.SherdsApiConstants;
import dev.thomasglasser.sherdsapi.impl.SherdsApi;
import net.neoforged.fml.common.Mod;

@Mod(SherdsApiConstants.MOD_ID)
public class SherdsApiNeoForge {
    public SherdsApiNeoForge() {
        SherdsApi.init();
    }
}
