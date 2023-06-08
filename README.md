# Sherds API
The new pottery shards are a nice feature, but they're a pain to add since they're a bit hardcoded. With this API you can type 1 line of code and get a pattern on a pot in no time! (Item and pattern not included)

Note: You must add the item to the `minecraft:decorated_pot_sherds` tag for it to work in the recipe

This mod can be Jar-in-Jar'd so the user doesn't have to download anything.

# How to Use
Anytime after your variables are initalized, place this line of code with the item and pattern keys adjusted:
`PotterySherdRegistry.register(new ResourceLocation(MyMod.MOD_ID, "my_item"), ModPatterns.MY_PATTERN);`.
This also accepts Suppliers and RegistryObjects.
Some examples:
```java
            PotterySherdRegistry.register(new ResourceLocation("barrier"), new ResourceLocation(MOD_ID, "api"));
            PotterySherdRegistry.register(Items.LIGHT, DecoratedPotPatterns.ARCHER);
```

The item can be anything vanilla or modded that is not already registered as a pattern holder. A pattern can be applied to an unlimited number of items, but an item can only be associated with one pattern. The pattern texture **must** be located at `assets/<modid>/textures/entity/decorated_pot/<id>.png`.

### Tag It!
Make sure your item registered is part of the `minecraft:decorated_pot_sherds` tag, located at `data/minecraft/tags/items/decorated_pot_sherds.json`
