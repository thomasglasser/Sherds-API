package dev.thomasglasser.sherdsapi.impl.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.thomasglasser.sherdsapi.impl.Sherd;
import dev.thomasglasser.sherdsapi.impl.SherdsApiRegistries;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.HashMap;
import java.util.Map;

@Mixin(Sheets.class)
public abstract class SheetsMixin
{
	@Unique
	private static final Map<Sherd, Material> CUSTOM_MATERIALS = new HashMap<>();

	@ModifyReturnValue(method = "getDecoratedPotMaterial", at = @At("RETURN"))
	private static Material sherdsapi_getDecoratedPotMaterial(Material original, @Nullable ResourceKey<String> key)
	{
		final Level level = Minecraft.getInstance().level;
		if (level != null)
		{
			Registry<Sherd> registry = level.registryAccess().registry(SherdsApiRegistries.SHERD).orElseThrow();
			for (Sherd sherd : registry.stream().toList())
			{
				ResourceKey<String> pattern;
				if (sherd.pattern().isPresent())
					pattern = sherd.pattern().get();
				else
					pattern = ResourceKey.create(Registries.DECORATED_POT_PATTERNS, registry.getResourceKey(sherd).orElseThrow().location());
				if (pattern == key)
				{
					if (!CUSTOM_MATERIALS.containsKey(sherd))
						CUSTOM_MATERIALS.put(sherd, createDecoratedPotMaterial(key));

					return CUSTOM_MATERIALS.get(sherd);
				}
			}
		}
		return original;
	}

	@Shadow
	private static Material createDecoratedPotMaterial(ResourceKey<String> resourceKey)
	{
		return null;
	}
}
