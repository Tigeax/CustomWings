package tigeax.customwings.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.Settings;
import tigeax.customwings.wings.Wing;
import tigeax.customwings.wings.WingParticle;

/*
 * Manager class for all the CustomWings GUI'ss
 */

public class CWGUIManager {
	
	private Settings settings;
	private WingSelect wingSelectGUI;
	private Editor editorGUI;
	private EditorMainSettings editorMainSettingsGUI;
	private EditorWingSettings editorWingSettingsGUI;
	private EditorWingParticlesSelect editorWingParticlesSelectGUI;
	private EditorWingParticleSettings editorWingParticleSettingsGUI;
	private EditorSelectMainGUISize editorSelectGuiSizeGUI;
	private EditorSelectParticle editorSelectParticleGUI;
	private EditorSelectSlot editorSelectSlotGUI;
	private EditorSelectInteger editorSelectIntegerGUI;
	private EditorSelectDouble editorSelectDoubleGUI;


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
					wingSelectGUI.open(cwPlayer, 0);
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
			System.out.println(e);
			cwPlayer.openCWGUI(CWGUIType.EDITOR);
		}
	}

	public CWGUIType getCWGUITypeByInvTitle(String invTitle) {

		if (invTitle.equals(settings.getMainGUIName())) 
			return CWGUIType.WINGSELECT;

		String parsedInvName = parseEditorMenuName(invTitle);

		switch (parsedInvName) {

			case "":
				return CWGUIType.EDITOR;
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
			case "Select Particle":
				return CWGUIType.EDITORSELECTPARTICLE;
			case "Select Slot":
				return CWGUIType.EDITORSELECTSLOT;
			case "Set Integer":
				return CWGUIType.EDITORSELECTINTEGER;
			case "Set Double":
				return CWGUIType.EDITORSELECTDOUBLE;
		}
		return null;
	}

	public void CWGUIClick(CWPlayer cwPlayer, CWGUIType guiType, InventoryView invView, ItemStack clickedItem, Integer clickedSlot) {

		String itemName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());

		if (guiType == CWGUIType.EDITORWINGSETTINGS 
				|| guiType == CWGUIType.EDITORWINGPARITCLESSELECT
				|| guiType == CWGUIType.EDITORWINGPARTICLESETTINGS) {
			
			if (clickedSlot == 4) return;
		}
		
		switch (guiType) {
			
			case WINGSELECT:
				wingSelectGUI.click(cwPlayer, clickedSlot, clickedItem);
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

		try {

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

		} catch (Exception e) {
			System.out.println(e);
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
		Wing wing = CustomWings.getWingByID(wingID);
		return wing;
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
