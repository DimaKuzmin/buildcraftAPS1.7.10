package buildcraftAPS.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import buildcraftAPS.blocks.TileEntity.TileEntityFussionReactor;
import buildcraftAPS.container.base.ContainerBase;

public class ContainerFussionReactor extends ContainerBase<TileEntityFussionReactor>{

	public TileEntityFussionReactor te;
	private int energy, tank;
	private int tankAm;
	private int Temp;
	 

	public ContainerFussionReactor(InventoryPlayer inventoryPlayer,	TileEntityFussionReactor te) {
		super(inventoryPlayer, te);
		addPlayerInventory(8, 84);
		this.te = te;
 	}
	
    @Override
    public void detectAndSendChanges() {
    	// TODO Auto-generated method stub
    	super.detectAndSendChanges();
    	
    	for (int i=0; i < this.crafters.size(); i++){
    	     ICrafting craft = (ICrafting) this.crafters.get(i);
    	     
    	     if (this.energy != te.energy)
    	    	 craft.sendProgressBarUpdate(this, 0, te.energy);
    	     if (this.tank != (this.te.tank.getFluidAmount() > 0 ? this.te.tank.getFluid().getFluidID() : -1))
    	    	 craft.sendProgressBarUpdate(this, 1, this.te.tank.getFluidAmount() > 0 ? this.te.tank.getFluid().getFluidID() : -1);
    	     if (this.tankAm != this.te.tank.getFluidAmount())
    	    	 craft.sendProgressBarUpdate(this, 2, this.te.tank.getFluidAmount());
    	     if (this.Temp != this.te.Temp)
    	    	 craft.sendProgressBarUpdate(this, 3, this.te.Temp);
    	}
    	this.energy = te.energy;
    	this.tank = this.te.tank.getFluidAmount() > 0 ? this.te.tank.getFluid().getFluidID() : -1;
    	this.tankAm = this.te.tank.getFluidAmount();
        this.Temp = this.te.Temp;
    }
	
	@Override
	public void updateProgressBar(int id, int value) {
		// TODO Auto-generated method stub
		super.updateProgressBar(id, value);
		
		switch(id){
		case 0:
			this.te.energy = value;
			break;
		case 1:
			if (value > 0){
				this.te.tank.setFluid(new FluidStack(FluidRegistry.getFluid(value) ,this.te.tank.getFluidAmount()));
			}else{
				this.te.tank.setFluid(null);
			}
			break;
		case 2:
			if (value > 0 && this.te.tank.getFluid() != null){
				this.te.tank.setFluid(new FluidStack(this.te.tank.getFluid(),value));
			}else{
				this.te.tank.setFluid(null);
			}
			break;
		case 3:
			this.te.Temp = value;
		}
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting craft) {
		// TODO Auto-generated method stub
		super.addCraftingToCrafters(craft);
		
		craft.sendProgressBarUpdate(this, 0, this.te.energy);
		craft.sendProgressBarUpdate(this, 1, this.te.tank.getFluidAmount() > 0 ? this.te.tank.getFluid().getFluidID() : -1);
		craft.sendProgressBarUpdate(this, 2, this.te.tank.getFluidAmount());
		craft.sendProgressBarUpdate(this, 3, this.te.Temp);
	}

}
