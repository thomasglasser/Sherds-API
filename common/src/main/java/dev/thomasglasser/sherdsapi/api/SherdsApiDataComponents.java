package dev.thomasglasser.sherdsapi.api;

import dev.thomasglasser.sherdsapi.impl.StackPotDecorations;
import dev.thomasglasser.tommylib.api.registration.ExtendedHolder;
import dev.thomasglasser.tommylib.api.registration.Registrar;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;

public class SherdsApiDataComponents {
    private static final Registrar.DataComponents DATA_COMPONENTS = Registrar.createDataComponents(Registries.DATA_COMPONENT_TYPE, SherdsApiConstants.MOD_ID);

    /// The texture for the sherd pattern.
    public static final ExtendedHolder<DataComponentType<?>, DataComponentType<Identifier>> SHERD_PATTERN = DATA_COMPONENTS.registerSimple("sherd_pattern", builder -> builder.persistent(Identifier.CODEC).networkSynchronized(Identifier.STREAM_CODEC).cacheEncoding());
    /// The list of stack pot decorations.
    public static final ExtendedHolder<DataComponentType<?>, DataComponentType<StackPotDecorations>> STACK_POT_DECORATIONS = DATA_COMPONENTS.registerSimple("stack_pot_decorations", builder -> builder.persistent(StackPotDecorations.CODEC).networkSynchronized(StackPotDecorations.STREAM_CODEC).cacheEncoding());

    @ApiStatus.Internal
    public static void init() {}
}
