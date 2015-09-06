package buildcraftAPS.recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;
import buildcraftAPS.mod_Aps;

public class RecipeManagerExt implements IRecipeMenagerExt{

	private List<RecipeIListExt> recipess = new ArrayList<RecipeIListExt>();
 
	private ItemStack[] output;
	private float[] flo;
 

	@Override
	public void addRecipe( ItemStack[] output, float[] flo) {
		if (output == null || output.length == 0) {
			mod_Aps.logger.error("Tried to register an invalid duster recipe! Skipping.");
			mod_Aps.logger.error("Was trying to add: Output: " + output);
			return;
		}
		RecipeIListExt recipe = getRecipe();
		if (recipe != null) {
			mod_Aps.logger.error("A duster recipe with input is already registered! Skipping.");
			mod_Aps.logger.error("Was trying to add: Input: Output: " + output);
			mod_Aps.logger.error("Found: Input: Output: ");
			return;
		}
//		this.input = input;
		this.output = output;
		this.flo = flo;
   
		recipess.add(new RecipeIExt(output, flo));
 
		mod_Aps.logger.info("Successfully added duster recipe with Input:  Output: " + output);		
	}
 
    public ItemStack[] getoutput(){return output;}
    public float[] getFlo(){return flo;}
	@Override
	public void removeRecipe() {
 
	}

	@Override
	public RecipeIListExt getRecipe() {
		// TODO Auto-generated method stub
 
			for (RecipeIListExt recipe : recipess) {
				if (recipe != null) {
					//mod_Aps.logger.info(recipe.toString());
					return recipe;
				}
			}
		
 		return null;
	}

	@Override
	public List<? extends RecipeIListExt> getRecipes() {
		// TODO Auto-generated method stub
		return Collections.unmodifiableList(recipess);
	}

}
