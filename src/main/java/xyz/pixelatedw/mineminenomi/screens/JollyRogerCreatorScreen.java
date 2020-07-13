package xyz.pixelatedw.mineminenomi.screens;

import java.awt.Color;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.client.config.GuiUtils;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRoger;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement.LayerType;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.data.world.ExtendedWorldData;
import xyz.pixelatedw.mineminenomi.init.ModI18n;
import xyz.pixelatedw.mineminenomi.init.ModJollyRogers;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.packets.client.CSyncWorldDataPacket;
import xyz.pixelatedw.mineminenomi.screens.extra.NoTextureButton;
import xyz.pixelatedw.mineminenomi.screens.extra.TexturedIconButton;
import xyz.pixelatedw.wypi.WyHelper;
import xyz.pixelatedw.wypi.network.WyNetwork;

@OnlyIn(Dist.CLIENT)
public class JollyRogerCreatorScreen extends Screen
{
	private PlayerEntity player;
	private Widget selectedButton;
	// private JollyRogerElement selectedElement;
	private LayerType layerType = LayerType.BASE;
	private ExtendedWorldData worldData;
	private JollyRoger jollyRoger;
	private float animationTime = 0;
	private int nextElementTry = 0;
	private boolean isEditing = false;
	
	private int layerIndex;
	private int trueIndex;
	private GuiSlider redSlider;
	private GuiSlider greenSlider;
	private GuiSlider blueSlider;
	private Collection<RegistryObject<JollyRogerElement>> allElements;
	private List<RegistryObject<JollyRogerElement>> allBases;
	private List<RegistryObject<JollyRogerElement>> allBackgrounds;
	private List<RegistryObject<JollyRogerElement>> allDetails;

	public JollyRogerCreatorScreen(boolean isEditing)
	{
		super(new StringTextComponent(""));
		this.player = Minecraft.getInstance().player;
		this.worldData = ExtendedWorldData.get(this.player.world);
		Crew crew = this.worldData.getCrewWithMember(this.player.getUniqueID());
		this.jollyRoger = crew.getJollyRoger();
		this.isEditing = isEditing;
		
		this.allElements = ModJollyRogers.JOLLY_ROGER_ELEMENTS.getEntries();
		this.allBases = this.getTotalElementsForType(this.player, LayerType.BASE);
		this.allBackgrounds = this.getTotalElementsForType(this.player, LayerType.BACKGROUND);
		this.allDetails = this.getTotalElementsForType(this.player, LayerType.DETAIL);

		this.animationTime = 1;
	}

	@Override
	public void render(int x, int y, float f)
	{
		this.renderBackground();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		int posX = this.width / 2;
		int posY = this.height / 2;

		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);

		GL11.glPushMatrix();
		{
			double scale = 0.5;
			GL11.glTranslated(posX - 115, posY - 130, 1);
			GL11.glTranslated(128, 128, 0);
			GL11.glScaled(scale, scale, scale);
			GL11.glTranslated(-128, -128, 0);

			// Draw the black flag background
			// this.fillGradient(-10, 0, 0 + 270, 0 + 260, WyHelper.hexToRGB("#111115").getRGB(), WyHelper.hexToRGB("#202025").getRGB());

			// Actually drawing the jolly roger using the player capability data provided.
			RendererHelper.drawPlayerJollyRoger(this.jollyRoger);

			// Animation for when a new layer is selecting, showcasing the selected element.
			// To make it more visible the complementary color is applied instead, if it has no color then a bright red will be applied.
			if (this.animationTime < 0.15)
			{
				this.animationTime += 0.007;
				scale += 0.45 + this.animationTime;

				GL11.glTranslated(128, 128, 0);
				GL11.glScaled(scale, scale, scale);
				GL11.glTranslated(-128, -128, 0);

				JollyRogerElement element = this.getLayerElement();
				if (element != null)
				{
					if (element.canBeColored())
					{
						Color color = WyHelper.getComplementaryColor(WyHelper.hexToRGB(element.getColor()));
						GlStateManager.color4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 0.9F - (this.animationTime * 5));
					}
					else
					{
						GlStateManager.color4f(1.0F, 0.0F, 0.0F, 0.9F - (this.animationTime * 4));
					}
					Minecraft.getInstance().getTextureManager().bindTexture(element.getTexture());
					GuiUtils.drawTexturedModalRect(0, 0, 0, 0, 256, 256, 10);
				}
			}
		}
		GL11.glPopMatrix();

		String text = this.trueIndex >= 0 ? (this.trueIndex + 1) + " / " + this.allBases.size() : new TranslationTextComponent(ModI18n.GUI_EMPTY).getFormattedText();
		if (this.layerType == LayerType.BACKGROUND)
			text = this.trueIndex >= 0 ? (this.trueIndex + 1) + " / " + this.allBackgrounds.size() : new TranslationTextComponent(ModI18n.GUI_EMPTY).getFormattedText();
		else if (this.layerType == LayerType.DETAIL)
			text = this.trueIndex >= 0 ? (this.trueIndex + 1) + " / " + this.allDetails.size() : new TranslationTextComponent(ModI18n.GUI_EMPTY).getFormattedText();
		WyHelper.drawStringWithBorder(this.font, text, posX - this.font.getStringWidth(text) / 2 + 12, posY + 80, WyHelper.hexToRGB("#FFFFFF").getRGB());

		GlStateManager.disableBlend();

		posX = this.width;
		posY = this.height / 2;

		int outlineSize = 2;
		int pX = posX - 95;
		int pY = posY - 85;
		int sW = posX + 100;
		int sH = posY + 45;

		JollyRogerElement element = this.getLayerElement();

		if (element != null)
		{
			if(element.canBeColored())
			{
				this.fillGradient(pX - outlineSize, pY - outlineSize, sW + outlineSize, sH + outlineSize, WyHelper.hexToRGB("#000000").getRGB(), WyHelper.hexToRGB("#000000").getRGB());
				this.fillGradient(pX, pY, sW, sH, WyHelper.hexToRGB("#B3B3B3").getRGB(), WyHelper.hexToRGB("#505050").getRGB());
	
				posY = posY - 75;
				WyHelper.drawStringWithBorder(this.font, new TranslationTextComponent(ModI18n.GUI_RED).getFormattedText() + " ", posX - 75, posY, WyHelper.hexToRGB("#FFFFFF").getRGB());
				WyHelper.drawStringWithBorder(this.font, "0", posX - 85, posY + 14, WyHelper.hexToRGB("#FFFFFF").getRGB());
				WyHelper.drawStringWithBorder(this.font, "255", posX - 23, posY + 14, WyHelper.hexToRGB("#FFFFFF").getRGB());
	
				posY += 40;
				WyHelper.drawStringWithBorder(this.font, new TranslationTextComponent(ModI18n.GUI_GREEN).getFormattedText() + " ", posX - 75, posY, WyHelper.hexToRGB("#FFFFFF").getRGB());
				WyHelper.drawStringWithBorder(this.font, "0", posX - 85, posY + 14, WyHelper.hexToRGB("#FFFFFF").getRGB());
				WyHelper.drawStringWithBorder(this.font, "255", posX - 23, posY + 14, WyHelper.hexToRGB("#FFFFFF").getRGB());
	
				posY += 40;
				WyHelper.drawStringWithBorder(this.font, new TranslationTextComponent(ModI18n.GUI_BLUE).getFormattedText() + " ", posX - 75, posY, WyHelper.hexToRGB("#FFFFFF").getRGB());
				WyHelper.drawStringWithBorder(this.font, "0", posX - 85, posY + 14, WyHelper.hexToRGB("#FFFFFF").getRGB());
				WyHelper.drawStringWithBorder(this.font, "255", posX - 23, posY + 14, WyHelper.hexToRGB("#FFFFFF").getRGB());
			}
				
			if(element != this.jollyRoger.getBase())
			{
				posY = this.height / 2;
				
				pX = posX - 95;
				pY = posY + 50;
				sW = posX + 100;
				sH = posY + 85;
				
				this.fillGradient(pX - outlineSize, pY - outlineSize, sW + outlineSize, sH + outlineSize, WyHelper.hexToRGB("#000000").getRGB(), WyHelper.hexToRGB("#000000").getRGB());
				this.fillGradient(pX, pY, sW, sH, WyHelper.hexToRGB("#B3B3B3").getRGB(), WyHelper.hexToRGB("#505050").getRGB());
				
				WyHelper.drawStringWithBorder(this.font, (this.layerIndex + 1) + "", posX - 55, posY + 64, WyHelper.hexToRGB("#FFFFFF").getRGB());
			}
		}

		super.render(x, y, f);
	}

	@Override
	public void init(Minecraft mc, int width, int height)
	{
		super.init(mc, width, height);

		int posX = 0;
		int posY = this.height / 2;

		int listPosY = posY - 85;

		NoTextureButton baseButton = new NoTextureButton(posX + 5, listPosY, 115, 16, new TranslationTextComponent(ModI18n.GUI_BASE).getFormattedText(), this::selectButton);
		this.addButton(baseButton);

		for (int i = 0; i < this.jollyRoger.getBackgrounds().length; i++)
		{
			NoTextureButton bgButton = new NoTextureButton(posX + 5, (listPosY + 20 + (i * 20)), 115, 16, new TranslationTextComponent(ModI18n.GUI_BACKGROUND).getFormattedText() + " " + (i + 1), this::selectButton);
			this.addButton(bgButton);
		}

		for (int i = 0; i < this.jollyRoger.getDetails().length; i++)
		{
			NoTextureButton detailButton = new NoTextureButton(posX + 5, (listPosY + 60 + (i * 20)), 115, 16, new TranslationTextComponent(ModI18n.GUI_DETAIL).getFormattedText() + " " + (i + 1), this::selectButton);
			this.addButton(detailButton);
		}

		posX = this.width / 2;

		TexturedIconButton nextBaseTexture = new TexturedIconButton(ModResources.BIG_WOOD_BUTTON_RIGHT, posX + 80, posY - 65, 32, 110, "", (btn) -> this.moveIndex(btn, true));
		nextBaseTexture = nextBaseTexture.setTextureInfo(posX + 80, posY - 75, 32, 128);
		this.addButton(nextBaseTexture);

		TexturedIconButton prevBaseTexture = new TexturedIconButton(ModResources.BIG_WOOD_BUTTON_LEFT, posX - 85, posY - 65, 32, 110, "", (btn) -> this.moveIndex(btn, false));
		prevBaseTexture = prevBaseTexture.setTextureInfo(posX - 85, posY - 75, 32, 128);
		this.addButton(prevBaseTexture);

		posX = this.width;

		GuiSlider redColorPicker = new GuiSlider(posX - 76, posY - 65, 50, 16, "", "", 0, 255, 255, false, true, (btn) ->
		{
		}, (slider) -> this.changeColor(slider, "red"));
		this.redSlider = redColorPicker;
		this.addButton(redColorPicker);

		GuiSlider greenColorPicker = new GuiSlider(posX - 76, posY - 25, 50, 16, "", "", 0, 255, 255, false, true, (btn) ->
		{
		}, (slider) -> this.changeColor(slider, "green"));
		this.greenSlider = greenColorPicker;
		this.addButton(greenColorPicker);

		GuiSlider blueColorPicker = new GuiSlider(posX - 76, posY + 15, 50, 16, "", "", 0, 255, 255, false, true, (btn) ->
		{
		}, (slider) -> this.changeColor(slider, "blue"));
		this.blueSlider = blueColorPicker;
		this.addButton(blueColorPicker);

		NoTextureButton editJollyRogerButton = new NoTextureButton((this.width / 2) - 17, posY + 95, 60, 16, new TranslationTextComponent(ModI18n.GUI_FINISH).getFormattedText(), (btn) -> { this.finishEditing(); });
		this.addButton(editJollyRogerButton);
		
		TexturedIconButton layerUpBtn = new TexturedIconButton(ModResources.BRIGHT_WOOD_ARROW, posX - 80, posY + 53, 16, 25, "", (btn) -> this.changeLayerIndex(true));
		layerUpBtn = layerUpBtn.setTextureInfo(posX - 104, posY + 51, 64, 32);
		this.addButton(layerUpBtn);

		TexturedIconButton layerDownBtn = new TexturedIconButton(ModResources.BRIGHT_WOOD_ARROW_DOWN, posX - 40, posY + 53, 16, 25, "", (btn) -> this.changeLayerIndex(false));
		layerDownBtn = layerDownBtn.setTextureInfo(posX - 64, posY + 48, 64, 32);
		this.addButton(layerDownBtn);

		this.updateButtons();
	}

	private void finishEditing()
	{
		if(this.isEditing)
			NewCrewScreen.open();
		else
			this.onClose();
	}
	
	private void changeLayerIndex(boolean isUp)
	{
		int layerIndex = this.layerIndex;
		boolean canSwitch = false;

		if (this.layerType == LayerType.BACKGROUND)
		{
			JollyRogerElement currentElement = this.jollyRoger.getBackgrounds()[layerIndex];
			JollyRogerElement nextElement = null;
			JollyRogerElement prevElement = null;
 
			if (isUp && this.layerIndex >= 0 && this.layerIndex + 1 < this.jollyRoger.getBackgrounds().length)
			{
				nextElement = this.jollyRoger.getBackgrounds()[++layerIndex];
				canSwitch = true;
			}
			else if (!isUp && this.layerIndex - 1 >= 0 && this.layerIndex <= this.jollyRoger.getBackgrounds().length)
			{
				prevElement = this.jollyRoger.getBackgrounds()[--layerIndex];
				canSwitch = true;
			}

			if (currentElement != null && canSwitch)
			{
				this.jollyRoger.getBackgrounds()[layerIndex] = currentElement;

				if (isUp)
				{
					this.jollyRoger.getBackgrounds()[--layerIndex] = nextElement;
					this.layerIndex++;				
				}
				else if (!isUp)
				{
					this.jollyRoger.getBackgrounds()[++layerIndex] = prevElement;
					this.layerIndex--;
				}
				
				this.updateButtons();
				this.animationTime = 0;
				((NoTextureButton) this.selectedButton).select();
				this.selectedButton = (this.buttons.get(1 + this.layerIndex));
				((NoTextureButton) this.selectedButton).select();
			}
		}
		else if (this.layerType == LayerType.DETAIL)
		{
			JollyRogerElement currentElement = this.jollyRoger.getDetails()[layerIndex];
			JollyRogerElement nextElement = null;
			JollyRogerElement prevElement = null;
 
			if (isUp && this.layerIndex >= 0 && this.layerIndex + 1 < this.jollyRoger.getDetails().length)
			{
				nextElement = this.jollyRoger.getDetails()[++layerIndex];
				canSwitch = true;
			}
			else if (!isUp && this.layerIndex - 1 >= 0 && this.layerIndex <= this.jollyRoger.getDetails().length)
			{
				prevElement = this.jollyRoger.getDetails()[--layerIndex];
				canSwitch = true;
			}

			if (currentElement != null && canSwitch)
			{
				this.jollyRoger.getDetails()[layerIndex] = currentElement;

				if (isUp)
				{
					this.jollyRoger.getDetails()[--layerIndex] = nextElement;
					this.layerIndex++;				
				}
				else if (!isUp)
				{
					this.jollyRoger.getDetails()[++layerIndex] = prevElement;
					this.layerIndex--;
				}
				
				this.updateButtons();
				this.animationTime = 0;
				((NoTextureButton) this.selectedButton).select();
				this.selectedButton = (this.buttons.get(3 + this.layerIndex));
				((NoTextureButton) this.selectedButton).select();
			}
		}
	}

	public void changeColor(GuiSlider slider, String color)
	{
		if (!slider.isHovered())
			slider.dragging = false;

		JollyRogerElement element = this.getLayerElement();
		if (element == null)
			return;

		String currentColor = element.getColor();
		Color rgb = WyHelper.hexToRGB(currentColor);

		String hex = "#FFFFFF";

		if (color.equalsIgnoreCase("red"))
			hex = WyHelper.rgbToHex(slider.getValueInt(), rgb.getGreen(), rgb.getBlue());
		else if (color.equalsIgnoreCase("green"))
			hex = WyHelper.rgbToHex(rgb.getRed(), slider.getValueInt(), rgb.getBlue());
		else if (color.equalsIgnoreCase("blue"))
			hex = WyHelper.rgbToHex(rgb.getRed(), rgb.getGreen(), slider.getValueInt());

		element.setColor(hex);
	}

	public void moveIndex(Button btn, boolean toRight)
	{
		try
		{
			this.nextElementTry++;

			if (toRight)
				this.trueIndex += 1;
			else
				this.trueIndex -= 1;

			if (this.layerType == LayerType.BASE)
			{
				if (this.trueIndex >= this.allBases.size())
					this.trueIndex = -1;
				if (this.trueIndex < 0 && this.jollyRoger.getBase() == null)
					this.trueIndex = this.allBases.size() - 1;

				if (this.trueIndex >= 0 && this.trueIndex <= this.allBases.size())
					this.jollyRoger.setBase(this.allBases.get(this.trueIndex).get());
				else if (this.trueIndex <= 0 && this.jollyRoger.getBase().getTexture() != null)
					this.jollyRoger.setBase(null);

				for (int i = 0; i < this.jollyRoger.getBackgrounds().length; i++)
				{
					JollyRogerElement element = this.jollyRoger.getBackgrounds()[i];
					boolean hasElement = this.allBackgrounds.stream().anyMatch(elem -> elem != null && elem.get() != null && elem.get().equals(element) && !elem.get().canUse(this.player));
					if (hasElement)
						this.jollyRoger.getBackgrounds()[i] = null;
				}

				for (int i = 0; i < this.jollyRoger.getDetails().length; i++)
				{
					JollyRogerElement element = this.jollyRoger.getDetails()[i];
					boolean hasElement = this.allDetails.stream().anyMatch(elem -> elem != null && elem.get() != null && elem.get().equals(element) && !elem.get().canUse(this.player));
					if (hasElement)
						this.jollyRoger.getDetails()[i] = null;
				}
			}
			else if (this.layerType == LayerType.BACKGROUND)
			{
				if (this.trueIndex >= this.allBackgrounds.size())
					this.trueIndex = -1;
				if (this.trueIndex < 0 && this.jollyRoger.getBackgrounds()[this.layerIndex] == null)
					this.trueIndex = this.allBackgrounds.size() - 1;

				if (this.nextElementTry > this.allBackgrounds.size())
				{
					this.jollyRoger.getBackgrounds()[this.layerIndex] = null;
					this.trueIndex = -1;
					this.nextElementTry = 0;
					this.updateButtons();
					return;
				}

				if (this.trueIndex >= 0 && this.trueIndex <= this.allBackgrounds.size())
				{
					JollyRogerElement ogElem = this.allBackgrounds.get(this.trueIndex).get();
					for (int i = 0; i < this.jollyRoger.getBackgrounds().length; i++)
					{
						JollyRogerElement element = this.jollyRoger.getBackgrounds()[i];
						if (element != null && ogElem != null && ogElem.equals(element))
						{
							this.moveIndex(btn, toRight);
							return;
						}
					}
				}

				if (this.trueIndex >= 0 && this.trueIndex <= this.allBackgrounds.size())
					this.jollyRoger.getBackgrounds()[this.layerIndex] = this.allBackgrounds.get(this.trueIndex).get();
				else if (this.trueIndex <= 0 && this.jollyRoger.getBackgrounds()[this.layerIndex].getTexture() != null)
					this.jollyRoger.getBackgrounds()[this.layerIndex] = null;

				this.nextElementTry = 0;
			}
			else if (this.layerType == LayerType.DETAIL)
			{
				if (this.trueIndex >= this.allDetails.size())
					this.trueIndex = -1;
				if (this.trueIndex < 0 && this.trueIndex <= this.allDetails.size() && this.jollyRoger.getDetails()[this.layerIndex] == null)
					this.trueIndex = this.allDetails.size() - 1;

				if (this.nextElementTry >= this.allDetails.size())
				{
					this.jollyRoger.getDetails()[this.layerIndex] = null;
					this.trueIndex = -1;
					this.nextElementTry = 0;
					this.updateButtons();
					return;
				}

				if (this.trueIndex >= 0 && this.trueIndex <= this.allDetails.size())
				{
					JollyRogerElement ogElem = this.allDetails.get(this.trueIndex).get();
					for (int i = 0; i < this.jollyRoger.getDetails().length; i++)
					{
						JollyRogerElement element = this.jollyRoger.getDetails()[i];
						if (element != null && ogElem != null && ogElem.equals(element))
						{
							this.moveIndex(btn, toRight);
							return;
						}
					}
				}

				if (this.trueIndex >= 0 && this.trueIndex <= this.allDetails.size())
					this.jollyRoger.getDetails()[this.layerIndex] = this.allDetails.get(this.trueIndex).get();
				else if (this.trueIndex <= 0 && this.jollyRoger.getDetails()[this.layerIndex].getTexture() != null)
					this.jollyRoger.getDetails()[this.layerIndex] = null;

				this.nextElementTry = 0;
			}

			this.updateButtons();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void selectButton(Button btn)
	{
		if (!(btn instanceof NoTextureButton))
			return;

		if (this.selectedButton != null)
			((NoTextureButton) this.selectedButton).select();
		this.selectedButton = btn;
		((NoTextureButton) btn).select();
		this.animationTime = 0;

		boolean hasLayerSet = false;

		if (this.buttons.get(0) == btn)
		{
			this.trueIndex = this.findIndex(this.getListFromType(LayerType.BASE), this.jollyRoger.getBase(), this.player);
			this.layerType = LayerType.BASE;
			this.layerIndex = 0;

			hasLayerSet = true;
		}

		if (!hasLayerSet)
		{
			int j = 0;
			for (int i = 1; i < this.jollyRoger.getBackgrounds().length + 1; i++)
			{
				if (this.buttons.get(i) == btn)
				{
					this.trueIndex = this.findIndex(this.getListFromType(LayerType.BACKGROUND), this.jollyRoger.getBackgrounds()[j], this.player);
					this.layerType = LayerType.BACKGROUND;
					this.allBackgrounds = this.getTotalElementsForType(this.player, LayerType.BACKGROUND);
					this.layerIndex = j;

					hasLayerSet = true;
				}
				j++;
			}
		}

		if (!hasLayerSet)
		{
			int j = 0;
			for (int i = this.jollyRoger.getBackgrounds().length + 1; i < this.jollyRoger.getDetails().length + this.jollyRoger.getBackgrounds().length + 1; i++)
			{
				if (this.buttons.get(i) == btn)
				{
					this.trueIndex = this.findIndex(this.getListFromType(LayerType.DETAIL), this.jollyRoger.getDetails()[j], this.player);
					this.layerType = LayerType.DETAIL;
					this.allDetails = this.getTotalElementsForType(this.player, LayerType.DETAIL);
					this.layerIndex = j;

					hasLayerSet = true;
				}
				j++;
			}
		}

		this.updateButtons();
	}

	private void updateButtons()
	{
		JollyRogerElement element = this.getLayerElement();

		if(element == null)
		{
			// Hides the layer buttons
			this.buttons.get(this.buttons.size() - 1).visible = false;
			this.buttons.get(this.buttons.size() - 2).visible = false;
						
			// Resets the color sliders
			for (Widget widget : this.buttons)
			{
				if (widget instanceof GuiSlider)
				{
					widget.visible = false;
				}
			}
		}
		else
		{
			this.buttons.get(this.buttons.size() - 1).visible = true;
			this.buttons.get(this.buttons.size() - 2).visible = true;
			
			if(element == this.jollyRoger.getBase())
			{
				this.buttons.get(this.buttons.size() - 1).visible = false;
				this.buttons.get(this.buttons.size() - 2).visible = false;
			}
			
			if(!element.canBeColored())
			{
				for (Widget widget : this.buttons)
				{
					if (widget instanceof GuiSlider)
					{
						widget.visible = false;
					}
				}
			}
			else
			{
				for (Widget widget : this.buttons)
				{
					if (widget instanceof GuiSlider)
					{
						widget.visible = true;
					}
				}
				this.resetColorSliders(element);
			}
		}
	}

	private JollyRogerElement getLayerElement()
	{
		JollyRogerElement element = this.jollyRoger.getBase();

		if (this.layerType == LayerType.BASE)
			element = this.jollyRoger.getBase();
		else if (this.layerType == LayerType.BACKGROUND)
			element = this.jollyRoger.getBackgrounds()[this.layerIndex];
		else if (this.layerType == LayerType.DETAIL)
			element = this.jollyRoger.getDetails()[this.layerIndex];

		return element;
	}

	private void resetColorSliders(JollyRogerElement element)
	{
		if (element != null)
		{
			Color rgb = WyHelper.hexToRGB(element.getColor());
			this.redSlider.setValue(rgb.getRed());
			this.redSlider.updateSlider();
			this.greenSlider.setValue(rgb.getGreen());
			this.greenSlider.updateSlider();
			this.blueSlider.setValue(rgb.getBlue());
			this.blueSlider.updateSlider();
		}
		else
		{
			this.redSlider.setValue(255);
			this.redSlider.updateSlider();
			this.greenSlider.setValue(255);
			this.greenSlider.updateSlider();
			this.blueSlider.setValue(255);
			this.blueSlider.updateSlider();
		}
	}

	@Override
	public void onClose()
	{
		WyNetwork.sendToServer(new CSyncWorldDataPacket(this.worldData));
		super.onClose();
	}

	private int findIndex(List<RegistryObject<JollyRogerElement>> elements, JollyRogerElement element, PlayerEntity player)
	{
		for (int i = 0; i < elements.size(); i++)
		{
			if (elements.get(i).get().equals(element))
			{
				return i;
			}
		}

		return -1;
	}

	public List<RegistryObject<JollyRogerElement>> getListFromType(LayerType type)
	{
		if (type == LayerType.BASE)
			return this.allBases;
		else if (type == LayerType.BACKGROUND)
			return this.allBackgrounds;
		else if (type == LayerType.DETAIL)
			return this.allDetails;

		return this.allBases;
	}

	public List<RegistryObject<JollyRogerElement>> getTotalElementsForType(PlayerEntity player, LayerType type)
	{
		return this.allElements.stream().filter(reg -> reg.get().getLayerType() == type && reg.get().canUse(player)).collect(Collectors.toList());
	}

	public static void open(boolean isEditing)
	{
		Minecraft.getInstance().displayGuiScreen(new JollyRogerCreatorScreen(isEditing));
	}
}
