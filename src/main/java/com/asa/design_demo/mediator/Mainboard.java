package com.asa.design_demo.mediator;

/**主板
 * Created by AsaFan on 2018-08-22.
 */
public class Mainboard {

    public void getData(Mediator mediator) {
        System.out.println("主板得到光驱的数据，将数据交给CPU进行分析处理。");
        mediator.notifyCPU();
    }

    public void getData2(Mediator mediator) {
        System.out.print("主板得到数据");
        mediator.outData();
    }
}
