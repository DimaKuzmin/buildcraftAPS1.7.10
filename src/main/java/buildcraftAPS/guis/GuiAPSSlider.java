package buildcraftAPS.guis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiAPSSlider extends GuiButton
{
    public float sliderValue;
    public boolean dragging;
    
    int[] notchTexInfo = new int[4];
    ResourceLocation Texture;
    
    public GuiAPSSlider(int id, int xPos, int yPos, int width, int height, int notchTexXPos, int notchTexYPos, int notchTexWidth, int notchTexHeight, ResourceLocation notchTex, String label, float startingValue)
    {
        //Not that kind of Notch.
    	super(id, xPos, yPos, width, height, label);
        sliderValue = 1.0F;
        dragging = false;
        sliderValue = startingValue;
        Texture = notchTex;
        
        notchTexInfo[0] = notchTexXPos;
        notchTexInfo[1] = notchTexYPos;
        notchTexInfo[2] = notchTexWidth;
        notchTexInfo[3] = notchTexHeight;
    }

    public float getScaledSliderValue(float MaxValue)
    {
    	return sliderValue * MaxValue;
    }
    
    public void setSliderValue(float Value)
    {
    	sliderValue = Value;
    }
    
    public int getHoverState(boolean flag)
    {
        return 0;
    }
    
    @Override
    public void drawButton(Minecraft minecraft, int i, int j)
    {
        if (!visible)
        {
            return;
        }
        minecraft.renderEngine.bindTexture(Texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        boolean isOverButton = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
        getHoverState(isOverButton);
        //drawTexturedModalRect(xPosition, yPosition, 0, 46 + k * 20, width / 2, height);
        //drawTexturedModalRect(xPosition + width / 2, yPosition, 200 - width / 2, 46 + k * 20, width / 2, height);
        //Slider Background Halves
        mouseDragged(minecraft, i, j);
    }
    
    protected void mouseDragged(Minecraft minecraft, int i, int j)
    {
        if (!visible)
        {
            return;
        }
        if (dragging)
        {
            sliderValue = (float)(i - (xPosition + 4)) / (float)(width - 8);
            if (sliderValue < 0.0F)
            {
                sliderValue = 0.0F;
            }
            if (sliderValue > 1.0F)
            {
                sliderValue = 1.0F;
            }
        }
        minecraft.renderEngine.getTexture(Texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect((int) (xPosition + (sliderValue * width) - (notchTexInfo[2] / 2)), yPosition + 3, notchTexInfo[0], notchTexInfo[1], notchTexInfo[2], notchTexInfo[3]);
    }

    public boolean mousePressed(Minecraft minecraft, int i, int j)
    {
        if (super.mousePressed(minecraft, i, j))
        {
            sliderValue = (float)(i - (xPosition + 4)) / (float)(width - 8);
            if (sliderValue < 0.0F)
            {
                sliderValue = 0.0F;
            }
            if (sliderValue > 1.0F)
            {
                sliderValue = 1.0F;
            }
            dragging = true;
            return true;
        }
        else
        {
            return false;
        }
    }

    public void mouseReleased(int i, int j)
    {
        dragging = false;
    }
}