package com.lx.G_facade;

public class ModenPostOffice {

    private LetterProcess letterProcess = new LetterProcessImpl();

    // 写信，封装，投递，一体化了
    public void sendLetter(String context, String address) {
        // 帮你写信
        letterProcess.writeContext(context);
        // 写好信封
        letterProcess.fillEnvelope(address);
        // 把信放到信封里，并封装好
        letterProcess.letterInfoEnvelope();
        // 投递邮件
        letterProcess.sendLetter();
    }
}
