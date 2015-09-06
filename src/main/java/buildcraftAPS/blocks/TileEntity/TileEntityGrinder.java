package buildcraftAPS.blocks.TileEntity;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile;
import buildcraft.api.transport.IPipeTile.PipeType;
import buildcraftAPS.UtilsAps;
import buildcraftAPS.VariablesAps;
import buildcraftAPS.blocks.TileEntity.Basic.TileEntityAps;
import buildcraftAPS.container.IInventoryBasic;
import buildcraftAPS.recipe.RecipeIList;
import buildcraftAPS.recipe.RecipeManager;
import buildcraftAPS.recipe.Recipes;
import cofh.api.energy.IEnergyReceiver;



public class TileEntityGrinder extends TileEntityAps implements ISidedInventory, IPipeConnection, IEnergyReceiver {
	public IInventoryBasic inventory = new IInventoryBasic("Grinder", 1, 64, this);

	private int tickk;
    public int energy;
    private int capacity;
    private int maxReceive;
	public TileEntityGrinder() {
		super(VariablesAps.SyncIDs.Grinder.ordinal());
		Recipes.recipe = new RecipeManager();
		energy = 0;
		capacity = 100000;
		maxReceive = 2000;
   	    Recipes.recipe.addRecipe(new ItemStack(Blocks.cobblestone), new ItemStack[]{
				new ItemStack(Blocks.gravel),
				new ItemStack(Items.coal),
				new ItemStack(Blocks.iron_ore),
				new ItemStack(Items.redstone)
				}, new float[]{
				75,
				2.5f,
				2,
				0.5f
				}, 750);
    }
    
	public RecipeIList getRecipe() {
		return Recipes.recipe.getRecipe(getStackInSlot(0));
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
    
    @Override
	public void updateEntity(){
		super.updateEntity();
		if (worldObj.isRemote)
			return;
		//mod_Aps.logger.info(Recipes.recipe.getRecipe(getStackInSlot(0)));
		if (!worldObj.isRemote && CanGrind() && energy >= 1000){
			sync();
			energy -= Recipes.recipe.getEnergy();
            drop();
        }
		
		if (tickk == 50){
			//mod_Aps.logger.info(rfTaken + "   RF");
			tickk=0;}
		else
			tickk++;
    }
    public boolean CanGrind(){return Recipes.recipe.getRecipe(getStackInSlot(0)) != null && getStackInSlot(0) != null; }
    public String[] getPossibleProducts()
    {
		ItemStack[] stack = Recipes.recipe.getoutput();
	   	if (getRecipe() == null) return new String[0];
	  	ItemStack[] P = stack;
	   	String[] Output = new String[P.length];
		float[] flo = Recipes.recipe.getFlo();
    		try{

    		for(int i = 0; i < P.length; i++)
			Output[i] = P[i].stackSize + " " + P[i].getItem().getItemStackDisplayName(P[i]) + " " + flo[i] + "%";
     		} catch (Exception e){
     		}
    	return Output;//;
    }
    
    public int getGrindProgressScaled(int i) {
    	if (energy != 0){
			return (energy * i/capacity);
		}else
		return 0 ;
      }
      

	private void drop() {
		ItemStack stack = Recipes.recipe.getRecipe(getStackInSlot(0)).getRandomProduct(getStackInSlot(0));
 
		if (!worldObj.isRemote  && stack != null){
			//dropItemstack(getWorldObj(), xCoord, yCoord, zCoord, stack);
			int added = 0;
		    added = UtilsAps.addToRandomInventoryAround(worldObj, xCoord, yCoord, zCoord, stack.copy());
			 
	        if (added == 0)
	        	added = UtilsAps.addToRandomInjectableAround(worldObj, xCoord, yCoord, zCoord, ForgeDirection.UNKNOWN, stack);
			if (added == 0){
				EntityItem entityitem = new EntityItem(getWorldObj(), xCoord + 0.5, yCoord + 0.7, zCoord + 0.5, stack.copy());
				worldObj.spawnEntityInWorld(entityitem);
			}

		getStackInSlot(0).stackSize--;
        if (this.getStackInSlot(0).stackSize <= 0)
        {
        	setInventorySlotContents(0, null);
        }
		sync();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}

	}
     
	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return inventory.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int p_70301_1_) {
		// TODO Auto-generated method stub
		return inventory.getStackInSlot(p_70301_1_);
	}

	@Override
	public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
		// TODO Auto-generated method stub
		return inventory.decrStackSize(p_70298_1_, p_70298_2_);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		// TODO Auto-generated method stub
		return inventory.getStackInSlotOnClosing(p_70304_1_);
	}

	@Override
	public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
		// TODO Auto-generated method stub
		inventory.setInventorySlotContents(p_70299_1_, p_70299_2_);
		markDirty();
	}

	@Override
	public String getInventoryName() {
		// TODO Auto-generated method stub
		return inventory.getInventoryName();
	}

	@Override
	public boolean hasCustomInventoryName() {
		// TODO Auto-generated method stub
		return inventory.hasCustomInventoryName();
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return inventory.getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		// TODO Auto-generated method stub
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {
		inventory.openInventory();
		
	}

	@Override
	public void closeInventory() {
         inventory.closeInventory();		
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		// TODO Auto-generated method stub
		return inventory.isItemValidForSlot(p_94041_1_, p_94041_2_);
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
	public boolean canExtractItem(int p_102008_1_, ItemStack stack,
			int p_102008_3_) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ConnectOverride overridePipeConnection(PipeType type,
			ForgeDirection with) {
		if (with.ordinal() == worldObj.getBlockMetadata(xCoord, yCoord, zCoord)) {
			return ConnectOverride.DISCONNECT;
		}
		return type == IPipeTile.PipeType.ITEM ? ConnectOverride.CONNECT : ConnectOverride.DEFAULT;
	}

	@Override
	public void writeToByteBuff(ByteBuf buf) {
		super.writeToByteBuff(buf);
        if (energy < 0)
		energy = 0;
		buf.writeInt(energy);
		inventory.writeToByteBuff(buf);
	}

	@Override
	public void readFromByteBuff(ByteBuf buf) {
		super.readFromByteBuff(buf);
		if (energy > capacity){
			energy = capacity;
		}
		
		energy = buf.readInt();
		inventory.readFromByteBuff(buf);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
	       if (energy < 0)
	   		energy = 0;
	       tag.setInteger("energy", energy);
		inventory.writeToNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		if (energy > capacity){
			energy = capacity;
		}
		
        energy = tag.getInteger("energy");
		inventory.readFromNBT(tag);
	}

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
}
