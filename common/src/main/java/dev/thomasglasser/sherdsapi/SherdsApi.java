package dev.thomasglasser.sherdsapi;

import dev.thomasglasser.sherdsapi.api.SherdsApiDataComponents;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SherdsApi {
    public static final String MOD_NAMESPACE = "sherdsapi";
    public static final String MOD_NAME = "Sherds API";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        LOGGER.info("Initializing {} for {} in a {} environment...", MOD_NAME, TommyLibServices.PLATFORM.getPlatformName(), TommyLibServices.PLATFORM.getEnvironmentName());

        SherdsApiDataComponents.init();
    }

    public static Identifier modId(String path) {
        return Identifier.fromNamespaceAndPath(MOD_NAMESPACE, path);
    }
}
