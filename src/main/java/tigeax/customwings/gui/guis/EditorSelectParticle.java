package tigeax.customwings.gui.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import tigeax.customwings.editor.EditorConfigManager;
import tigeax.customwings.gui.CWGUIManager;
import tigeax.customwings.gui.CWGUIType;
import tigeax.customwings.gui.ParticleItem;
import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.Settings;

import java.util.ArrayList;

public class EditorSelectParticle {

	Settings settings;
	EditorConfigManager editorConfigManager;

	ArrayList<ItemStack> page1Items;
	ArrayList<ItemStack> page2Items;

	public EditorSelectParticle() {
		settings = CustomWings.getSettings();
		editorConfigManager = CustomWings.getEditorConfigManager();

		page1Items = new ArrayList<>();
		page2Items = new ArrayList<>();

		int counter = 0;
		for (ParticleItem particleItem : ParticleItem.values()) {

			try {
				Particle.valueOf(particleItem.toString()); //Check if the particle exists
				ItemStack item = CWGUIManager.getItem(particleItem.getMaterial(), "&f" + particleItem.getName());

				if (counter <= 44)
					page1Items.add(item);
				else
					page2Items.add(item);

				counter++;

			} catch(Exception e) {
				continue;
			}
		}
	}

	public void openPage1(CWPlayer cwPlayer) {

		String guiName = settings.getEditorGUIName() + " - Select Particle - Page 1";

		Inventory gui = Bukkit.createInventory(null, 54, guiName);

		int slot = 0;
		for (ItemStack item : page1Items) {
			gui.setItem(slot, item);
			slot++;
		}

		gui.setItem(50, CWGUIManager.getItem(Material.ARROW, "&2Next Page"));
		gui.setItem(53, CWGUIManager.getItem(Material.RED_WOOL, "&4Cancel"));

		cwPlayer.getPlayer().openInventory(gui);
	}

	public void openPage2(CWPlayer cwPlayer) {

		String guiName = settings.getEditorGUIName() + " - Select Particle - Page 2";

		Inventory gui = Bukkit.createInventory(null, 54, guiName);

		int slot = 0;
		for (ItemStack item : page2Items) {
			gui.setItem(slot, item);
			slot++;
		}

		gui.setItem(48, CWGUIManager.getItem(Material.ARROW, "&3Previous Page"));
		gui.setItem(53, CWGUIManager.getItem(Material.RED_WOOL, "&4Cancel"));

		cwPlayer.getPlayer().openInventory(gui);
	}

	public void click(CWPlayer cwPlayer, String itemName, Integer clickedSlot) {

		switch (itemName) {
			case "Cancel":
				cwPlayer.setWaitingSetting(null);
				cwPlayer.openCWGUI(CWGUIType.LASTEDITORGUI);
				return;
			case "Next Page":
				openPage2(cwPlayer);
				return;
			case "Previous Page":
				openPage1(cwPlayer);
				return;
		}

		editorConfigManager.setSetting(cwPlayer.getWaitingSetting(), ParticleItem.getParticleByName(itemName), cwPlayer.getWaitingSettingInfo());
		cwPlayer.setWaitingSetting(null);
		cwPlayer.openCWGUI(CWGUIType.LASTEDITORGUI);
	}
}
