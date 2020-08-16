package tigeax.customwings.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import tigeax.customwings.gui.guis.Editor;
import tigeax.customwings.gui.guis.EditorMainSettings;
import tigeax.customwings.gui.guis.EditorSelectDouble;
import tigeax.customwings.gui.guis.EditorSelectInteger;
import tigeax.customwings.gui.guis.EditorSelectMainGUISize;
import tigeax.customwings.gui.guis.EditorSelectParticle;
import tigeax.customwings.gui.guis.EditorSelectSlot;
import tigeax.customwings.gui.guis.EditorWingParticleSettings;
import tigeax.customwings.gui.guis.EditorWingParticlesSelect;
import tigeax.customwings.gui.guis.EditorWingSettings;
import tigeax.customwings.gui.guis.WingSelect;
import tigeax.customwings.main.CWPlayer;
import tigeax.customwings.main.CustomWings;
import tigeax.customwings.main.Settings;
import tigeax.customwings.main.Wing;
import tigeax.customwings.main.WingParticle;

/*
 * Manager class for all the CustomWings GUI'ss
 */

public class CWGUIManager {
	
	private final Settings settings;

	private final WingSelect wingSelectGUI;
	private final Editor editorGUI;
	private final EditorMainSettings editorMainSettingsGUI;
	private final EditorWingSettings editorWingSettingsGUI;
	private final EditorWingParticlesSelect editorWingParticlesSelectGUI;
	private final EditorWingParticleSettings editorWingParticleSettingsGUI;
	private final EditorSelectMainGUISize editorSelectGuiSizeGUI;
	private final EditorSelectParticle editorSelectParticleGUI;
	private final EditorSelectSlot editorSelectSlotGUI;
	private final EditorSelectInteger editorSelectIntegerGUI;
	private final EditorSelectDouble editorSelectDoubleGUI;

	public CWGUIManager() {
		this.settings = CustomWings.getSettings();

		this.wingSelectGUI = new WingSelect();
		this.editorGUI = new Editor();
		this.editorMainSettingsGUI = new EditorMainSettings();
		this.editorWingSettingsGUI = new EditorWingSettings();
		this.editorWingParticlesSelectGUI = new EditorWingParticlesSelect();
		this.editorWingParticleSettingsGUI = new EditorWingParticleSettings();
		this.editorSelectGuiSizeGUI = new EditorSelectMainGUISize();
		this.editorSelectParticleGUI = new EditorSelectParticle();
		this.editorSelectSlotGUI = new EditorSelectSlot();
		this.editorSelectIntegerGUI = new EditorSelectInteger();
		this.editorSelectDoubleGUI = new EditorSelectDouble();
	}

	public void openGUI(CWPlayer cwPlayer, CWGUIType cwGUIType, Object info) {
		try {
			switch (cwGUIType) {
				case WINGSELECT:
					wingSelectGUI.open(cwPlayer);
					return;
				case EDITOR:
					editorGUI.open(cwPlayer);
					return;
				case EDITORMAINSETTINGS:
					editorMainSettingsGUI.open(cwPlayer);
					return;
				case EDITORWINGSETTINGS:
					editorWingSettingsGUI.open(cwPlayer, (Wing) info);
					return;
				case EDITORWINGPARITCLESSELECT:
					editorWingParticlesSelectGUI.open(cwPlayer, (Wing) info);
					return;
				case EDITORWINGPARTICLESETTINGS:
					editorWingParticleSettingsGUI.open(cwPlayer, (WingParticle) info);
					return;
				case EDITORSELECTGUISIZE:
					editorSelectGuiSizeGUI.open(cwPlayer);
					return;
				case EDITORSELECTPARTICLE:
					editorSelectParticleGUI.openPage1(cwPlayer);
					return;
				case EDITORSELECTSLOT:
					editorSelectSlotGUI.open(cwPlayer);
					return;
				case EDITORSELECTINTEGER:
					editorSelectIntegerGUI.open(cwPlayer, (Integer) info);
					return;
				case EDITORSELECTDOUBLE:
					editorSelectDoubleGUI.open(cwPlayer, (double) info);
					return;
				case LASTEDITORGUI:
					openLastEditorGUI(cwPlayer);
					return;
			}
		} catch (Exception e) {
			CustomWings.sendError(e);
			if (cwPlayer.getPlayer().hasPermission("customwings.editor")) {
				editorGUI.open(cwPlayer);
			}
		}
	}

	public CWGUIType getCWGUITypeByInvTitle(String invTitle) {

		if (invTitle.equals(settings.getMainGUIName())) 
			return CWGUIType.WINGSELECT;

		switch (parseEditorMenuName(invTitle)) {
			case "Main Settings":
				return CWGUIType.EDITORMAINSETTINGS;
			case "Wing Settings":
				return CWGUIType.EDITORWINGSETTINGS;
			case "Wing Particles":
				return CWGUIType.EDITORWINGPARITCLESSELECT;
			case "Particle Settings":
				return CWGUIType.EDITORWINGPARTICLESETTINGS;
			case "Main GUI Size":
				return CWGUIType.EDITORSELECTGUISIZE;
			case "Select Slot":
				return CWGUIType.EDITORSELECTSLOT;
			case "Set Integer":
				return CWGUIType.EDITORSELECTINTEGER;
			case "Set Double":
				return CWGUIType.EDITORSELECTDOUBLE;
			case "Select Particle":
				return CWGUIType.EDITORSELECTPARTICLE;
			default:
				return CWGUIType.EDITOR;
		}
	}

	public void CWGUIClick(CWPlayer cwPlayer, CWGUIType guiType, InventoryView invView, ItemStack clickedItem, Integer clickedSlot) {

		String itemName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());

		if (guiType == CWGUIType.EDITORWINGSETTINGS 
				|| guiType == CWGUIType.EDITORWINGPARITCLESSELECT
				|| guiType == CWGUIType.EDITORWINGPARTICLESETTINGS) {
			
			if (clickedSlot == 4) return;
		}
		
		Player player = cwPlayer.getPlayer();
		
		//Open an empty inventory and then close it to make sure they cannot shift click items out of their inventory
		Inventory emptyInv = Bukkit.createInventory(null, 54, "");
		player.openInventory(emptyInv);
		player.closeInventory();
		
		switch (guiType) {
			
			case WINGSELECT:
				wingSelectGUI.click(cwPlayer, clickedSlot);
				return;
			case EDITOR:
				editorGUI.click(cwPlayer, itemName, clickedSlot);
				return;
			case EDITORMAINSETTINGS:
				editorMainSettingsGUI.click(cwPlayer, itemName);
				return;
			case EDITORWINGSETTINGS:
				editorWingSettingsGUI.click(cwPlayer, itemName, getWingFromGUI(invView));
				return;
			case EDITORWINGPARITCLESSELECT:
				editorWingParticlesSelectGUI.click(cwPlayer, itemName, getWingFromGUI(invView));
				return;
			case EDITORWINGPARTICLESETTINGS:
				editorWingParticleSettingsGUI.click(cwPlayer, itemName, getWingParticleFromGUI(invView));
				return;
			case EDITORSELECTGUISIZE:
				editorSelectGuiSizeGUI.click(cwPlayer, itemName);
				return;
			case EDITORSELECTSLOT:
				editorSelectSlotGUI.click(cwPlayer, itemName);
				return;
			case EDITORSELECTINTEGER:
				editorSelectIntegerGUI.click(cwPlayer, invView.getTopInventory(), itemName, clickedSlot);
				return;
			case EDITORSELECTDOUBLE:
				editorSelectDoubleGUI.click(cwPlayer, invView.getTopInventory(), itemName, clickedSlot);
				return;
			case EDITORSELECTPARTICLE:
				editorSelectParticleGUI.click(cwPlayer, itemName, clickedSlot);
				return;
			default:
				return;
		}
	}

	private void openLastEditorGUI(CWPlayer cwPlayer) {
		InventoryView lastInvView = cwPlayer.getLastEditorInvView();

		if (lastInvView == null) {
			cwPlayer.openCWGUI(CWGUIType.EDITOR);
			return;
		}

		switch (getCWGUITypeByInvTitle(lastInvView.getTitle())) {

			case EDITORMAINSETTINGS:
				cwPlayer.openCWGUI(CWGUIType.EDITORMAINSETTINGS);
				return;
			case EDITORWINGSETTINGS:
				cwPlayer.openCWGUI(CWGUIType.EDITORWINGSETTINGS, getWingFromGUI(lastInvView));
				return;
			case EDITORWINGPARITCLESSELECT:
				cwPlayer.openCWGUI(CWGUIType.EDITORWINGPARITCLESSELECT, getWingFromGUI(lastInvView));
				return;
			case EDITORWINGPARTICLESETTINGS:
				cwPlayer.openCWGUI(CWGUIType.EDITORWINGPARTICLESETTINGS, getWingParticleFromGUI(lastInvView));
				return;
			default:
				cwPlayer.openCWGUI(CWGUIType.EDITOR);
		}
	}

	private String parseEditorMenuName(String name) {
		String menuName = ChatColor.stripColor(name);
		menuName = menuName.replace(ChatColor.stripColor(CustomWings.getSettings().getEditorGUIName()), "");
		menuName = menuName.replace(ChatColor.stripColor("Page 1"), "");
		menuName = menuName.replace(ChatColor.stripColor("Page 2"), "");
		menuName = menuName.replaceAll(" - ", "");
		return menuName;
	}

	private Wing getWingFromGUI(InventoryView invView) {
		String wingID = ChatColor.stripColor(invView.getTopInventory().getItem(4).getItemMeta().getDisplayName());
		return CustomWings.getWingByID(wingID);
	}

	private WingParticle getWingParticleFromGUI(InventoryView invView) {
		String[] IDs = ChatColor.stripColor(invView.getTopInventory().getItem(4).getItemMeta().getDisplayName()).split(" - ");
		return CustomWings.getWingByID(IDs[0]).getWingParticleByID(IDs[1]);
	}

	public static ItemStack getItem(Material material, String name) {
		return getItem(material, name, null);
	}

	public static ItemStack getItem(Material material, String name, Object loreInfo) {

		ItemStack item;

		if (material == null)
			item = new ItemStack(Material.GRASS_BLOCK);
		else
			item = new ItemStack(material);

		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		
		if (loreInfo != null) {
			itemMeta.setLore(parseLore("" + loreInfo));
		}
		
		item.setItemMeta(itemMeta);
		return item;
	}

	private static List<String> parseLore(String loreString) {
		List<String> uncoloredLore = Arrays.asList(loreString.split(", "));
		List<String> lore = new ArrayList<>();

		for (String line : uncoloredLore) {
			lore.add(ChatColor.translateAlternateColorCodes('&', "&f" + line));
		}

		return lore;
	}

}
