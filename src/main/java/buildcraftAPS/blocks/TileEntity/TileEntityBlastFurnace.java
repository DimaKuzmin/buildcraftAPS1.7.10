package buildcraftAPS.blocks.TileEntity;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
import buildcraftAPS.container.IInventoryBasic;
import cofh.api.energy.IEnergyReceiver;

public class TileEntityBlastFurnace extends TileEntityAps implements ISidedInventory, IFluidHandler, IEnergyReceiver {
	public IInventoryBasic inv = new IInventoryBasic("BlastFurnace", 1, 64, this);
 
    public FluidTank tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 10);
	static int BlockToLavaRatio = 16;
	private int BlockMeltEnergy = 1250;
	static int BlockCapacity = 10 * BlockToLavaRatio;
	static int LiquidSideTex = 3;
	public int energy;
	public int LavaLevel;
	public int BlockLevel;
	ItemStack[] MeltableBlocks = new ItemStack[] {getItemStack(Blocks.netherrack), getItemStack(Blocks.gravel) ,getItemStack(Blocks.cobblestone), getItemStack(Blocks.stone)};
 	private int capacity;
	private int maxReceive;
 
	public TileEntityBlastFurnace() {
		super(VariablesAps.SyncIDs.BlastFurnace.ordinal());
		energy = 0;
	    LavaLevel = 0;
		BlockLevel = 0;
		capacity = 100000;
	    maxReceive = 2000;
 	}
	private ItemStack getItemStack(Block block){
		return new ItemStack(Item.getItemFromBlock(block));
	}
	int LavaCounter = 0;
	public Fluid lavaID = FluidRegistry.LAVA;
    @Override
	public boolean canUpdate() {
		return true;
	}
    public boolean isMeltable(ItemStack stack){
    	if (stack != null){
    	for ( int i = 0; i < MeltableBlocks.length; i++){
    		if ( stack.isItemEqual(MeltableBlocks[i]) ){
    		   return true;	
    		}
    	}
    	}
	    return false;
    }
 
	@Override
	public void updateEntity() {
		super.updateEntity();
		ItemStack itemInInventory = getStackInSlot(0);
		if (!worldObj.isRemote){
		if (itemInInventory != null && BlockLevel < BlockCapacity && isMeltable(getStackInSlot(0)))
		{
			if(BlockCapacity - BlockLevel < itemInInventory.stackSize)
			{
				int i = BlockCapacity - BlockLevel;
				BlockLevel = BlockCapacity;
				decrStackSize(0, i);
			}
			else
			{
				BlockLevel += itemInInventory.stackSize;
				setInventorySlotContents(0, null);
			}
		}
         if (isActive()){
			if (BlockLevel > 0 && energy >= BlockMeltEnergy)
			{
				tank.fill(new FluidStack(FluidRegistry.LAVA,FluidContainerRegistry.BUCKET_VOLUME * 10 / 10 / BlockToLavaRatio), true);
				BlockLevel--;
				energy -= BlockMeltEnergy;
            }
         }
      }

	}
	
	public boolean isActive() {return BlockLevel > 0 && tank.getFluidAmount() < FluidContainerRegistry.BUCKET_VOLUME * 9;
	}
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		if (energy < 0)
			energy = 0;
		tag.setInteger("energy", energy);
		tag.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
		tag.setInteger("blocklevel", BlockLevel);
		inv.writeToNBT(tag);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag){
	super.readFromNBT(tag);	
	if (energy > capacity)
		energy = capacity;
	energy = tag.getInteger("energy");
	if (tag.hasKey("tank", Constants.NBT.TAG_COMPOUND))
	tank.readFromNBT(tag.getCompoundTag("tank"));
	BlockLevel = tag.getInteger("blocklevel");
	inv.readFromNBT(tag);
	}
	@Override 
	public void writeToByteBuff(io.netty.buffer.ByteBuf buf) {
	super.writeToByteBuff(buf);
	buf.writeInt(energy);
//	buf.writeInt(LavaLevel);
	buf.writeInt(BlockLevel);
	buf.writeInt(tank.getFluidAmount());
	inv.writeToByteBuff(buf);
	}
	
	@Override 
	public void readFromByteBuff(io.netty.buffer.ByteBuf buf) {
		super.readFromByteBuff(buf);
		energy = buf.readInt();
//		LavaLevel = buf.readInt();
		BlockLevel = buf.readInt();
		tank.setFluid(new FluidStack(FluidRegistry.LAVA, buf.readInt()));
		inv.readFromByteBuff(buf);
	};
	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return inv.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int p_70301_1_) {
		// TODO Auto-generated method stub
		return inv.getStackInSlot(p_70301_1_);
	}

	@Override
	public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
		// TODO Auto-generated method stub
		return inv.decrStackSize(p_70298_1_, p_70298_2_);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		// TODO Auto-generated method stub
		return inv.getStackInSlotOnClosing(p_70304_1_);
	}

	@Override
	public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
		// TODO Auto-generated method stub
		inv.setInventorySlotContents(p_70299_1_, p_70299_2_);
	}

	@Override
	public String getInventoryName() {
		// TODO Auto-generated method stub
		return inv.getInventoryName();
	}

	@Override
	public boolean hasCustomInventoryName() {
		// TODO Auto-generated method stub
		return inv.hasCustomInventoryName();
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return inv.getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		// TODO Auto-generated method stub
		return inv.isUseableByPlayer(p_70300_1_);
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		// TODO Auto-generated method stub
		return inv.isItemValidForSlot(p_94041_1_, p_94041_2_);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		// TODO Auto-generated method stub
		return new int[p_94128_1_];
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack p_102007_2_,
			int p_102007_3_) {
		// TODO Auto-generated method stub
		return slot==0;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_,
			int p_102008_3_) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		if (resource == null) {
			return null;
		} else if (!resource.isFluidEqual(tank.getFluid())) {
			return null;
		} else {
			return drain(from, resource.amount, doDrain);
		}
	}
	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		// TODO Auto-generated method stub
		return tank.drain(maxDrain, doDrain);
	}
	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		// TODO Auto-generated method stub
		return null;
	}
	public int getScaledBlockQuantity(int MaxLevel) {
		return BlockLevel *  MaxLevel/ BlockCapacity;
	}
	public int getScaledPowerLevel(int i) {
    	if (energy != 0){
			return (int) (energy * i/capacity);
		}else
		return 0 ;
	}
	public int getScaledLiquidQuantity(int MaxLevel) { return (this.tank.getFluidAmount() <= 0) ? 0 : this.tank.getFluidAmount() * MaxLevel/tank.getCapacity(); }
	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive,
			boolean simulate) {
		// TODO Auto-generated method stub
	    int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));

		if (!simulate) {
			energy += energyReceived;
		}
		return energyReceived;
	}
	@Override
	public int getEnergyStored(ForgeDirection from) {
		// TODO Auto-generated method stub
		return energy;
	}
	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		// TODO Auto-generated method stub
		return capacity;
	}
	   public int getTankAmount() {
	      return getFluidTank().getFluidAmount();
	   }
	   
	   public FluidTank getFluidTank() {
		  return this.tank;
	   }

}
