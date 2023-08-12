package dev.thomasglasser.sherdsapi;

import dev.thomasglasser.sherdsapi.impl.data.SherdsApiDataGenerators;
import dev.thomasglasser.sherdsapi.platform.ForgePlatformHelper;
import dev.thomasglasser.sherdsapi.platform.Services;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SherdsApi.MOD_ID)
public class SherdsApiForge {
    public SherdsApiForge() {
        SherdsApi.init();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(((ForgePlatformHelper)Services.PLATFORM)::onRegister);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(SherdsApiDataGenerators::gatherData);
    }
}