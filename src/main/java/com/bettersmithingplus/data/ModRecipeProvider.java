package com.bettersmithingplus.data;

import com.bettersmithingplus.BetterSmithingPlus;
import com.bettersmithingplus.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BETTER_SMITHING_TABLE.get())
            .pattern("III")
            .pattern("ISI")
            .pattern("III")
            .define('I', Items.IRON_INGOT)
            .define('S', Items.SMITHING_TABLE)
            .unlockedBy("has_smithing_table", has(Items.SMITHING_TABLE))
            .save(recipeOutput);
    }
}
