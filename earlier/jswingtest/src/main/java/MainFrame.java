import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/**
 * Copyright (C) 2014 北京视达科科技有限公司 <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-12-10 下午4:25 <br>
 * description:
 */

public class MainFrame extends JFrame {

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    MainFrame() {
        setTitle("Virtual Remote");
        setSize(420, 700);
        getContentPane().setLayout(null);

        addJButton("OK", AndroidKeyCode.KEYCODE_DPAD_CENTER, new Rectangle(170, 80, 70, 60));

        addJButton("UP", AndroidKeyCode.KEYCODE_DPAD_UP, new Rectangle(160, 20, 90, 40));
        addJButton("DOWN", AndroidKeyCode.KEYCODE_DPAD_DOWN, new Rectangle(160, 170, 90, 40));
        addJButton("LEFT", AndroidKeyCode.KEYCODE_DPAD_LEFT, new Rectangle(50, 90, 90, 40));
        addJButton("RIGHT", AndroidKeyCode.KEYCODE_DPAD_RIGHT, new Rectangle(260, 90, 90, 40));

        addJButton("MENU", AndroidKeyCode.KEYCODE_MENU, new Rectangle(50, 230, 80, 40));
        addJButton("BACK", AndroidKeyCode.KEYCODE_BACK, new Rectangle(275, 230, 80, 40));

        addJButton("HOME", AndroidKeyCode.KEYCODE_HOME, new Rectangle(165, 230, 80, 40));
        addJButton("DEL", AndroidKeyCode.KEYCODE_DEL, new Rectangle(165, 610, 80, 40));
//        addJButton("tmp1", 257, new Rectangle(165, 230, 80, 40));
//        addJButton("tmp2", 258, new Rectangle(165, 610, 80, 40));

        for (int i = 0; i < 10; i++) {
            int x = 20 + 75 * (i % 5);
            int y = i < 5 ? 300 : 340;
            addJButton("" + i, AndroidKeyCode.KEYCODE_0 + i, new Rectangle(x, y, 65, 30));
        }

        for (int i = 0; i < letters.length; i++) {
            int x = 20 + 75 * (i % 5);
            int y = (i / 5) * 40 + 400;
            addJButton(letters[i], AndroidKeyCode.KEYCODE_A + i, new Rectangle(x, y, 65, 30));
        }
    }

    String[] letters = {
            "a", "b", "c", "d", "e", "f", "g",
            "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z"};

    private void addJButton(String text, final int keyCode, Rectangle rectangle) {
        JButton button = new JButton(text);

        button.setFont(new Font("Dialog", Font.BOLD, 14));
        button.setBounds(rectangle);
        button.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
//                System.out.println("keyTyped, keyCode=" + e.getKeyCode());
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("keyPressed, keyCode=" + e.getKeyCode());
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        inputKeyEvent(AndroidKeyCode.KEYCODE_DPAD_UP);
                        break;
                    case KeyEvent.VK_DOWN:
                        inputKeyEvent(AndroidKeyCode.KEYCODE_DPAD_DOWN);
                        break;
                    case KeyEvent.VK_LEFT:
                        inputKeyEvent(AndroidKeyCode.KEYCODE_DPAD_LEFT);
                        break;
                    case KeyEvent.VK_RIGHT:
                        inputKeyEvent(AndroidKeyCode.KEYCODE_DPAD_RIGHT);
                        break;
                    case KeyEvent.VK_ENTER:
                        inputKeyEvent(AndroidKeyCode.KEYCODE_DPAD_CENTER);
                        break;
                    case KeyEvent.VK_BACK_SPACE:
                        inputKeyEvent(AndroidKeyCode.KEYCODE_DEL);
                        break;
                    case KeyEvent.VK_ESCAPE:
                        inputKeyEvent(AndroidKeyCode.KEYCODE_BACK);
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
//                System.out.println("keyReleased, keyCode=" + e.getKeyCode());
            }
        });
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                inputKeyEvent(keyCode);
            }
        });
        add(button);
    }

    public void inputKeyEvent(final int keyCode) {
        System.out.println("keycode=" + keyCode);
        try {
            ProcessBuilder builder = new ProcessBuilder("adb", "shell", "input", "keyevent", String.valueOf(keyCode));
            builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JButton findJButtonByText(String text) {
        Component[] components = getContentPane().getComponents();
        for (Component component : components) {
            String name = ((JButton) component).getText();
            System.out.println(name);
            if (name.equalsIgnoreCase(text)) {
                return (JButton) component;
            }
        }
        return null;
    }

    @Override
    public boolean keyDown(Event evt, int key) {
        System.out.println("keyDown, keyCode=");
        return super.keyDown(evt, key);
    }

    @Override
    protected void processKeyEvent(KeyEvent e) {
        System.out.println("processKeyEvent, keyCode=" + e.getKeyCode());
        super.processKeyEvent(e);
    }
}
