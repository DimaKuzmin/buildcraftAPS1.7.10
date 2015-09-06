package buildcraftAPS.guis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import buildcraftAPS.MessageButtonUpdate;
import buildcraftAPS.PacketHandler;
import buildcraftAPS.mod_Aps;
import buildcraftAPS.container.ContainerStoreEnergy;

public class GuiStoreEnergy extends GuiContainer{

	private final ResourceLocation texture = new ResourceLocation(mod_Aps.textureName , "textures/guis/EnergyStoreGUI.png");
	private ContainerStoreEnergy cont;
 
	private GuiAPSSlider InSlider;
	private GuiAPSSlider OutSlider;

	public GuiStoreEnergy(ContainerStoreEnergy container) {
		super(container);
		this.cont = container;
	}
	@Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        this.fontRendererObj.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
        
        String s1 = "" + cont.te.UserMaxPowerIn;
        this.fontRendererObj.drawString(s1, 157 - this.fontRendererObj.getStringWidth(s1), 40, 4210752);
        String s2 = "" + cont.te.UserMaxPowerOut;
        this.fontRendererObj.drawString(s2, 157 - this.fontRendererObj.getStringWidth(s2), 53, 4210752);
    }
	
 	@SuppressWarnings("unchecked")
	@Override
	public void initGui()
    {
      super.initGui();
      
      InSlider = new GuiAPSSlider(420, (width - xSize) / 2 + 87, (height - ySize) / 2 + 40, 71, 8, 184, 0, 5, 7, texture, "", (((float) cont.te.UserMaxPowerIn) /  cont.te.maxEnergyInp));
	  OutSlider = new GuiAPSSlider(421, (width - xSize) / 2 + 87, (height - ySize) / 2 + 53, 71, 8, 184, 0, 5, 7, texture, "", (((float)  cont.te.UserMaxPowerOut) /  cont.te.maxEnergyOut));
		
		buttonList.add(InSlider);
		buttonList.add(OutSlider);
    }
    
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_,int p_146976_2_, int p_146976_3_) {
	    this.mc.renderEngine.bindTexture(this.texture);
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    int topLeftX = (this.width - this.xSize) / 2;
	    int topLeftY = (this.height - this.ySize) / 2;
	    drawTexturedModalRect(topLeftX, topLeftY, 0, 0, this.xSize, this.ySize);
	    
	    if (this.cont.te.IsDraining)
	    drawTexturedModalRect(topLeftX + 87, topLeftY + 16, 176, 0, 8, 8);
	    
	    displayStoreGauge(topLeftX, topLeftY, 68, 14, (int)this.cont.te.getScaledPowerStored(58));
	    if (!this.cont.te.IsDraining)
	    displayPowerGauge(topLeftX, topLeftY, 87, 40, (int)this.cont.te.getScaledPower(false, 71));
	    else
	    displayPowerGauge(topLeftX, topLeftY, 87, 40, (int)this.cont.te.getScaledPower(false, 0));
	    
	    if (this.cont.te.IsDraining)
	    displayPowerGauge(topLeftX, topLeftY, 87, 53, (int)this.cont.te.getScaledPower(true, 71));
	    else
	    displayPowerGauge(topLeftX, topLeftY, 87, 53, (int)this.cont.te.getScaledPower(true, 0));
        
	    this.cont.te.UserMaxPowerIn = ((int)this.InSlider.getScaledSliderValue(cont.te.maxEnergyInp));
	    this.cont.te.UserMaxPowerOut = ((int)this.OutSlider.getScaledSliderValue(cont.te.maxEnergyOut));
	    
	    networkUpdate();
	}
	public void updateScreen(){
		super.updateScreen();
		
		networkUpdate();
	}
	private void networkUpdate() {
        	PacketHandler.instance.sendToServer(new MessageButtonUpdate(cont.te, 0, (int) InSlider.getScaledSliderValue(cont.te.maxEnergyInp)));
        	PacketHandler.instance.sendToServer(new MessageButtonUpdate(cont.te, 1, (int) OutSlider.getScaledSliderValue(cont.te.maxEnergyOut)));
         //	mod_Aps.logger.info((int) InSlider.getScaledSliderValue(cont.te.maxEnergyInp));
    }
	
	private void displayStoreGauge(int TopLeftX, int TopLeftY, int GaugeTopLeftX, int GaugeTopLeftY, int BarHeight)
	  {
	    int start = 0;
	    
	    while(true)
	    {
	      int x = 0;
	      
	      if (BarHeight > 8) {
	        x = 8;
	        BarHeight -= 8;
	      } else {
	        x = BarHeight;
	        BarHeight = 0;
	      }
	      
	      drawTexturedModalRect(TopLeftX + GaugeTopLeftX, TopLeftY + GaugeTopLeftY + 58 - x - start, 176, 8 + (8 - x), 8, x);
	      start += 8;
	      
	      if ((x == 0) || (BarHeight == 0)) {
	        break;
	      }
	    }
	  }
	  
	  private void displayPowerGauge(int TopLeftX, int TopLeftY, int GaugeTopLeftX, int GaugeTopLeftY, int BarWidth)
	  {
		//getMCRender().bindTexture(this.texture);
	    
	    int start = 0;
	    
	    while(true)
	    {
	      int x = 0;
	      
	      if (BarWidth > 8) {
	        x = 8;
	        BarWidth -= 8;
	      } else {
	        x = BarWidth;
	        BarWidth = 0;
	      }
	      
	      drawTexturedModalRect(TopLeftX + GaugeTopLeftX + start, TopLeftY + GaugeTopLeftY, 176, 8, x, 8);
	      start += 8;
	      
	      if ((x == 0) || (BarWidth == 0)) {
	        break;
	      }
	    }
	  }

		protected Minecraft getMC(){
			return Minecraft.getMinecraft();
		}
		
		protected TextureManager getMCRender(){
			return getMC().renderEngine;
		}
}
