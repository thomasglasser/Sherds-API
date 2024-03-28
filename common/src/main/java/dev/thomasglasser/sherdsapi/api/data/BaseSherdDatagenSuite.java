package dev.thomasglasser.sherdsapi.api.data;

import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.sherdsapi.impl.Sherd;
import dev.thomasglasser.sherdsapi.impl.SherdsApiRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * The BaseSherdDatagenSuite class is an abstract class that serves as the base for creating sherd datagen suites.
 * It provides functionality for generating sherd data and creating sherd suites.
 */
public abstract class BaseSherdDatagenSuite
{
	/**
	 * The id of the mod making sherds
	 */
	protected String modId;
	/**
	 * Stores pairs of resource keys and sherd objects.
	 * <p>
	 * The 'sherds' variable is initialized with an empty ArrayList in an instance of the 'BaseSherdDatagenSuite' class.
	 * It is protected, so it can be accessed by subclasses of 'BaseSherdDatagenSuite'.
	 * <p>
	 * Example Usage:
	 * BaseSherdDatagenSuite suite = new BaseSherdDatagenSuite(modId);
	 * suite.makeSherdSuite(key, sherd);
	 */
	protected List<Pair<ResourceKey<Sherd>, Sherd>> sherds = new ArrayList<>();

	/**
	 * The constructor for the BaseSherdDatagenSuite class.
	 *
	 * @param modId The id of the mod making sherds.
	 */
	public BaseSherdDatagenSuite(String modId) {
		this.modId = modId;
		this.generate();
	}

	/**
	 * Provides a designated method for making sherd suites if the chain method is not preferred
	 */
	public void generate() {
	}

	/**
	 * Creates a sherd suite with the given resource key and sherd object.
	 *
	 * @param key   The resource key of the sherd.
	 * @param sherd The sherd object.
	 * @return The sherd suite.
	 */
	public BaseSherdDatagenSuite makeSherdSuite(ResourceKey<Sherd> key, Sherd sherd)
	{
		sherds.add(Pair.of(key, sherd));
		return this;
	}

	/**
	 * Creates a resource key for a sherd with the given path.
	 * @param path The path of the sherd.
	 * @return The resource key of the sherd.
	 */
	protected ResourceKey<Sherd> key(String path)
	{
		return SherdsApiRegistries.sherdKey(new ResourceLocation(modId, path));
	}
}
