package dev.thomasglasser.shardsapi;

import dev.thomasglasser.shardsapi.api.PotteryShardRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;

public class ShardsApiFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        ShardsApi.init();
    }
}
