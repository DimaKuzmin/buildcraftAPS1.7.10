package buildcraftAPS.items;

import java.util.List;

 
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import buildcraftAPS.mod_Aps;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCore extends Item{
	@SideOnly(Side.CLIENT)
	protected IIcon[] core = new IIcon[16];
  	  
      public ItemCore() {
		setHasSubtypes(true);
		setCreativeTab(mod_Aps.BUILDCRAFTAPS);
		setMaxDamage(0);
		GameRegistry.registerItem(this, "Power Core");
 	  }
     
      @SuppressWarnings({"rawtypes", "unchecked"})
      @SideOnly(Side.CLIENT)
      public void getSubItems(Item item, CreativeTabs p_150895_2_, List list) {
    	for (int i=0; i <= 3; i++){
    		list.add(new ItemStack(item, 1, i));
    	}
      }
      @Override
    public String getUnlocalizedName(ItemStack p_77667_1_) {
         switch (p_77667_1_.getItemDamage()){
        case 0:
       	 return "Power Core Wooden";
        case 1:
       	 return "Power Core Iron";
        case 2: 
       	 return "Power Core Gold";
        case 3:
       	 return "Power Core Diamond";
        default:
       	 return "powercore";
        }
    }
    
    
    @Override
    public IIcon getIconFromDamage(int damage) {
        if (damage < 0 || damage >= 4)
            return core[0];
		return core[damage];
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister icon) {
    	core[0] = icon.registerIcon(mod_Aps.textureName + ":PowerCore0");
    	core[1] = icon.registerIcon(mod_Aps.textureName + ":PowerCore1");
    	core[2] = icon.registerIcon(mod_Aps.textureName + ":PowerCore2");
    	core[3] = icon.registerIcon(mod_Aps.textureName + ":PowerCore3");
    }
    
 
}
