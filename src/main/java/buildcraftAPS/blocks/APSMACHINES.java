package buildcraftAPS.blocks;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import buildcraftAPS.mod_Aps;
import buildcraftAPS.blocks.TileEntity.TileEntityBlastFurnace;
import buildcraftAPS.blocks.TileEntity.TileEntityFussionReactor;
import buildcraftAPS.blocks.TileEntity.TileEntityGrinder;
import buildcraftAPS.blocks.TileEntity.TileEntityMineralExtractor;
import buildcraftAPS.blocks.TileEntity.TileEntityMineralExtractorMK2;
import buildcraftAPS.blocks.TileEntity.TileEntityMineralExtractorMK3;
import buildcraftAPS.blocks.TileEntity.TileEntityPoweredFurnace;
import buildcraftAPS.blocks.TileEntity.TileEntityStoreEnergy;
import buildcraftAPS.blocks.basic.APSBasicBlock;
import buildcraftAPS.items.ItemBlocks;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class APSMACHINES extends APSBasicBlock{
 
    public APSMACHINES() {
		super(Material.iron);
    	GameRegistry.registerBlock(this, ItemBlocks.class, "Machines");
        GameRegistry.registerTileEntity(TileEntityGrinder.class, "Grinder");
        GameRegistry.registerTileEntity(TileEntityBlastFurnace.class, "Blast Furnace");
        GameRegistry.registerTileEntity(TileEntityMineralExtractor.class, "Mineral Extractor");
        GameRegistry.registerTileEntity(TileEntityStoreEnergy.class, "Storage Energy");
        GameRegistry.registerTileEntity(TileEntityFussionReactor.class, "Fussion Reactor");
        GameRegistry.registerTileEntity(TileEntityPoweredFurnace.class, "Powered Furnace");
        GameRegistry.registerTileEntity(TileEntityMineralExtractorMK2.class, "Mineral Extractor Ore 2");
        GameRegistry.registerTileEntity(TileEntityMineralExtractorMK3.class, "Mineral Extractor Ore 3");
 	}

	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {
        this.top[0] = ir.registerIcon(mod_Aps.textureName + ":grinder/top");
        this.bottom[0] = ir.registerIcon(mod_Aps.textureName + ":grinder/bottom");
        this.side[0] = ir.registerIcon(mod_Aps.textureName + ":grinder/side");
 
        this.top[1] = ir.registerIcon(mod_Aps.textureName + ":magmalifier/top");
        this.bottom[1] = ir.registerIcon(mod_Aps.textureName + ":magmalifier/bottom");
        this.side[1] = ir.registerIcon(mod_Aps.textureName + ":magmalifier/side");
        
        this.top[2] = ir.registerIcon(mod_Aps.textureName + ":MineralExtractor/top");
        this.bottom[2] = ir.registerIcon(mod_Aps.textureName + ":MineralExtractor/bottom");
        this.side[2] = ir.registerIcon(mod_Aps.textureName + ":MineralExtractor/side");
        
        this.top[3] = ir.registerIcon(mod_Aps.textureName + ":EnergyStore/top");
        this.bottom[3] = ir.registerIcon(mod_Aps.textureName + ":EnergyStore/bottom");
        this.side[3] = ir.registerIcon(mod_Aps.textureName + ":EnergyStore/side");
        
        this.top[4] = ir.registerIcon(mod_Aps.textureName + ":FussionReactor/top");
        this.bottom[4] = ir.registerIcon(mod_Aps.textureName + ":FussionReactor/bottom");
        this.side[4] = ir.registerIcon(mod_Aps.textureName + ":FussionReactor/side");  
        
        this.top[5] = ir.registerIcon(mod_Aps.textureName + ":PoweredFurnace/top");
        this.bottom[5] = ir.registerIcon(mod_Aps.textureName + ":PoweredFurnace/bottom");
        this.side[5] = ir.registerIcon(mod_Aps.textureName + ":PoweredFurnace/side");
        		
        this.top[6] = ir.registerIcon(mod_Aps.textureName + ":MineralExtractorMk2/top");
        this.bottom[6] = ir.registerIcon(mod_Aps.textureName + ":MineralExtractorMk2/bottom");
        this.side[6] = ir.registerIcon(mod_Aps.textureName + ":MineralExtractorMk2/side");
        		
        this.top[7] = ir.registerIcon(mod_Aps.textureName + ":MineralExtractorMk3/top");
        this.bottom[7] = ir.registerIcon(mod_Aps.textureName + ":MineralExtractorMk3/bottom");
        this.side[7] = ir.registerIcon(mod_Aps.textureName + ":MineralExtractorMk3/side");
        				
	}
	
    @SuppressWarnings({"rawtypes", "unchecked"})
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item id, CreativeTabs tab, List list) {
        list.add(new ItemStack(id, 1, 0));
        list.add(new ItemStack(id, 1, 1));
        list.add(new ItemStack(id, 1, 2));
        list.add(new ItemStack(id, 1, 3));
        list.add(new ItemStack(id, 1, 4));
        list.add(new ItemStack(id, 1, 5));
        list.add(new ItemStack(id, 1, 6));
        list.add(new ItemStack(id, 1, 7));
    }

	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,int par6, float par7, float par8, float par9)
    {
        if (!world.isRemote && world.getTileEntity(x, y, z) instanceof TileEntityGrinder) {
        player.openGui(mod_Aps.instance, 0, world, x, y, z);
        }else if(!world.isRemote && world.getTileEntity(x, y, z) instanceof TileEntityBlastFurnace) {
        player.openGui(mod_Aps.instance, 1, world, x, y, z);
        }else if(!world.isRemote && world.getTileEntity(x, y, z) instanceof TileEntityMineralExtractor){
        player.openGui(mod_Aps.instance, 2, world, x, y, z);
        }else if(!world.isRemote && world.getTileEntity(x, y, z) instanceof TileEntityStoreEnergy){
        player.openGui(mod_Aps.instance, 3, world, x, y, z);
        }else if(!world.isRemote && world.getTileEntity(x, y, z) instanceof TileEntityFussionReactor){
        player.openGui(mod_Aps.instance, 4, world, x, y, z);
        }else if(!world.isRemote && world.getTileEntity(x, y, z) instanceof TileEntityPoweredFurnace){
        player.openGui(mod_Aps.instance, 5, world, x, y, z);
        }else if(!world.isRemote && world.getTileEntity(x, y, z) instanceof TileEntityMineralExtractorMK2){
        player.openGui(mod_Aps.instance, 6, world, x, y, z);
        }else if(!world.isRemote && world.getTileEntity(x, y, z) instanceof TileEntityMineralExtractorMK3){
        player.openGui(mod_Aps.instance, 7, world, x, y, z);
        }
		return true;
    }

	@Override
	public TileEntity createTileEntity(World world, int meta) {
        if (meta == 0) {
            return new TileEntityGrinder();
        }
        if (meta == 1) {
        	return new TileEntityBlastFurnace();
        }
        if (meta == 2){
        	return new TileEntityMineralExtractor();
        }
        if (meta == 3){
        	return new TileEntityStoreEnergy();
        }
        if (meta == 4){
        	return new TileEntityFussionReactor();
        }
        if (meta == 5){
        	return new TileEntityPoweredFurnace();
        }
        if (meta == 6){
        	return new TileEntityMineralExtractorMK2();
        }
        if (meta == 7){
        	return new TileEntityMineralExtractorMK3();
        }
        return super.createTileEntity(world, meta);
	}
}
