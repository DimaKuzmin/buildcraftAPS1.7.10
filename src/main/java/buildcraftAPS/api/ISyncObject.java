package buildcraftAPS.api;

import io.netty.buffer.ByteBuf;

public interface ISyncObject {

	void writeToByteBuff(ByteBuf buf);

	void readFromByteBuff(ByteBuf buf);
}

