package dev.thomasglasser.sherdsapi.api;

import dev.thomasglasser.sherdsapi.impl.PotterySherdRegistryImpl;
import dev.thomasglasser.sherdsapi.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

/**
 * Registers pattern to ${@link ResourceLocation} and puts pattern in {@link BuiltInRegistries#DECORATED_POT_PATTERNS} if not already
 */
public class PotterySherdRegistry
{
    public static void register(ResourceKey<Item> sherd, ResourceKey<String> key)
    {
        register(sherd.location(), key);
    }

    public static void register(ResourceKey<Item> sherd, ResourceLocation key)
    {
        register(sherd.location(), ResourceKey.create(Registries.DECORATED_POT_PATTERNS, key));
    }

    public static void register(ResourceLocation sherd, ResourceKey<String> key)
    {
        if (!BuiltInRegistries.DECORATED_POT_PATTERNS.containsKey(key))
            Services.PLATFORM.register(BuiltInRegistries.DECORATED_POT_PATTERNS, key.location(), key.location().getPath());
        PotterySherdRegistryImpl.register(sherd, key);
    }

    public static void register(ResourceLocation sherd, ResourceLocation key)
    {
        register(sherd, ResourceKey.create(Registries.DECORATED_POT_PATTERNS, key));
    }

    public static void register(Item item, ResourceKey<String> key)
    {
        register(BuiltInRegistries.ITEM.getKey(item), key);
    }

    public static void register(Item item, ResourceLocation key)
    {
        register(BuiltInRegistries.ITEM.getKey(item), key);
    }
}
