package buildcraftAPS;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {
	public static final SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel("bcaps");
	public static void init(){
	instance.registerMessage(MessageByteBuff.class, MessageByteBuff.class, 0, Side.CLIENT);
	instance.registerMessage(MessageButtonUpdate.class, MessageButtonUpdate.class, 1, Side.SERVER);
	}
}
