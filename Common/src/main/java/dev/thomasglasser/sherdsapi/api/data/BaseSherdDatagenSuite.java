package dev.thomasglasser.sherdsapi.api.data;

import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.sherdsapi.impl.Sherd;
import net.minecraft.resources.ResourceKey;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSherdDatagenSuite
{
	protected String modId;
	protected List<Pair<ResourceKey<Sherd>, Sherd>> sherds = new ArrayList<>();

	public BaseSherdDatagenSuite(String modId) {
		this.modId = modId;
		this.generate();
	}

	public void generate() {
	}

	public BaseSherdDatagenSuite makeSherdSuite(ResourceKey<Sherd> key, Sherd sherd)
	{
		sherds.add(Pair.of(key, sherd));
		return this;
	}
}
