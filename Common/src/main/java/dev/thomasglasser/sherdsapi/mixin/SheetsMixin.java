package dev.thomasglasser.sherdsapi.mixin;

import dev.thomasglasser.sherdsapi.impl.Sherd;
import dev.thomasglasser.sherdsapi.impl.SherdsApiRegistries;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(Sheets.class)
public abstract class SheetsMixin
{
	@Unique
	private static final Map<Sherd, Material> CUSTOM_MATERIALS = new HashMap<>();

	@Inject(method = "getDecoratedPotMaterial", at = @At("HEAD"), cancellable = true)
	private static void sherdsapi_getDecoratedPotMaterial(ResourceKey<String> resourceKey, CallbackInfoReturnable<Material> cir)
	{
		final Level level = Minecraft.getInstance().level;
		if (level != null)
		{
			for (Sherd sherd : level.registryAccess().registry(SherdsApiRegistries.SHERD).orElseThrow().stream().toList())
			{
				if (sherd.pattern() == resourceKey)
				{
					if (!CUSTOM_MATERIALS.containsKey(sherd))
						CUSTOM_MATERIALS.put(sherd, createDecoratedPotMaterial(resourceKey));

					cir.setReturnValue(CUSTOM_MATERIALS.get(sherd));
				}
			}
		}
	}

	@Shadow
	static Material createDecoratedPotMaterial(ResourceKey<String> resourceKey)
	{
		return null;
	}
}
