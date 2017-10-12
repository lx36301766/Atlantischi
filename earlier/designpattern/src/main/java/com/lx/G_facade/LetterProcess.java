package com.lx.G_facade;

/**
 * 定义写信的过程
 */
public interface LetterProcess {

    // 首先写信的内容
    public void writeContext(String context);

    // 其次写信封
    public void fillEnvelope(String address);

    // 把信放到信封里
    public void letterInfoEnvelope();

    // 然后邮递
    public void sendLetter();

}
