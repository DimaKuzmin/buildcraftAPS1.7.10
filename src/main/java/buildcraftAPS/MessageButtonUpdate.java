package buildcraftAPS;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import buildcraftAPS.api.IBottonListener;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MessageButtonUpdate implements IMessage, IMessageHandler<MessageButtonUpdate, IMessage> {

	public int x;
	public int y;
	public int z;
	public int id;
	public int value;

	public MessageButtonUpdate() {

	}

	public MessageButtonUpdate(TileEntity tile, int id, int value) {
		this.x = tile.xCoord;
		this.y = tile.yCoord;
		this.z = tile.zCoord;
		this.id = id;
		this.value = value;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		id = buf.readInt();
		value = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(id);
		buf.writeInt(value);
	}

	@Override
	public IMessage onMessage(MessageButtonUpdate message, MessageContext ctx) {
		TileEntity tile = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);

		if (tile instanceof IBottonListener)
			((IBottonListener) tile).onWidgetPressed(message.id, message.value);
		return null;
	}
}
