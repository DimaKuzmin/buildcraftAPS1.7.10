package buildcraftAPS.guis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import buildcraftAPS.mod_Aps;
import buildcraftAPS.blocks.TileEntity.TileEntityFussionReactor;
import buildcraftAPS.container.ContainerFussionReactor;

public class GuiFussionReactor extends GuiContainer{

	private TileEntityFussionReactor tokamakInventory;
    private ResourceLocation texture = new ResourceLocation(mod_Aps.textureName, "textures/guis/TokamakGUI.png");	
	public GuiFussionReactor(ContainerFussionReactor container)
	{
		super(container);
        tokamakInventory = container.te;
	}
	
	protected void drawGuiContainerForegroundLayer()
    {
        fontRendererObj.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
        String s1 = "" + (int) tokamakInventory.PowerIn;
		fontRendererObj.drawString(s1, 160 - fontRendererObj.getStringWidth(s1), 44, 0x404040);
		String s2 = "" + (int) tokamakInventory.PowerOut;
		fontRendererObj.drawString(s2, 160 - fontRendererObj.getStringWidth(s2), 57, 0x404040);
    }
	
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(texture);
        int topLeftX = (width - xSize) / 2;
        int topLeftY = (height - ySize) / 2;
        drawTexturedModalRect(topLeftX, topLeftY, 0, 0, xSize, ySize);
        
        if (tokamakInventory.isFusing())
        	drawTexturedModalRect(topLeftX + 103, topLeftY + 20, 176, 0, 8, 8); //Fusing Indicator
        
        displayWaterGauge(topLeftX, topLeftY); //Water Level Gauge
        
        displayTempGauge(topLeftX, topLeftY, 85, 18, (int) tokamakInventory.getScaledTemp(58), (int) tokamakInventory.getScaledFusionTemp(58)); //Temperature Gauge
        
        displayPowerGauge(topLeftX, topLeftY, 103, 57, (int) tokamakInventory.getScaledPower(false, 58)); //Power Output Gauge
        
        displayPowerGauge(topLeftX, topLeftY, 103, 44, (int) tokamakInventory.getScaledPower(true,  58)); //Power Input Gauge
    }
	
	private void displayWaterGauge(int TopLeftX, int TopLeftY)
	{
        if (tokamakInventory.tank.getFluidAmount() != 0){
         	Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
    	    IIcon icon = this.tokamakInventory.tank.getFluid().getFluid().getIcon();
    	    int l = (int) tokamakInventory.getScaledLiquidQuantity(58);
  	        GuiBlastFurnace.drawRepeated(icon, TopLeftX + 61, TopLeftY + 18 + 58 - l, 16.0D, l, this.zLevel);
  	        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
  		    drawTexturedModalRect(TopLeftX + 61, TopLeftY + 19 + 58 - 58, 176, 18, 16, 60);
        }
    }
	
	private void displayTempGauge(int TopLeftX, int TopLeftY, int GaugeTopLeftX, int GaugeTopLeftY, int BarHeight, int FusionHeight)
	{
		 
		
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

			drawTexturedModalRect(TopLeftX + GaugeTopLeftX, TopLeftY + GaugeTopLeftY + 58 - x - start, 176, 8 + (8 - x), 8, x); //The 58 is the gauge height
			start = start + 8;

			if (x == 0 || BarHeight == 0) {
				break;
			}
		} //This while draws the repeating gauge graphic.
		drawTexturedModalRect(TopLeftX + GaugeTopLeftX, TopLeftY + GaugeTopLeftY + (58 - FusionHeight), 176, 16, 8, 1); //This draws the fusion temp line on top
    }
	
	private void displayPowerGauge(int TopLeftX, int TopLeftY, int GaugeTopLeftX, int GaugeTopLeftY, int BarWidth)
	{
		 
		
		int start = 0;

		while (true)
		{
			int x = 0;

			if (BarWidth > 8) {
				x = 8;
				BarWidth -= 8;
			} else {
				x = BarWidth;
				BarWidth = 0;
			}

			drawTexturedModalRect(TopLeftX + GaugeTopLeftX + start, TopLeftY + GaugeTopLeftY, 176, 8, x, 8); //The 58 is the gauge height
			start = start + 8;

			if (x == 0 || BarWidth == 0) {
				break;
			}
		} //This while draws the repeating gauge graphic.
    }
}
