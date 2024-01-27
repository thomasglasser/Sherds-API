package dev.thomasglasser.sherdsapi.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;

public class SherdsApiClientUtils
{
	public static Level level()
	{
		return Minecraft.getInstance().level;
	}
}
