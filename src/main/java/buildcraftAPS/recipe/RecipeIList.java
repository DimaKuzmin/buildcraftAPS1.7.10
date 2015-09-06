package buildcraftAPS.recipe;

import net.minecraft.item.ItemStack;

public interface RecipeIList {
	ItemStack getInputs();
    ItemStack getRandomProduct(ItemStack itemStack);
}
