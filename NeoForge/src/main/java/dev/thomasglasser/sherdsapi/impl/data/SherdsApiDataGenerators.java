package dev.thomasglasser.sherdsapi.impl.data;

import dev.thomasglasser.sherdsapi.SherdsApi;
import dev.thomasglasser.sherdsapi.api.data.ForgeSherdDatagenSuite;
import dev.thomasglasser.sherdsapi.impl.Sherd;
import dev.thomasglasser.sherdsapi.impl.SherdsApiSherds;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.VanillaBlockTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SherdsApiDataGenerators
{
	public static void gatherData(GatherDataEvent event)
	{
		new ForgeSherdDatagenSuite(event, SherdsApi.MOD_ID)
				.makeSherdSuite(SherdsApiSherds.LIGHTBULB, new Sherd(Items.LIGHT, DecoratedPotPatterns.ARCHER))
				.makeSherdSuite(SherdsApiSherds.API, new Sherd(() -> Items.BARRIER, SherdsApi.modLoc("api")));
	}
}
