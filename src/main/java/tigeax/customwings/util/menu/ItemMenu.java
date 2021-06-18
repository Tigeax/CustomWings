package tigeax.customwings.util.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import tigeax.customwings.CustomWings;
import tigeax.customwings.util.Util;

/**
 * A Menu controlled by ItemStacks in an Inventory.
 */
public class ItemMenu {
    
    private String name;
    private Rows size;
    private MenuItem[] items;
    private ItemMenu parent;

    /**
     * Creates an {@link ItemMenu}.
     *
     * @param name   The name of the inventory.
     * @param size   The {@link ItemMenu.Rows} of the inventory.
     * @param plugin The {@link org.bukkit.plugin.java.JavaPlugin} instance.
     * @param parent The ItemMenu's parent.
     */
    public ItemMenu(String name, Rows size, ItemMenu parent) {
        this.name = Util.parseChatColors(name);
        this.size = size;
        this.items = new MenuItem[size.size];
        this.parent = parent;
    }

    /**
     * Creates an {@link ItemMenu} with no parent.
     *
     * @param name   The name of the inventory.
     * @param size   The {@link ItemMenu.Rows} of the inventory.
     * @param plugin The Plugin instance.
     */
    public ItemMenu(String name, Rows size) {
        this(name, size, null);
    }

    /**
     * Gets the name of the {@link ItemMenu}.
     *
     * @return The {@link ItemMenu}'s name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the {@link ItemMenu.Rows} of the {@link ItemMenu}.
     *
     * @return The {@link ItemMenu}'s {@link ItemMenu.Rows}.
     */
    public Rows getSize() {
        return size;
    }

    public void setSize(Rows size) {
        this.size = size;

        MenuItem[] newItems = new MenuItem[size.size];

        for (int i = 0; i < items.length && i < newItems.length; i++) {
            newItems[i] = items[i];
        }

        items = newItems;
    }

    /**
     * Checks if the {@link ItemMenu} has a parent.
     *
     * @return True if the {@link ItemMenu} has a parent, else false.
     */
    public boolean hasParent() {
        return parent != null;
    }

    /**
     * Gets the parent of the {@link ItemMenu}.
     *
     * @return The {@link ItemMenu}'s parent.
     */
    public ItemMenu getParent() {
        return parent;
    }

    /**
     * Sets the parent of the {@link ItemMenu}.
     *
     * @param parent The {@link ItemMenu}'s parent.
     */
    public void setParent(ItemMenu parent) {
        this.parent = parent;
    }

    /**
     * Sets the {@link MenuItem} of a slot.
     *
     * @param position The slot position.
     * @param menuItem The {@link MenuItem}.
     * @return The {@link ItemMenu}.
     */
    public ItemMenu setItem(int position, MenuItem menuItem) {

        try {
            items[position] = menuItem;
        } catch (ArrayIndexOutOfBoundsException e) {
            CustomWings.getInstance().getLogger().warning("The item '" + menuItem.getDisplayName() + "' is placed outside the inventory, at position " + position);
            e.printStackTrace();
        }

        return this;
    }

    public void openPreviousPage(Player player) {
        // Do nothing by default
    }

    public void openNextPage(Player player) {
        // Do nothing by default
    }

    /**
     * Opens the {@link ItemMenu} for a player.
     *
     * @param player The player.
     */
    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(new MenuHolder(this, Bukkit.createInventory(player, size.getSize())), size.getSize(), name);
        apply(inventory, player);
        player.openInventory(inventory);
    }

    /**
     * Updates the {@link ItemMenu} for a player.
     *
     * @param player The player to update the {@link ItemMenu} for.
     */
    public void update(Player player) {

        if (player.getOpenInventory() == null) {
            return;
        }

        Inventory inventory = player.getOpenInventory().getTopInventory();

        if (inventory.getHolder() instanceof MenuHolder && ((MenuHolder) inventory.getHolder()).getMenu().equals(this)) {
            apply(inventory, player);
            player.updateInventory();
        }
    }

    /**
     * Applies the {@link ItemMenu} for a player to an Inventory.
     *
     * @param inventory The Inventory.
     * @param player    The Player.
     */
    private void apply(Inventory inventory, Player player) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                inventory.setItem(i, items[i].getFinalItem(player));
            } else {
                inventory.setItem(i, null);
            }
        }
    }

    public void clearItems() {
        items = new MenuItem[size.size];
    }

    public void reload() {
        // Do nothing by default
    }

    /**
     * Handles InventoryClickEvents for the {@link ItemMenu}.
     */
    public void onInventoryClick(InventoryClickEvent event) {

        if (event.getClick() != ClickType.LEFT) {
            return;
        }

        int slot = event.getRawSlot();

        if (slot >= 0 && slot < size.getSize() && items[slot] != null) {

            Player player = (Player) event.getWhoClicked();
            ItemClickEvent itemClickEvent = new ItemClickEvent(player, slot, this);

            items[slot].onItemClick(itemClickEvent);

            if (itemClickEvent.willUpdate()) {
                update(player);
                return;
            }
            
            if (itemClickEvent.willClose()) {
                player.closeInventory();
                return;
            }

            if (itemClickEvent.willGoBack() && hasParent()) {
                parent.open(player);
                return;
            }
        }

    }

    /**
     * Possible sizes of an {@link ItemMenu}.
     */
    public enum Rows {
        ONE(9), TWO(18), THREE(27), FOUR(36), FIVE(45), SIX(54);

        private final int size;

        private Rows(int size) {
            this.size = size;
        }

        /**
         * Gets the {@link ItemMenu.Rows}'s amount of slots.
         *
         * @return The amount of slots.
         */
        public int getSize() {
            return size;
        }

        /**
         * Gets the required {@link ItemMenu.Rows} for an amount of slots.
         *
         * @param slots The amount of slots.
         * @return The required {@link ItemMenu.Rows}.
         */
        public static Rows fit(int slots) {
            if (slots < 10) {
                return ONE;
            } else if (slots < 19) {
                return TWO;
            } else if (slots < 28) {
                return THREE;
            } else if (slots < 37) {
                return FOUR;
            } else if (slots < 46) {
                return FIVE;
            } else {
                return SIX;
            }
        }
    }
}
