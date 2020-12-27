package nabebuta.man10.moremining;

public class Tokei extends Thread implements Runnable {
    boolean dotokei = true;
    private int ilimit = 1;
    Effloc efl = new Effloc();
    public static void main() {
    }
    public void setIlimit(int ilimit){
        this.ilimit = ilimit;
    }
    public int getIlimit(){
        return ilimit;
    }


    @Override
    public void run() {
            while (dotokei = true) {
                try {
                    for(int i = 0;i >= ilimit; i++){
                        efl.main(i);
                    }
                    Thread.sleep(100); // 10秒(1万ミリ秒)間だけ処理を止める
                } catch (InterruptedException e) {
                }
        }
    }
}
