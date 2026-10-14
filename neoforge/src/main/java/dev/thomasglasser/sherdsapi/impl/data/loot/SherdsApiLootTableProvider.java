package dev.thomasglasser.sherdsapi.impl.data.loot;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import dev.thomasglasser.sherdsapi.impl.data.loot.packs.SherdsApiBlockLoot;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class SherdsApiLootTableProvider extends LootTableProvider {
    public SherdsApiLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, ImmutableSet.of(), ImmutableList.of(
                new SubProviderEntry(SherdsApiBlockLoot::new, LootContextParamSets.BLOCK)), registries);
    }
}
