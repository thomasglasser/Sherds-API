package dev.thomasglasser.sherdsapi.impl.data;

import dev.thomasglasser.sherdsapi.SherdsApi;
import dev.thomasglasser.sherdsapi.api.data.SherdForgeDatagenSuite;
import dev.thomasglasser.sherdsapi.impl.Sherd;
import dev.thomasglasser.sherdsapi.impl.SherdsApiSherds;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import net.minecraftforge.data.event.GatherDataEvent;

public class SherdsApiDataGenerators
{
	public static void gatherData(GatherDataEvent event)
	{
		new SherdForgeDatagenSuite(event, SherdsApi.MOD_ID)
				.makeSherdSuite(SherdsApiSherds.LIGHTBULB, new Sherd(Items.LIGHT, DecoratedPotPatterns.ARCHER))
				.makeSherdSuite(SherdsApiSherds.API, new Sherd(() -> Items.BARRIER, SherdsApi.modLoc("api")));
	}
}
