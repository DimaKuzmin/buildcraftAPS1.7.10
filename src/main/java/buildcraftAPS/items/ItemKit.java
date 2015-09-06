package buildcraftAPS.items;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import buildcraftAPS.mod_Aps;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemKit extends Item {
     
	private IIcon[] kit = new IIcon[16]; 
	public ItemKit() {
       setHasSubtypes(true);
       setMaxDamage(0);
       GameRegistry.registerItem(this, "Kit");
       setCreativeTab(mod_Aps.BUILDCRAFTAPS);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item item, CreativeTabs p_150895_2_, List list) {
	    for (int i = 0; i <=7;i++ ){
	    	list.add(new ItemStack(item, 1, i));
	    }
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
	    switch (stack.getItemDamage()){
	    case 0:
	    	return "Grinder";
	    case 1:
	    	return "BlastFurnace";
	    case 2:
	    	return "MineralExtractor";
	    case 3:
	    	return "StoreEnergy";
	    case 4:
	    	return "FussionReactor";
	    case 5:
	    	return "PoweredFurnace";
	    case 6:
	    	return "MineralExtractorMK2";
	    case 7:
	    	return "MineralExtractorMK3";
	    default:
	    	return "";
	    }
	}
	
	@Override
	public void registerIcons(IIconRegister icon) {
		 for (int i = 0; i <= 7; i++){
			 kit[i] = icon.registerIcon(mod_Aps.textureName + ":Kit/Machines" + i);
		 }
	}
	
	@Override
	public IIcon getIconFromDamage(int damage) {
        if (damage < 0 || damage >= 8)
            return kit[0];
		return kit[damage];
	}
	
	
}
