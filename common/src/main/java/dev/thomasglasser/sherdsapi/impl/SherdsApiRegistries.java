package dev.thomasglasser.sherdsapi.impl;

import dev.thomasglasser.sherdsapi.SherdsApi;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class SherdsApiRegistries
{
	public static final ResourceKey<Registry<Sherd>> SHERD = createRegistryKey("sherd");

	private static <T> ResourceKey<Registry<T>> createRegistryKey(String pName)
	{
		return ResourceKey.createRegistryKey(SherdsApi.modLoc(pName));
	}

	public static void init() {}
}
