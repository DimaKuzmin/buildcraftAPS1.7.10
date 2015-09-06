package buildcraftAPS.guis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import buildcraftAPS.mod_Aps;
import buildcraftAPS.blocks.TileEntity.TileEntityPoweredFurnace;
import buildcraftAPS.container.ContainerPoweredFurnace;

public class GuiPoweredFurnace extends GuiContainer{

	
	public static final ResourceLocation tex = new ResourceLocation(mod_Aps.textureName, "textures/guis/PowerFurnace.png");
	public TileEntityPoweredFurnace te;
	public GuiPoweredFurnace(ContainerPoweredFurnace container) {
		super(container);
  
	    this.te = container.te;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_,int p_146976_2_, int p_146976_3_) {
		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(tex);
	    int topLeftX = (this.width - this.xSize) / 2;
	    int topLeftY = (this.height - this.ySize) / 2;
	    drawTexturedModalRect(topLeftX, topLeftY, 0, 0, this.xSize, this.ySize); 
	    displayTempGauge(topLeftX, topLeftY, 79, 11, te.powerlevel(64));
	}

	
	private void displayTempGauge(int TopLeftX, int TopLeftY, int GaugeTopLeftX, int GaugeTopLeftY,	int BarHeight) {
	this.mc.renderEngine.bindTexture(tex);
		
		int start = 0;

		while (true)
		{
			int x = 0;

			if (BarHeight > 8) {
				x = 8;
				BarHeight -= 8;
			} else {
				x = BarHeight;
				BarHeight = 0;
			}

			drawTexturedModalRect(TopLeftX + GaugeTopLeftX, TopLeftY + GaugeTopLeftY + 64 - x - start, 176, 8 - x, 8, x); //The 58 is the gauge height
			start = start + 8;

			if (x == 0 || BarHeight == 0) {
				break;
			}
		} 
    }
		
	

	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_,	int p_146979_2_) {
        	fontRendererObj.drawString("Inventory",8, this.ySize - 96 + 2, 4210752);
	}
	
}
