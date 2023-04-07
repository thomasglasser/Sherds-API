package dev.thomasglasser.shardsapi;

import dev.thomasglasser.shardsapi.api.PotteryShardRegistry;
import dev.thomasglasser.shardsapi.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.launch.platform.MixinPlatformAgentMinecraftForge;

public class ShardsApi {
    public static final String MOD_ID = "shardsapi";
    public static final String MOD_NAME = "Shards API";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        LOGGER.info("Initializing {} for {} in a {} environment...", MOD_NAME, Services.PLATFORM.getPlatformName(), Services.PLATFORM.getEnvironmentName());

//        PotteryShardRegistry.register(() -> Items.CACTUS, new ResourceLocation(MOD_ID, "api"));
//        PotteryShardRegistry.register(Items.ACACIA_BUTTON, DecoratedPotPatterns.ARCHER);
    }
}