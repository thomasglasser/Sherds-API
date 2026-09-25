package dev.thomasglasser.sherdsapi.api.world.item;

import dev.thomasglasser.sherdsapi.api.SherdsApiDataComponents;
import dev.thomasglasser.tommylib.api.registration.ItemHolder;
import dev.thomasglasser.tommylib.api.registration.Registrar;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

/// Utilities for registering and configuring sherd items.
public class SherdUtils {
    /// Registers a sherd item with the given registry, id, pattern, and properties.
    public static Item registerSherd(Registry<Item> registry, Identifier id, Identifier pattern, Item.Properties properties) {
        return Registry.register(registry, id, new Item(sherdProperties(properties, pattern)));
    }

    /// Registers a sherd item with the given registry, id, and properties, deriving the pattern from the id.
    public static Item registerSherd(Registry<Item> registry, Identifier id, Item.Properties properties) {
        return registerSherd(registry, id.withSuffix("_pottery_sherd"), Identifier.fromNamespaceAndPath(id.getNamespace(), id.getPath() + "_pottery_pattern"), properties);
    }

    /// Registers a sherd item with the given registry and id with default properties.
    public static Item registerSherd(Registry<Item> registry, Identifier id) {
        return registerSherd(registry, id, new Item.Properties());
    }

    /// Registers a sherd item using a supplier-based registration function with the given name, pattern, and properties.
    public static <R> R registerSherd(BiFunction<String, Supplier<Item>, R> registerFunc, String name, Identifier pattern, Item.Properties properties) {
        return registerFunc.apply(name, () -> new Item(sherdProperties(properties, pattern)));
    }

    /// Registers a sherd item using a supplier-based registration function with default vanilla naming.
    public static <R> R registerSherd(BiFunction<String, Supplier<Item>, R> registerFunc, String namespace, String name, Item.Properties properties) {
        return registerSherd(registerFunc, name + "_pottery_sherd", Identifier.fromNamespaceAndPath(namespace, name + "_pottery_pattern"), properties);
    }

    /// Registers a sherd item using a supplier-based registration function with default vanilla naming and properties.
    public static <R> R registerSherd(BiFunction<String, Supplier<Item>, R> registerFunc, String namespace, String name) {
        return registerSherd(registerFunc, namespace, name, new Item.Properties());
    }

    /// Registers a sherd item with TommyLib Registrar with the given name, pattern, and properties.
    public static ItemHolder<Item> registerSherd(Registrar.Items provider, String name, Identifier pattern, Item.Properties properties) {
        return registerSherd(provider::register, name, pattern, properties);
    }

    /// Registers a sherd item with TommyLib Registrar with default vanilla naming.
    public static ItemHolder<Item> registerSherd(Registrar.Items provider, String name, Item.Properties properties) {
        return registerSherd(provider::register, provider.namespace(), name, properties);
    }

    /// Registers a sherd item with TommyLib Registrar with default vanilla naming and properties.
    public static ItemHolder<Item> registerSherd(Registrar.Items provider, String name) {
        return registerSherd(provider::register, provider.namespace(), name);
    }

    /// Applies the sherd pattern component to the given item properties.
    public static Item.Properties sherdProperties(Item.Properties properties, Identifier pattern) {
        return properties.component(SherdsApiDataComponents.SHERD_PATTERN.get(), pattern);
    }

    /// Creates default item properties with the given sherd pattern component.
    public static Item.Properties sherdProperties(Identifier pattern) {
        return sherdProperties(new Item.Properties(), pattern);
    }
}
