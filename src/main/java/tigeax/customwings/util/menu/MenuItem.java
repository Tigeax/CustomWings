package tigeax.customwings.util.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import tigeax.customwings.util.Util;

import java.util.Arrays;
import java.util.List;

/**
 * An Item inside an {@link ninja.amp.ampmenus.menus.ItemMenu}.
 */
public class MenuItem {
    private String displayName;
    private Material material;
    private int amount;
    private List<String> lore;

    public MenuItem() {
        displayName = "";
        material = Material.BARRIER;
        amount = 1;
        lore = Arrays.asList();
    }

    // Display name
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = Util.parseChatColors(displayName);
    }

    // Material
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    // Amount
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    // Lore
    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    /**
     * Gets the ItemStack to be shown to the player.
     *
     * @param player The player.
     * @return The final icon.
     */
    public ItemStack getFinalItem(Player player) {
        return setNameAndLore(new ItemStack(material, amount), getDisplayName(), getLore());
    }

    /**
     * Called when the MenuItem is clicked.
     *
     * @param event The {@link ItemClickEvent}.
     */
    public void onItemClick(ItemClickEvent event) {
        // Do nothing by default
    }

    /**
     * Sets the display name and lore of an ItemStack.
     *
     * @param itemStack   The ItemStack.
     * @param displayName The display name.
     * @param lore        The lore.
     * @return The ItemStack.
     */
    public static ItemStack setNameAndLore(ItemStack itemStack, String displayName, List<String> lore) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
