package dev.thomasglasser.shardsapi.impl;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PotteryShardRegistryImpl
{
    private static final Map<Supplier<Item>, ResourceKey<String>> SHARDS = new HashMap<>();

    public static void register(Supplier<Item> shard, ResourceKey<String> key)
    {
        if (SHARDS.containsKey(shard))
            throw new IllegalArgumentException("Item already registered with a pattern!");
        else
        {
            SHARDS.put(shard, key);
        }
    }

    public static void register(Item shard, ResourceKey<String> key)
    {
        if (DecoratedPotPatterns.getResourceKey(shard) != null)
            throw new IllegalArgumentException("Item already registered with a pattern!");
        else
            register(() -> shard, key);
    }

    @Nullable
    public static ResourceKey<String> getFor(Item item)
    {
        return SHARDS.get((Supplier<Item>) () -> item);
    }
}
