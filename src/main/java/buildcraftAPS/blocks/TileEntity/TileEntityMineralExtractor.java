package buildcraftAPS.blocks.TileEntity;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile;
import buildcraft.api.transport.IPipeTile.PipeType;
import buildcraftAPS.UtilsAps;
import buildcraftAPS.VariablesAps;
import buildcraftAPS.mod_Aps;
import buildcraftAPS.blocks.TileEntity.Basic.TileEntityAps;
import buildcraftAPS.recipe.Recipes;
import cofh.api.energy.IEnergyReceiver;

public class TileEntityMineralExtractor extends TileEntityAps implements IEnergyReceiver, IFluidHandler, IPipeConnection{
	
	public FluidTank tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 10);
 	public int energy;
	private int capacity;
	private int maxReceive;
 	private int minemalenergy = 1500;
	public TileEntityMineralExtractor() {
		 super(VariablesAps.SyncIDs.MineralExtractor.ordinal());
		 energy = 0;
		 capacity = 100000;
	     maxReceive = 2000;
	}

	@Override
	public boolean canUpdate() {
 		return true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if (worldObj.isRemote)
			return;
		
		if (tank.getFluid() != null && tank.getFluidAmount() > 10 && energy > minemalenergy){
			energy -= minemalenergy;
			tank.drain(10, true);
 			try {
			ItemStack item = Recipes.recipeExt.getRecipe().getRandomProducts(100);
			
		    if (item != null){
		    	int added = 0;
		    	added = UtilsAps.addToRandomInventoryAround(worldObj, xCoord, yCoord, zCoord, item.copy());
				 
		        if (added == 0)
		        	added = UtilsAps.addToRandomInjectableAround(worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.UNKNOWN, item.copy());
 
		        
				if (added == 0){
					EntityItem entityitem = new EntityItem(getWorldObj(), xCoord + 0.5, yCoord + 0.7, zCoord + 0.5, item.copy());
					worldObj.spawnEntityInWorld(entityitem);
				}
 
		    }} catch (Exception e){
		    	mod_Aps.logger.info(e);
		    }
	    }
		
	}
	
	 public String[] getPossibleProducts()
	  {
		 
	    if (this.worldObj.isRemote)
	    {
	      if (Recipes.recipeExt.getoutput() == null) return new String[0];
	      ItemStack[] P = Recipes.recipeExt.getoutput();
	      String[] Output = new String[P.length];
	      for (int i = 0; i < P.length; i++)
	        Output[i] = (P[i].stackSize + " " + P[i].getItem().getUnlocalizedName(P[i]) + " " + Recipes.recipeExt.getFlo()[i] * 100 + "%");
	      return Output;
	    }
	    
	    return null;
	  }
	 
	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
 		return true;
	}
    @Override
    public void writeToByteBuff(ByteBuf buf) {
 	super.writeToByteBuff(buf);
    buf.writeInt(energy);
    
    }

    @Override
    public void readFromByteBuff(ByteBuf buf) {
 	    super.readFromByteBuff(buf);
	    energy = buf.readInt();
    }
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
 		super.writeToNBT(tagCompound);
		
		tagCompound.setInteger("energy", energy);
//        tagCompound.setInteger("Tank", tank.getFluid() == null ? 0 : tank.getFluidAmount());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
 		super.readFromNBT(tagCompound);
		
		energy = tagCompound.getInteger("energy");
//		tank.fill(new FluidStack(FluidRegistry.LAVA, tagCompound.getInteger("Tank")), true);
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive,
			boolean simulate) {
 		 int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));

		if (!simulate) {
			energy += energyReceived;
		}
		return energyReceived;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
 		return energy;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
 		return capacity;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
	       return tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
				return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
 
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
 		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
 		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
 		return null;
	}

	@Override
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection with) {
 		if (with.ordinal() == worldObj.getBlockMetadata(xCoord, yCoord, zCoord)) {
			return ConnectOverride.DISCONNECT;
		}
		return type == IPipeTile.PipeType.ITEM ? ConnectOverride.CONNECT : ConnectOverride.DEFAULT;
	}

	  public float getScaledLiquidQuantity(int MaxLevel) { return (this.tank.getFluid() == null ? 0 : this.tank.getFluid().amount) * MaxLevel/tank.getCapacity() ; }
	  
	  public float getScaledPowerLevel(int i) { return (float) (this.energy * i/capacity); }
	  
	  public boolean getPowerSufficient() { return this.energy >= minemalenergy; }

	 
}
