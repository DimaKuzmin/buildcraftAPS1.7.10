package buildcraftAPS.guis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import buildcraftAPS.mod_Aps;
import buildcraftAPS.blocks.TileEntity.TileEntityMineralExtractorMK3;
import buildcraftAPS.container.ContainerMineralExtractorMk3;

public class GuiMineralExtractorMk3 extends GuiContainer {

	private TileEntityMineralExtractorMK3 te;
    private static final ResourceLocation tex = new ResourceLocation(mod_Aps.textureName, "textures/guis/ExtractorGUIMk2.png");
	public GuiMineralExtractorMk3(ContainerMineralExtractorMk3 container) {
		super(container);
        this.te = container.te;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(tex);
        int topLeftX = (width - xSize) / 2;
        int topLeftY = (height - ySize) / 2;
        drawTexturedModalRect(topLeftX, topLeftY, 0, 0, xSize, ySize);
        displayTempGauge(topLeftX, topLeftY, 8, 16, (int) te.getScaledPowerLevel(64));
        if (te.tank.getFluidAmount() > 0){
   			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
 			IIcon icon = te.tank.getFluid().getFluid().getIcon();
			int l = (int) te.getScaledLiquidQuantity(58);
  	        GuiBlastFurnace.drawRepeated(icon, topLeftX + 22, topLeftY + 25 + 52 - l, 16.0D, l, this.zLevel);
  		    this.mc.renderEngine.bindTexture(tex);
  	        drawTexturedModalRect(topLeftX + 22, topLeftY + 25 + 55 - 61, 176, 8, 16, 60);
        }
 }
	
    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
     	super.drawGuiContainerForegroundLayer(p_146979_1_, p_146979_2_);
		fontRendererObj.drawString("Traces Detected:", 42, 19, 0x404040);
        int i = 0;
        if (te.getPossibleProducts() != null){
        for(String S : te.getPossibleProducts())
        {
        	fontRendererObj.drawString(S, 42, 29 + (10 * i), 0x404040);
        	i++;
        }}
    }
    
	private void displayTempGauge(int TopLeftX, int TopLeftY, int GaugeTopLeftX, int GaugeTopLeftY, int BarHeight)
	{		
		int start = 0;
		int texPosX;
		if(te.getPowerSufficient())
			texPosX = 184;
		else
			texPosX = 176;
		
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

			drawTexturedModalRect(TopLeftX + GaugeTopLeftX, TopLeftY + GaugeTopLeftY + 64 - x - start, texPosX, 8 - x, 8, x); //The 58 is the gauge height
			start = start + 8;

			if (x == 0 || BarHeight == 0) {
				break;
			}
		} //This while draws the repeating gauge graphic.
    }

}
