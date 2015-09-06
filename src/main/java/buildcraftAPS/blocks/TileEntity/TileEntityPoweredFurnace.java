package buildcraftAPS.blocks.TileEntity;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.util.ForgeDirection;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile;
import buildcraft.api.transport.IPipeTile.PipeType;
import buildcraftAPS.UtilsAps;
import buildcraftAPS.VariablesAps;
import buildcraftAPS.blocks.TileEntity.Basic.TileEntityAps;
import buildcraftAPS.container.IInventoryBasic;
import cofh.api.energy.IEnergyReceiver;

public class TileEntityPoweredFurnace extends TileEntityAps implements ISidedInventory, IEnergyReceiver, IPipeConnection{
    public IInventoryBasic inv = new IInventoryBasic("Power Furnace", 2, 64, this);
	public int energy;
	private int capacity;
	private int maxRecive;
	private int needEnergy;

	public TileEntityPoweredFurnace() {
		super(VariablesAps.SyncIDs.PoweredFurnace.ordinal());
		
		energy = 0;
		capacity = 100000;
		maxRecive = 2000;
		needEnergy = 1200;
 	}
    @Override
    public void updateEntity() {
    	// TODO Auto-generated method stub
    	super.updateEntity();
    	
    	if (worldObj.isRemote){
    		return;
    	}
    	if (!worldObj.isRemote && energy > needEnergy && canSmelt()){
    		smelt();
     		energy -= needEnergy;
    	}    	
    	if (energy > capacity){
    		energy = capacity;
    	}
    	
    	if (energy < 0){
    		energy = 0;
    	}
    }
    public int powerlevel (int i){
       return energy * i/capacity;
    }
 
	@SuppressWarnings("deprecation")
	private void smelt() {
        if (!canSmelt()){
         	return;
        }
 
        ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(getStackInSlot(0));
        
        if (this.getStackInSlot(1) == null){
        	setInventorySlotContents(1, itemstack.copy()); 
        }
        else if (this.getStackInSlot(1).isItemEqual(itemstack))
        {
          this.getStackInSlot(1).stackSize += itemstack.stackSize;
        }
        if (this.getStackInSlot(1).getItem().hasContainerItem())
        {
          this.setInventorySlotContents(0,new ItemStack(this.getStackInSlot(0).getItem().getContainerItem()));
        }
        else {
          this.getStackInSlot(0).stackSize -= 1;
        }
        if (this.getStackInSlot(0).stackSize <= 0)
        {
          setInventorySlotContents(0, null);
        }
        
		int added = 0;
	    added = UtilsAps.addToRandomInventoryAround(worldObj, xCoord, yCoord, zCoord, itemstack.copy());
	    
	    if (added == 0)
	    	added = UtilsAps.addToRandomInjectableAround(worldObj, xCoord, yCoord, zCoord, ForgeDirection.UNKNOWN, itemstack);
    	if (getStackInSlot(1).stackSize > 0 && added != 0){
//    	getNeighbour(getStackInSlot(1));

        getStackInSlot(1).stackSize -= 1;
     	}
    	if (getStackInSlot(1).stackSize <= 0 ){
    		setInventorySlotContents(1, null);
    	}
    	sync();
    	markDirty();
	}
	
	private boolean canSmelt(){
		if (getStackInSlot(0) == null){
			return false;
		}
		ItemStack item = FurnaceRecipes.smelting().getSmeltingResult(getStackInSlot(0));
		if (item == null)
		return false;
		
		if (getStackInSlot(1) == null){
			return true;
		}
		
		if (!this.getStackInSlot(1).isItemEqual(item)){
			return false;
		}
	    int st = this.getStackInSlot(1).stackSize + item.stackSize;
	    return (st <= getInventoryStackLimit()) && (st <= item.getMaxStackSize());
	}
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
		inv.openInventory();
	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub
		inv.closeInventory();
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		// TODO Auto-generated method stub
		return inv.isItemValidForSlot(p_94041_1_, p_94041_2_);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		// TODO Auto-generated method stub
		return true;
	}
    @Override
    public void writeToByteBuff(ByteBuf buf) {
    	// TODO Auto-generated method stub
    	super.writeToByteBuff(buf);
    	
    	inv.writeToByteBuff(buf);
    }
    
    @Override
    public void readFromByteBuff(ByteBuf buf) {
    	// TODO Auto-generated method stub
    	super.readFromByteBuff(buf);
    	
    	inv.readFromByteBuff(buf);
    }
    
	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive,boolean simulate) {
		int energyReceived = Math.min(capacity - energy, Math.min(this.maxRecive, maxReceive));

		if (!simulate) {
			energy += energyReceived;
		}
		return energyReceived;
	}
	@Override
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection with) {
//		if (with.ordinal() == worldObj.getBlockMetadata(xCoord, yCoord, zCoord)) {
//			return ConnectOverride.DISCONNECT;
//		}
		return type == IPipeTile.PipeType.ITEM ? ConnectOverride.CONNECT : ConnectOverride.DEFAULT;
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

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		// TODO Auto-generated method stub
		return new int[side];
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack p_102007_2_,
			int p_102007_3_) {
		// TODO Auto-generated method stub
		return slot==0;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack p_102008_2_,
			int p_102008_3_) {
		// TODO Auto-generated method stub
		return slot==1;
	}

}
