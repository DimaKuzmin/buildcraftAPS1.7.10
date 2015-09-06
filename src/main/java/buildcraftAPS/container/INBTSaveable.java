package buildcraftAPS.container;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTSaveable {

	void readFromNBT(NBTTagCompound tag);

	void writeToNBT(NBTTagCompound tag);
}

