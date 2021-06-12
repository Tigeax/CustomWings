package tigeax.customwings;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import tigeax.customwings.configuration.WingConfig;
import tigeax.customwings.editor.SettingType;
import tigeax.customwings.nms.NMSSupport;
import tigeax.customwings.util.Util;
import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.wing.Wing;

/*
 * Class made for every player interacting with the plugin
 */

public class CWPlayer {

	private final Player player;

	private Wing equippedWing;
	private boolean hideOtherPlayerWings;
	private String wingFilter;

	private SettingType waitingSetting;
	private Object waitingSettingInfo;
	private ItemMenu lastEditorMenu;

	private Location wingPreviewLocation = null;

	private long lastMove;

	public CWPlayer(UUID uuid) {

		player = Bukkit.getPlayer(uuid);

		this.equippedWing = null;
		this.hideOtherPlayerWings = false;
		this.wingFilter = "none";

		this.waitingSetting = null;
		this.waitingSettingInfo = null;
		this.lastEditorMenu = null;

		this.lastMove = Instant.now().getEpochSecond() - 1;
	}

	/**
	 * Own implmentaton to send a message to a player using
	 * {@link Util#sendMessage(CommandSender, String)}.
	 * 
	 * @param message Message to send
	 */
	public void sendMessage(String message) {
		Util.sendMessage(player, message);
	}

	public Player getPlayer() {
		return player;
	}

	public Wing getEquippedWing() {
		return equippedWing;
	}

	public boolean isPreviewingWing() {
		return (wingPreviewLocation != null);
	}

	public void setPreviewingWing(boolean previewing) {

		if (previewing) {
			Location loc = getPlayer().getLocation();
			loc.setYaw(NMSSupport.getBodyRotation(getPlayer()));
			wingPreviewLocation = loc;
		} else {
			wingPreviewLocation = null;
		}
	}

	/**
	 * Return the location to spawn a wing. Returns null is the player is not
	 * previewing a wing.
	 */
	public Location getPreviewWingLocation() {
		return wingPreviewLocation;
	}

	public boolean getHideOtherPlayerWings() {
		return hideOtherPlayerWings;
	}

	public void setHideOtherPlayerWings(boolean hideOtherPlayerWings) {
		this.hideOtherPlayerWings = hideOtherPlayerWings;
	}

	public ItemMenu getLastEditorMenu() {
		return lastEditorMenu;
	}

	public void setLastEditorMenu(ItemMenu itemMenu) {
		this.lastEditorMenu = itemMenu;
	}

	/**
	 * Calculate if the player is currently moving, based on when the player was
	 * last detected as moving
	 */
	public boolean isMoving() {
		Instant instant = Instant.now();
		long now = instant.getEpochSecond();
		return this.lastMove >= (now - 1);
	}

	public SettingType getWaitingSetting() {
		return waitingSetting;
	}

	public Object getWaitingSettingInfo() {
		return waitingSettingInfo;
	}

	public void setWaitingSetting(SettingType waitingSetting) {
		setWaitingSetting(waitingSetting, null);
	}

	public void setWaitingSetting(SettingType waitingSetting, Object waitingSettingInfo) {
		this.waitingSetting = waitingSetting;
		this.waitingSettingInfo = waitingSettingInfo;
	}

	public boolean hasPermissionForWing(Wing wing) {
		return getPlayer().hasPermission(wing.getPermission()) || getPlayer().hasPermission(("customwings.wing.*"));
	}

	public void setEquippedWing(Wing wing) {
		if (this.equippedWing == wing) {
			return;
		}

		// Remove the player from the old wing
		if (this.equippedWing != null) {
			setPreviewingWing(false); // Disable preview
			this.equippedWing.removePlayersWithWingActive(getPlayer());
		}

		this.equippedWing = wing;

		if (this.equippedWing != null) {
			this.equippedWing.addPlayersWithWingActive(getPlayer());
		}

	}

	public List<String> getWingMenuItemLore(Wing wing) {

		WingConfig wingConfig = wing.getConfig();

		if (getEquippedWing() == wing) {
			return wingConfig.getLoreWhenEquipped();
		}

		if (hasPermissionForWing(wing)) {
			return wingConfig.getLoreWhenUnequipped();
		}

		if (wingConfig.getPrice() == -1 || wingConfig.getPriceType() == null
				|| wingConfig.getPriceType().equalsIgnoreCase("none")) {
			return wingConfig.getLoreWhenNoPermission();
		}

		return wingConfig.getloreWhenCanBuy();

	}

	// Set the time when the player was last counted at moving
	public void setLastTimeMoving(long moveTimestamp) {
		this.lastMove = moveTimestamp;
	}

	public void closeInventory() {
		// Open an empty inventory and then close it to make sure they cannot shift
		// click items out of their inventory
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
