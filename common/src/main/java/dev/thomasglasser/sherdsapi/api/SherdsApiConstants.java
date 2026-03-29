package dev.thomasglasser.sherdsapi.api;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SherdsApiConstants {
    public static final String MOD_ID = "sherdsapi";
    public static final String MOD_NAME = "Sherds API";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static Identifier modId(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
