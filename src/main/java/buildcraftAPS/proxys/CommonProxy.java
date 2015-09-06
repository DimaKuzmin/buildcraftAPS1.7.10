package buildcraftAPS.proxys;

import java.lang.reflect.Constructor;
import java.util.Locale;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import buildcraft.BuildCraftTransport;
import buildcraft.core.BCCreativeTab;
import buildcraft.transport.BlockGenericPipe;
import buildcraft.transport.ItemPipe;
import buildcraft.transport.Pipe;
import buildcraft.transport.TransportProxy;
import buildcraftAPS.mod_Aps;
import buildcraftAPS.items.PipePowerLapis;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {
    @SidedProxy(clientSide = mod_Aps.clientProxy, serverSide = mod_Aps.commonProxy)
    public static CommonProxy proxy;
 
	@SuppressWarnings("rawtypes")
	public static ItemPipe registerPipe(Class<? extends Pipe> clas, BCCreativeTab creativeTab) {
		ItemPipe item = null;
		 try {
		      Constructor<ItemPipe> ctor = ItemPipe.class.getDeclaredConstructor(new Class[] { BCCreativeTab.class });
		      ctor.setAccessible(true);
		      item = (ItemPipe)ctor.newInstance(new Object[] { creativeTab });
		    } catch (Exception e) {
		      e.printStackTrace();
		}
		
		assert (item != null) : "The ItemPipe instance must not be null";
		item.setUnlocalizedName("buildcraftPipe." + clas.getSimpleName().toLowerCase(Locale.ENGLISH));
		GameRegistry.registerItem(item, item.getUnlocalizedName());
		
		BlockGenericPipe.pipes.put(item, clas);

		Pipe<?> dummyPipe = BlockGenericPipe.createPipe(item);
		if (dummyPipe != null) {
			item.setPipeIconIndex(dummyPipe.getIconIndexForItem());
			TransportProxy.proxy.setIconProviderFromPipe(item, dummyPipe);
		}

		return item;
	}
	
	  public void initLapisPipe()
	  {
	    mod_Aps.PipePowerLapis = createPipe(PipePowerLapis.class, BCCreativeTab.get("pipes"));
	  }
	  
	  @SuppressWarnings("rawtypes")
	  public Item createPipe(Class<? extends Pipe> clas, BCCreativeTab creativeTab)
	  {
	    return registerPipe(clas, creativeTab);
	  }
	  
	  public void registerCraftingPropolis(ItemStack resource) {
		    GameRegistry.addRecipe(new ItemStack(mod_Aps.PipePowerLapis), new Object[] { "#X#", Character.valueOf('#'), resource, Character.valueOf('X'), BuildCraftTransport.pipeItemsDiamond });
	  }
}
