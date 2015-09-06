/**
 * 
 */
package buildcraftAPS.container;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import buildcraftAPS.api.ISyncObject;
import cpw.mods.fml.common.network.ByteBufUtils;

/**
 * @author Dima
 *
 */
public class IInventoryBasic implements IInventory, ISyncObject, INBTSaveable {
	

	private final ItemStack[] itemStacks;
	private final String name;
	private final int stackLimit;
	private TileEntity entity;

	public IInventoryBasic(String name, int slots, int stackLimit, TileEntity entity) {
		this.name = name;
		this.stackLimit = stackLimit;
		itemStacks = new ItemStack[slots];
		this.entity = entity;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		NBTTagList nbttaglist = tag.getTagList("ItemStacks", Constants.NBT.TAG_COMPOUND);
		for (int t = 0; t < nbttaglist.tagCount(); t++) {
			NBTTagCompound slot = nbttaglist.getCompoundTagAt(t);
			int index;
			if (slot.hasKey("index")) {
				index = slot.getInteger("index");
			} else {
				index = slot.getByte("Slot");
			}
			if (index >= 0 && index < itemStacks.length) {
				setInventorySlotContents(index, ItemStack.loadItemStackFromNBT(slot));
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		NBTTagList list = new NBTTagList();
		for (byte t = 0; t < itemStacks.length; t++) {
			if (itemStacks[t] != null && itemStacks[t].stackSize > 0) {
				NBTTagCompound slot = new NBTTagCompound();
				list.appendTag(slot);
				slot.setByte("Slot", t);
				itemStacks[t].writeToNBT(slot);
			}
		}
		tag.setTag("ItemStacks", list);
	}
	@Override
	public int getSizeInventory() {
		return itemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return itemStacks[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (slot < itemStacks.length && itemStacks[slot] != null) {
			if (itemStacks[slot].stackSize > amount) {
				return itemStacks[slot].splitStack(amount);
			}
			ItemStack stack = itemStacks[slot];
			setInventorySlotContents(slot, null);
			return stack;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (itemStacks[slot] == null) {
			return null;
		}

		ItemStack stack = itemStacks[slot];
		setInventorySlotContents(slot, null);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		itemStacks[slot] = stack;
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
		entity.markDirty();
	}

	@Override
	public String getInventoryName() {
		return name;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return stackLimit;
	}

	@Override
	public void markDirty() {
		entity.markDirty();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return true;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}

	@Override
	public void writeToByteBuff(ByteBuf buf) {
		for (ItemStack stack : itemStacks)
			ByteBufUtils.writeItemStack(buf, stack);
	}

	@Override
	public void readFromByteBuff(ByteBuf buf) {
		for (int i = 0; i < itemStacks.length; i++)
			itemStacks[i] = ByteBufUtils.readItemStack(buf);
	}
}
