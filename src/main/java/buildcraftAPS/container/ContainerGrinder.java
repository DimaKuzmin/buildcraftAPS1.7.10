package buildcraftAPS.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import buildcraftAPS.blocks.TileEntity.TileEntityGrinder;
import buildcraftAPS.container.base.ContainerBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerGrinder extends ContainerBase<TileEntityGrinder>{

 
	private int energy;
	public TileEntityGrinder tileEntity;

	public ContainerGrinder(InventoryPlayer inventoryPlayer, TileEntityGrinder te) {
		super(inventoryPlayer, te);
        addSlotToContainer(new Slot(te, 0, 30, 35));
		addPlayerInventory(8, 84);
	    this.tileEntity = te;
	}
	
	@Override
	public void updateProgressBar(int id, int value) {
		 super.updateProgressBar(id, value);
		
         switch(id) {
         case 0:
        	 inv.energy = value;
         }
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		 for (int i = 0; i < this.crafters.size(); i++){
			 ICrafting icrafting = (ICrafting) this.crafters.get(i);
			 if (this.energy != inv.energy){
				 icrafting.sendProgressBarUpdate(this, 0, inv.energy);
			 }
		 }
		 this.energy = inv.energy;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addCraftingToCrafters(ICrafting crafting) {
		// TODO Auto-generated method stub
		super.addCraftingToCrafters(crafting);
		
		crafting.sendProgressBarUpdate(this, 0, inv.energy);
	}
}

	
