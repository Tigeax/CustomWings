package tigeax.customwings;

import java.time.Instant;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import tigeax.customwings.configuration.WingConfig;
import tigeax.customwings.configuration.settings.Setting;
import tigeax.customwings.nms.NMSSupport;
import tigeax.customwings.util.Util;
import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.wing.Wing;

/*
 * Class made for every player interacting with the plugin
 */

public class CWPlayer {

	private static final CustomWings plugin = CustomWings.getInstance();

	private final Player player;

	private boolean hideOtherPlayerWings;
	private String wingFilter;

	private Setting waitingSetting;

	private ItemMenu lastEditorMenu;

	private Location wingPreviewLocation;

	private long lastMove;

	public CWPlayer(Player player) {

		this.player = player;

		this.hideOtherPlayerWings = false;
		this.wingFilter = "noFilter"; // Either 'noFilter', 'ownedWings', 'unownedWings'

		this.wingPreviewLocation = null;
		this.waitingSetting = null;
		this.lastEditorMenu = null;

		this.lastMove = Instant.now().getEpochSecond() - 1;
	}

	public Player getPlayer() {
		return player;
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

	/**
	 * Get the wing the player has equpped. Returns null if no wing is equipped.
	 * 
	 * @return Wing or null
	 */
	public Wing getEquippedWing() {

		for (Wing wing : plugin.getWings()) {
			if (wing.doesPlayerHaveWingEquipped(this)) {
				return wing;
			}
		}

		return null;

	}

	public void setEquippedWing(Wing wing) {

		Wing currentWing = getEquippedWing();

		// Check if the player already has the wing equipped
		if (currentWing == wing) {
			return;
		}

		// Remove the player from the old wing
		if (currentWing != null) {
			currentWing.removePlayer(this);
		}

		// Add to the new wing, if it is not null
		if (wing != null) {
			wing.addPlayer(this);
		}

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

	public Setting getWaitingSetting() {
		return waitingSetting;
	}

	public void setWaitingSetting(Setting waitingSetting) {
		this.waitingSetting = waitingSetting;
	}

	public boolean hasPermissionForWing(Wing wing) {
		return getPlayer().hasPermission(wing.getPermission()) || getPlayer().hasPermission(("customwings.wing.*"));
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

	public void cycleWingFilter() {

		if (wingFilter.equals("noFilter")) {
			wingFilter = "ownedWings";
			return;
		}

		if (wingFilter.equals("ownedWings")) {
			wingFilter = "unownedWings";
			return;
		}

		if (wingFilter.equals("unownedWings")) {
			wingFilter = "noFilter";
			return;
		}

	}

    public void delete() {
		setEquippedWing(null);
    }

}
