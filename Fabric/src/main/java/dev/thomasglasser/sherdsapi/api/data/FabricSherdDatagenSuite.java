package dev.thomasglasser.sherdsapi.api.data;

import dev.thomasglasser.sherdsapi.impl.Sherd;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The FabricSherdDatagenSuite class is a subclass of BaseSherdDatagenSuite that provides functionality specific to the Fabric modding environment.
 */
public class FabricSherdDatagenSuite extends BaseSherdDatagenSuite
{
	/**
	 * List of providers for generating FabricDataGenerator packs.
	 * Each provider is an implementation of the RegistryDependentFactory interface.
	 */
	List<FabricDataGenerator.Pack.RegistryDependentFactory<?>> providers = new ArrayList<>();

	/**
	 * The constructor for the FabricSherdDatagenSuite class.
	 *
	 * @param modId The id of the mod making sherds.
	 */
	public FabricSherdDatagenSuite(String modId)
	{
		super(modId);
		providers.add(((output, registries) -> new FabricDynamicRegistryProvider(output, registries) {
			@Override
			protected void configure(HolderLookup.Provider registries, Entries entries)
			{
				sherds.forEach(pair -> entries.add(pair.getFirst(), pair.getSecond()));
			}


			@Override
			public String getName()
			{
				return "SherdDatagenSuite / Registries";
			}
		}));

		providers.add((output, registries) -> new FabricTagProvider.ItemTagProvider(output, registries, null)
		{
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

				TagAppender<Item> tag = tag(ItemTags.DECORATED_POT_SHERDS);

				items.stream().map(BuiltInRegistries.ITEM::getResourceKey).map(Optional::orElseThrow).forEach(tag::add);
			}
		});
	}

	/**
	 * Makes a new sherd suite for the given resource key and sherd.
	 * Returns the updated instance of the FabricSherdDatagenSuite.
	 *
	 * @param key   The resource key for the sherd.
	 * @param sherd The sherd to be added.
	 *
	 * @return The updated instance of the FabricSherdDatagenSuite.
	 */
	@Override
	public FabricSherdDatagenSuite makeSherdSuite(ResourceKey<Sherd> key, Sherd sherd)
	{
		return (FabricSherdDatagenSuite) super.makeSherdSuite(key, sherd);
	}

	/**
	 * Builds the FabricDataGenerator pack by adding providers to it.
	 *
	 * @param pack The FabricDataGenerator pack to build.
	 * @see FabricDataGenerator.Pack
	 */
	public void build(FabricDataGenerator.Pack pack)
	{
		providers.forEach(pack::addProvider);
	}
}
