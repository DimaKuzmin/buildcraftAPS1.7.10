package buildcraftAPS.proxys;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import buildcraftAPS.blocks.TileEntity.TileEntityBlastFurnace;
import buildcraftAPS.blocks.TileEntity.TileEntityFussionReactor;
import buildcraftAPS.blocks.TileEntity.TileEntityGrinder;
import buildcraftAPS.blocks.TileEntity.TileEntityMineralExtractor;
import buildcraftAPS.blocks.TileEntity.TileEntityMineralExtractorMK2;
import buildcraftAPS.blocks.TileEntity.TileEntityMineralExtractorMK3;
import buildcraftAPS.blocks.TileEntity.TileEntityPoweredFurnace;
import buildcraftAPS.blocks.TileEntity.TileEntityStoreEnergy;
import buildcraftAPS.container.ContainerBlastFurnace;
import buildcraftAPS.container.ContainerFussionReactor;
import buildcraftAPS.container.ContainerGrinder;
import buildcraftAPS.container.ContainerMineralExtractor;
import buildcraftAPS.container.ContainerMineralExtractorMk2;
import buildcraftAPS.container.ContainerMineralExtractorMk3;
import buildcraftAPS.container.ContainerPoweredFurnace;
import buildcraftAPS.container.ContainerStoreEnergy;
import buildcraftAPS.guis.GuiBlastFurnace;
import buildcraftAPS.guis.GuiFussionReactor;
import buildcraftAPS.guis.GuiGrinder;
import buildcraftAPS.guis.GuiMineralExtractor;
import buildcraftAPS.guis.GuiMineralExtractorMk2;
import buildcraftAPS.guis.GuiMineralExtractorMk3;
import buildcraftAPS.guis.GuiPoweredFurnace;
import buildcraftAPS.guis.GuiStoreEnergy;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	public static final GuiHandler INSTANCE = new GuiHandler();

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		switch (ID) {
		case 0:
		if (entity instanceof TileEntityGrinder )
			return new ContainerGrinder(player.inventory, (TileEntityGrinder) entity);
		case 1:
		if (entity instanceof TileEntityBlastFurnace)
			return new ContainerBlastFurnace((TileEntityBlastFurnace) entity, player.inventory);
		case 2:
		if (entity instanceof TileEntityMineralExtractor)
			return new ContainerMineralExtractor(player.inventory, (TileEntityMineralExtractor) entity);
		case 3:
		if (entity instanceof TileEntityStoreEnergy)
			return new ContainerStoreEnergy(player.inventory, (TileEntityStoreEnergy) entity);
		case 4:  
		if (entity instanceof TileEntityFussionReactor)
		    return new ContainerFussionReactor(player.inventory, (TileEntityFussionReactor) entity );
		case 5:
		if (entity instanceof TileEntityPoweredFurnace)
			return new ContainerPoweredFurnace(player.inventory, (TileEntityPoweredFurnace) entity);
		case 6:
		if (entity instanceof TileEntityMineralExtractorMK2)
			return new ContainerMineralExtractorMk2(player.inventory, (TileEntityMineralExtractorMK2) entity);
		case 7:
		if (entity instanceof TileEntityMineralExtractorMK3)
		    return new ContainerMineralExtractorMk3(player.inventory, (TileEntityMineralExtractorMK3) entity);
		case 8:
			break;
		default:
			return null;
		}
		return null;

	
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		switch (ID){
		case 0:
			if (entity instanceof TileEntityGrinder )
				return new GuiGrinder(new ContainerGrinder(player.inventory, (TileEntityGrinder) entity));
		case 1:
			if (entity instanceof TileEntityBlastFurnace)
				return new GuiBlastFurnace(new ContainerBlastFurnace((TileEntityBlastFurnace) entity, player.inventory));
		case 2:
			if (entity instanceof TileEntityMineralExtractor)
				return new GuiMineralExtractor(new ContainerMineralExtractor(player.inventory, (TileEntityMineralExtractor) entity));
		case 3:
			if (entity instanceof TileEntityStoreEnergy)
				return new GuiStoreEnergy(new ContainerStoreEnergy(player.inventory, (TileEntityStoreEnergy) entity));
		case 4:
			if (entity instanceof TileEntityFussionReactor)
				return new GuiFussionReactor(new ContainerFussionReactor(player.inventory, (TileEntityFussionReactor) entity));
		case 5:
			if (entity instanceof TileEntityPoweredFurnace)
				return new GuiPoweredFurnace(new ContainerPoweredFurnace(player.inventory, (TileEntityPoweredFurnace) entity));
		case 6:
		    if (entity instanceof TileEntityMineralExtractorMK2)
			    return new GuiMineralExtractorMk2(new ContainerMineralExtractorMk2(player.inventory, (TileEntityMineralExtractorMK2) entity));
		case 7:
		    if (entity instanceof TileEntityMineralExtractorMK3)
		        return new GuiMineralExtractorMk3(new ContainerMineralExtractorMk3(player.inventory, (TileEntityMineralExtractorMK3) entity));
		case 8:
			break;
		default:
			return null;
		


	}
		return null;
	}

}
