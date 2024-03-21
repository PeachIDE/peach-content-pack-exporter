package com.github.mouse0w0.pcpe.gui;

import com.github.mouse0w0.pcpe.PCPE;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = PCPE.MOD_ID)
public class GuiListener {

    private static final int EXPORT_BUTTON_ID = 0xE0202;

    @SubscribeEvent
    public static void postInitGui(GuiScreenEvent.InitGuiEvent.Post event) {
        GuiScreen gui = event.getGui();
        if (gui instanceof GuiMainMenu || gui instanceof GuiIngameMenu) {
            event.getButtonList().add(new GuiButton(EXPORT_BUTTON_ID, 0, 0, 60, 20, I18n.format("pcpe.gui.export")));
        }
    }

    @SubscribeEvent
    public static void onButtonPressed(GuiScreenEvent.ActionPerformedEvent.Post event) {
        if (event.getButton().id == EXPORT_BUTTON_ID) {
            new GuiExport().setVisible(true);
        }
    }
}
