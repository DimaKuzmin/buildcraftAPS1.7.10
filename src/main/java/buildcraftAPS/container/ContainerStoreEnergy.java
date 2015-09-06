package buildcraftAPS.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import buildcraftAPS.blocks.TileEntity.TileEntityStoreEnergy;
import buildcraftAPS.container.base.ContainerBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerStoreEnergy extends ContainerBase<TileEntityStoreEnergy>{
 
	public TileEntityStoreEnergy te;
	private int energy,maxEnergyInp,maxEnergyOut,UserMaxPowerIn, UserMaxPowerOut, IsDraining , PowerIn, PowerOut;
 
 

	public ContainerStoreEnergy(InventoryPlayer inventoryPlayer,
			TileEntityStoreEnergy te) {
		super(inventoryPlayer, te);
		addPlayerInventory(8,84);
		this.te = te;
		
//		mod_Aps.logger.info(energy + "     " + maxEnergyInp + "     " + maxEnergyOut + "     " + UserMaxPowerIn + "      " + UserMaxPowerOut);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
 		super.updateProgressBar(id, value);
		
	    switch (id){
	    case 0:
	    this.te.energy = value;
	    break;
	    case 1:
	    this.te.maxEnergyInp = value;
	    break;
	    case 2:
	    this.te.maxEnergyOut = value;
	    break;
	    case 3:
	    this.te.UserMaxPowerIn  = value;
	    break;
	    case 4:
	    this.te.UserMaxPowerOut  = value;
	    break;
	    case 5:
	    	if (value == 1)
	    this.te.IsDraining = true;
	    	else
	    this.te.IsDraining = false;
	    break;
	    case 6:
	    this.te.PowerIn = value;
	    break;
	    case 7:
	    this.te.PowerOut = value;
	    
 	    }
	}
	
	@Override
	public void detectAndSendChanges() {
 		super.detectAndSendChanges();
		
		if (crafters != null) {
			for (Object o : crafters) {
				if (o != null && o instanceof ICrafting) {
					ICrafting craft = (ICrafting) o;
			if (this.energy != this.te.energy)
			craft.sendProgressBarUpdate(this, 0, this.te.energy);
			if (this.maxEnergyInp != this.te.maxEnergyInp)
			craft.sendProgressBarUpdate(this, 1, this.te.maxEnergyInp);
			if (this.maxEnergyOut != this.te.maxEnergyOut)
		 	craft.sendProgressBarUpdate(this, 2, this.te.maxEnergyOut);
			if (this.UserMaxPowerIn != this.te.UserMaxPowerIn)
			craft.sendProgressBarUpdate(this, 3, this.te.UserMaxPowerIn);
			if (this.UserMaxPowerOut != this.te.UserMaxPowerOut)
		   	craft.sendProgressBarUpdate(this, 4, this.te.UserMaxPowerOut);
			if (this.IsDraining != (this.te.IsDraining ? 1 : 0))
			craft.sendProgressBarUpdate(this, 5, this.te.IsDraining ? 1 : 0);
			if (this.PowerIn != this.te.PowerIn)
			craft.sendProgressBarUpdate(this, 6, this.te.PowerIn);
			if (this.PowerOut != this.te.PowerOut)
			craft.sendProgressBarUpdate(this, 7, this.te.PowerOut);
		 }}}
	
		 
		 this.energy = this.te.energy;
		 this.maxEnergyInp = this.te.maxEnergyInp;
		 this.maxEnergyOut = this.te.maxEnergyOut;
		 this.UserMaxPowerIn = this.te.UserMaxPowerIn;
		 this.UserMaxPowerOut = this.te.UserMaxPowerOut;
		 this.IsDraining = (this.te.IsDraining ? 1 : 0);
		 this.PowerIn = this.te.PowerIn;
		 this.PowerOut = this.te.PowerOut;
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting icrafting) {
 		super.addCraftingToCrafters(icrafting);
			icrafting.sendProgressBarUpdate(this, 0, this.te.energy);
			icrafting.sendProgressBarUpdate(this, 1, this.te.maxEnergyInp);
			icrafting.sendProgressBarUpdate(this, 2, this.te.maxEnergyOut);
			icrafting.sendProgressBarUpdate(this, 3, this.te.UserMaxPowerIn);
			icrafting.sendProgressBarUpdate(this, 4, this.te.UserMaxPowerOut);
			icrafting.sendProgressBarUpdate(this, 5, this.te.IsDraining ? 1 : 0);
			icrafting.sendProgressBarUpdate(this, 6, this.te.PowerIn);
			icrafting.sendProgressBarUpdate(this, 7, this.te.PowerOut);
	}

}
