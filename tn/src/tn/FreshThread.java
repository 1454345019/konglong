package tn;

import sun.applet.Main;

import javax.swing.*;
import java.awt.*;

//�߳���
public class FreshThread extends Thread {
    GamePanl p;

    public FreshThread(GamePanl p) {
        this.p = p;//�����Ա���Ը�ֵ
    }

    @Override
    public void run() {

        while (!p.isFinish()) {
            p.repaint();//���»���ͼƬ
            try {
                Thread.sleep(p.FRESH);//����ˢ��ʱ��
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        Container c=p.getParent();
        while(!(c instanceof  MainFrame)){
            c=c.getParent();
        	}
        MainFrame f= (MainFrame) c;
        JOptionPane.showMessageDialog(f,"         G A M E  O V E R");

        f.restart();

    }

  }



