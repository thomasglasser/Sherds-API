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

/**
 * ForgeSherdDatagenSuite is a class that represents a suite of sherd data generation for Forge mods.
 * It extends the BaseSherdDatagenSuite class.
 */
public class ForgeSherdDatagenSuite extends BaseSherdDatagenSuite
{
	/**
	 * Variable registries represents a CompletableFuture object that holds a Provider of HolderLookup.
	 * This variable is private and final, indicating that it cannot be changed once assigned.
	 */
	private final CompletableFuture<HolderLookup.Provider> registries;

	/**
	 * Constructor for ForgeSherdDatagenSuite.
	 * @param event The GatherDataEvent object that is passed in from the data generation event.
	 * @param modid The modid of the mod that is being generated for.
	 */
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
				return "SherdDatagenSuite / " + var10000;
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
				return "SherdDatagenSuite / " + var10000;
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
