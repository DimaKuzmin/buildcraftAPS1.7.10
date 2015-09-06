package buildcraftAPS.blocks.basic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import buildcraftAPS.mod_Aps;
import buildcraftAPS.blocks.TileEntity.Basic.TileEntityAps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class APSBasicBlock extends BlockContainer{
	@SideOnly(Side.CLIENT)
	protected IIcon[] bottom = new IIcon[16];
	@SideOnly(Side.CLIENT)
	protected IIcon[] top = new IIcon[16];
	@SideOnly(Side.CLIENT)
	protected IIcon[] side = new IIcon[16];
	
	
	protected APSBasicBlock(Material material) {
		super(material);
        
        setHardness(3.0F);
        setCreativeTab(mod_Aps.BUILDCRAFTAPS);
 
 	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
 		return null;
	}

	@Override
    @SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side) {
        //TileEntityAps tile = (TileEntityAps) access.getTileEntity(x, y, z);
        int i = access.getBlockMetadata(x, y, z);

        if (side == 0) {
            return this.bottom[i];
        } else if (side == 1) {
            return this.top[i];
        } else {
            return this.side[i];
        }
	}
 

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side < 1) {
            return this.bottom[meta];
        }
        if (side == 1) {
            return this.top[meta];
        }
        return this.side[meta];
    }

	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {

	    int facing = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        TileEntityAps tile = (TileEntityAps) world.getTileEntity(x, y, z);

        if (facing == 0)
            tile.facing = 2;
        else if (facing == 1)
            tile.facing = 5;
        else if (facing == 2)
            tile.facing = 3;
        else if (facing == 3)
            tile.facing = 4;
	}

	
    @Override
    public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
        world.removeTileEntity(x, y, z);
        super.breakBlock(world, x, y, z, par5, par6);
    }
}
