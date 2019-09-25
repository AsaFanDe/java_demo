package com.asa.design_demo.mediator;

/**
 * 中介者模式
 * Created by AsaFan on 2018-08-22.
 */
public class MediatorDemo {

    public static void main(String[] args) {
        CDRom cdRom = new CDRom();
        CPU cpu = new CPU();
        Mainboard mainboard = new Mainboard();
        SoundCard soundCard = new SoundCard();
        VGACard vgaCard = new VGACard();

        Mediator mediator = new Mediator(cdRom, cpu, mainboard, soundCard, vgaCard);
        cdRom.readData(mediator);
    }
}
