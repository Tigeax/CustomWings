package tigeax.customwings;

import java.time.Instant;
import java.util.UUID;
import tigeax.customwings.editor.SettingType;
import tigeax.customwings.gui.CWGUIManager;
import tigeax.customwings.gui.CWGUIType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import tigeax.customwings.wings.Wing;

/*
 * Class made for every player interacting with the plugin
 */

public class CWPlayer {

	private final CWGUIManager cwGUIManager;

	private final Player player;

	private Wing equippedWing;
	private boolean hideOtherPlayerWings;
	private String wingFilter;

	private SettingType waitingSetting;
	private Object waitingSettingInfo;
	private InventoryView lastEditorInvView;

	private long lastMove;

	public CWPlayer(UUID uuid) {

		this.cwGUIManager = CustomWings.getCWGUIManager();

		this.player = Bukkit.getPlayer(uuid);

		this.equippedWing = null;
		this.hideOtherPlayerWings = false;
		this.wingFilter = "none";

		this.waitingSetting = null;
		this.waitingSettingInfo = null;
		this.lastEditorInvView = null;

		this.lastMove = 0;
	}

	public Player getPlayer() { return player; }
	
	public Wing getEquippedWing() { return equippedWing; }

	public boolean getHideOtherPlayerWings() { return hideOtherPlayerWings; }
	
	public void setHideOtherPlayerWings(boolean hideOtherPlayerWings) {
		this.hideOtherPlayerWings = hideOtherPlayerWings;
		if (hideOtherPlayerWings) {
			this.getPlayer().sendMessage(CustomWings.getMessages().getSeeOtherPlayersWingsON());
		} else {
			this.getPlayer().sendMessage(CustomWings.getMessages().getSeeOtherPlayersWingsOFF());
		}
	}
	
	public InventoryView getLastEditorInvView() { return lastEditorInvView; }
	public void setLastEditorInvView(InventoryView invView) { this.lastEditorInvView = invView; }

	// Checks if player is moving by comparing current timestamp to the timestamp
	// of last player movement.
	public boolean isMoving() {
		Instant instant = Instant.now();
		long now = instant.getEpochSecond();
		long milli = instant.getNano();
		now *= 1000000000L;
		now += milli;
		return now < lastMove+49000000;
	}
	
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
		return getPlayer().hasPermission("customwings.wing." + wing.getID()) || getPlayer().hasPermission(("customwings.wing.*"));
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

		if (this.equippedWing != null) {
			this.equippedWing.addPlayersWithWingActive(getPlayer());
		}

	}

	public void setMoving(long moveTimestamp) {
		this.lastMove = moveTimestamp;
	}
	
	public void closeInventory() {
		//Open an empty inventory and then close it to make sure they cannot shift click items out of their inventory
		Inventory emptyInv = Bukkit.createInventory(null, 54, "");
		this.getPlayer().openInventory(emptyInv);
		this.getPlayer().closeInventory();
	}

	public String getWingFilter() {
		return wingFilter;
	}

	public void setWingFilter(String filter) {
		wingFilter = filter;
	}

}
