package buildcraftAPS.recipe;

import java.util.Random;

import net.minecraft.item.ItemStack;
import buildcraftAPS.mod_Aps;

public class RecipeIExt implements RecipeIListExt {
 	private ItemStack[] output;
	private float[] flo;
	
	public RecipeIExt(ItemStack[] output, float[] flo) {
         this.output = output;
        this.flo = flo;
	}
 	@Override
	public ItemStack getOutput() {
		for (int i = 0; i < output.length;)
			return output[i].copy();
		return null;
	}
    @Override
    public ItemStack getRandomProducts(int chance){
		try{
		float R = new Random().nextFloat() * chance;
		float C = 0;
		for (int i=0; i < output.length;i++){
			if (output.length > 1){
				if (flo.length > 1){
				    if(R < C + flo[i]){
 					  return output[i].copy();}
				    else{
				    	C += flo[i];
 				    }
				}
			    }
			    else
			    {
				if(R < C + flo[0])
					return output[0].copy();
				else C += flo[0];
			    }
			}
 			
		} catch(Exception e) {
			mod_Aps.logger.info(e);
		}
		return null;
    }
}
