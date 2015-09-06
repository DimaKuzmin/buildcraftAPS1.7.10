package buildcraftAPS.blocks.TileEntity.Basic;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import buildcraft.core.lib.utils.Utils;
import buildcraftAPS.MessageByteBuff;
import buildcraftAPS.PacketHandler;
import buildcraftAPS.api.ISynchronizedTile;
import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.common.network.NetworkRegistry;

public class TileEntityAps extends TileEntity implements ISynchronizedTile {
//Fields
    public int facing;
    public boolean isOn = false;
 	private int tick = 0;
	protected final int IDENTIFIER;
//functions
	public TileEntityAps(int identifier){
		IDENTIFIER = identifier;
	}
    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        facing = tagCompound.getInteger("facing");
        isOn = tagCompound.getBoolean("isOn");
 
    }
	public void sync() {
		if (!worldObj.isRemote) {
			PacketHandler.instance.sendToAllAround(new MessageByteBuff(this), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, getX(), getY(), getZ(), 64));
		}
	}
    public int getZ() {
		// TODO Auto-generated method stub
		return zCoord;
	}
    public int getY() {
		// TODO Auto-generated method stub
		return yCoord;
	}
    public int getX() {
		// TODO Auto-generated method stub
		return xCoord;
	}
    
    
	@Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        tagCompound.setInteger("facing", facing);
        tagCompound.setBoolean("isOn", isOn);
    }
 
    @Override
    public final Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        writeToNBT(nbt);
        S35PacketUpdateTileEntity packet = new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbt);
        return packet;
    }
    

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        NBTTagCompound nbt = pkt.func_148857_g();
        readFromNBT(nbt);
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if (worldObj != null) {
        	sync();
        	this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }
    @Override 
    public void updateEntity(){
    	if(tick <= 0){
    		tick=20;
    	     sync();
    	     }else
    	    	 tick=20;
    	
		
    }
	@Override
	public void writeToByteBuff(ByteBuf buf) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void readFromByteBuff(ByteBuf buf) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getIdentifier() {
		// TODO Auto-generated method stub
		return IDENTIFIER;
	}
 
	 protected boolean isPoweredTile(TileEntity tile, ForgeDirection side)
	  {
		 if (tile == null) {
	            return false;
			} else if (tile instanceof IEnergyHandler || tile instanceof IEnergyReceiver) {
	            return ((IEnergyConnection) tile).canConnectEnergy(side.getOpposite());
	        } else {
				return false;
			}
	  }
	 
	  protected ForgeDirection getPoweredNeighbour()
	  {
	        if (isPoweredTile(this.worldObj.getTileEntity(this.xCoord + ForgeDirection.EAST.offsetX, this.yCoord + ForgeDirection.EAST.offsetY, this.zCoord + ForgeDirection.EAST.offsetZ), ForgeDirection.EAST)) return ForgeDirection.EAST;
	        else
		    if (isPoweredTile(this.worldObj.getTileEntity(this.xCoord + ForgeDirection.NORTH.offsetX, this.yCoord + ForgeDirection.NORTH.offsetY, this.zCoord + ForgeDirection.NORTH.offsetZ), ForgeDirection.NORTH)) return ForgeDirection.NORTH;
		    else
		    if (isPoweredTile(this.worldObj.getTileEntity(this.xCoord + ForgeDirection.SOUTH.offsetX, this.yCoord + ForgeDirection.SOUTH.offsetY, this.zCoord + ForgeDirection.SOUTH.offsetZ), ForgeDirection.SOUTH)) return ForgeDirection.SOUTH;
		    else
		    if (isPoweredTile(this.worldObj.getTileEntity(this.xCoord + ForgeDirection.WEST.offsetX, this.yCoord + ForgeDirection.WEST.offsetY, this.zCoord + ForgeDirection.WEST.offsetZ), ForgeDirection.WEST)) return ForgeDirection.WEST;
		    else
	        if (isPoweredTile(this.worldObj.getTileEntity(this.xCoord + ForgeDirection.DOWN.offsetX, this.yCoord + ForgeDirection.DOWN.offsetY, this.zCoord + ForgeDirection.DOWN.offsetZ), ForgeDirection.DOWN)) return ForgeDirection.DOWN;
	        else
	        if (isPoweredTile(this.worldObj.getTileEntity(this.xCoord + ForgeDirection.UP.offsetX, this.yCoord + ForgeDirection.UP.offsetY, this.zCoord + ForgeDirection.UP.offsetZ), ForgeDirection.UP)) return ForgeDirection.UP;
	        else
	    	return null;
	  }
	  
	  protected void getNeighbour(ItemStack stack)
	  {
	    int added = 0;
	    added = Utils.addToRandomInventoryAround(worldObj, xCoord, yCoord, zCoord, stack.copy());
	    
		if (added == 0)
         added = Utils.addToRandomInjectableAround(worldObj, xCoord, yCoord, zCoord+1,  ForgeDirection.UNKNOWN, stack.copy());
         if (added == 0)
       	    	added = Utils.addToRandomInjectableAround(worldObj, xCoord, yCoord, zCoord-1,  ForgeDirection.UNKNOWN, stack.copy());
         if (added == 0)
       	   	added = Utils.addToRandomInjectableAround(worldObj, xCoord+1, yCoord, zCoord,  ForgeDirection.UNKNOWN, stack.copy());
         if (added == 0)
        	added = Utils.addToRandomInjectableAround(worldObj, xCoord-1, yCoord, zCoord,  ForgeDirection.UNKNOWN, stack.copy());
	  }
}
