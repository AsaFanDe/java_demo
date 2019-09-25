package com.asa.design_demo.mediator;

/**光驱
 * Created by AsaFan on 2018-08-22.
 */
public class CDRom {

    private Mediator mediator;

    public void readData(Mediator mediator) {
        System.out.println("光驱读取完数据让中介者通知主板");
        mediator.notifyMainBoard();
    }
}
