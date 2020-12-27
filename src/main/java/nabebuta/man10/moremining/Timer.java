package nabebuta.man10.moremining;

import org.bukkit.plugin.java.JavaPlugin;

public class Timer extends Thread implements Runnable {
    boolean dotokei = true;
    private int ilimit = 1;
    private JavaPlugin jp;
    Effloc efl = new Effloc();
    LinkTiEf lte = new LinkTiEf();
    public static void main() {
    }
    public void setIlimit(int ilimit){
        this.ilimit = ilimit;
    }
    public int getIlimit(){
        return ilimit;
    }
    public JavaPlugin getjp(){
        return jp;
    }
    public void setJp(JavaPlugin jp){
        this.jp = jp;
    }
    @Override
    public void run() {
            while (dotokei = true) {
                try {
                    for(int i = 0;i >= ilimit; i++){
                        efl.main(jp,i);
                        ilimit=lte.getI();
                    }
                    Thread.sleep(100); // 10秒(1万ミリ秒)間だけ処理を止める
                } catch (InterruptedException e) {
                }
        }
    }
}
