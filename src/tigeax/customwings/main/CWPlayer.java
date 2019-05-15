package tigeax.customwings.main;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import tigeax.customwings.editor.SettingType;
import tigeax.customwings.gui.CWGUIManager;
import tigeax.customwings.gui.CWGUIType;

/*
 * Class made for every player interacting with the plugin
 */

public class CWPlayer {

	private CustomWings plugin;
	private final CWGUIManager cwGUIManager;

	private final UUID uuid;

	private Wing equippedWing;
	private boolean hideOtherPlayerWings;

	private SettingType waitingSetting;
	private Object waitingSettingInfo;
	private InventoryView lastEditorInvView;

	private boolean isMoving;
	private BukkitTask movementChecker;

	public CWPlayer(UUID uuid, CustomWings plugin) {
		this.plugin = plugin;
		this.cwGUIManager = CustomWings.getCWGUIManager();

		this.uuid = uuid;

		this.equippedWing = null;
		this.hideOtherPlayerWings = false;

		this.waitingSetting = null;
		this.waitingSettingInfo = null;
		this.lastEditorInvView = null;

		this.isMoving = false;
		this.movementChecker = null;
	}

	public Player getPlayer() { return Bukkit.getPlayer(uuid); }
	
	public Wing getEquippedWing() { return equippedWing; }

	public boolean getHideOtherPlayerWings() { return hideOtherPlayerWings; }
	public void setHideOtherPlayerWings(boolean hideOtherPlayerWings) { this.hideOtherPlayerWings = hideOtherPlayerWings; }
	
	public InventoryView getLastEditorInvView() { return lastEditorInvView; }
	public void setLastEditorInvView(InventoryView invView) { this.lastEditorInvView = invView; }
	
	public boolean isMoving() { return isMoving; }
	
	public SettingType getWaitingSetting() { return waitingSetting; }
	
	public Object getWaitingSettingInfo() { return waitingSettingInfo; }

	public void setWaitingSetting(SettingType waitingSetting) {
		setWaitingSetting(waitingSetting, null);
	}

	public void setWaitingSetting(SettingType waitingSetting, Object waitingSettingInfo) {
		this.waitingSetting = waitingSetting;
		this.waitingSettingInfo = waitingSettingInfo;
	}

	public boolean hasPermissionForWing(Wing wing) {
		if (getPlayer().hasPermission("customwings.wing." + wing.getID()))
			return true;
		else
			return false;
	}

	public void openCWGUI(CWGUIType cwGUIType) {
		openCWGUI(cwGUIType, null);
	}

	public void openCWGUI(CWGUIType cwGUIType, Object info) {
		cwGUIManager.openGUI(this, cwGUIType, info);
	}

	public void setEquippedWing(Wing wing) {
		if (this.equippedWing == wing) { return; }

		// Remove the player from the old wing
		if (this.equippedWing != null) {
			this.equippedWing.removeFromPreview(getPlayer());
			this.equippedWing.removePlayersWithWingActive(getPlayer());
		}

		this.equippedWing = wing;

		if (this.equippedWing == null) {
			stopMovementChecker();
			return;
		} else {
			this.equippedWing.addPlayersWithWingActive(getPlayer());
			startMovementChecker();
		}

		return;
	}

	// Start checking if the player is moving
	public void startMovementChecker() {

		// If a movementChecker is already active, return
		if (movementChecker != null) {
			if (!movementChecker.isCancelled()) { return; }
		}

		// Start the runnable
		movementChecker = new BukkitRunnable() {

			Location lastLocation;
			Location currentLocation;

			public void run() {

				if (lastLocation == null) {
					lastLocation = getPlayer().getLocation();
					return;
				}

				currentLocation = getPlayer().getLocation();

				// If the player is moving more then 1 blocks per 0.5 second, the player is moving
				isMoving = lastLocation.distance(currentLocation) > 1 ? true : false; 

				lastLocation = currentLocation;
			}
		}.runTaskTimer(plugin, 0, 10); // Check this every .5 seconds
	}

	public void stopMovementChecker() {
		movementChecker.cancel();
	}

}
