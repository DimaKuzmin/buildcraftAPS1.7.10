package buildcraftAPS.items;

 

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import buildcraftAPS.mod_Aps;
import cpw.mods.fml.common.registry.GameRegistry;

 

public class ItemApsFussion extends Item {
     
	private IIcon[] fussion = new IIcon[16];
 
	public ItemApsFussion() {
       setHasSubtypes(true);
       setCreativeTab(mod_Aps.BUILDCRAFTAPS);
       setMaxDamage(0);
       GameRegistry.registerItem(this, "Fussion");
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		switch (stack.getItemDamage()) {
		case 0:
			return "Tokamak Plating";
		case 1:
            return "Tokamak Chember";
		case 2:
			return "Tokamak Control";
		case 3:
			return "Deuterium Extractor";
		case 4:
			return "Tokamak Input";
		case 5:
			return "Tokamak Output";
		default:
           return "";
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item item, CreativeTabs p_150895_2_,List list) {
		for (int i = 0; i <= 5; i++){
			list.add(new ItemStack(item, 1, i));
		}
  	}
	
	@Override
	public IIcon getIconFromDamage(int damage) {
	       if (damage < 0 || damage >= 6)
	            return fussion[0];
			return fussion[damage];
	}
	
	@Override
	public void registerIcons(IIconRegister icon) {
		 for (int i = 0; i <= 5; i++){
			fussion[i] = icon.registerIcon(mod_Aps.textureName + ":Fussion/Item" + i);
		 }
	}
	
	
	
	
	}
