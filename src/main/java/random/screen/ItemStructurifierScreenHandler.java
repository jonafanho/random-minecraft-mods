package random.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import random.Main;
import random.ScreenHandlers;

public class ItemStructurifierScreenHandler extends ScreenHandler {

	private final ScreenHandlerContext context;
	private final Inventory input;
	private final Inventory output;
	private final Slot inputSlot;
	private final Slot outputSlot;

	public ItemStructurifierScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
	}

	public ItemStructurifierScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(ScreenHandlers.ITEM_STRUCTURIFIER_SCREEN_HANDLER, syncId);
		this.context = context;
		input = new SimpleInventory(1);
		output = new CraftingResultInventory();

		inputSlot = addSlot(new Slot(input, 0, 20, 33));
		outputSlot = addSlot(new Slot(output, 1, 143, 33) {
			@Override
			public boolean canInsert(ItemStack stack) {
				return false;
			}
		});
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for (int i = 0; i < 9; ++i) {
			addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return input.canPlayerUse(player) || output.canPlayerUse(player);
	}

	@Override
	public ItemStack transferSlot(PlayerEntity player, int index) {
		ItemStack newStack = ItemStack.EMPTY;
		final Slot slot = slots.get(index);
		if (slot.hasStack()) {
			final ItemStack originalStack = slot.getStack();
			newStack = originalStack.copy();
			if (index < 2) {
				if (!insertItem(originalStack, 2, slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!insertItem(originalStack, 0, input.size(), false)) {
				return ItemStack.EMPTY;
			}

			if (originalStack.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}

		return newStack;
	}

	@Override
	public void close(PlayerEntity player) {
		super.close(player);
		output.removeStack(1);
		context.run((world, pos) -> dropInventory(player, input));
	}

	public void generate() {
		final Item inputItem = inputSlot.getStack().getItem();
		if (Main.RECIPES.containsKey(inputItem)) {
			outputSlot.setStack(new ItemStack(Main.RECIPES.get(inputItem)));
			inputSlot.getStack().decrement(1);
		} else {
			outputSlot.setStack(ItemStack.EMPTY);
		}
		sendContentUpdates();
	}
}
