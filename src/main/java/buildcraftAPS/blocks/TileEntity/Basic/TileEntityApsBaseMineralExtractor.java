package buildcraftAPS.blocks.TileEntity.Basic;

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
import cofh.api.energy.IEnergyReceiver;

public class TileEntityApsBaseMineralExtractor extends TileEntityAps implements IEnergyReceiver, IFluidHandler, IPipeConnection{

	public FluidTank tank;
	public int capacity;
	public int energy;
	public int maxReceive;
	
	
	public TileEntityApsBaseMineralExtractor(int identifier, int fluidCapacity,int capacity, int energy, int maxRecive) {
		super(identifier);
 		this.tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * fluidCapacity);
 		this.capacity = capacity;
 		this.energy = energy;
 		this.maxReceive = maxRecive;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
 		return true;
	}
	
    public void dropItems(ItemStack item){
    	if (item != null){
    	int added = 0;
    	added = UtilsAps.addToRandomInventoryAround(worldObj, xCoord, yCoord, zCoord, item.copy());
		 
        if (added == 0)
        	added = UtilsAps.addToRandomInjectableAround(worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.UNKNOWN, item.copy());

        
		if (added == 0){
			EntityItem entityitem = new EntityItem(getWorldObj(), xCoord + 0.5, yCoord + 0.7, zCoord + 0.5, item.copy());
			worldObj.spawnEntityInWorld(entityitem);
		}
    	}
    }
    
	@Override
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection with) {
 		if (with.ordinal() == worldObj.getBlockMetadata(xCoord, yCoord, zCoord)) {
			return ConnectOverride.DISCONNECT;
		}
		return type == IPipeTile.PipeType.ITEM ? ConnectOverride.CONNECT : ConnectOverride.DEFAULT;
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
	public void readFromByteBuff(ByteBuf buf) {
 		super.readFromByteBuff(buf);
		
		energy = buf.readInt();
//		tank.fill(new FluidStack(tank.getFluid(), buf.readInt()), true);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
 		super.readFromNBT(tagCompound);
		
		energy = tagCompound.getInteger("energy");
//	    if (tagCompound.getInteger("tank") != 0){
//	       tank.fill(new FluidStack(tank.getFluid(), tagCompound.getInteger("tank")), true);
//	    }
	}
	
	@Override
	public void writeToByteBuff(ByteBuf buf) {
 		super.writeToByteBuff(buf);
		
		buf.writeByte(energy);
//		buf.writeInt(tank.getFluidAmount());
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
 		super.writeToNBT(tagCompound);
		
		tagCompound.setInteger("energy", energy);
//	    if (tank.getFluidAmount() > 0){
//	    	tank.fill(new FluidStack(tank.getFluid(), tagCompound.getInteger("tank")), true);
//	    }
	}
	
}
