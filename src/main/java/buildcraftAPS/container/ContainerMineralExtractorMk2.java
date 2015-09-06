package buildcraftAPS.container;

import net.minecraft.entity.player.InventoryPlayer;
import buildcraftAPS.blocks.TileEntity.TileEntityMineralExtractorMK2;
import buildcraftAPS.container.base.ContainerBaseMineralExtractor;

public class ContainerMineralExtractorMk2 extends ContainerBaseMineralExtractor<TileEntityMineralExtractorMK2> {

	public TileEntityMineralExtractorMK2 te;

	public ContainerMineralExtractorMk2(InventoryPlayer inventoryPlayer, TileEntityMineralExtractorMK2 te) {
		super(inventoryPlayer, te);
		this.te = te;
 	}
}
