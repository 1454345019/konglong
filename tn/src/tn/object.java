package tn;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * �ϰ�����
 */
public class object {
	kLong klong = new kLong();
	Random r = new Random();
	int x=800, y=209;//�ϰ����ʼλ��,Ҳ�ǵ����ҳ�ʼλ��
	int xmogu=700,ymogu=90;//Ģ����ʼλ��




	public boolean judge = false;
	BufferedImage image;
	BufferedImage imageicon;
	BufferedImage imagemogu;
	BufferedImage cactus01, cactus02, cactus03, bird1, bird2,icon,mogu,paotai;// ����������+��+���+Ģ��
	int speed = BackgroundImage.SPEED-1;
	public int temp = r.nextInt(4) + 1;// 1-2-3

	public object() {
        //��ȡͼƬ
        try {
            cactus01 = ImageIO.read(new File("image/cactus01.png"));
            cactus02 = ImageIO.read(new File("image/cactus02.png"));
            cactus03 = ImageIO.read(new File("image/cactus03.png"));
              bird1= ImageIO.read(new File("image/bird1.png"));
              bird2= ImageIO.read(new File("image/bird2.png"));
              icon=ImageIO.read(new File("image/jinbi.png"));
              mogu=ImageIO.read(new File("image/mogu.jpg"));
              paotai=ImageIO.read(new File("image/paotai.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        imageicon=icon;//ֱ�ӵ���g2����������

        //�������ϰ���
        switch (temp) {
            case 1:
                image = cactus01;
                
           
                break;
            case 2:
                image = cactus02;
             
           
                break;
            case 3:
                image = cactus03;
               
           
                break;
            default:
                judge=true;
        }


    }

	// ����˶�
	void brid() {
		if (judge) {

			int tmp = klong.stepTime / 100 % 2;
			if (tmp == 1) {
				image = bird1;
			} else {
				image = bird2;
			}

			klong.stepTime += klong.fresh;
		}
	}

	// �����������ϰ���͵����ң�
	public void move() {
		x -= speed*2;

	}

	
	public void iconmove() {//�����ʧ����
		y-=700;
	}
	
	public void mogumove() {//Ģ���˶�����
		xmogu-=speed;
	}
	
	

	
	// ��������
	public Rectangle cucbounds() {//�����Ƶ���ײ����
		return new Rectangle(x, y + 2, image.getWidth() - 2, image.getHeight() - 10);//�������ֵ���image�ڴ�ռ�
		
	}
	
	public Rectangle birdbounds() {//�����ײ����
		return new Rectangle(x,y,image.getWidth(),image.getHeight());
	}
	
	
	public Rectangle upiconbounds() {//�ϰ����Ϸ���ҵ���ײ����
		return new Rectangle(x,y-100,icon.getWidth(),icon.getHeight());//һ������ֵ�ֱ�ӵ���ͼƬ�Ĵ�С
	}
	
	
	
	public Rectangle undergroundbounds() {//�����ҵ���ײ����
		return new Rectangle(x,y,icon.getWidth(),icon.getHeight());
	}
	
	public Rectangle mogubounds() {//Ģ������ײ����
		return new Rectangle(xmogu,ymogu-25,mogu.getWidth(),mogu.getHeight()-26);
	}
	
	
	
		
}
