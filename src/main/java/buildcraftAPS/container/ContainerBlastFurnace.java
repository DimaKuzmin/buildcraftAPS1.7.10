package buildcraftAPS.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import buildcraftAPS.blocks.TileEntity.TileEntityBlastFurnace;
import buildcraftAPS.container.base.ContainerBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerBlastFurnace extends ContainerBase<TileEntityBlastFurnace> {

 

	private int energy ,BlockLevel , LavaTank;
 
	public TileEntityBlastFurnace te;

	private int LavaTankAmount;
 

	public ContainerBlastFurnace(TileEntityBlastFurnace te,
			InventoryPlayer inventoryPlayer) {
		super(inventoryPlayer, te);
		addSlotToContainer(new Slot(te, 0, 53, 35));
		addPlayerInventory(8, 84);
		this.te = te;

	}
    
	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
	
		crafting.sendProgressBarUpdate(this, 0, this.te.energy);
		crafting.sendProgressBarUpdate(this, 1, this.te.BlockLevel);
		crafting.sendProgressBarUpdate(this, 2, this.te.tank.getFluidAmount() > 0 ? this.te.tank.getFluid().getFluidID() : -1);
		crafting.sendProgressBarUpdate(this, 3, this.te.tank.getFluidAmount());
	}
	@Override
    public void detectAndSendChanges() {
     	super.detectAndSendChanges();
		if (crafters != null) {
			for (Object o : crafters) {
				if (o != null && o instanceof ICrafting) {
    	ICrafting icrafting = (ICrafting) o;
    	if (this.energy != this.te.energy){
    		icrafting.sendProgressBarUpdate(this, 0, this.te.energy);
    	}
    	if (this.BlockLevel != this.te.BlockLevel){
    		icrafting.sendProgressBarUpdate(this, 1, this.te.BlockLevel);
    	}
    	if (this.LavaTank != (this.te.tank.getFluidAmount() > 0 ? this.te.tank.getFluid().getFluidID() : -1)){
    		icrafting.sendProgressBarUpdate(this, 2, this.te.tank.getFluidAmount() > 0 ? this.te.tank.getFluid().getFluidID() : -1);
    	}
    	if (this.LavaTankAmount != this.te.tank.getFluidAmount()){
    		icrafting.sendProgressBarUpdate(this, 3, this.te.tank.getFluidAmount());
    	}
    }
}
}  
    this.BlockLevel = this.te.BlockLevel;
    this.energy = this.te.energy;
    this.LavaTank = this.te.tank.getFluidAmount() > 0 ? this.te.tank.getFluid().getFluidID() : -1;
    this.LavaTankAmount = this.te.tank.getFluidAmount();
   }
    @Override
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int i, int j) {
     	super.updateProgressBar(i, j);
     	
     	switch (i){
     	case 0: 
     		this.te.energy = j;
     		break;
     	case 1:
     		this.te.BlockLevel = j;
     		break;
     	case 2:
     		if (j >= 0)
     		this.te.tank.setFluid(new FluidStack(FluidRegistry.getFluid(j), this.te.tank.getFluidAmount()));
     		else
     		this.te.tank.setFluid(null);
     		break;
     	case 3:
     		if (j > 0 && this.te.tank.getFluid() != null){
     		this.te.tank.setFluid(new FluidStack(this.te.tank.getFluid(), j));
     		}else{
     			this.te.tank.setFluid(null);
     		}
     	}
    }
}
