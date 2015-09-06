package buildcraftAPS.recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.item.ItemStack;
import buildcraftAPS.mod_Aps;

public class RecipeManager implements IRecipeManager{
	private final List<RecipeIList> recipes = new ArrayList<RecipeIList>();
 
	ItemStack input;
	ItemStack[] output;
    float[] flo;
	int energy;
   

	@Override
	public void addRecipe(ItemStack input, ItemStack[] output, float[] flo , int energy) {
		if (input == null || input.getItem() == null || output == null || output.length == 0) {
			mod_Aps.logger.error("Tried to register an invalid duster recipe! Skipping.");
			mod_Aps.logger.error("Was trying to add: Input: " + input + " Output: " + output);
			return;
		}
		RecipeIList recipe = getRecipe(input);
		if (recipe != null) {
			mod_Aps.logger.error("A duster recipe with input " + input + " is already registered! Skipping.");
			mod_Aps.logger.error("Was trying to add: Input: " + input + " Output: " + output);
			mod_Aps.logger.error("Found: Input: " + input + " Output: ");
			return;
		}
		this.input = input;
		this.output = output;
		this.flo = flo;
		this.energy = energy;
		recipes.add(new RecipeI(input, output, flo, energy));

		mod_Aps.logger.info("Successfully added duster recipe with Input: " + input + " Output: " + output);
	}
	@Override
	public void removeRecipe(ItemStack input) {
		if (input != null) {
			RecipeIList recipe = null;
			for (Iterator<RecipeIList> iterator = recipes.iterator(); iterator.hasNext(); recipe = iterator.next()) {
				if (recipe != null && recipe.getRandomProduct(input) != null) {
					iterator.remove();
					mod_Aps.logger.info("Successfully removed duster recipe with Valid inputs: " + recipe.getInputs());
					return;
				}
			}
			mod_Aps.logger.error("Tried to remove an invalid duster recipe! A duster recipe with the input " + input + " could not be found! Skipping.");
		} else {
			mod_Aps.logger.error("Tried to remove an invalid duster recipe! Skipping.");
		}
	}
	public int getEnergy(){return energy;}
    public ItemStack[] getoutput(){return output;}
    public float[] getFlo(){return flo;}
	@Override
	public RecipeIList getRecipe(ItemStack input) {
		if (input != null) {
			for (RecipeIList recipe : recipes) {
				if (recipe != null && input.isItemEqual(recipe.getInputs())) {
					//mod_Aps.logger.info(recipe.toString());
					return recipe;
				}
			}
		}
 		return null;
		
	}
	@Override
	public List<? extends RecipeIList> getRecipes() {
		return Collections.unmodifiableList(recipes);
	}
   
}
