package dev.thomasglasser.sherdsapi.api;

import dev.thomasglasser.sherdsapi.SherdsApi;
import dev.thomasglasser.sherdsapi.impl.StackPotDecorations;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

public class SherdsApiDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, SherdsApi.MOD_ID);

    /**
     * Data component type for sherd pattern. Points to a texture.
     */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ResourceLocation>> SHERD_PATTERN = DATA_COMPONENTS.registerComponentType("sherd_pattern", builder -> builder.persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC).cacheEncoding());
    /**
     * Data component type for stack pot decorations. Points to a list of stack pot decorations.
     */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StackPotDecorations>> STACK_POT_DECORATIONS = DATA_COMPONENTS.registerComponentType("stack_pot_decorations", builder -> builder.persistent(StackPotDecorations.CODEC).networkSynchronized(StackPotDecorations.STREAM_CODEC).cacheEncoding());

    public static void init() {}
}
