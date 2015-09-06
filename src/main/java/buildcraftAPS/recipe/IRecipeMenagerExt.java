package buildcraftAPS.recipe;

import java.util.List;

import net.minecraft.item.ItemStack;

public interface IRecipeMenagerExt {
	void addRecipe( ItemStack[] output, float[] flo);

	void removeRecipe();

	RecipeIListExt getRecipe();

	List<? extends RecipeIListExt> getRecipes();

	 

}
