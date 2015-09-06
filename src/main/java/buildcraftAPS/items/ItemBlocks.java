package buildcraftAPS.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlocks extends ItemBlock{

	public ItemBlocks(Block p) {
		super(p);
        setHasSubtypes(true);
 	}
	
    @Override
	public int getMetadata(int i){
		return i;
	}
    @Override
    public String getUnlocalizedName(ItemStack item)
    {
    	String name = "";
		switch (item.getItemDamage()) {
    	case 0:{
    		name  = "Grinder";
    		break;}
		case 1:{
			name = "Blast Furnace";
			break;}
		case 2:{
			name = "Mineral Extractor";
			break;}
		case 3:{
			name = "Energy Store";
			break;
		}
		case 4:{
			name = "Fusion Reactor";
			break;
		}
		case 5:{
			name = "Power Furnace";
			break;
		}
		case 6:{
			name = "Mineral Extractor MK2";
			break;
		}
		case 7:{
			name = "Mineral Extractor MK3";
			break;
		}
		default:
            name = "nothing";	
    	}

		return getUnlocalizedName() + "." + name;
    }
    
    
}
