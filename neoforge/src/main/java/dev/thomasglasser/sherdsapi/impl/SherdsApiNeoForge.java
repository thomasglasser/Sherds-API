package dev.thomasglasser.sherdsapi.impl;

import dev.thomasglasser.sherdsapi.api.SherdsApiConstants;
import net.neoforged.fml.common.Mod;

@Mod(SherdsApiConstants.MOD_ID)
public class SherdsApiNeoForge {
    public SherdsApiNeoForge() {
        SherdsApi.init();
    }
}
