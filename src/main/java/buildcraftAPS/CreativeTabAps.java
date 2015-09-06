package buildcraftAPS;

import buildcraftAPS.items.init.Itemkit;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTabAps extends CreativeTabs {
 	public CreativeTabAps() {
		super("APS");
	}

	@Override
	@SideOnly(Side.CLIENT)
 	public Item getTabIconItem() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ItemStack getIconItemStack() {
		// TODO Auto-generated method stub
		return Itemkit.FussionReactor;
	}
 

}
