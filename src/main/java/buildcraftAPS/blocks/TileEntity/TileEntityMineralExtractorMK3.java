package buildcraftAPS.blocks.TileEntity;

import net.minecraft.item.ItemStack;
import buildcraftAPS.VariablesAps;
import buildcraftAPS.mod_Aps;
import buildcraftAPS.blocks.TileEntity.Basic.TileEntityApsBaseMineralExtractor;
import buildcraftAPS.recipe.Recipes;

public class TileEntityMineralExtractorMK3 extends TileEntityApsBaseMineralExtractor {

	private int minemalenergy = 2500;
	public TileEntityMineralExtractorMK3() {
		super(VariablesAps.SyncIDs.MineralExtractorMk3.ordinal(), 30, 300000, 0, 8000);
 	}
	
	@Override
    public void updateEntity() {
     	super.updateEntity();
     	
     	if (worldObj.isRemote){
     		return;
     	}
     	
     	if (tank.getFluid() != null && tank.getFluidAmount() > 30 && energy > minemalenergy ){
     		energy -= minemalenergy;
     		tank.drain(30, true);
     		try{
     		ItemStack item = Recipes.recipeExt.getRecipe().getRandomProducts(40);
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
	        Output[i] = (P[i].stackSize + " " + P[i].getItem().getUnlocalizedName(P[i]) + " " + Recipes.recipeExt.getFlo()[i] * 40 + "%");
	      return Output;
	    }
	    
	    return null;
	  }
	  public float getScaledLiquidQuantity(int MaxLevel) { return (this.tank.getFluid() == null ? 0 : this.tank.getFluid().amount) * MaxLevel/tank.getCapacity() ; }
	  public float getScaledPowerLevel(int i) { return (float) (this.energy * i/capacity); }
	  public boolean getPowerSufficient() { return this.energy >= minemalenergy; }
}
