package buildcraftAPS;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import buildcraftAPS.items.init.Itemkit;
import buildcraftAPS.proxys.CommonProxy;
import buildcraftAPS.proxys.GuiHandler;
import buildcraftAPS.recipe.RecipeManagerExt;
import buildcraftAPS.recipe.Recipes;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(name = mod_Aps.name, modid = mod_Aps.modid, version = mod_Aps.version, dependencies = "required-after:BuildCraft|Core;required-after:BuildCraft|Transport")
public class mod_Aps {
public final static String modid= "APS";
public final static String version = "4.0.0";
public final static String name = "Buildcraft Advanced Power System";
public final static String textureName = "buildcraftaps"; 
public final static String clientProxy = "buildcraftAPS.proxys.ClientProxy";
public final static String commonProxy = "buildcraftAPS.proxys.CommonProxy";

public static final Logger logger = LogManager.getLogger(modid);
 
 

@Instance(mod_Aps.modid)
public static mod_Aps instance;
 
    public static Item PipePowerLapis;
	public static final CreativeTabs BUILDCRAFTAPS = new CreativeTabAps();
       @EventHandler
       public void preInit(FMLPreInitializationEvent event){
           Itemkit.preInit();
       }
       
       @EventHandler
       public void Init (FMLInitializationEvent event){
         	  NetworkRegistry.INSTANCE.registerGuiHandler(instance, GuiHandler.INSTANCE);
         	  PacketHandler.init();
         	  logger.info("Init");
         	  CommonProxy.proxy.initLapisPipe();
         	  CommonProxy.proxy.registerCraftingPropolis(new ItemStack(Blocks.lapis_block));
         	  Recipes.recipeExt = new RecipeManagerExt();
      	      Recipes.recipeExt.addRecipe(new ItemStack[] { new ItemStack(Items.iron_ingot), new ItemStack(Items.redstone), new ItemStack(Blocks.obsidian), new ItemStack(Items.gold_ingot), new ItemStack(Items.diamond) }, new float[] { 0.046875F, 0.025F, 0.009375F, 0.00625F, 0.003125F });
              Itemkit.coreInit();
      	      Itemkit.init();
              Itemkit.fussion();
              Itemkit.kitBuild();
       }
       
       @EventHandler
       public void PostInit (FMLPostInitializationEvent event){
    	   logger.info("PostInit");
       }
}
