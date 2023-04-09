package dev.thomasglasser.shardsapi.api;

import dev.thomasglasser.shardsapi.impl.PotteryShardRegistryImpl;
import dev.thomasglasser.shardsapi.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

/**
 * Registers pattern to ${@link ResourceLocation} and puts pattern in {@link BuiltInRegistries#DECORATED_POT_PATTERNS} if not already
 */
public class PotteryShardRegistry
{
    public static void register(ResourceKey<Item> shard, ResourceKey<String> key)
    {
        register(shard.location(), key);
    }

    public static void register(ResourceKey<Item> shard, ResourceLocation key)
    {
        register(shard.location(), ResourceKey.create(Registries.DECORATED_POT_PATTERNS, key));
    }

    public static void register(ResourceLocation shard, ResourceKey<String> key)
    {
        if (!BuiltInRegistries.DECORATED_POT_PATTERNS.containsKey(key))
            Services.PLATFORM.register(BuiltInRegistries.DECORATED_POT_PATTERNS, key.location(), key.location().getPath());
        PotteryShardRegistryImpl.register(shard, key);
    }

    public static void register(ResourceLocation shard, ResourceLocation key)
    {
        register(shard, ResourceKey.create(Registries.DECORATED_POT_PATTERNS, key));
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
