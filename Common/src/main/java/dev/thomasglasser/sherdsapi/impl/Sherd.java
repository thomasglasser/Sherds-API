package dev.thomasglasser.sherdsapi.impl;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public record Sherd(Supplier<Item> itemSupplier, ResourceKey<String> pattern)
{
    public static final Codec<Sherd> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(Sherd::item),
            ResourceKey.codec(Registries.DECORATED_POT_PATTERNS).fieldOf("pattern").forGetter(Sherd::pattern)
    ).apply(instance, Sherd::new));

    public Sherd(Item item, ResourceKey<String> pattern)
    {
        this(() -> item, pattern);
    }

    public Sherd(Item item, ResourceLocation pattern)
    {
        this(item, ResourceKey.create(Registries.DECORATED_POT_PATTERNS, pattern));
    }

    public Sherd(Supplier<Item> item, ResourceLocation pattern)
    {
        this(item, ResourceKey.create(Registries.DECORATED_POT_PATTERNS, pattern));
    }

    public Item item()
    {
        return itemSupplier.get();
    }
}
