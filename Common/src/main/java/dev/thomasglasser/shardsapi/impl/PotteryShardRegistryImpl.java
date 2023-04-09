package dev.thomasglasser.shardsapi.impl;

import dev.thomasglasser.shardsapi.mixin.accessor.ItemsToPotsAccessor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class PotteryShardRegistryImpl
{
    private static final Map<ResourceLocation, ResourceKey<String>> SHARDS = new HashMap<>();

    public static void register(ResourceLocation shard, ResourceKey<String> key)
    {
        boolean exists = false;

        for (Item item : ItemsToPotsAccessor.getItemsToPots().keySet())
        {
            if (BuiltInRegistries.ITEM.getKey(item).equals(shard))
            {
                exists = true;
                break;
            }
        }

        if (SHARDS.containsKey(shard) || exists)
            throw new IllegalArgumentException("Item already registered with a pattern!");
        else
        {
            SHARDS.put(shard, key);
        }
    }

    @Nullable
    public static ResourceKey<String> getFor(Item item)
    {
        return SHARDS.get(BuiltInRegistries.ITEM.getKey(item));
    }
}
