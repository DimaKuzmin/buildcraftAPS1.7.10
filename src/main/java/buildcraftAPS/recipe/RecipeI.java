package buildcraftAPS.recipe;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import buildcraftAPS.mod_Aps;

public class RecipeI implements RecipeIList{

	
	private ItemStack input;
	private ItemStack[] output;
	private float[] flo;
 

 
 	public RecipeI (ItemStack input,ItemStack[] output, float[] flo, int energy){
		this.input = input.copy();
		//this.input.stackSize = 1;
		this.output = output;
        this.flo = flo;
	}
	
	@Override
	public ItemStack getInputs() {
 		return this.input.copy();
	}
	
	public static boolean areItemStacksEqualRecipe(ItemStack stack1, ItemStack stack2) {
		return stack1 != null && stack2 != null && stack1.getItem() == stack2.getItem() && (stack1.getItemDamage() == stack2.getItemDamage() || stack1.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack2.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack1.getItem().isDamageable());
	}
 

	@Override
	public ItemStack getRandomProduct(ItemStack input) {
 
		try{
		float R = ((new Random()).nextFloat() * 100);
		float C = 0;
		for (int i=0; i < output.length;i++){
			if (output.length > 1){
				if (flo.length > 1){
				    if(R < C + flo[i])
					  return areItemStacksEqualRecipe(this.input, input) ? output[i].copy() : null;
				    else C += flo[i];
			    }
			    else
			    {
				if(R < C + flo[0])
					return areItemStacksEqualRecipe(this.input, input) ? output[0].copy() : null;
				else C += flo[0];
			    }
			}
			//mod_Aps.logger.info("Work Fine");
			
		}} catch(Exception e) {
			mod_Aps.logger.info(e);
		}
		return null;
	}

}
