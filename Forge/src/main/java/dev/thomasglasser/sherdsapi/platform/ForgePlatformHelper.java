package dev.thomasglasser.sherdsapi.platform;

import dev.thomasglasser.sherdsapi.platform.services.IPlatformHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.RegisterEvent;

import java.util.HashMap;
import java.util.Map;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    private final Map<ResourceKey, Object> STUFF = new HashMap<>();

    @Override
    public <T> T register(Registry<T> registry, ResourceLocation location, T t) {
        STUFF.put(ResourceKey.create(registry.key(), location), t);
        return t;
    }

    public void onRegister(RegisterEvent event)
    {
        for (ResourceKey<?> key : STUFF.keySet())
        {
            event.register(ResourceKey.createRegistryKey(key.registry()), key.location(),  () -> STUFF.get(key));
        }
    }
}