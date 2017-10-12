package com.lx.J_builder;

import java.util.ArrayList;

/**
 * 要什么顺序的车，你说，我给你建造出来
 */
public abstract class CarBuilder {

    // 建造一个模型。你要给我一个顺序，就是组装顺序
    public abstract void setSequence(ArrayList<String> sequence);

    // 设置完毕后，就可以直接拿到这个车辆模型
    public abstract CarModel getCarModel();

}
