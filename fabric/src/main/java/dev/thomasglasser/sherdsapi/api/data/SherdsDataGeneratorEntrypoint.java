package dev.thomasglasser.sherdsapi.api.data;

import dev.thomasglasser.sherdsapi.impl.Sherd;
import dev.thomasglasser.sherdsapi.impl.SherdsApiRegistries;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;

import java.util.List;
import java.util.Objects;

/**
 * The SherdsDataGeneratorEntrypoint interface extends the DataGeneratorEntrypoint interface and provides methods for generating sherd data for the Fabric modding environment.
 * <p>
 * Should be used for datagen instead of normal DataGeneratorEntrypoint.
 */
public interface SherdsDataGeneratorEntrypoint extends DataGeneratorEntrypoint
{
	/**
	 * Retrieves a list of FabricSherdDatagenSuite objects.
	 *
	 * @return The list of FabricSherdDatagenSuite objects.
	 */
	List<FabricSherdDatagenSuite> getSuites();

	/**
	 * This method builds the data generator suites for a specific mod in the Fabric modding environment.
	 * <p>
	 * Must be called in the onInitializeDataGenerator method of the mod's data generator entrypoint.
	 *
	 * @param pack The FabricDataGenerator pack to build.
	 * @param modId The ID of the mod for which to build the suites.
	 * @see FabricDataGenerator.Pack
	 * @see FabricSherdDatagenSuite
	 * @see BaseSherdDatagenSuite
	 * @see Sherd
	 */
	default void buildSuites(FabricDataGenerator.Pack pack, String modId)
	{
		getSuites().stream().filter(suite -> Objects.equals(suite.modId, modId)).forEach(suite -> suite.build(pack));
	}

	/**
	 * Builds the registry for sherd objects.
	 *
	 * @param registryBuilder The registry builder used to build the sherd registry.
	 *
	 * @see RegistrySetBuilder
	 */
	@Override
	default void buildRegistry(RegistrySetBuilder registryBuilder)
	{
		registryBuilder.add(SherdsApiRegistries.SHERD, (pContext) -> getSuites().stream().map(suite -> suite.sherds).forEach(list -> list.forEach((pair) -> pContext.register(pair.getFirst(), pair.getSecond()))));
	}
}
