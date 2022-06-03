package random.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import random.ScreenHandlers;

public class ItemStructurifierScreenHandler extends ScreenHandler {

	private final Inventory input;
	private final Inventory output;

	public ItemStructurifierScreenHandler(int syncId, PlayerInventory playerInventory) {
		super(ScreenHandlers.ITEM_STRUCTURIFIER_SCREEN_HANDLER, syncId);
		input = new SimpleInventory(1) {
			@Override
			public void markDirty() {
				super.markDirty();

			}
		};
		output = new CraftingResultInventory();

		addSlot(new Slot(input, 0, 20, 33));
		addSlot(new Slot(output, 1, 143, 33) {
			@Override
			public boolean canInsert(ItemStack stack) {
				return false;
			}

			@Override
			public void onTakeItem(PlayerEntity player, ItemStack stack) {
				stack.onCraft(player.world, player, stack.getCount());
//				output.unlockLastRecipe(player);
//				ItemStack itemStack = ItemStructurifierScreenHandler.this.inputSlot.takeStack(1);
//				if (!itemStack.isEmpty()) {
//					ItemStructurifierScreenHandler.this.populateResult();
//				}
				super.onTakeItem(player, stack);
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
		return input.canPlayerUse(player);
	}

	@Override
	public ItemStack transferSlot(PlayerEntity player, int index) {
		ItemStack newStack = ItemStack.EMPTY;
		final Slot slot = slots.get(index);
		if (slot.hasStack()) {
			final ItemStack originalStack = slot.getStack();
			newStack = originalStack.copy();
			if (index < input.size()) {
				if (!insertItem(originalStack, input.size(), slots.size(), true)) {
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
}
