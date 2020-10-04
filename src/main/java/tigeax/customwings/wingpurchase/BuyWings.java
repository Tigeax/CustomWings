package tigeax.customwings.wingpurchase;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tigeax.customwings.CustomWings;
import tigeax.customwings.wings.Wing;

public class BuyWings {

    public static boolean buyWing(Wing wing, Player player) {

        String priceType = wing.getPriceType();

        try {
            if (priceType.equalsIgnoreCase("none") || wing.getWingPrice() == -1) {
                player.sendMessage(CustomWings.getMessages().getNoPermissionEquipWing(wing));
                return false;
            }

            if (priceType.equalsIgnoreCase("token")) {
                if (CustomWings.getPlugin(CustomWings.class).getServer().getPluginManager().getPlugin("TokenManager") != null) {
                    if (BuyTokenManager.makePayment(wing.getWingPrice(), player)) {
                        CustomWings.getPermissions().playerAdd(null, player, "customwings.wing."+wing.getID().toLowerCase());
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',wing.getBuyMessage()));
                    } else {
                        player.sendMessage(CustomWings.getMessages().getCannotAffordWing(wing));
                    }
                    return true;
                }
                return false;
            } else if (priceType.equalsIgnoreCase("economy")) {
                double playerbal = CustomWings.getEconomy().getBalance(player);
                if (playerbal >= wing.getWingPrice()) {
                    CustomWings.getEconomy().withdrawPlayer(player, wing.getWingPrice() );
                    CustomWings.getPermissions().playerAdd(null, player, "customwings.wing." + wing.getID().toLowerCase());
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',wing.getBuyMessage()));
                } else {
                    player.sendMessage(CustomWings.getMessages().getCannotAffordWing(wing));
                }
                return true;
            }
            return false;
        } catch (NullPointerException e) {
            return false;
        }


    }

}
