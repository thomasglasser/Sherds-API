package dev.thomasglasser.sherdsapi.api.data;

import dev.thomasglasser.sherdsapi.impl.SherdsApiRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ForgeSherdDatagenSuite extends BaseSherdDatagenSuite
{
	private final CompletableFuture<HolderLookup.Provider> registries;

	public ForgeSherdDatagenSuite(GatherDataEvent event, final String modid)
	{
		super(modid);
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = event.getGenerator().getPackOutput();
		RegistrySetBuilder builder = (new RegistrySetBuilder()).add(SherdsApiRegistries.SHERD, (pContext) -> this.sherds.forEach((pair) -> pContext.register(pair.getFirst(), pair.getSecond())));

		DatapackBuiltinEntriesProvider datapackBuiltinEntriesProvider = new DatapackBuiltinEntriesProvider(packOutput, event.getLookupProvider(), builder, Set.of(modid))
		{
			public String getName()
			{
				String var10000 = super.getName();
				return "SherdDatagenSuite / " + var10000 + " " + modid;
			}
		};

		generator.addProvider(event.includeServer(), datapackBuiltinEntriesProvider);

		registries = datapackBuiltinEntriesProvider.getRegistryProvider();

		generator.addProvider(event.includeServer(), new ItemTagsProvider(packOutput, registries, null, modId, event.getExistingFileHelper())
		{
			@Override
			protected CompletableFuture<HolderLookup.Provider> createContentsProvider()
			{
				return registries.thenApply((p_274768_) -> {
					this.builders.clear();
					this.addTags(p_274768_);
					return p_274768_;
				});
			}

			public String getName()
			{
				String var10000 = super.getName();
				return "SherdDatagenSuite / " + var10000 + " " + modid;
			}

			@Override
			protected void addTags(HolderLookup.Provider pProvider)
			{
				List<Item> items = new ArrayList<>();

				sherds.forEach(pair ->
						items.add(pair.getSecond().item()));

				tag(ItemTags.DECORATED_POT_SHERDS)
						.add(items.toArray(new Item[] {}));
			}
		});
	}
}
