package dev.thomasglasser.sherdsapi.impl;

import dev.thomasglasser.sherdsapi.SherdsApi;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class SherdsApiRegistries
{
	public static final ResourceKey<Registry<Sherd>> SHERD = createRegistryKey("sherd");

	public static ResourceKey<Sherd> sherdKey(ResourceLocation rl)
	{
		return ResourceKey.create(SherdsApiRegistries.SHERD, rl);
	}

	private static <T> ResourceKey<Registry<T>> createRegistryKey(String pName)
	{
		return ResourceKey.createRegistryKey(SherdsApi.modLoc(pName));
	}

	public static void init() {}
}
