package nabebuta.man10.moremining;

import org.bukkit.Bukkit;

public class Timer implements Runnable {
    boolean dotokei = true;
    int i = 0;
    int ilimit = 1;
    Effloc efl = new Effloc();
    LinkTiEf lte = new LinkTiEf();
    public void setIlimit(int ilimit){
        this.ilimit = ilimit;
    }
    public int getIlimit(){
        return ilimit;
    }
    @Override
    public void run() {
            if (dotokei) {
                try {
                    for (; i <= ilimit; i++) {
                        lte.setI(i);
                        efl.effloc(i);
                        ilimit = lte.getI();
                        Bukkit.getConsoleSender().sendMessage("Timer worked!");
                    }
                    Thread.sleep(100);
                    i = 0;
                } catch (InterruptedException e) {
                    dotokei = false;
                }
            }
    }
}
