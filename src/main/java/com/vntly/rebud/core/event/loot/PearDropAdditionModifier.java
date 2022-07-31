package com.vntly.rebud.core.event.loot;
import com.google.gson.JsonObject;
import com.vntly.rebud.RebudMod;
import com.vntly.rebud.core.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.List;

public class PearDropAdditionModifier extends LootModifier {
    private final Item addition;

    protected PearDropAdditionModifier(LootItemCondition[] conditionsIn, Item addition) {
        super(conditionsIn);
        this.addition = addition;
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        float a = context.getRandom().nextFloat();
        if (a > 0.95f) {
            generatedLoot.clear();
            generatedLoot.add(new ItemStack(addition, 1));
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<PearDropAdditionModifier> {
        @Override
        public PearDropAdditionModifier read(ResourceLocation name, JsonObject object,
                                             LootItemCondition[] conditionsIn) {
            Item addition = ForgeRegistries.ITEMS.getValue(
                    new ResourceLocation(GsonHelper.getAsString(object, "addition")));
            return new PearDropAdditionModifier(conditionsIn, addition);
        }

        @Override
        public JsonObject write(PearDropAdditionModifier instance) {
            JsonObject json = makeConditions(instance.conditions);
            json.addProperty("addition", ForgeRegistries.ITEMS.getKey(instance.addition).toString());

            return json;
        }
    }
}
