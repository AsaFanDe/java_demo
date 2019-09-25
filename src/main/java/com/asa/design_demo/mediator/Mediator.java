package com.asa.design_demo.mediator;

/**中介者
 * Created by AsaFan on 2018-08-22.
 */
public class Mediator {

    private CDRom cdRom;

    private CPU cpu;

    private Mainboard mainboard;

    private SoundCard soundCard;

    private VGACard vgaCard;

    public Mediator(CDRom cdRom, CPU cpu, Mainboard mainboard, SoundCard soundCard, VGACard vgaCard) {
        this.cdRom = cdRom;
        this.cpu = cpu;
        this.mainboard = mainboard;
        this.soundCard = soundCard;
        this.vgaCard = vgaCard;
    }


    public void notifyMainBoard() {
        System.out.println("中介者来通知主板");
        mainboard.getData(this);
    }

    public void notifyCPU() {
        System.out.println("中介者来将数据给CPU");
        cpu.dealData(this);
    }

    public void notifyMainboard() {
        System.out.println("中介者来通知主板");
        mainboard.getData2(this);
    }

    public void outData() {
        System.out.println("中介者将数据交给显卡和声卡进行输出。");
        soundCard.out();
        vgaCard.out();
    }


}
