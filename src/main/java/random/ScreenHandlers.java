package random;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import random.screen.ItemStructurifierScreenHandler;

public interface ScreenHandlers {

	ScreenHandlerType<ItemStructurifierScreenHandler> ITEM_STRUCTURIFIER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(Main.MOD_ID, "item_structurifier"), ItemStructurifierScreenHandler::new);
}
