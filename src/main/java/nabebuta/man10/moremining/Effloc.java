package nabebuta.man10.moremining;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

public class Effloc {
    int i = 0;
    String eflsw;
    double eflsx;
    double eflsy;
    double eflsz;
    public void showparticle() {
        //i = md.getI();
            try {
                int index = eflsw.indexOf("=");
                int indexx = eflsw.indexOf("}");
                index=index+1;
                World neweflsw = Bukkit.getWorld(eflsw.substring(index,indexx));
                Location efl = new Location(neweflsw, eflsx, eflsy, eflsz);
                efl.getWorld().spawnParticle(
                            Particle.END_ROD, // パーティクルの種類
                            efl, // 発生させる場所
                            1,// 発生させる数
                            0.2, // 散開させるXの範囲
                            0.2, // 散開させるYの範囲
                            0.2, // 散開させるZの範囲
                        0
                    );
            } catch (Exception e) {

            }
    }
    public void Locationrecorder(String blw,String blx,String bly,String blz){
        eflsw = blw;
        eflsx = Double.parseDouble(blx)+0.5;
        eflsy = Double.parseDouble(bly)+1.5;
        eflsz = Double.parseDouble(blz)+0.5;
    }
    public int getInt(){
        return i;
    }
    public void setInt(int i){
        this.i = i;
    }
}
