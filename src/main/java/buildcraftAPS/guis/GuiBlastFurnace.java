package buildcraftAPS.guis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import buildcraftAPS.mod_Aps;
import buildcraftAPS.container.ContainerBlastFurnace;

public class GuiBlastFurnace extends GuiContainer{

	private ContainerBlastFurnace cont;

	public GuiBlastFurnace(ContainerBlastFurnace container)
	{
		super(container);
        this.cont = container;
        xSize = 176;
        ySize = 165;
	}
	public static final ResourceLocation texture = new ResourceLocation(mod_Aps.textureName, "textures/guis/MagmafierGUI.png");
	
	protected void drawGuiContainerForegroundLayer(int x, int y)
    {
		super.drawGuiContainerForegroundLayer(x, y);
        this.fontRendererObj.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
    }
	@Override
	protected void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
 
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(texture);
        int topLeftX = (width - xSize) / 2;
        int topLeftY = (height - ySize) / 2;
        drawTexturedModalRect(topLeftX, topLeftY, 0, 0, xSize, ySize);
        if (cont.te.BlockLevel != 0){
        displayBlockLevelGauge(topLeftX, topLeftY, 79, 11, (int) cont.te.getScaledBlockQuantity(64));
        }
        if (cont.te.energy != 0){
        displayPowerLevelGauge(topLeftX, topLeftY, 93, 11, (int) cont.te.getScaledPowerLevel(64));
        }
        if (cont.te.tank.getFluidAmount() != 0){
         	Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
    	    IIcon icon = this.cont.te.tank.getFluid().getFluid().getIcon();
    	    int l = cont.te.getScaledLiquidQuantity(58);
  	        drawRepeated(icon, topLeftX + 107, topLeftY + 25 + 47 - l, 16.0D, l, this.zLevel);
  	        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
  		    drawTexturedModalRect(topLeftX + 107, topLeftY + 25 + 47 - 58, 176, 24, 16, 60);
        }
 
    }
	@Override
	public void initGui() {
		super.initGui();
 
	}
	       
	     public static void drawRepeated(IIcon icon, double x, double y, double width, double height, double z)
	  /*    */   {
	  /* 20 */     double iconWidthStep = (icon.getMaxU() - icon.getMinU()) / 16.0D;
	  /* 21 */     double iconHeightStep = (icon.getMaxV() - icon.getMinV()) / 16.0D;
	  /*    */     
	  /* 23 */     Tessellator tessellator = Tessellator.instance;
	  /* 24 */     tessellator.startDrawingQuads();
	  /*    */     
	  /* 26 */     for (double cy = y; cy < y + height; cy += 16.0D) {
	  /* 27 */       double quadHeight = Math.min(16.0D, height + y - cy);
	  /* 28 */       double maxY = cy + quadHeight;
	  /* 29 */       double maxV = icon.getMinV() + iconHeightStep * quadHeight;
	  /*    */       
	  /* 31 */       for (double cx = x; cx < x + width; cx += 16.0D) {
	  /* 32 */         double quadWidth = Math.min(16.0D, width + x - cx);
	  /* 33 */         double maxX = cx + quadWidth;
	  /* 34 */         double maxU = icon.getMinU() + iconWidthStep * quadWidth;
	  /*    */         
	  /* 36 */         tessellator.addVertexWithUV(cx, maxY, z, icon.getMinU(), maxV);
	  /* 37 */         tessellator.addVertexWithUV(maxX, maxY, z, maxU, maxV);
	  /* 38 */         tessellator.addVertexWithUV(maxX, cy, z, maxU, icon.getMinV());
	  /* 39 */         tessellator.addVertexWithUV(cx, cy, z, icon.getMinU(), icon.getMinV());
	  /*    */       }
	  /*    */     }
	  /*    */     
	  /* 43 */     tessellator.draw();
	  /*    */   }
        
        private void displayPowerLevelGauge(int TopLeftX, int TopLeftY, int GaugeTopLeftX, int GaugeTopLeftY, int BarHeight)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		
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
		} //This while draws the repeating gauge graphic.
    }
	
	private void displayBlockLevelGauge(int TopLeftX, int TopLeftY, int GaugeTopLeftX, int GaugeTopLeftY, int BarHeight)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		
		int start = 0;

		while (true)
		{
			int x = 0;

			if (BarHeight > 16) {
				x = 16;
				BarHeight -= 16;
			} else {
				x = BarHeight;
				BarHeight = 0;
			}

			drawTexturedModalRect(TopLeftX + GaugeTopLeftX, TopLeftY + GaugeTopLeftY + 64 - x - start, 176, 24 - x, 8, x); //The 58 is the gauge height
			start = start + 16;

			if (x == 0 || BarHeight == 0) {
				break;
			}
		} //This while draws the repeating gauge graphic.
	}	
}
