package buildcraftAPS.container.base;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import buildcraftAPS.blocks.TileEntity.Basic.TileEntityApsBaseMineralExtractor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerBaseMineralExtractor<T extends TileEntityApsBaseMineralExtractor> extends ContainerBase<TileEntityApsBaseMineralExtractor> {

	public TileEntityApsBaseMineralExtractor tileEntity;
	private int energy;
	private int tank;
	private int getFluidAmount;
	public ContainerBaseMineralExtractor(InventoryPlayer inventoryPlayer, T te) {
		super(inventoryPlayer, te);
        addPlayerInventory(8, 84);
        this.tileEntity = te;
	}
	
	@Override
	public void detectAndSendChanges() {
 		
		for (int i = 0; i < this.crafters.size(); i++){
			if (crafters != null) {
				for (Object o : crafters) {
					if (o != null && o instanceof ICrafting) {
	    	ICrafting icrafting = (ICrafting) o;
			if (this.energy != this.tileEntity.energy)
				icrafting.sendProgressBarUpdate(this, 0, this.tileEntity.energy);
			if (this.tank != (this.tileEntity.tank.getFluidAmount() > 0 ? this.tileEntity.tank.getFluid().getFluidID() : -1))
				icrafting.sendProgressBarUpdate(this, 1, this.tileEntity.tank.getFluidAmount() > 0 ? this.tileEntity.tank.getFluid().getFluidID() : -1);
			if (this.getFluidAmount != this.tileEntity.tank.getFluidAmount() )
				icrafting.sendProgressBarUpdate(this, 2, this.tileEntity.tank.getFluidAmount());
 			}}}}
		this.energy = tileEntity.energy;
		this.tank = this.tileEntity.tank.getFluidAmount() > 0 ? this.tileEntity.tank.getFluid().getFluidID() : -1;
		this.getFluidAmount = this.tileEntity.tank.getFluidAmount();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
 		super.updateProgressBar(id, value);
		
		switch(id){
		case 0:
			this.tileEntity.energy = value;
			break;
		case 1:
			if (value >= 0)
			this.tileEntity.tank.setFluid(new FluidStack(FluidRegistry.getFluid(value), this.tileEntity.tank.getFluidAmount()));
			else
			this.tileEntity.tank.setFluid(null);
			break;
		case 2:
			if ( value > 0 && this.tileEntity.tank.getFluid() != null)
			this.tileEntity.tank.setFluid(new FluidStack(this.tileEntity.tank.getFluid(), value));
			else
			this.tileEntity.tank.setFluid(null);
		}
	}
	

}
