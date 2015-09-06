package buildcraftAPS.items.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import buildcraft.BuildCraftCore;
import buildcraft.BuildCraftFactory;
import buildcraft.BuildCraftSilicon;
import buildcraft.BuildCraftTransport;
import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraftAPS.mod_Aps;
import buildcraftAPS.blocks.APSMACHINES;
import buildcraftAPS.items.ItemApsFussion;
import buildcraftAPS.items.ItemCore;
import buildcraftAPS.items.ItemKit;
import cpw.mods.fml.common.registry.GameRegistry;

public class Itemkit {

	private static ItemKit Kit;
	private static ItemApsFussion Fus;
	private static APSMACHINES Machine;
	private static ItemCore PowerCore;
	
	private static ItemStack tokamakPlatingItem;
	private static ItemStack tokamakChamberItem;
	private static ItemStack tokamakControlItem;
	private static ItemStack tokamakExtractorItem;
	private static ItemStack tokamakInputItem;
	private static ItemStack tokamakOutputItem;
	
	private static ItemStack kitG;
	private static ItemStack kitB;
	private static ItemStack kitM;
	private static ItemStack kitS;
	private static ItemStack kitF;
	private static ItemStack kitFu;
	private static ItemStack kitbMk2;
	private static ItemStack kitBMk3;
	
	
	private static ItemStack Grinder;
	private static ItemStack BlastFurnace;
	private static ItemStack MineralExt;
	private static ItemStack StorePower;
	public  static ItemStack FussionReactor;
    private static ItemStack PowerFurnace;
	private static ItemStack BlastFurnaceMk2;
	private static ItemStack BlastFurnaceMk3;

	public static ItemStack PowerCoreWood;
	public static ItemStack PowerCoreIron;
	public static ItemStack PowerCoreGold;
	public static ItemStack PowerCoreDiamon;
   
	
	
	public static void preInit(){
		Kit = new ItemKit();		
		Machine = new APSMACHINES();
		Fus = new ItemApsFussion();
		
        kitG = new ItemStack(Kit, 1, 0);
        kitB = new ItemStack(Kit, 1, 1);
        kitM = new ItemStack(Kit, 1, 2);
        kitS = new ItemStack(Kit, 1, 3);
        kitF = new ItemStack(Kit, 1, 4);
        kitFu = new ItemStack(Kit, 1, 5);
        kitbMk2 = new ItemStack(Kit, 1, 6);
        kitBMk3 = new ItemStack(Kit, 1, 7);
        
        tokamakPlatingItem = new ItemStack(Fus, 1, 0);
		tokamakChamberItem = new ItemStack(Fus, 1, 1);
		tokamakControlItem = new ItemStack(Fus, 1, 2);
		tokamakExtractorItem = new ItemStack(Fus, 1, 3);
		tokamakInputItem = new ItemStack(Fus, 1, 4);
		tokamakOutputItem = new ItemStack(Fus, 1, 5);
		 
        Grinder = new ItemStack(Machine, 1, 0);
        BlastFurnace = new ItemStack(Machine, 1, 1);
        MineralExt = new ItemStack(Machine, 1, 2);
        StorePower = new ItemStack(Machine, 1, 3);
        FussionReactor = new ItemStack(Machine, 1, 4);
        PowerFurnace = new ItemStack(Machine, 1, 5);
        BlastFurnaceMk2 = new ItemStack(Machine, 1, 6);
        BlastFurnaceMk3 = new ItemStack(Machine, 1, 7);
        PowerCore = new ItemCore();
   	    
        PowerCoreWood = new ItemStack(PowerCore, 1, 0);
        PowerCoreIron = new ItemStack(PowerCore, 1, 1);
        PowerCoreGold = new ItemStack(PowerCore, 1, 2);
        PowerCoreDiamon = new ItemStack(PowerCore, 1, 3);
 
	}
	
	public static void coreInit(){
	  	   GameRegistry.addShapedRecipe(new ItemStack(PowerCore, 1, 0), new Object[]{ "IrI", "GRG", "IrI",
	            Character.valueOf('I'), Items.iron_ingot,
	            Character.valueOf('G'), BuildCraftCore.woodenGearItem,
	            Character.valueOf('r'), Items.redstone,
	            Character.valueOf('R'), new ItemStack(BuildCraftSilicon.redstoneChipset, 1, 0)
	            });
	   	   GameRegistry.addShapedRecipe(new ItemStack(PowerCore, 1, 1), new Object[]{ "IrI", "GRG", "IrI",
	            Character.valueOf('I'), Items.iron_ingot,
	            Character.valueOf('G'), BuildCraftCore.ironGearItem,
	            Character.valueOf('r'), Items.redstone,
	            Character.valueOf('R'), new ItemStack(BuildCraftSilicon.redstoneChipset, 1, 1)
	            });
	   	   GameRegistry.addShapedRecipe(new ItemStack(PowerCore, 1, 2), new Object[]{ "IrI", "GRG", "IrI",
	            Character.valueOf('I'), Items.iron_ingot,
	            Character.valueOf('G'), BuildCraftCore.goldGearItem,
	            Character.valueOf('r'), Items.redstone,
	            Character.valueOf('R'), new ItemStack(BuildCraftSilicon.redstoneChipset, 1, 2)
	   	       });
	   	   GameRegistry.addShapedRecipe(new ItemStack(PowerCore, 1, 3), new Object[]{ "IrI", "GRG", "IrI",
	            Character.valueOf('I'), Items.iron_ingot,
	            Character.valueOf('G'), BuildCraftCore.diamondGearItem,
	            Character.valueOf('r'), Items.redstone,
	            Character.valueOf('R'), new ItemStack(BuildCraftSilicon.redstoneChipset, 1, 3)
	            });
	   	    BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + 0 , 100000, new ItemStack(PowerCore, 1, 0), new ItemStack(Items.redstone, 2),new ItemStack(BuildCraftCore.woodenGearItem,2));
	        BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + 1, 100000*2, new ItemStack(PowerCore, 1, 1), new ItemStack(Items.iron_ingot, 2) ,new ItemStack(PowerCore, 2, 0), new ItemStack(BuildCraftSilicon.redstoneChipset, 1 , 1));
	        BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + 2, 100000*3, new ItemStack(PowerCore, 1, 2), new ItemStack(Items.gold_ingot, 2), new ItemStack(PowerCore, 2, 1) , new ItemStack(BuildCraftSilicon.redstoneChipset, 1 , 2));
	        BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + 3, 100000*4, new ItemStack(PowerCore, 1, 3), new ItemStack(Items.diamond, 2), new ItemStack(PowerCore, 2, 2), new ItemStack(BuildCraftSilicon.redstoneChipset, 1 , 3));
	        BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + 4, 50000, new ItemStack(BuildCraftCore.woodenGearItem), new ItemStack(Items.stick, 2));
	        BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + 5, 50000, new ItemStack(BuildCraftCore.ironGearItem), new ItemStack(Items.iron_ingot, 2), new ItemStack(BuildCraftCore.stoneGearItem));
	        BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + 6, 50000, new ItemStack(BuildCraftCore.stoneGearItem), new ItemStack(Blocks.cobblestone, 2), new ItemStack(BuildCraftCore.woodenGearItem));
	        BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + 7, 50000, new ItemStack(BuildCraftCore.goldGearItem), new ItemStack(Items.gold_ingot, 2), new ItemStack(BuildCraftCore.ironGearItem));
	        BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + 8, 50000, new ItemStack(BuildCraftCore.diamondGearItem), new ItemStack(Items.diamond, 2), new ItemStack(BuildCraftCore.goldGearItem));
	}
	public static void init() {
		
	    BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + "Grinder", 200000, 
	    		new ItemStack(Kit, 1 ,0), 
	    		new ItemStack(BuildCraftTransport.pipeItemsWood, 2), 
	    		new ItemStack(BuildCraftSilicon.redstoneChipset, 4),
	    		new ItemStack(Blocks.obsidian, 4), 
	    		new ItemStack(Items.gold_ingot, 2), 
	    		new ItemStack(PowerCore, 2 , 0),
	    		new ItemStack(PowerCore, 2 , 1));
	    BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + "BlastFurnace", 150000, 
	    		new ItemStack(Kit, 1 ,1), 
	    		new ItemStack(BuildCraftTransport.pipeFluidsGold, 2), 
	    		new ItemStack(BuildCraftTransport.pipeItemsGold),
	    		new ItemStack(Blocks.obsidian, 4), 
	    		new ItemStack(Items.gold_ingot, 2), 
	    		new ItemStack(PowerCore, 2 , 0),
	    		new ItemStack(PowerCore, 2 , 1), 
	    		new ItemStack(PowerCore, 2, 2));
	    BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + "MineralExtractor", 250000, 
	    		new ItemStack(Kit, 1 ,2), 
	    		new ItemStack(BuildCraftTransport.pipeItemsWood, 2), 
	    		new ItemStack(BuildCraftSilicon.redstoneChipset, 4),
	    		new ItemStack(Blocks.obsidian, 4), 
	    		new ItemStack(Items.gold_ingot, 2), 
	    		new ItemStack(PowerCore, 2 , 0),
	    		new ItemStack(PowerCore, 2 , 1),
	            new ItemStack(PowerCore, 2 , 2),
	            new ItemStack(PowerCore, 1 , 3)
	    		);
	            
	    BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + "EnergyStore", 300000, 
	    		new ItemStack(Kit, 1 ,3), 
	    		new ItemStack(BuildCraftTransport.pipePowerDiamond, 2), 
 	    		new ItemStack(Blocks.obsidian, 8), 
	    		new ItemStack(Items.gold_ingot, 4),
	    		new ItemStack(BuildCraftCore.goldGearItem),
	            new ItemStack(BuildCraftCore.ironGearItem, 5),
	    		new ItemStack(PowerCore, 2 , 0),
	    		new ItemStack(PowerCore, 2 , 1), 
	    		new ItemStack(PowerCore, 4,  2),
	            new ItemStack(PowerCore, 1 , 3)
	    		);
	    BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + "Fussion Reactor", 250000,
	    		new ItemStack(Kit, 1, 4),
	    		new ItemStack(Fus, 1, 1),
	    		new ItemStack(Fus, 1, 2),
	    		new ItemStack(Fus, 1, 3),
	    		new ItemStack(Fus, 1, 4),
	    		new ItemStack(Fus, 1, 5)
	    );
	    
	    BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + "PowerFurnace", 100000, 
	    		kitFu, 
	    		new ItemStack(PowerCore, 2 , 0),
	    		new ItemStack(PowerCore, 2, 1),
	    		new ItemStack(BuildCraftTransport.pipeItemsGold, 2),
	    		new ItemStack(Blocks.obsidian, 4)
	   );
	    
	   BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + "BlastFurnaceMk2", 200000, 
			   kitbMk2,
	           new ItemStack(PowerCore, 4, 2),
	           new ItemStack(PowerCore, 1, 3),
	           kitB,
	           new ItemStack(BuildCraftSilicon.redstoneChipset, 4, 5)
	   );
	   
	   BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + "BlastFurnaceMk3", 300000, 
			   kitBMk3,
	           new ItemStack(PowerCore, 4, 2),
	           new ItemStack(PowerCore, 4, 3),
	           kitbMk2,
	           new ItemStack(BuildCraftSilicon.redstoneChipset, 4, 3)
	   );
	   
	  }

	public static void fussion() {
		 BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + "Plating", 100000, 
				 tokamakPlatingItem, 
				 new ItemStack(Items.diamond, 3), 
				 new ItemStack(Items.iron_ingot, 3), 
				 new ItemStack(Blocks.obsidian, 3));
	     BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + "Chember", 150000, 
	    		 tokamakChamberItem, 
	    		 new ItemStack(Fus, 4, 0), 
	    		 new ItemStack(Blocks.obsidian,4), 
	    		 new ItemStack(Items.iron_ingot,4));
	     BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + "Control", 200000, 
	    		 tokamakControlItem, 
	    		 new ItemStack(BuildCraftSilicon.redstoneChipset, 4, 0), 
	    		 new ItemStack(Fus, 4 , 0), 
	    		 new ItemStack(PowerCore, 2, 3));
	     BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + "Extractor", 200000, 
	    		 tokamakExtractorItem, 
	    		 new ItemStack(BuildCraftFactory.refineryBlock, 2), 
	    		 new ItemStack(BuildCraftCore.engineBlock, 2, 2), 
	    		 new ItemStack(Fus, 2, 2), 
	    		 new ItemStack(BuildCraftTransport.pipeFluidsGold, 4), 
	    		 new ItemStack(BuildCraftTransport.pipePowerGold, 4));
	     BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + "Input", 150000, 
	    		 tokamakInputItem, 
	    		 new ItemStack(BuildCraftTransport.pipePowerDiamond, 8), 
	    		 new ItemStack(PowerCore, 2, 2));
	     BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + "Output", 150000, 
	    		 tokamakOutputItem, 
	    		 new ItemStack(BuildCraftCore.diamondGearItem, 2), 
	    		 new ItemStack(Items.diamond, 4), 
	    		 new ItemStack(BuildCraftTransport.pipePowerDiamond,2), 
	    		 new ItemStack(BuildCraftTransport.pipePowerEmerald, 1));
 	     
	}

	public static void kitBuild() {
        BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + "From kit to Grinder", 10000, Grinder, kitG);
        BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + "From kit to BlastFurnace", 10000, BlastFurnace, kitB);
        BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + "From kit to MineralExt", 10000, MineralExt, kitM);
        BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + "From kit to StorePower", 10000, StorePower, kitS);
        BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + "From kit to FussionReactor", 10000, FussionReactor, kitF);
        BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + "From kit to Power Furnace", 10000, PowerFurnace, kitFu);
        BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + "From kit to BlastFurnaceMk2", 10000, BlastFurnaceMk2, kitbMk2);
        BuildcraftRecipeRegistry.assemblyTable.addRecipe(mod_Aps.modid + "From kit to BlastFurnaceMk3", 10000, BlastFurnaceMk3, kitBMk3);
        
	}
	
	

}
