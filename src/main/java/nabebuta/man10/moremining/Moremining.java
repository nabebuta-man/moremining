package nabebuta.man10.moremining;


import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Moremining extends JavaPlugin implements Listener {
    JavaPlugin jp = this;
    public MySQLManager data = new MySQLManager(this,"moremining");
    FileConfiguration config = this.getConfig();
    LinkTiEf lte = new LinkTiEf();
    Effloc efl = new Effloc();

    @Override
    public void onEnable() {
        // Plugin startup logic
        config.addDefault("nabeconfig", true);
        this.saveDefaultConfig();
        //loadConfigを変えた
        reloadConfig();
        //paper部分
        this.getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("Hello World!!");
        getCommand("nabestick").setExecutor(this);
        lte.setJp(this);
        Timer tk = new Timer();
        new Thread(tk).start();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("nabestick")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage("このコマンドはプレイヤーからしか動作しません");
                return true;
            }
            Player player = (Player) sender;
            PlayerInventory inventory = player.getInventory();

            String myDisplayName = "§6鍋蓋の不思議な棒";

            ItemStack nabebutastick = new ItemStack(Material.STICK, 1);
            ItemMeta im = nabebutastick.getItemMeta();
            im.setDisplayName(myDisplayName);
            nabebutastick.setItemMeta(im);
            List<String> lores = new ArrayList<String>();
            lores.add("これは採掘場所を決める棒です。");
            lores.add("これは2行目です。");
            im.setDisplayName(myDisplayName);
            im.setLore(lores);
            nabebutastick.setItemMeta(im);

            String myDisplayturuhashiName = "§l§2m§fa§dn§r10ツルハシ";
            ItemStack turuhashi = new ItemStack(Material.DIAMOND_PICKAXE, 1);
            ItemMeta imturuhashi = turuhashi.getItemMeta();
            imturuhashi.setDisplayName(myDisplayturuhashiName);
            turuhashi.setItemMeta(imturuhashi);
            List<String> loresturuhashi = new ArrayList<String>();
            loresturuhashi.add("これは採掘場で使えるツルハシです。");
            loresturuhashi.add("決して耐久値を減らさないで下さい");

            imturuhashi.setDisplayName(myDisplayturuhashiName);
            imturuhashi.setLore(loresturuhashi);
            inventory.addItem(nabebutastick);
            inventory.addItem(turuhashi);


            player.sendMessage("§a採掘場所を決める特別な棒を受け取りました");
            return true;
        }
        return true;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        MainHand i = p.getMainHand();
        Block b = event.getClickedBlock();
        PlayerInventory inventory = p.getInventory();
        String myDisplayName = "§6鍋蓋の不思議な棒";
        ItemStack nabebutastick = new ItemStack(Material.STICK, 1);
        ItemMeta im = nabebutastick.getItemMeta();
        im.setDisplayName(myDisplayName);
        //ItemMeta imname = (ItemMeta) p.getInventory().getItemInMainHand().getItemMeta();

        //String karabou = imname.getDisplayName();
        //アイテムが鍋蓋の棒か確認するため
        if(event.getHand() == EquipmentSlot.HAND){
            return;
        }
        if (p.getInventory().getItemInMainHand().getType() != Material.STICK) {
            return;
        } else {
            ItemMeta imname = p.getInventory().getItemInMainHand().getItemMeta();
            String karabou = imname.getDisplayName();
            if (karabou.equals(myDisplayName)) {
                        if (event.getAction() == Action.RIGHT_CLICK_BLOCK){
                            Location bl = b.getLocation();
                            World blw = bl.getWorld();
                            double blx = bl.getX();
                            double bly = bl.getY();
                            double blz = bl.getZ();
                            data.execute("insert into effect_location values ( '" + blw + "',"+blx+","+bly+","+blz+")");//この際mysqlにmoreminingというDB、effect_locatonというテーブル、

                    bl.getWorld().spawnParticle(
                            Particle.VILLAGER_HAPPY, // パーティクルの種類
                            bl, // 発生させる場所
                            50,// 発生させる数
                            0.5, // 散開させるXの範囲
                            0.5, // 散開させるYの範囲
                            0.5 // 散開させるZの範囲
                    );
                        }
                        else if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                            return;
                        }
            }
        }
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Player p = event.getPlayer();
        PlayerInventory inventory = p.getInventory();
        ItemStack are = new ItemStack(Material.DIAMOND, 1);
        //ツルハシ部分
        /*String myDisplayturuhashiName = "§l§2m§fa§d§r10ツルハシ";
        ItemStack turuhashi = new ItemStack(Material.DIAMOND_PICKAXE,1);
        ItemMeta imturuhashi = turuhashi.getItemMeta();
        imturuhashi.setDisplayName(myDisplayturuhashiName);
        turuhashi.setItemMeta(imturuhashi);
        List<String> lores = new ArrayList<String>();
        lores.add("これは採掘場で使えるツルハシです。");
        lores.add("決して耐久値を減らさないで下さい");*/
        //ItemMeta turuname = (ItemMeta) p.getInventory().getItemInMainHand().getItemMeta();
        //String dispName = turuname.getDisplayName();
        if (p.getInventory().getItemInMainHand().getType() != Material.AIR) {
            p.sendMessage("|ω・｀)ﾉ ﾔｧ");
        }
        else if (p.getInventory().getItemInMainHand().getType() == Material.AIR) {
            return;
        }
        else{
            return;
        }
        return;
    }
    public void particlefef(Location wherel,int amountp,double amountx,double amounty,double amountz){
        wherel.getWorld().spawnParticle(
                Particle.END_ROD, // パーティクルの種類
                wherel, // 発生させる場所
                amountp,// 発生させる数
                amountx, // 散開させるXの範囲
                amounty, // 散開させるYの範囲
                amountz // 散開させるZの範囲
        );
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
