package com.lx.G_facade;

/**
 * 开始给朋友写信了
 */
public class Client {

    public static void main(String[] args) {
        System.out.println("（门面模式）\n");

        /**
         * //创建一个处理信件的过程 LetterProcess letterProcess = new LetterProcessImpl();
         * //开始写信 letterProcess.writeContext(
         * "Hello,It's me,do you know who I am? I'm your old lover."); //开始写信封
         * letterProcess
         * .fillEnvelope("Happy Road No.666, God Province, Heaven");
         * //把信放到信封里，并封装好 letterProcess.letterInfoEnvelope(); //跑到邮局把信塞到邮箱，投递
         * letterProcess.sendLetter();
         */

        // 现代化得邮局，有这个服务，邮局名字叫Hell Road
        ModenPostOffice hellRoadPostOffice = new ModenPostOffice();
        // 只需把信的内容和收件人地址给他，他会帮你完成一系列工作
        String address = "Hello,It's me,do you know who I am? I'm your old lover.";
        String context = "Happy Road No.666, God Province, Heaven";
        hellRoadPostOffice.sendLetter(context, address);
    }

}
