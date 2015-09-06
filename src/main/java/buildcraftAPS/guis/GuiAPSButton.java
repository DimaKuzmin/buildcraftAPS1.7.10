package buildcraftAPS.guis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;



public class GuiAPSButton extends GuiButton
{
  int[] texInfo = new int[4];
  ResourceLocation Texture;
  
  public GuiAPSButton(int id, int xPos, int yPos, int texXPos, int texYPos, int texWidth, int texHeight, ResourceLocation tex, String label)
  {
    super(id, xPos, yPos, texWidth, texHeight, label);
    this.texInfo[0] = texXPos;
    this.texInfo[1] = texYPos;
    this.texInfo[2] = texWidth;
    this.texInfo[3] = texHeight;
    
    this.Texture = tex;
  }
  
  public void setTexXPos(int xPos) { this.texInfo[0] = xPos; }
  public void setTexYPos(int yPos) { this.texInfo[1] = yPos; }
  

  public void drawButton(Minecraft minecraft, int i, int j)
  {
    if (!this.visible)
    {
      return;
    }
    FontRenderer fontrenderer = minecraft.fontRenderer;
    Minecraft.getMinecraft().renderEngine.getTexture(Texture);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    boolean isOverButton = (i >= this.xPosition) && (j >= this.yPosition) && (i < this.xPosition + this.width) && (j < this.yPosition + this.height);
    drawTexturedModalRect(this.xPosition, this.yPosition, this.texInfo[0], this.texInfo[1], this.texInfo[2], this.texInfo[3]);
    mouseDragged(minecraft, i, j);
    if (!this.enabled)
    {
      drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, -6250336);
    }
    else if (isOverButton)
    {
      drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 16777120);
    }
    else
    {
      drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 14737632);
    }
  }
}
