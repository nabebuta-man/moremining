package nabebuta.man10.moremining;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Timer extends BukkitRunnable {
    boolean dotokei = true;
    Moremining plugin;
    File datafile;
    FileConfiguration dataconf;
    int listsize = 0;
    int i = 1;
    Effloc efl = new Effloc();
    Timer(Moremining plugin){
        this.plugin = plugin;
        //efl.setJavaPlugin(plugin);
    }

    HashMap<Integer,List<String>> sneakmap = new HashMap<>();

    @Override
    public void run() {
        //パーティクル部分
        Bukkit.getConsoleSender().sendMessage("Hello!");
                try {
                    datafile = new File(plugin.getDataFolder()+"/data.yml");
                    dataconf = YamlConfiguration.loadConfiguration(datafile);
                    List<List<String>> list = (List<List<String>>)dataconf.getList("Data.Block");
                    listsize = list.size();
                    for (i = 0;i < listsize; i++) {
                        efl.setInt(i);
                        try{
                            efl.Locationrecorder(list.get(i).get(0),list.get(i).get(1),list.get(i).get(2),list.get(i).get(3));
                            efl.showparticle();
                            }catch (NullPointerException e){
                        }
                    }
                    Thread.sleep(100);
                    i = 0;
                } catch (Exception e) {
                    dotokei = false;
                }
                //抽選部分 アイテムの抽選は別のところでやる
                int sneakmapsize = sneakmap.size();
                List<String> isSneakList = plugin.getisSneak();
                int isSneakListsize = isSneakList.size();
                List<String> isSneakinList = new ArrayList<>();
                List<String> isfirstSneakinList = new ArrayList<>();
                if(isSneakListsize==0){
                    sneakmap=new HashMap<>();
                    Bukkit.getConsoleSender().sendMessage("isSneakListはなにも入ってないです");
                }else{
                    if (sneakmapsize==0){
                    sneakmap.put(1,isSneakList);
                    }else {
                    for (int f = 0; f < isSneakListsize; f++) {
                        boolean isinList = false;
                        for (i = 1; i <= sneakmapsize; i++) {
                            try {
                                int check = sneakmap.get(i).indexOf(isSneakList.get(f));
                                if (check == -1) {
                                    } else {
                                    isinList = true;
                                    }
                                } catch (Exception e) {
                                Bukkit.getConsoleSender().sendMessage("Timer.java error:"+e);
                            }
                        }
                        if (isinList == false) {
                            isfirstSneakinList.add(isSneakList.get(f));
                        } else {
                            isSneakinList.add(isSneakList.get(f));
                        }
                    }
                    Bukkit.getConsoleSender().sendMessage("isfirstSneakinList"+isfirstSneakinList);
                    Bukkit.getConsoleSender().sendMessage("isSneakinList:"+isSneakinList);


                    List<String> insertlist;
                    for (int f = sneakmapsize; f > 0; f--) {
                        int listsize = sneakmap.get(f).size();
                        insertlist = new ArrayList<>();
                        for (int m = 0; m < listsize; m++) {
                            int check = isSneakList.indexOf(sneakmap.get(f).get(m));
                            if (check != -1) {
                                insertlist.add(isSneakList.get(m));
                                }
                            }
                        sneakmap.put(f+1, insertlist);
                        }
                    sneakmap.put(1, isfirstSneakinList);
                    }
                }
                Bukkit.getConsoleSender().sendMessage("sneakmap:"+String.valueOf(sneakmap));


                //抽選する人をはじく
        if(sneakmap.containsKey(5)){
            List<String> LotteryPlayer = sneakmap.get(5);
            sneakmap.remove(5);
            for(int i = 0;i<LotteryPlayer.size();i++){
                String impstr = LotteryPlayer.get(i);
                int index = impstr.indexOf("=");
                int indexx = impstr.indexOf("}");
                index=index+1;
                Player p = Bukkit.getPlayer(impstr.substring(index,indexx));
                PlayerInventory inventory = p.getInventory();
                Location location = p.getLocation();
                Bukkit.getConsoleSender().sendMessage("player's location:"+location);
                World blw = location.getWorld();
                double blx = Math.floor(location.getX());
                double bly = Math.floor(location.getY())-1;
                double blz = Math.floor(location.getZ());
                List<String> searchlist = new ArrayList<>();
                searchlist.add(String.valueOf(blw));
                searchlist.add(String.format("%.1f",blx));
                searchlist.add(String.format("%.1f",bly));
                searchlist.add(String.format("%.1f",blz));
                List<List<String>> list;
                list = (List) dataconf.getList("Data.Block");
                String getpreset = null;
                String tmp = null;
                if(list == null){
                    return;
                }
                else{
                    for(int f = 0;f < list.size();f++){
                        List<String> getlist = new ArrayList<>();
                        getlist = list.get(f);
                        try{
                            tmp=getlist.get(4);
                            getlist.remove(4);
                        }catch (Exception e){
                        }
                        if(getlist.equals(searchlist)){
                            getpreset = tmp;
                        }
                    }
                }if(getpreset!=null) {
                    if (getpreset.equals("プリセット1")) {
                        ItemStack dia = new ItemStack(Material.DIAMOND, 1);
                        ItemMeta diameta = dia.getItemMeta();
                        diameta.setDisplayName("おめでとうのダイヤ");
                        dia.setItemMeta(diameta);
                        inventory.addItem(dia);
                    } else if (getpreset.equals("プリセット2")) {
                        ItemStack dia = new ItemStack(Material.REDSTONE, 1);
                        ItemMeta diameta = dia.getItemMeta();
                        diameta.setDisplayName("おめでとうのレッドストーン");
                        dia.setItemMeta(diameta);
                        inventory.addItem(dia);
                    }
                }
            }
        }


    }
    public void setListsize(int i){
        this.listsize = i;
    }
    public Moremining getplugin(){
        return plugin;
    }
    public void setPlugin(Moremining pl){
        this.plugin = pl;
    }
}
