package buildcraftAPS.recipe;

import net.minecraft.item.ItemStack;

public interface RecipeIListExt {
  	ItemStack getOutput();
 	ItemStack getRandomProducts(int chance);
}
