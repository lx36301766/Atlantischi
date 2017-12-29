package pl.atlantischi.test;

/**
 * Created on 27/12/2017.
 *
 * @author lx
 */

public class GoogleAuth {

    static GoogleAuthenticator authenticator = new GoogleAuthenticator();

    public static void main(String[] args) {

        authenticator.setWindowSize(5);
        while (true) {
            try {
                String deviceId = "1266f6b0-99ff-4ac1-ae8a-c4368b62218f";
//                deviceId = "fcd51cee-2104-489a-a155-bea653bdc030";
//                deviceId = "226d52da-0d78-4f6d-adde-14d98304eb7e";
                deviceId = "5421e2ec-8543-4ec9-8d5e-8332ac2d2cc9";
                long code = authenticator.get_code(deviceId, System.currentTimeMillis());
                System.out.println("code=" + code);
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
