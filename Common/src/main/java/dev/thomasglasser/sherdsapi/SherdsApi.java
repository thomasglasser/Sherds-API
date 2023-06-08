package dev.thomasglasser.sherdsapi;

import dev.thomasglasser.sherdsapi.api.PotterySherdRegistry;
import dev.thomasglasser.sherdsapi.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SherdsApi {
    public static final String MOD_ID = "sherdsapi";
    public static final String MOD_NAME = "Sherds API";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        LOGGER.info("Initializing {} for {} in a {} environment...", MOD_NAME, Services.PLATFORM.getPlatformName(), Services.PLATFORM.getEnvironmentName());

        if (Services.PLATFORM.isDevelopmentEnvironment())
        {
            PotterySherdRegistry.register(new ResourceLocation("barrier"), new ResourceLocation(MOD_ID, "api"));
            PotterySherdRegistry.register(Items.LIGHT, DecoratedPotPatterns.ARCHER);
        }
    }
}