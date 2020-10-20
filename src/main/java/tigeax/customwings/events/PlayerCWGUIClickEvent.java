package tigeax.customwings.events;

import tigeax.customwings.gui.CWGUIType;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import tigeax.customwings.CWPlayer;

/*
 * This Event will fire whenever a Player clicks an item in a CustomWings GUI
 */

public class PlayerCWGUIClickEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;

	private CWPlayer cwPlayer;
	private CWGUIType clickedCWGUI;
	private InventoryView invView;
	private ItemStack clickedItem;
	private int clickedSlot;

	@Override
	public HandlerList getHandlers() { return handlers; }

	public PlayerCWGUIClickEvent(CWPlayer cwPlayer, CWGUIType clickedCWGUIType, InventoryView invView, ItemStack clickedItem, int clickedSlot) {
		this.cancelled = false;
		
		this.cwPlayer = cwPlayer;
		this.invView = invView;
		this.clickedItem = clickedItem;
		this.clickedSlot = clickedSlot;
	}

	@Override
	public boolean isCancelled() { return cancelled; }

	@Override
	public void setCancelled(boolean cancel) { cancelled = cancel; }

	public CWPlayer getCWPlayerWhoClicked() { return cwPlayer; }

	public CWGUIType getClickedCWGUI() { return clickedCWGUI; }

	public InventoryView getInvView() { return invView; }

	public ItemStack getClickedItem() { return clickedItem; }

	public Integer getClickedSlot() { return clickedSlot; }
	
}
