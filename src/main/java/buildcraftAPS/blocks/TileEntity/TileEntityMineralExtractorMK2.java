package buildcraftAPS.blocks.TileEntity;

import net.minecraft.item.ItemStack;
import buildcraftAPS.VariablesAps;
import buildcraftAPS.mod_Aps;
import buildcraftAPS.blocks.TileEntity.Basic.TileEntityApsBaseMineralExtractor;
import buildcraftAPS.recipe.Recipes;

public class TileEntityMineralExtractorMK2 extends TileEntityApsBaseMineralExtractor{
 	private int minemalenergy = 2000;
 	
	public TileEntityMineralExtractorMK2() {
		super(VariablesAps.SyncIDs.MineralExtractorMk2.ordinal(), 20, 200000, 0, 4000);
 	}

    @Override
    public void updateEntity() {
     	super.updateEntity();
     	
     	if (worldObj.isRemote){
     		return;
     	}
     	
     	if (tank.getFluid() != null && tank.getFluidAmount() > 20 && energy > minemalenergy){
     		energy -= minemalenergy;
     		tank.drain(20, true);
     		try{
     		ItemStack item = Recipes.recipeExt.getRecipe().getRandomProducts(75);
     		dropItems(item);
     		} catch(Exception e){
     			mod_Aps.logger.info(e);
     		}
     	}
     	
    }
	
    public String[] getPossibleProducts()
	  {
		 
	    if (this.worldObj.isRemote)
	    {
	      if (Recipes.recipeExt.getoutput() == null) return null;
	      ItemStack[] P = Recipes.recipeExt.getoutput();
	      String[] Output = new String[P.length];
	      for (int i = 0; i < P.length; i++)
	        Output[i] = (P[i].stackSize + " " + P[i].getItem().getUnlocalizedName(P[i]) + " " + Recipes.recipeExt.getFlo()[i] * 75 + "%");
	      return Output;
	    }
	    
	    return null;
    }
	  public float getScaledLiquidQuantity(int MaxLevel) { return (this.tank.getFluid() == null ? 0 : this.tank.getFluid().amount) * MaxLevel/tank.getCapacity() ; }
	  public float getScaledPowerLevel(int i) { return (float) (this.energy * i/capacity); }
	  public boolean getPowerSufficient() { return this.energy >= minemalenergy; }

}
