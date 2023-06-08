package dev.thomasglasser.sherdsapi.impl;

import dev.thomasglasser.sherdsapi.mixin.accessor.ItemsToPotsAccessor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class PotterySherdRegistryImpl
{
    private static final Map<ResourceLocation, ResourceKey<String>> SHERDS = new HashMap<>();

    public static void register(ResourceLocation sherd, ResourceKey<String> key)
    {
        boolean exists = false;

        for (Item item : ItemsToPotsAccessor.getItemsToPots().keySet())
        {
            if (BuiltInRegistries.ITEM.getKey(item).equals(sherd))
            {
                exists = true;
                break;
            }
        }

        if (SHERDS.containsKey(sherd) || exists)
            throw new IllegalArgumentException("Item already registered with a pattern!");
        else
        {
            SHERDS.put(sherd, key);
        }
    }

    @Nullable
    public static ResourceKey<String> getFor(Item item)
    {
        return SHERDS.get(BuiltInRegistries.ITEM.getKey(item));
    }
}
