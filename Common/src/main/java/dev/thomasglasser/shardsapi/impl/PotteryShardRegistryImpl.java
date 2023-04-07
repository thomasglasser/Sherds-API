package dev.thomasglasser.shardsapi.impl;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PotteryShardRegistryImpl
{
    private static final Map<Item, ResourceKey<String>> SHARDS = new HashMap<>();

    public static void register(Supplier<Item> shard, ResourceKey<String> key)
    {
        register(shard.get(), key);
    }

    public static void register(Item shard, ResourceKey<String> key)
    {
        if (SHARDS.containsKey(shard) || DecoratedPotPatterns.getResourceKey(shard) != null)
            throw new IllegalArgumentException("Item already registered with a pattern!");
        else
        {
            SHARDS.put(shard, key);
        }
    }

    @Nullable
    public static ResourceKey<String> getFor(Item item)
    {
        return SHARDS.get(item);
    }
}
