package buildcraftAPS.container;

import net.minecraft.entity.player.InventoryPlayer;
import buildcraftAPS.blocks.TileEntity.TileEntityMineralExtractorMK3;
import buildcraftAPS.container.base.ContainerBaseMineralExtractor;

public class ContainerMineralExtractorMk3 extends ContainerBaseMineralExtractor<TileEntityMineralExtractorMK3> {

	public TileEntityMineralExtractorMK3 te;

	public ContainerMineralExtractorMk3(InventoryPlayer inventoryPlayer, TileEntityMineralExtractorMK3 te) {
		super(inventoryPlayer, te);
		this.te = te;
 	}

}
