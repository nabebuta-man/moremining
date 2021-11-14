package nabebuta.man10.moremining;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemRegisterCategory {
    /*

    テスト事項
    ・通し番号の付け方があってるかどうか
    ・一回登録したらリストからプレイヤーをはじく
    ・70行目にエラーある(ぬるぽ)

    */
    MySQLManager data;
    JavaPlugin plugin;
    public ItemRegisterCategory(JavaPlugin plugin) {
        this.plugin = plugin;
        data = new MySQLManager(plugin, "Moremining");
    }
    public boolean CreateNewItemCategory(String title, ItemStack additem, ItemStack rarelity) {
        Map<String, Object> insertISMap = additem.serialize();
        try {
            data.execute("use moremining");
            data.execute("CREATE TABLE IF NOT EXISTS itemcategory (CheckNumber int(10), CategoryName CHAR(50) UNIQUE,IconItem TEXT(1000),Rarelity CHAR(50))");
            data.execute("INSERT IGNORE INTO itemcategory (CheckNumber,CategoryName,IconItem,Rarelity) VALUES(0,'dummy','dummy','dummy')");
            int count = data.countRows("itemcategory");
            data.execute("INSERT IGNORE INTO itemcategory (CheckNumber,CategoryName,IconItem,Rarelity) VALUES(" + count + ",'" + title + "','" + String.valueOf(insertISMap) + "','" + String.valueOf(rarelity.getItemMeta().getDisplayName()) + "')");
            if (count < data.countRows("itemcategory")) {
                //dummy削除クエリ
                data.execute("delete from itemcategory where CheckNumber=0");
                return true;
            } else if (count==-1){
                plugin.getLogger().info("countが-1でした");
                return false;
            }else{
                //dummy削除クエリ
                data.execute("delete from itemcategory where CheckNumber=0");
                //insertが失敗しているのでfalseを返す
                return false;
            }

        }catch (Exception exception){
            plugin.getLogger().info("[Moremining] カテゴリー作成クエリに失敗しました");
            plugin.getLogger().info("[Moremining] エラーコード : "+ exception);
            return false;
        }
    }
    public boolean ItemRegisterCategoryWindowReload(InventoryClickEvent e,int ClickedSlot){
        boolean result = true;
        if(ClickedSlot==6){
            //カテゴリーの処理
            ItemStack setItem = new ItemStack(Material.PAPER,1);
            ItemMeta setItemMeta = setItem.getItemMeta();
            setItemMeta.setDisplayName("Next page");
            setItem.setItemMeta(setItemMeta);
            e.getInventory().setItem(50,setItem);
            setItemMeta.setDisplayName("Previous page");
            setItem.setItemMeta(setItemMeta);
            e.getInventory().setItem(48,setItem);
            plugin.getLogger().info("test Itemstack:"+ setItem);

            //MySQLからデータを引っ張ってくる
            Map<List<String>,List<ItemStack>> Iconmap;//格納用データ
            List<String> TitleList = new ArrayList<>();//抽出用データ
            List<ItemStack> IconItemList = new ArrayList<>();//抽出用データ
            ResultSet rs =  data.query("select max(CheckNumber) as LargestPrice from itemcategory;");
            int countrow = 0;
            boolean counttrue = false;
            try {
                while (rs.next()){
                    countrow = rs.getInt("LargestPrice");
                    plugin.getLogger().info("LargestPrice:"+String.valueOf(countrow));
                }
                counttrue = true;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (NullPointerException exception){
                TitleList.add("データのテーブルが作成されてません");
            }
            if(counttrue) {
                    for (int i = 0; i <= countrow; i++) {
                        rs = data.query("select * from itemcategory where CheckNumber=" + i);
                        plugin.getLogger().info("i = :"+i);
                        try {
                            rs.next();
                            String resultitem = rs.getString("IconItem");
                            int resultitemlengh = resultitem.length();
                            resultitem = resultitem.substring(1,resultitemlengh);
                            plugin.getLogger().info("resultItem :"+resultitem);
                            Map<String, Object> ISMap = null;
                            setItem = ItemStack.deserialize(ISMap);
                            plugin.getLogger().info("setitem :"+setItem);
                            setItemMeta = setItem.getItemMeta();
                            setItemMeta.setDisplayName(String.valueOf(rs.getString("CategoryName")));
                            setItem.setItemMeta(setItemMeta);
                            e.getInventory().setItem(9+i,setItem);
                        } catch (Exception exception) {
                            plugin.getLogger().info("exception is :"+exception);
                            result = false;
                        }
                    }
            }

        }
        return result;
    }
}
