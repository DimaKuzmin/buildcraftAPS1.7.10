package buildcraftAPS.guis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import buildcraftAPS.mod_Aps;
import buildcraftAPS.container.ContainerGrinder;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class GuiGrinder extends GuiContainer{

 
	private ContainerGrinder container;

	public GuiGrinder(ContainerGrinder container) {
        super(container);
        xSize = 176;
        ySize = 165;
        this.container = container;

    }
 
	public static final ResourceLocation texture = new ResourceLocation(mod_Aps.textureName, "textures/guis/GrinderGUI.png");
 
	@Override
    protected void drawGuiContainerBackgroundLayer(float f, int j, int i) {
		GL11.glColor4f(1F, 1F, 1F, 1F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        
        int topLeftX = (width - xSize) / 2;
        int topLeftY = (height - ySize) / 2;
        drawTexturedModalRect(topLeftX, topLeftY, 0, 0, xSize, ySize);
        displayTempGauge(topLeftX, topLeftY, 52, 11, container.tileEntity.getGrindProgressScaled(64));
       // if (grinderInventory.getEnergy() != 0 && grinderInventory.MAxRf != 0)
//        mod_Aps.logger.info(grinderInventory.energy);
    }
 
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
		super.drawGuiContainerForegroundLayer(par1, par2);
		this.fontRendererObj.drawString("Traces Detected:", 65, 10, 0x404040);
        int i = 0;
        for(String S : container.tileEntity.getPossibleProducts())
        {
        	this.fontRendererObj.drawString(S, 65, 20 + (10 * i), 0x404040);
        	i++;
        }
        container.tileEntity.getPossibleProducts();
    }
	@Override
	protected void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
 
	}
    protected void displayTempGauge(int TopLeftX, int TopLeftY, int GaugeTopLeftX, int GaugeTopLeftY, int BarHeight)
	{
        //Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		
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
	}
