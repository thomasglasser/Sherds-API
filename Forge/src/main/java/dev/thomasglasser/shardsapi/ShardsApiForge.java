package dev.thomasglasser.shardsapi;

import dev.thomasglasser.shardsapi.platform.ForgePlatformHelper;
import dev.thomasglasser.shardsapi.platform.Services;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static dev.thomasglasser.shardsapi.ShardsApi.MOD_ID;

@Mod(MOD_ID)
public class ShardsApiForge {
    public ShardsApiForge() {
        ShardsApi.init();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(((ForgePlatformHelper)Services.PLATFORM)::onRegister);
    }
}