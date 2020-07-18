package main.java.tigeax.customwings.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import main.java.tigeax.customwings.main.CWPlayer;
import main.java.tigeax.customwings.main.Wing;

/*
 * This Event is fired whenever a player equips a wing trough the selectWing GUI
 * wing can be null and means the player removed their wing
 */

public class PlayerEquipWingEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;

	private CWPlayer cwPlayer;
	private Wing wing;

	@Override
	public HandlerList getHandlers() { return handlers; }

	public PlayerEquipWingEvent(CWPlayer cwPlayer, Wing wing) {
		this.cancelled = false;
		
		this.cwPlayer = cwPlayer;
		this.wing = wing;
	}

	@Override
	public boolean isCancelled() { return cancelled; }

	@Override
	public void setCancelled(boolean cancel) { cancelled = cancel; }

	public CWPlayer getCWPlayerWhoClicked() { return cwPlayer; }

	public Wing getWing() { return wing; }
	
}
