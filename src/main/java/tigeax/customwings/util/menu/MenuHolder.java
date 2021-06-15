package tigeax.customwings.util.menu;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * Allows you to set the {@link ItemMenu} that created the Inventory as the Inventory's holder.
 */
public class MenuHolder implements InventoryHolder {
    private ItemMenu menu;
    private Inventory inventory; 

    public MenuHolder(ItemMenu menu, Inventory inventory) {
        this.menu = menu;
        this.inventory = inventory;
    }

    /**
     * Gets the {@link ItemMenu} holding the Inventory.
     *
     * @return The {@link ItemMenu} holding the Inventory.
     */
    public ItemMenu getMenu() {
        return menu;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
