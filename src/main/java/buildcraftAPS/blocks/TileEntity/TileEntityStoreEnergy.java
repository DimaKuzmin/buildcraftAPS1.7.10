package buildcraftAPS.blocks.TileEntity;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import buildcraft.api.power.IEngine;
import buildcraftAPS.VariablesAps;
import buildcraftAPS.api.IBottonListener;
import buildcraftAPS.blocks.TileEntity.Basic.TileEntityAps;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;

public class TileEntityStoreEnergy extends TileEntityAps implements IBottonListener , IEnergyHandler  {
      
	  public int capacity;
	  public int maxEnergyInp=5000;
	  public int maxEnergyOut=5000;
	  public int UserMaxPowerIn;
	  public int UserMaxPowerOut;
	  public int energy;
	  public IEnergyHandler rf;
      public boolean IsDraining;
	  public int PowerOut;
	  public int PowerIn;
	  private boolean isPumping;
		
	 
		private int getPowerToExtract() {
			if (getPoweredNeighbour() != null){
		    TileEntity tile = worldObj.getTileEntity(xCoord + getPoweredNeighbour().offsetX, yCoord + getPoweredNeighbour().offsetY, zCoord + getPoweredNeighbour().offsetZ);

	        if (tile instanceof IEnergyHandler) {
				IEnergyHandler handler = (IEnergyHandler) tile;
				int maxEnergy = handler.receiveEnergy(getPoweredNeighbour().getOpposite(),this.PowerOut, true);
				return extractEnergy(maxEnergy, false);
				
			} else if (tile instanceof IEnergyReceiver) {
				IEnergyReceiver handler = (IEnergyReceiver) tile;
				int maxEnergy = handler.receiveEnergy(getPoweredNeighbour().getOpposite(),this.PowerOut, true);
				return extractEnergy(maxEnergy, false);
				
			} else {
				return 0;
			}
	        }
			return 0;
		}
		public int extractEnergy(int energyMax, boolean doExtract) {
			int max = Math.min(energyMax,maxEnergyOut);

			int extracted;

			if (energy >= max) {
				extracted = max;

				if (doExtract) {
					energy -= max;
				}
			} else {
				extracted = energy;

				if (doExtract) {
					energy = 0;
				}
			}

			return extracted;
		}
		protected final void setPumping(boolean isActive) {
			if (this.isPumping == isActive) {
				return;
			}

			this.isPumping = isActive;
			sync();
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

				if (tile instanceof IEngine) {
	                IEngine engine = (IEngine) tile;
	                int neededRF = engine.receiveEnergyFromEngine(getPoweredNeighbour().getOpposite(),extracted, false);
	                extractEnergy(neededRF, true);
	                
	            } else if (tile instanceof IEnergyHandler) {
	                IEnergyHandler handler = (IEnergyHandler) tile;
	                int neededRF = handler.receiveEnergy(getPoweredNeighbour().getOpposite(),extracted, false);
	                extractEnergy(neededRF, true);
	                
	            } else if (tile instanceof IEnergyReceiver) {
					IEnergyReceiver handler = (IEnergyReceiver) tile;
					int neededRF = handler.receiveEnergy(getPoweredNeighbour().getOpposite(),extracted, false);
					extractEnergy(neededRF, true);
					
				}
			}
		}
	}
	  
	  
	public TileEntityStoreEnergy() {
		super(VariablesAps.SyncIDs.EnergyStore.ordinal());
		    energy = 0;
		    capacity = 10000000;
            UserMaxPowerIn = maxEnergyInp;
            UserMaxPowerOut = maxEnergyOut;
	       }
    public void updateEntity()
	  {
	    super.updateEntity();
	    if (worldObj.isRemote){
	    	return;
	    }
	  
		IsDraining = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
		if (IsDraining){
			float extracted;
			if (this.energy > this.UserMaxPowerOut) {
		          extracted = this.UserMaxPowerOut;
		        } else {
		          extracted = this.maxEnergyOut;
		        }
		        if (extracted > 0)
		        {
		        	this.PowerOut = (int) extracted;
 		        	sendPower();
 		        	sync();
		        }
		}
		else {
			this.PowerOut = 0;
		}
		if (!IsDraining){
			int input;
			if (this.energy < this.capacity){
				input = this.UserMaxPowerIn;
			}else{
				input = this.maxEnergyInp;
			}
			if (input > 0){
				this.PowerIn = input;
			}
		}
		else{
			this.PowerIn = 0;
		}
		sync();
	  }
	  public void readFromNBT(NBTTagCompound nbttagcompound)
	  {
	    super.readFromNBT(nbttagcompound);
	    
	    this.energy = nbttagcompound.getInteger("energy");
        this.maxEnergyInp = nbttagcompound.getInteger("maxEnergyInp");
        this.maxEnergyOut = nbttagcompound.getInteger("maxEnergyOut");
        this.UserMaxPowerIn = nbttagcompound.getInteger("UserMaxPowerIn");
        this.UserMaxPowerOut = nbttagcompound.getInteger("UserMaxPowerOut");
	  }
	  

	  public void writeToNBT(NBTTagCompound nbttagcompound)
	  {
	    super.writeToNBT(nbttagcompound);
	    
	    nbttagcompound.setInteger("energy", this.energy);
        nbttagcompound.setInteger("maxEnergyInp", maxEnergyInp);
        nbttagcompound.setInteger("maxEnergyOut", maxEnergyOut);
        nbttagcompound.setInteger("UserMaxPowerIn", UserMaxPowerIn);
        nbttagcompound.setInteger("UserMaxPowerOut", UserMaxPowerOut);
	  }
	  
	  @Override
	  public void writeToByteBuff(ByteBuf buf) {
	    	super.writeToByteBuff(buf);
	    	
	    	buf.writeInt(energy);
	    	buf.writeInt(maxEnergyInp);
	    	buf.writeInt(maxEnergyOut);
	    	buf.writeInt(UserMaxPowerIn);
	    	buf.writeInt(UserMaxPowerOut);
	  }
	    public int extractEnergy() {
	        int amount = maxEnergyOut;
	        if (energy >= amount) {
	            energy -= amount;
	            return amount;
	        }
	        int returnValue = energy;
	        energy = 0;
	        return returnValue;
	    }
	  @Override
	public void readFromByteBuff(ByteBuf buf) {
		super.readFromByteBuff(buf);
		
		energy = buf.readInt();
		maxEnergyInp = buf.readInt();
		maxEnergyOut = buf.readInt();
		UserMaxPowerIn = buf.readInt();
		UserMaxPowerOut = buf.readInt();
	}
	  public float getScaledPower(boolean InputOutput, int MaxLevel)
	  {
	    if (InputOutput) {
	      return this.PowerOut  * MaxLevel/ maxEnergyOut;
	    }
	    return this.PowerIn  * MaxLevel/ maxEnergyInp;
	  }
	  
	  public float getScaledPowerMax(boolean InputOutput, int MaxLevel)
	  {
	    if (InputOutput) {
	      return this.PowerOut  * MaxLevel/ maxEnergyOut;
	    }
	    return this.PowerIn * MaxLevel/ maxEnergyInp;
	  }
	  
	  public float getScaledPowerStored(int MaxLevel) { return this.energy  * MaxLevel/capacity; }
 
	@Override
	public void onWidgetPressed(int id, int value) {
		switch(id){
		case 0:
		if (value != 0)
		this.UserMaxPowerIn = value;
 		break;
		case 1:
		if (value != 0)
		this.UserMaxPowerOut = value;
 	    break;
		}
	}
	
	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
 		return true;
	}
	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive,
			boolean simulate) {
		
		int energyReceived = Math.min(capacity - energy, Math.min(this.PowerIn, maxReceive));

		if (!simulate) {
			energy += energyReceived;
		}
		return energyReceived;
	}
	@Override
	public int getEnergyStored(ForgeDirection from) {
 		return 0;
	}
	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
 		return 0;
	}
	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract,
			boolean simulate) {
		int energyExtracted = Math.min(energy, Math.min(this.PowerOut, maxExtract));

		if (!simulate) {
			energy -= energyExtracted;
		}
		return energyExtracted;
	}
}
