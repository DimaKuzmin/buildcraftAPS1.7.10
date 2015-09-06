package buildcraftAPS.proxys;

import net.minecraft.item.Item;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import buildcraft.transport.TransportProxyClient;
import buildcraftAPS.mod_Aps;

public class ClientProxy extends CommonProxy {
	 
	@Override
	public void initLapisPipe() {
 		super.initLapisPipe();
 		 registerCustomItemRenderer(mod_Aps.PipePowerLapis, TransportProxyClient.pipeItemRenderer);
	}
	
	public void registerCustomItemRenderer(Item item, IItemRenderer basemod)
	{
	    MinecraftForgeClient.registerItemRenderer(item, basemod);
    }
}
