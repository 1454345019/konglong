package tn;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    GamePanl p;

    public MainFrame() {
        //���ڹر�
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        p = new GamePanl();
        //��ȡ�����������
        Container c = getContentPane();
        c.add(p);//�������ӵ���������

        addKeyListener(p);//��Ӽ��̼���

    }

    public static void main(String[] args) {

        MainFrame frame = new MainFrame();
        //�����С
        frame.setBounds(340, 220, 734, 320);
        //����ɼ�
        frame.setVisible(true);
        //��Ļ����
        frame.setLocationRelativeTo(null);
        frame.setTitle("��һ��(��0�����ϰ����һ�ٸ���ҽ������Ģ��)");


    }


    //����������Ķ���ɾ������������
    public void restart(){
        Container c=getContentPane();
        //ɾ�������������
        c.removeAll();;

        GamePanl gp=new GamePanl();
        c.add(gp);

        addKeyListener(gp);

        c.validate();//������֤�������

    }







}
