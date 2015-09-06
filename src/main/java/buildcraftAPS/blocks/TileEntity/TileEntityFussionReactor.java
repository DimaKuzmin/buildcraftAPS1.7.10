package buildcraftAPS.blocks.TileEntity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import buildcraftAPS.VariablesAps;
import buildcraftAPS.blocks.TileEntity.Basic.TileEntityAps;
import cofh.api.energy.IEnergyHandler;

public class TileEntityFussionReactor extends TileEntityAps implements IEnergyHandler, IFluidHandler {
  
    public FluidTank tank = new FluidTank(FluidRegistry.WATER, 0, FluidContainerRegistry.BUCKET_VOLUME * 10);
    
    public int TempMax;
    public int Temp;
    public int energy;
    public double TokamakFusionFraction =  0.8;
    public double StartWorkEnergy;
    public int capacity;
    public boolean Idle;
    public int BurnTimeRemaining;
	public int TokamakIdlingBurnDelay;
	public float FusionWaterUse = 0.1f;
	public int FusionBurnTime = 1;
	public int PowerIn;
	public int EnergyToHeatingScalar = 1;
	public int TokamakMaxEnergyRec = 5000;
	public int TokamakMaxCoolRate = 1000;
	public ForgeDirection PowerOutDirection;
	public int FusionMaxHeatGen = 500;
 	public float PowerOut;
    public int outEnergy;
	private double EnergyGradient;

	private boolean isPumping;
	

    
	public TileEntityFussionReactor() {
		super(VariablesAps.SyncIDs.FussionReactor.ordinal());
		energy = 0;
		capacity = 5000;
		Temp = 0;
 	    TempMax = 1000000;
 	    StartWorkEnergy =  TempMax * TokamakFusionFraction;
 	    BurnTimeRemaining = 0;
	}
	
	@Override
	public void updateEntity() {
 		super.updateEntity();
		PowerOut = 0;
		PowerIn = 0;
        if (worldObj.isRemote)
        	return;
        
		Idle = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
		
		if (Temp >= StartWorkEnergy)
		{
			if (BurnTimeRemaining > 0)//if it's still burning some fuel
			{
//				mod_Aps.logger.info("Burn");
				Burn();//go burn that fuel some more
				if (!Idle)
				{
					BurnTimeRemaining--;//and decrease the burn time accordingly
					TokamakIdlingBurnDelay = 0;
				}
				else if (TokamakIdlingBurnDelay == 0)
				{
					BurnTimeRemaining--;
					TokamakIdlingBurnDelay = 10;
				}
				else
					TokamakIdlingBurnDelay--;
			}
			else//otherwise
			{
				if (tank.getFluidAmount() > (FluidContainerRegistry.BUCKET_VOLUME * FusionWaterUse) && FusionBurnTime != 0)//if there's enough fuel
				{
					tank.drain((int) (FluidContainerRegistry.BUCKET_VOLUME * FusionWaterUse), true);
					BurnTimeRemaining = FusionBurnTime;//and fill up the burn time
					Burn();
				}
			}
//			if (Idle)
//			{
//				if (getPoweredNeighbour() != null){
//				TileEntity tile = worldObj.getTileEntity(xCoord + getPoweredNeighbour().offsetX , yCoord + getPoweredNeighbour().offsetY, zCoord + getPoweredNeighbour().offsetZ);
//				if (tile instanceof IEnergyHandler){
//				IEnergyHandler energ = (IEnergyHandler) tile;
//				PowerIn = energ.receiveEnergy(getPoweredNeighbour().getOpposite(), (int) (TokamakMaxEnergyRec / 4f), false);
//				Temp += PowerIn * EnergyToHeatingScalar;
//				sync();}
//			    }
//		    }
 
		}
		else
		{
            if (energy != 0 && energy >= TokamakMaxEnergyRec){
            energy -= TokamakMaxEnergyRec;
			PowerIn = (int) TokamakMaxEnergyRec;
 			Temp += PowerIn * EnergyToHeatingScalar; 
			}
		}
		float j = ((float) Temp / (float) TempMax);
		Temp -= TokamakMaxCoolRate * j; 
		if (Temp > TempMax) 
			Temp = TempMax;
		else if (Temp < 0)
			Temp = 0;
		
		if (tank.getFluidAmount() > tank.getCapacity())//and finally make sure the fuel is in range
			tank.setFluid(new FluidStack(tank.getFluid(), tank.getCapacity()));
		else if (tank.getFluidAmount() < 0)
			tank.setFluid(null);
		
		if (energy > capacity){
			energy = capacity;
		}
		
		if (energy < 0){
			energy = 0;
		}
	}
	
	private void Burn() {
		if(!Idle)
		{
		    EnergyGradient = (float) (Temp - StartWorkEnergy) / (float) (TempMax - StartWorkEnergy);//then work out the current energy gradient			
		        outEnergy = 10000;
				sendPower();
				sync();
			Temp += (EnergyGradient * FusionMaxHeatGen) + (TokamakMaxCoolRate * ((float) Temp /(float) TempMax) + 1);
		}
		else
			Temp += TokamakMaxCoolRate * TokamakFusionFraction;
	}
	private int getPowerToExtract() {
		if (getPoweredNeighbour() != null){
	    TileEntity tile = worldObj.getTileEntity(xCoord + getPoweredNeighbour().offsetX, yCoord + getPoweredNeighbour().offsetY, zCoord + getPoweredNeighbour().offsetZ);
        if (tile != null && getPoweredNeighbour() != null){
        if (tile instanceof IEnergyHandler) {
			IEnergyHandler handler = (IEnergyHandler) tile;
			int maxEnergy = handler.receiveEnergy(getPoweredNeighbour().getOpposite(),(int) (this.TokamakMaxEnergyRec * EnergyGradient), true);
			return extractEnergy(maxEnergy, false);	
		}
        }
        }
		return 0;
	}
	public int extractEnergy(int energyMax, boolean doExtract) {
		int max = Math.min(energyMax,TokamakMaxEnergyRec);

		int extracted;

		if (outEnergy >= max) {
			extracted = max;

			if (doExtract) {
				outEnergy -= max;
			}
		} else {
			extracted = outEnergy;

			if (doExtract) {
				outEnergy = 0;
			}
		}

		return extracted;
	}
	protected void sendPower() {
		
		if (getPoweredNeighbour() != null){
		TileEntity tile = worldObj.getTileEntity(xCoord + getPoweredNeighbour().offsetX, yCoord + getPoweredNeighbour().offsetY, zCoord + getPoweredNeighbour().offsetZ);
		if (tile != null && getPoweredNeighbour() != null) {
			int extracted = getPowerToExtract();
			if (extracted <= 0) {
				setPumping(false);
                return;
			}

            setPumping(true);

		    if (tile instanceof IEnergyHandler) {
                IEnergyHandler handler = (IEnergyHandler) tile;
                int neededRF = handler.receiveEnergy(getPoweredNeighbour().getOpposite(),extracted, false);
                extractEnergy(neededRF, true);
            } 
		}
	}
}
	protected final void setPumping(boolean isActive) {
		if (this.isPumping == isActive) {
			return;
		}

		this.isPumping = isActive;
		sync();
	}
	public float getScaledLiquidQuantity(int MaxLevel) {return (float) tank.getFluidAmount() * MaxLevel / tank.getCapacity();}
	
	public float getScaledTemp(int MaxLevel) {return (float) Temp * MaxLevel/ (float) TempMax;}
	
	public float getScaledFusionTemp(int MaxLevel) {return (float) StartWorkEnergy  * MaxLevel/ (float) TempMax;}
	
	public float getScaledPower(boolean InputOutput, int MaxLevel)
	{
		if (InputOutput)
			return energy * MaxLevel / capacity;
		else
			return PowerOut * MaxLevel / TokamakMaxEnergyRec;
	}
	
	public boolean isFusing()
	{
		if (Temp >= StartWorkEnergy)
			return true;
		else
			return false;
	}
	
	boolean isIdling() {return Idle;}
	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		// TODO Auto-generated method stub
		return tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive,
			boolean simulate) {
		int energyReceived = Math.min(capacity - energy, Math.min(5000, maxReceive));

		if (!simulate) {
			energy += energyReceived;
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract,
			boolean simulate) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		// TODO Auto-generated method stub
		return energy;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		// TODO Auto-generated method stub
		return capacity ;
	}
	
    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
    	// TODO Auto-generated method stub
    	super.writeToNBT(tagCompound);
    	
    	tagCompound.setInteger("energy", energy);
    	tagCompound.setInteger("Temp", Temp);
    	tagCompound.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
    }
    
    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
    	// TODO Auto-generated method stub
    	super.readFromNBT(tagCompound);
    	
    	energy = tagCompound.getInteger("energy");
    	Temp = tagCompound.getInteger("Temp");
    	if (tagCompound.hasKey("tank", Constants.NBT.TAG_COMPOUND))
    		tank.readFromNBT(tagCompound.getCompoundTag("tank"));
    }

}
