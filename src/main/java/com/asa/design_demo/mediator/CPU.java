package com.asa.design_demo.mediator;

/**CPU
 * Created by AsaFan on 2018-08-22.
 */
public class CPU {

    public void dealData(Mediator mediator) {
        System.out.println("CPU处理完，将数据分成了视频数据和音频数据，");
        mediator.notifyMainboard();
    }
}
