package buildcraftAPS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import buildcraft.api.core.Position;
import buildcraft.api.power.IEngine;
import buildcraft.api.transport.IInjectable;
import buildcraft.core.CompatHooks;
import buildcraft.core.lib.inventory.ITransactor;
import buildcraft.core.lib.inventory.Transactor;
import buildcraft.core.lib.utils.BlockUtils;
import buildcraft.core.lib.utils.XorShift128Random;

public class UtilsAps {
	private static final List<ForgeDirection> directions = new ArrayList<ForgeDirection>(Arrays.asList(ForgeDirection.VALID_DIRECTIONS));
	public static final XorShift128Random RANDOM = new XorShift128Random();
 
	public static final ForgeDirection[] Side_DIRECTIONS = {ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.EAST};
	
	public static int addToRandomInjectableAround(World world, int x, int y, int z, ForgeDirection from, ItemStack stack) {
		List<IInjectable> possiblePipes = new ArrayList<IInjectable>();
		List<ForgeDirection> pipeDirections = new ArrayList<ForgeDirection>();
//        mod_Aps.logger.info(Side_DIRECTIONS);
		for (ForgeDirection side : Side_DIRECTIONS) {
			if (from.getOpposite() == side) {
				continue;
			}

			Position pos = new Position(x, y, z, side);

			pos.moveForwards(1.0);

			TileEntity tile = BlockUtils.getTileEntity(world, (int) pos.x, (int) pos.y, (int) pos.z);

			if (tile instanceof IInjectable) {
				if (!((IInjectable) tile).canInjectItems(side.getOpposite())) {
					continue;
				}

				possiblePipes.add((IInjectable) tile);
				pipeDirections.add(side.getOpposite());
			} else {
				IInjectable wrapper = CompatHooks.INSTANCE.getInjectableWrapper(tile, side);
				if (wrapper != null) {
					possiblePipes.add(wrapper);
					pipeDirections.add(side.getOpposite());
				}
			}
		}

		if (possiblePipes.size() > 0) {
			int choice = RANDOM.nextInt(possiblePipes.size());

			IInjectable pipeEntry = possiblePipes.get(choice);

			return pipeEntry.injectItem(stack, true, pipeDirections.get(choice), null);
		}
		return 0;
	}

	public static int addToRandomInventoryAround(World world, int x, int y, int z, ItemStack stack) {
		Collections.shuffle(directions);
		for (ForgeDirection orientation : directions) {
			Position pos = new Position(x, y, z, orientation);
			pos.moveForwards(1.0);

			TileEntity tileInventory = BlockUtils.getTileEntity(world, (int) pos.x, (int) pos.y, (int) pos.z);
			ITransactor transactor = Transactor.getTransactorFor(tileInventory);
			if (transactor != null && !(tileInventory instanceof IEngine) && transactor.add(stack, orientation.getOpposite(), false).stackSize > 0) {
				return transactor.add(stack, orientation.getOpposite(), true).stackSize;
			}
		}
		return 0;

	}

}
