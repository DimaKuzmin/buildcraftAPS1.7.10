package buildcraftAPS.recipe;

import java.util.List;

import net.minecraft.item.ItemStack;


public interface IRecipeManager {
	void addRecipe(ItemStack input, ItemStack[] output, float[] flo, int energy);

	//void addRecipe(String oreInput, ItemStack output);

	//void addRecipe(RecipeIList recipe);

	void removeRecipe(ItemStack input);

	RecipeIList getRecipe(ItemStack input);

	List<? extends RecipeIList> getRecipes();

}
