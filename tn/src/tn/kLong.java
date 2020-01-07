package tn;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

/**
 * ������
 */
public class kLong {
	public int x, y;// С��������
	public int X, Y;// ���������
	public int xfire, yfire;// ��������
	BufferedImage image;// ��ͼƬ
	BufferedImage fireimage;//����ͼƬ
	BufferedImage image1, image2, image3, image_over, big1, big2, big3,fire;
	int stepTime = 0;// ��ʱ��
	int fresh = GamePanl.FRESH;
	Graphics2D g2;
	GamePanl p;

	
	boolean firestate =false;//����״̬
	boolean bigstate = false;// ���״̬
	boolean jumpState = false;// ��Ծ��״̬
	boolean jumpbigState = false;//�������Ծ״̬
	int jumpHeight = 120;// ��Ծ�߶�
	int jumpbigHeight=110;//�������Ծ�߶�
	final int LOWEST_Y = 200;// �������
	final int bigLOWEST_Y = 170;
	int jumpValue = 1;// ��Ծ��������

	public kLong() {

		try {
			image1 = ImageIO.read(new File("image/long1.png"));
			image2 = ImageIO.read(new File("image/long2.png"));
			image3 = ImageIO.read(new File("image/long3.png"));
			image_over = ImageIO.read(new File("image/over.png"));
			big1 = ImageIO.read(new File("image/bianda1.png"));
			big2 = ImageIO.read(new File("image/bianda2.png"));
			big3 = ImageIO.read(new File("image/bianda3.png"));
			fire = ImageIO.read(new File("image/huoyan.png"));

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		x = 50;//С������ʼ����
		y = LOWEST_Y;
		X = 70;//�������ʼ����
		Y = bigLOWEST_Y;
		xfire=-27;
		yfire=190;
		
	}

	public void move() {// �ƶ�
		step();// ̤��
		if (jumpState) {
			if (y >= LOWEST_Y) {
				jumpValue = -2;
			}
			if (y <= LOWEST_Y - jumpHeight) {
				jumpValue = 2;
			}
			y += jumpValue;
			image = image3;
			if (y >= LOWEST_Y) {
				jumpState = false;
			}

		}
		
		if(firestate) {//������������£�����״̬��Ϊtrue
			if(xfire<800){//������滹û�ɳ���Ļ��
				fireimage=fire;//firestateΪtrue�Żử������,Ҫ��fireimage�ڴ���
				xfire+=1;//�ƶ��ٶ�
			}
			if(xfire>=800) {
				xfire=-27;//������泬����Ļ�⣬�ͷ���ԭʼλ��
				firestate=false;//firestate��Ϊfalse���ȴ����°���
			}
		}
	}

	
	
	
	// ̤��
	void step() {
		int tmp = stepTime / 100 % 2;
		if (tmp == 1) {
			image = image1;
		} else {
			image = image2;
		}
	

		stepTime += fresh;

	}


//***************С������ײ
	public Rectangle bounds1() {//ͷ
		return new Rectangle(x + 20, y, 20, 10);
	}

	public Rectangle bounds2() {//��
		return new Rectangle(x + 5, y + 35, 20, 10);
	}

	
//***************�������ײ
	public Rectangle Bounds1() {//ͷ
		return new Rectangle(X+40,Y,35,25);
	}
	
	public Rectangle Bounds2() {//��
		return new Rectangle(X+13,Y+60,24,15);
	}
	
//****************������ײ
	public Rectangle firebounds() {
		return new Rectangle(xfire,yfire,fire.getWidth(),fire.getWidth());
	}
	
	
	
	
	
	//�����滻ͼƬ����,������step��ͬ,ʵ�ִ����̤��
	public void Bigstep() {

    	int tmp=stepTime /100%2;
    	if(bigstate) {
    		if(tmp==1) {
    			image=big1;
    		}
    		else {
    			image=big2;
    		}
    	}
    	stepTime += fresh;
    }

	//ʵ�ִ������Ծ
	public void bigmove() {
		Bigstep();
		if (jumpbigState) {
			if (Y >= bigLOWEST_Y) {
				jumpValue = -1;
			}
			if (Y <= bigLOWEST_Y - jumpbigHeight) {
				jumpValue = 1;
			}
			Y += jumpValue;
			image = big1;
			if (Y >= bigLOWEST_Y) {
				jumpbigState = false;
			}

		}
	}
	
	
	public void turnbig() {//�����״ֵ̬��Ϊtrue
		bigstate=true;
	}
	
	
	public void bigjump() {// �������Ծ
		jumpbigState = true;//�������Ծֵ��Ϊtrue
	}
	

	public void jump() {// С������Ծ
		jumpState = true;
	}
	
	public void fire() {
		firestate=true;
	}
	

	
}
	
	
	
