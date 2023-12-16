package dev.thomasglasser.sherdsapi.mixin;

import dev.thomasglasser.sherdsapi.impl.Sherd;
import dev.thomasglasser.sherdsapi.impl.SherdsApiRegistries;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DecoratedPotPatterns.class)
public class DecoratedPotPatternsMixin
{
	@Inject(method = "getResourceKey", at = @At("RETURN"), cancellable = true)
	private static void sherdsapi_checkAddedPatterns(Item item, CallbackInfoReturnable<ResourceKey<String>> cir)
	{
		Level level = Minecraft.getInstance().level;

		if (cir.getReturnValue() == null && level != null)
		{
			for (Sherd sherd : level.registryAccess().registry(SherdsApiRegistries.SHERD).orElseThrow().stream().toList())
			{
				if (sherd.item() == item)
					cir.setReturnValue(sherd.pattern());
			}
		}
	}
}
