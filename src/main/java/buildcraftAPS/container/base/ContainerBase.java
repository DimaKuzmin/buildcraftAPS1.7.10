package buildcraftAPS.container.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBase<T> extends Container{
	
       
	protected final InventoryPlayer inventoryPlayer;
	protected final T inv;
 
  
 
	public ContainerBase(InventoryPlayer inventoryPlayer, T te) {
	    this.inventoryPlayer = inventoryPlayer;
	    this.inv = te ;    	
	}
	
	protected void addPlayerInventory(int x, int y) {
		if (inventoryPlayer != null) {
			for (int inventoryRowIndex = 0; inventoryRowIndex < 3; ++inventoryRowIndex)
				for (int inventoryColumnIndex = 0; inventoryColumnIndex < 9; ++inventoryColumnIndex)
					addSlotToContainer(new Slot(inventoryPlayer, 9 + inventoryColumnIndex + inventoryRowIndex * 9, x + inventoryColumnIndex * 18, y + inventoryRowIndex * 18));
			for (int hotBarIndex = 0; hotBarIndex < 9; ++hotBarIndex)
				addSlotToContainer(new Slot(inventoryPlayer, hotBarIndex, 8 + hotBarIndex * 18, y + 58));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return !(inv instanceof IInventory) || ((IInventory) inv).isUseableByPlayer(player);
	}
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int size)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(size);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (size == 0)
            {
                if (!this.mergeItemStack(itemstack1, 1, 37, true))
                {
                	//mod_Aps.logger.info("Size 0");
                    return null;
                }
            }
            else
            {

             if (itemstack1.stackSize >= 1)
                {
            	 if (!this.mergeItemStack(itemstack1, 0, 1, false))
                 {
               	 // mod_Aps.logger.info("Size 66666");
               	  return null;
                 }
                }
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }
	
}
