package buildcraftAPS.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import buildcraftAPS.blocks.TileEntity.TileEntityPoweredFurnace;
import buildcraftAPS.container.base.ContainerBase;

public class ContainerPoweredFurnace extends ContainerBase<TileEntityPoweredFurnace>{

	public TileEntityPoweredFurnace te;
	private int energy;

	public ContainerPoweredFurnace(InventoryPlayer inventoryPlayer,
			TileEntityPoweredFurnace te) {
		super(inventoryPlayer, te);
		addPlayerInventory(8, 84);
	    addSlotToContainer(new Slot(te, 0, 53, 35));
	    addSlotToContainer(new SlotFurnace(inventoryPlayer.player, te, 1, 101, 35));
	    this.te = te;
	}

	@Override
	public void detectAndSendChanges() {
		// TODO Auto-generated method stub
		super.detectAndSendChanges();
		
		 for (int i = 0; i < this.crafters.size(); i++){
			 ICrafting icrafting = (ICrafting) this.crafters.get(i);
			 if (this.energy != te.energy){
				 icrafting.sendProgressBarUpdate(this, 0, te.energy);
			 }
		 }
		 this.energy = inv.energy;
	}
	
	@Override
	public void updateProgressBar(int id, int value) {
		// TODO Auto-generated method stub
		super.updateProgressBar(id, value);
		
        switch(id) {
        case 0:
       	 inv.energy = value;
        }
	}
	
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void addCraftingToCrafters(ICrafting crafting) {
//		// TODO Auto-generated method stub
//		super.addCraftingToCrafters(crafting);
//		
//		crafting.sendProgressBarUpdate(this, 0, inv.energy);
//	}
}
