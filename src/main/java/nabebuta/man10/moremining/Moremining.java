package nabebuta.man10.moremining;


import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Moremining extends JavaPlugin implements Listener {
    private final Moremining javaplugin = this;
    private File datafile = new File(javaplugin.getDataFolder()+"/data.yml");
    FileConfiguration config = this.getConfig();
    List<String> list = new ArrayList();
    HashMap<Player,List<ItemStack>> ISPmap = new HashMap<>();
    //ブロック系のデータ用コンフィグ
    FileConfiguration dataconf = YamlConfiguration.loadConfiguration(datafile);
    MoreminingData md;
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
        getCommand("nabe_itemregister").setExecutor(this);
        //nullだったらキャスト...
        Bukkit.getConsoleSender().sendMessage("Moreminingは"+javaplugin+"です");
        Bukkit.getConsoleSender().sendMessage("こんにちは！"+dataconf.getString("Data.Message"));
        //md = new MoreminingData(this);
        //List<String> list = new ArrayList();
        list = (List) dataconf.getStringList("data.data");
        int cnflistsize = list.size();
        saveConfig();
        Timer tk = new Timer(this);
        tk.setListsize(cnflistsize);
        tk.setPlugin(javaplugin);
        new Timer(javaplugin).runTaskTimer(this,20,20);
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
        }else if(command.getName().equalsIgnoreCase("nabe_itemregister")){
            Player p = (Player) sender;
            showItemRegisterGUI(p);
            //処理をかく
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
                            /*reloadConfig();
                            World blw = bl.getWorld();
                            double blx = bl.getX();
                            double bly = bl.getY();
                            double blz = bl.getZ();
                            List<List<String>> list;
                            list = (List) dataconf.getList("Data.Block");
                            if(list==null){
                                String blws = String.valueOf(blw);
                                String blwx = String.valueOf(blx);
                                String blwy = String.valueOf(bly);
                                String blwz = String.valueOf(blz);
                                String insertlist[] = {blws,blwx,blwy,blwz};
                                List<List<String>> masterlist = new ArrayList<>();
                                masterlist.add(Arrays.asList(insertlist));
                                list = masterlist;
                                Bukkit.getConsoleSender().sendMessage("test:"+list);
                            }else {
                            List<String> insertlist = new ArrayList<>();
                            insertlist.add(String.valueOf(blw));
                            insertlist.add(String.valueOf(blx));
                            insertlist.add(String.valueOf(bly));
                            insertlist.add(String.valueOf(blz));
                            Bukkit.getConsoleSender().sendMessage("test:"+insertlist);
                            //insert
                            list.add(insertlist);
                            }
                            Bukkit.getConsoleSender().sendMessage("list:"+list);
                            */
                            showdisplayGUI(p,bl);
                            /*try{
                                dataconf.set("Data.Block",list);
                                dataconf.save(datafile);
                            }catch (IOException e){
                                e.printStackTrace();
                            }*/
                            //md.Locationrecorder(blw,blx,bly,blz);//この際mysqlにmoreminingというDB、effect_locatonというテーブル、をつくる

                    bl.getWorld().spawnParticle(
                            Particle.VILLAGER_HAPPY, // パーティクルの種類
                            bl, // 発生させる場所
                            50,// 発生させる数
                            0.5, // 散開させるXの範囲
                            0.5, // 散開させるYの範囲
                            0.5 // 散開させるZの範囲
                    );
                            Bukkit.getConsoleSender().sendMessage(String.valueOf(bl.getWorld()));
                        }
                        else if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                            return;
                        }
            }
        }
    }

    @EventHandler
    public void onLeftClick(BlockBreakEvent event){
        Player p = event.getPlayer();
        MainHand i = p.getMainHand();
        Block b = event.getBlock();
        PlayerInventory inventory = p.getInventory();
        String myDisplayName = "§6鍋蓋の不思議な棒";
        ItemStack nabebutastick = new ItemStack(Material.STICK, 1);
        ItemMeta im = nabebutastick.getItemMeta();
        im.setDisplayName(myDisplayName);
        //ItemMeta imname = (ItemMeta) p.getInventory().getItemInMainHand().getItemMeta();

        //String karabou = imname.getDisplayName();
        //アイテムが鍋蓋の棒か確認するため
        if (p.getInventory().getItemInMainHand().getType() != Material.STICK) {
            return;
        } else {
            ItemMeta imname = p.getInventory().getItemInMainHand().getItemMeta();
            String karabou = imname.getDisplayName();
            if (karabou.equals(myDisplayName)) {
                event.setCancelled(true);
                Location bl = b.getLocation();
                reloadConfig();
                World blw = bl.getWorld();
                double blx = bl.getX();
                double bly = bl.getY();
                double blz = bl.getZ();
                List searchlist = new ArrayList();
                searchlist.add(String.valueOf(blw));
                searchlist.add(String.valueOf(blx));
                searchlist.add(String.valueOf(bly));
                searchlist.add(String.valueOf(blz));
                List<List<String>> list;
                list = (List) dataconf.getList("Data.Block");
                if(list == null){
                    return;
                }
                else{
                    for(int f = 0;f < list.size();f++){
                        List<String> getlist = new ArrayList<>();
                        getlist = list.get(f);
                        try{
                            getlist.remove(4);
                        }catch (Exception e){
                        }
                        if(getlist.equals(searchlist)){
                            list.remove(f);
                            try {
                                dataconf.save(datafile);
                            }catch (Exception e){
                            }
                        }
                    }
                }
            }
        }
    }
    List<String> isSneak = new ArrayList<>();
    List<String> isnotSneak = new ArrayList<>();

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Player p = event.getPlayer();
        p.sendMessage("§6legendary");
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
            //最初にリストに入れる部分
            String answer = isinList(String.valueOf(p));
            if(answer=="insert"){
                isSneak.add(String.valueOf(p));
            }else if(answer=="isSneak"){
                int remove = isSneak.indexOf(String.valueOf(p));
                isSneak.remove(remove);
                isnotSneak.add(String.valueOf(p));
            }else if(answer=="isnotSneak") {
                int remove = isnotSneak.indexOf(String.valueOf(p));
                isnotSneak.remove(remove);
                isSneak.add(String.valueOf(p));
            }
        }
        else if (p.getInventory().getItemInMainHand().getType() == Material.AIR) {
            return;
        }
        else{
            return;
        }
    }

    @EventHandler
    public void onChating(AsyncPlayerChatEvent event) {
        List<Player> valuelist = new ArrayList<>();
        ISPmap.forEach((key,value) -> {
            valuelist.add(key);
        });
        for(int i = 0;i<valuelist.size();i++){
            if(event.getPlayer()==valuelist.get(i)){
                String message = event.getMessage();
                ItemRegisterCategory irc = new ItemRegisterCategory(this);
                boolean success = irc.CreateNewItemCategory(message, ISPmap.get(valuelist.get(i)).get(0), ISPmap.get(valuelist.get(i)).get(1));
                if(success == false){
                    event.getPlayer().sendMessage("§c既に同じ名前のカテゴリーが登録されています。もう一度行ってください");
                    ISPmap.remove(valuelist.get(i));
                    valuelist.remove(i);
                }else{
                    event.getPlayer().sendMessage("§aカテゴリーの登録が成功しました");
                    ISPmap.remove(valuelist.get(i));
                    valuelist.remove(i);
                }
                event.setCancelled(true);
            }
        }
    }

    //GUI部分のイベント
    @EventHandler
    public void onItemClickGUI(InventoryClickEvent e){
        HumanEntity p = e.getWhoClicked();
        ItemStack gotitem = e.getCurrentItem();
        Inventory inventory = e.getInventory();
        if(gotitem==null){
            return;
        }else {
            String gettitle = e.getView().getTitle();
            if (gettitle.equals("Please choose register item")) {
                if (e.getInventory().getItem(4) != null) {
                    gotitem = e.getInventory().getItem(4);
                    p.sendMessage(gotitem.getItemMeta().getDisplayName());
                    if (0 <= e.getRawSlot() && e.getRawSlot() <= 3) {
                        showItemRegisterGUISec((Player) p, gotitem);
                        e.setCancelled(true);
                    } else if (5 <= e.getRawSlot() && e.getRawSlot() <= 8) {
                        showItemRegisterGUISec((Player) p, gotitem);
                        e.setCancelled(true);
                    }
                }
            }
            else if (gettitle.equals("Please setup register item")) {
                switch (e.getRawSlot()) {
                    case 0:
                        e.setCancelled(true);
                        break;
                    case 1:
                        e.setCancelled(true);
                        for (int i = 1; i <= 5; i++) {
                            if (e.getInventory().getItem(i) != null) {
                                if (e.getInventory().getItem(i).getItemMeta().hasEnchants() == true) {
                                    ItemMeta removeEnchants = e.getInventory().getItem(i).getItemMeta();
                                    removeEnchants.removeEnchant(Enchantment.DAMAGE_ARTHROPODS);
                                    e.getInventory().getItem(i).setItemMeta(removeEnchants);
                                }
                            }
                        }
                        if (e.getInventory().getItem(1).getItemMeta() != null) {
                            ItemMeta setEnchants = e.getInventory().getItem(1).getItemMeta();
                            setEnchants.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 1, false);
                            setEnchants.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                            e.getInventory().getItem(1).setItemMeta(setEnchants);
                        }
                        break;
                    case 2:
                        e.setCancelled(true);
                        for (int i = 1; i <= 5; i++) {
                            if (e.getInventory().getItem(i).getItemMeta().hasEnchants() == true) {
                                ItemMeta removeEnchants = e.getInventory().getItem(i).getItemMeta();
                                removeEnchants.removeEnchant(Enchantment.DAMAGE_ARTHROPODS);
                                e.getInventory().getItem(i).setItemMeta(removeEnchants);
                            }
                        }
                        if (e.getInventory().getItem(2).getItemMeta() != null) {
                            ItemMeta setEnchants = e.getInventory().getItem(2).getItemMeta();
                            setEnchants.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 1, false);
                            setEnchants.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                            e.getInventory().getItem(2).setItemMeta(setEnchants);
                        }
                        break;
                    case 3:
                        e.setCancelled(true);
                        for (int i = 1; i <= 5; i++) {
                            if (e.getInventory().getItem(i).getItemMeta().hasEnchants() == true) {
                                ItemMeta removeEnchants = e.getInventory().getItem(i).getItemMeta();
                                removeEnchants.removeEnchant(Enchantment.DAMAGE_ARTHROPODS);
                                e.getInventory().getItem(i).setItemMeta(removeEnchants);
                            }
                        }
                        if (e.getInventory().getItem(3).getItemMeta() != null) {
                            ItemMeta setEnchants = e.getInventory().getItem(3).getItemMeta();
                            setEnchants.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 1, false);
                            setEnchants.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                            e.getInventory().getItem(3).setItemMeta(setEnchants);
                        }
                        break;
                    case 4:
                        e.setCancelled(true);
                        for (int i = 1; i <= 5; i++) {
                            if (e.getInventory().getItem(i).getItemMeta().hasEnchants() == true) {
                                ItemMeta removeEnchants = e.getInventory().getItem(i).getItemMeta();
                                removeEnchants.removeEnchant(Enchantment.DAMAGE_ARTHROPODS);
                                e.getInventory().getItem(i).setItemMeta(removeEnchants);
                            }
                        }
                        if (e.getInventory().getItem(4).getItemMeta() != null) {
                            ItemMeta setEnchants = e.getInventory().getItem(4).getItemMeta();
                            setEnchants.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 1, false);
                            setEnchants.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                            e.getInventory().getItem(4).setItemMeta(setEnchants);
                        }
                        break;
                    case 5:
                        e.setCancelled(true);
                        for (int i = 1; i <= 5; i++) {
                            if (e.getInventory().getItem(i).getItemMeta().hasEnchants() == true) {
                                ItemMeta removeEnchants = e.getInventory().getItem(i).getItemMeta();
                                removeEnchants.removeEnchant(Enchantment.DAMAGE_ARTHROPODS);
                                e.getInventory().getItem(i).setItemMeta(removeEnchants);
                            }
                        }
                        if (e.getInventory().getItem(5).getItemMeta() != null) {
                            ItemMeta setEnchants = e.getInventory().getItem(5).getItemMeta();
                            setEnchants.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 1, false);
                            setEnchants.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                            e.getInventory().getItem(5).setItemMeta(setEnchants);
                        }
                        break;
                    case 6:
                        e.setCancelled(true);
                        ItemRegisterCategory irc = new ItemRegisterCategory(this);
                        irc.ItemRegisterCategoryWindowReload(e,6);
                        break;
                    case 9:
                        e.setCancelled(true);
                        break;
                    case 48:
                        e.setCancelled(true);
                        break;
                    case 49:
                        e.setCancelled(true);
                        List<ItemStack> ISlist = new ArrayList<>();
                        ItemStack setitem = e.getInventory().getItem(0);
                        ISlist.add(setitem);
                        for(int i = 1;i<=5;i++){
                            if(e.getInventory().getItem(i).getItemMeta().hasEnchants()==true){
                                setitem=e.getInventory().getItem(i);
                                ISlist.add(setitem);
                                break;
                            }
                        }
                        if(ISlist.size()==2){
                            ISPmap.put((Player)p,ISlist);
                            p.closeInventory();
                            p.sendMessage("新しく作るカテゴリーの名前を入力してください");
                        } else if(ISlist.size()==1){
                            List<String> Lore = new ArrayList<String>();
                            Lore.add("§4Please choose rarelity!!!");
                            ItemMeta getMeta =  e.getInventory().getItem(49).getItemMeta();
                            getMeta.setLore(Lore);
                            e.getInventory().getItem(49).setItemMeta(getMeta);
                        }else {
                            p.openInventory(inventory);
                        }
                        break;
                    case 50:
                        e.setCancelled(true);
                        break;
                }
            }
            else {
                if (gotitem.hasItemMeta()) {
                    ItemMeta gotitemmeta = gotitem.getItemMeta();
                    if (gotitemmeta.hasDisplayName()) {
                        String gotitemDisplay = gotitemmeta.getDisplayName();
                        p.sendMessage(gettitle);
                        if (gotitemDisplay.equals("§6素晴らしいダイヤ")) {
                            p.sendMessage("おちんちんwww");
                            e.setCancelled(true);
                        } else if (gotitemDisplay.equals("§6§ka§r§f登録したいアイテムを入れてクリックしてください§6§ka")) {
                            ItemStack getItem = e.getClickedInventory().getItem(4);
                            showItemRegisterGUISec((Player) p, getItem);
                            e.setCancelled(true);
                        } else if (gotitemDisplay.equals("プリセットされたものを使う")) {
                            ItemStack getItem = e.getClickedInventory().getItem(0);
                            ItemMeta getItemMeta = getItem.getItemMeta();
                            Bukkit.getConsoleSender().sendMessage(String.valueOf(getItemMeta.getLore()));
                            String world = getItemMeta.getLore().get(1);
                            double getItemX = Double.parseDouble(getItemMeta.getLore().get(2));
                            double getItemY = Double.parseDouble(getItemMeta.getLore().get(3));
                            double getItemZ = Double.parseDouble(getItemMeta.getLore().get(4));
                            int index = world.indexOf("=");
                            int indexx = world.indexOf("}");
                            index = index + 1;
                            World newworld = Bukkit.getWorld(world.substring(index, indexx));
                            Location ishi = new Location(newworld, getItemX, getItemY, getItemZ);
                            showDisplayGUIforpreset((Player) p, ishi);
                            p.sendMessage("新しウインドウ");
                            e.setCancelled(true);
                        } else if (gotitemDisplay.equals("プリセット1")) {
                            ItemStack getItem = e.getClickedInventory().getItem(0);
                            ItemMeta getItemMeta = getItem.getItemMeta();
                            String world = getItemMeta.getLore().get(1);
                            double getItemX = Double.parseDouble(getItemMeta.getLore().get(2));
                            double getItemY = Double.parseDouble(getItemMeta.getLore().get(3));
                            double getItemZ = Double.parseDouble(getItemMeta.getLore().get(4));
                            int index = world.indexOf("=");
                            int indexx = world.indexOf("}");
                            index = index + 1;
                            World newworld = Bukkit.getWorld(world.substring(index, indexx));
                            Location ishi = new Location(newworld, getItemX, getItemY, getItemZ);
                            e.setCancelled(true);
                            showadvancedGUI((Player) p, ishi, "プリセット1");
                        } else if (gotitemDisplay.equals("プリセット2")) {
                            ItemStack getItem = e.getClickedInventory().getItem(0);
                            ItemMeta getItemMeta = getItem.getItemMeta();
                            String world = getItemMeta.getLore().get(1);
                            double getItemX = Double.parseDouble(getItemMeta.getLore().get(2));
                            double getItemY = Double.parseDouble(getItemMeta.getLore().get(3));
                            double getItemZ = Double.parseDouble(getItemMeta.getLore().get(4));
                            int index = world.indexOf("=");
                            int indexx = world.indexOf("}");
                            index = index + 1;
                            World newworld = Bukkit.getWorld(world.substring(index, indexx));
                            Location ishi = new Location(newworld, getItemX, getItemY, getItemZ);
                            e.setCancelled(true);
                            showadvancedGUI((Player) p, ishi, "プリセット2");
                        } else if (gotitemDisplay.equals("§aこれで登録！")) {
                            //登録
                            ItemStack getItem = e.getClickedInventory().getItem(0);
                            ItemMeta getItemMeta = getItem.getItemMeta();
                            reloadConfig();
                            String blw = getItemMeta.getLore().get(1);
                            double blx = Double.parseDouble(getItemMeta.getLore().get(2));
                            double bly = Double.parseDouble(getItemMeta.getLore().get(3));
                            double blz = Double.parseDouble(getItemMeta.getLore().get(4));
                            getItem = e.getClickedInventory().getItem(4);
                            String getstring = getItem.getItemMeta().getDisplayName();
                            List<List<String>> list;
                            list = (List) dataconf.getList("Data.Block");
                            if (list == null) {
                                String blws = String.valueOf(blw);
                                String blwx = String.valueOf(blx);
                                String blwy = String.valueOf(bly);
                                String blwz = String.valueOf(blz);
                                String insertlist[] = {blws, blwx, blwy, blwz, getstring};
                                List<List<String>> masterlist = new ArrayList<>();
                                masterlist.add(Arrays.asList(insertlist));
                                list = masterlist;
                                Bukkit.getConsoleSender().sendMessage("test:" + list);
                            } else {
                                List<String> insertlist = new ArrayList<>();
                                insertlist.add(String.valueOf(blw));
                                insertlist.add(String.valueOf(blx));
                                insertlist.add(String.valueOf(bly));
                                insertlist.add(String.valueOf(blz));
                                insertlist.add(String.valueOf(getstring));
                                Bukkit.getConsoleSender().sendMessage("test:" + insertlist);
                                //insert
                                list.add(insertlist);
                            }
                            try {
                                dataconf.set("Data.Block", list);
                                dataconf.save(datafile);
                            } catch (IOException exception) {
                                exception.printStackTrace();
                            }
                            e.setCancelled(true);
                            p.closeInventory();

                        } else if (gotitemDisplay.equals("頑なに動かない石(意思)")) {
                            e.setCancelled(true);
                        } else {
                            return;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public void showdisplayGUI(Player player,Location location){
        Inventory inv = Bukkit.createInventory(null, 9,"GUIName");
        ItemStack dia = new ItemStack(Material.DIAMOND,1);
        ItemMeta diameta = dia.getItemMeta();
        diameta.setDisplayName("§6素晴らしいダイヤ");
        dia.setItemMeta(diameta);
        inv.setItem(4, dia);


        ItemStack stone = new ItemStack(Material.STONE,1);
        ItemMeta stonemeta = stone.getItemMeta();
        List<String> stonemetalist = new ArrayList<>();
        stonemetalist.add("§6位置情報の記録です");
        World world = location.getWorld();
        stonemetalist.add(String.valueOf(world));
        double getx = location.getX();
        stonemetalist.add(String.valueOf(getx));
        double gety = location.getY();
        stonemetalist.add(String.valueOf(gety));
        double getz = location.getZ();
        stonemetalist.add(String.valueOf(getz));
        stonemeta.setLore(stonemetalist);
        stonemeta.setDisplayName("頑なに動かない石(意思)");
        stone.setItemMeta(stonemeta);
        inv.setItem(0,stone);


        ItemStack ironore = new ItemStack(Material.IRON_ORE,1);
        ItemMeta ironoremeta = ironore.getItemMeta();
        ironoremeta.setDisplayName("プリセットされたものを使う");
        ironore.setItemMeta(ironoremeta);
        inv.setItem(2,ironore);


        player.openInventory(inv);
    }

    public void showDisplayGUIforpreset(Player player,Location location){
        Inventory inv = Bukkit.createInventory(null, 9,"GUIName");
        ItemStack stone = new ItemStack(Material.STONE,1);
        ItemMeta stonemeta = stone.getItemMeta();
        List<String> stonemetalist = new ArrayList<>();
        stonemetalist.add("§6位置情報の記録です");
        World world = location.getWorld();
        stonemetalist.add(String.valueOf(world));
        double getx = location.getX();
        stonemetalist.add(String.valueOf(getx));
        double gety = location.getY();
        stonemetalist.add(String.valueOf(gety));
        double getz = location.getZ();
        stonemetalist.add(String.valueOf(getz));
        stonemeta.setLore(stonemetalist);
        stonemeta.setDisplayName("頑なに動かない石(意思)");
        stone.setItemMeta(stonemeta);
        inv.setItem(0,stone);

        ItemStack gold = new ItemStack(Material.GOLD_INGOT,1);
        ItemMeta goldmeta = gold.getItemMeta();
        goldmeta.setDisplayName("プリセット1");
        List<String> goldLore = new ArrayList<>();
        goldLore.add("テスト用のプリセット");
        goldmeta.setLore(goldLore);
        goldmeta.addEnchant(Enchantment.LUCK,1,false);
        goldmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        gold.setItemMeta(goldmeta);
        inv.setItem(1,gold);

        ItemStack iron = new ItemStack(Material.IRON_INGOT,1);
        ItemMeta ironmeta = iron.getItemMeta();
        ironmeta.setDisplayName("プリセット2");
        List<String> ironLore = new ArrayList<>();
        ironLore.add("テスト用のプリセット");
        ironmeta.setLore(ironLore);
        ironmeta.addEnchant(Enchantment.LUCK,1,false);
        ironmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        iron.setItemMeta(ironmeta);
        inv.setItem(2,iron);

        player.openInventory(inv);
    }


    public void showadvancedGUI(Player player,Location location,String preset){
        Inventory inv = Bukkit.createInventory(null, 9,"GUIName");
        ItemStack dia = new ItemStack(Material.DIAMOND,1);
        ItemMeta diameta = dia.getItemMeta();
        diameta.setDisplayName("§6素晴らしいダイヤ");
        dia.setItemMeta(diameta);
        inv.setItem(4, dia);


        ItemStack stone = new ItemStack(Material.STONE,1);
        ItemMeta stonemeta = stone.getItemMeta();
        List<String> stonemetalist = new ArrayList<>();
        stonemetalist.add("§6位置情報の記録です");
        World world = location.getWorld();
        stonemetalist.add(String.valueOf(world));
        double getx = location.getX();
        stonemetalist.add(String.valueOf(getx));
        double gety = location.getY();
        stonemetalist.add(String.valueOf(gety));
        double getz = location.getZ();
        stonemetalist.add(String.valueOf(getz));
        stonemeta.setLore(stonemetalist);
        stonemeta.setDisplayName("頑なに動かない石(意思)");
        stone.setItemMeta(stonemeta);
        inv.setItem(0,stone);


        ItemStack ironore = new ItemStack(Material.IRON_ORE,1);
        ItemMeta ironoremeta = ironore.getItemMeta();
        ironoremeta.setDisplayName("プリセットされたものを使う");
        ironore.setItemMeta(ironoremeta);
        inv.setItem(2,ironore);

        ItemStack doneitem = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta doneitemMeta = doneitem.getItemMeta();
        doneitemMeta.setDisplayName("§aこれで登録！");
        doneitem.setItemMeta(doneitemMeta);
        inv.setItem(8,doneitem);

        if(preset.equals("プリセット1")){
            ItemStack gold = new ItemStack(Material.GOLD_INGOT,1);
            ItemMeta goldmeta = gold.getItemMeta();
            goldmeta.setDisplayName("プリセット1");
            List<String> goldLore = new ArrayList<>();
            goldLore.add("テスト用のプリセット");
            goldmeta.setLore(goldLore);
            goldmeta.addEnchant(Enchantment.LUCK,1,false);
            goldmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            gold.setItemMeta(goldmeta);
            inv.setItem(4,gold);
        }else if(preset.equals("プリセット2")){
            ItemStack iron = new ItemStack(Material.IRON_INGOT,1);
            ItemMeta ironmeta = iron.getItemMeta();
            ironmeta.setDisplayName("プリセット2");
            List<String> ironLore = new ArrayList<>();
            ironLore.add("テスト用のプリセット");
            ironmeta.setLore(ironLore);
            ironmeta.addEnchant(Enchantment.LUCK,1,false);
            ironmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            iron.setItemMeta(ironmeta);
            inv.setItem(4,iron);
        }
        player.openInventory(inv);
    }

    public void showItemRegisterGUI(Player player){
        Inventory inv = Bukkit.createInventory(null,9,"Please choose register item");
        ItemStack tmpane = new ItemStack(Material.GREEN_STAINED_GLASS_PANE,1);
        ItemMeta tmpanemeta = tmpane.getItemMeta();
        tmpanemeta.setDisplayName("§6§ka§r登録したいアイテムを入れてクリックしてください§6§ka§r");
        tmpane.setItemMeta(tmpanemeta);
        for(int i = 0;i<9;i++){
            if(i==4){
                continue;
            }else{
                inv.setItem(i,tmpane);
            }
        }
        player.openInventory(inv);
    }

    public void showItemRegisterGUISec(Player player, ItemStack itemStack){
        Inventory inv = Bukkit.createInventory(null,54,"Please setup register item");
        ItemMeta gotMeta = itemStack.getItemMeta();
        if(gotMeta.hasLore()==true){
            List<String> metalore=gotMeta.getLore();
            metalore.add(0,"chose item for ItemRegister");
            gotMeta.setLore(metalore);
        }else{
            List<String> metalore = new ArrayList<>();
            metalore.add(0,"chose item for ItemRegister");
            gotMeta.setLore(metalore);
        }
        ///
        itemStack.setItemMeta(gotMeta);
        inv.setItem(0,itemStack);
        ItemStack setitem = new ItemStack(Material.STONE,1);
        ItemMeta setmeta = setitem.getItemMeta();
        setmeta.setDisplayName("§7common");
        setitem.setItemMeta(setmeta);
        inv.setItem(1,setitem);
        setitem = new ItemStack(Material.IRON_ORE,1);
        setmeta = setitem.getItemMeta();
        setmeta.setDisplayName("§9uncommon");
        setitem.setItemMeta(setmeta);
        inv.setItem(2,setitem);
        setitem = new ItemStack(Material.GOLD_ORE,1);
        setmeta = setitem.getItemMeta();
        setmeta.setDisplayName("§1rare");
        setitem.setItemMeta(setmeta);
        inv.setItem(3,setitem);
        setitem = new ItemStack(Material.DIAMOND_ORE,1);
        setmeta = setitem.getItemMeta();
        setmeta.setDisplayName("§5epic");
        setitem.setItemMeta(setmeta);
        inv.setItem(4,setitem);
        setitem = new ItemStack(Material.END_STONE,1);
        setmeta = setitem.getItemMeta();
        setmeta.setDisplayName("§6legendary");
        setitem.setItemMeta(setmeta);
        inv.setItem(5,setitem);
        setitem = new ItemStack(Material.BARRIER,1);
        setmeta = setitem.getItemMeta();
        setmeta.setDisplayName("Reload ItemCategory!!");
        setitem.setItemMeta(setmeta);
        inv.setItem(6,setitem);
        setitem = new ItemStack(Material.NETHER_STAR,1);
        setmeta.setDisplayName("§fMake a New Category!");
        setitem.setItemMeta(setmeta);
        inv.setItem(49,setitem);
        player.openInventory(inv);
        ///
    }

    public String isinList(String str){
        String answer = null;
        if(isSneak.indexOf(str)==-1) {
            if (isnotSneak.indexOf(str) == -1) {
                answer="insert";
                return answer;
            }
            else{
                answer="isnotSneak";
                return answer;
            }
        }else{
            answer="isSneak";
            return answer;
        }
    }
    public List getisSneak(){
        Bukkit.getConsoleSender().sendMessage("getisSneak:"+String.valueOf(this.isSneak));
        return this.isSneak;
    }
}

