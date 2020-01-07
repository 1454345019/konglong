package tn;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * �̳д�������ͼƬ
 */
public class GamePanl extends JPanel implements KeyListener {
	// ��ͼƬ
	BufferedImage image;

	// ��ͼ����g2
	Graphics2D g2;
	kLong klong;// ����
	boolean finish = false;// ��Ϸ����

	static final int FRESH = 4;// ˢ��ʱ�䣬����

	BackgroundImage background;

	Rectangle rt;

	List<object> list = new ArrayList<object>();// �ϰ��Ｏ��
	List<object> list2 = new ArrayList<object>();//��Ҽ���

	

	int addObjectTimer = 0;
	object o = new object();


	int score = 0;// ����
	int icon=0;//���
	int addScoreTimer = 0;//��������ʱ��
	int addiconTimer=0;//�����Ҽ�ʱ��


	// ���췽��
	public GamePanl() {
		image = new BufferedImage(734, 286, BufferedImage.TYPE_INT_BGR);
		g2 = image.createGraphics();
		klong = new kLong();// ʵ����������
		background = new BackgroundImage();// ʵ��������ͼƬ



		FreshThread t = new FreshThread(this);// ˢ���߳�
		t.start();

	}

	private void painImage() {// ����ͼƬ
		klong.move();// ��С�����ƶ�
		background.roll();// ���ù���ͼƬ
		klong.bigmove();//�ô�����ƶ�
		g2.drawImage(background.image, 0, 0, this);// ���Ʊ���
		g2.drawImage(background.image_yun, background.x_yun, background.y_yun, this);// ���ư���
		g2.drawImage(klong.fireimage, klong.xfire, klong.yfire, this);//������
		g2.drawImage(o.paotai,-27, 154, this);//����̨���ǻ��棬���ڻ����
		if(icon>=100) {//��һ�ٸ���ҲŻ����Ģ������
		g2.drawImage(o.mogu, o.xmogu, o.ymogu, this);//��Ģ��
		o.mogumove();}//Ģ���ƶ���ʽ
		
		g2.setColor(Color.BLACK);;//����������ɫ
		g2.setFont(new Font("����",Font.BOLD,20));//�����ֹ��
		
		g2.drawString(String.format("%4d", +icon), 300, 35);//���Ƽ����ҵ�λ��
		g2.drawString("���룺", 500, 35);//���ƾ����ʶ
		g2.drawString(String.format("%05d",+score), 600, 35);//���Ƽ������߾����λ��
		g2.drawImage(background.icon, 270, 16, this);// ���ƽ��ͼ��


		addiconTimer+=FRESH;
		addObjectTimer += FRESH;
		addScoreTimer += FRESH;

		
		if (addScoreTimer >= 50) {//�Ǿ�������
			score += 1;//���������ÿ50ms��1
			addScoreTimer = 0;//�����ʱ������
		}
		
		
		
		if(klong.bigstate==false) {
		g2.drawImage(klong.image, klong.x+40, klong.y, this);// ����С����
		}
		else  {g2.drawImage(klong.image, klong.X, klong.Y, this);}// ���ƴ����
		
		

		
		
//�ϰ��Ｏ���������
		if (addObjectTimer >= 1500) {// �ϰ����������
			list.add(new object());//ÿ1�����ϰ��Ｏ��������ϰ���
		
			addObjectTimer = 0;
		}
		
//����ϰ��Ｏ�Ϻ��ϰ����Ϸ��Ľ��
		for (int i = 0; i < list.size(); i++) {
			object o = list.get(i);//oΪ�ϰ������������g2���û����ϰ������е��ϰ�����ϰ����Ϸ��Ľ��ͼ
			o.move();//�ϰ�������Ʒ�ʽ
			o.brid();//������
			g2.drawImage(o.image, o.x, o.y, this);// �����ϰ�
			g2.drawImage(o.icon, o.x, o.y-80, this);//�����ϰ����Ϸ��Ľ��
//С������ײ�� ˫�������жϣ����ж��ǲ���С���������ж���û��ײ��
			if (klong.bigstate==false&(o.cucbounds().intersects(klong.bounds1()) || o.cucbounds().intersects(klong.bounds2())||o.birdbounds().intersects(klong.bounds1())||o.birdbounds().intersects(klong.bounds2()))) {//���ϰ������ײ���
				gameOver();// ײ������Ϸ����
			}
			if (klong.bigstate==false&(o.upiconbounds().intersects(klong.bounds1()) || o.upiconbounds().intersects(klong.bounds2()))) {//���ϰ����Ϸ���ҵ���ײ���
				o.iconmove();//ײ��������ʧ
				icon+=1;// ײ�������+1
			}
//�������ײ��	˫�������жϣ����ж��ǲ��Ǵ���������ж���û��ײ��		
			if(klong.bigstate==true&(o.cucbounds().intersects(klong.Bounds1()) || o.cucbounds().intersects(klong.Bounds2())||o.birdbounds().intersects(klong.Bounds1())||o.birdbounds().intersects(klong.Bounds2()))) {
				gameOver();// ײ������Ϸ����
			}
			if(klong.bigstate==true&(o.upiconbounds().intersects(klong.Bounds1()) || o.upiconbounds().intersects(klong.Bounds2()))) {
				o.iconmove();//ײ��������ʧ
				icon+=1;// ײ�������+1
			}
//�ϰ����������ײ			
			if(o.cucbounds().intersects(klong.firebounds())) {
				o.iconmove();//�ϰ�����ʧ����Ϊ�ϰ����y�����ϰ����Ϸ���ҵ�y��������iconmove�ϰ���һ����ʧ
			}
			
		}
		
		
////**********************************************************����			
//			rt = o.mogubounds();// ��ȡ��ײ����
//			g2.fillRect(rt.x, rt.y, rt.width, rt.height);// �����߿򣨲����ã�
////**********************************************************����
			
			
		
//�����Ҽ����������
		if (addiconTimer >= 100) {// �ӽ������
			list2.add(new object());//ÿ50ms����Ҽ��������һ�����
		
			addiconTimer = 0;//��ʱ������
			}
		
//��������Ҽ��ϣ������������ϰ��
		for(int i = 0; i < list2.size(); i++) {
			object o = list2.get(i);//o���ϰ����һ�������������û����ϰ�����Ľ��ͼ
			g2.drawImage(o.imageicon, o.x,o.y-5,this);
			o.move();//������γ���
		if (o.undergroundbounds().intersects(klong.bounds1()) || o.undergroundbounds().intersects(klong.bounds2())) {//����������ҵ���ײ��⣬��Ϊ�����Ҵ����С��������ײ�嶼��������������ľ�û��
			o.iconmove();//�Ե���Һ�����ʧ
			icon+=1;//���ֵ+1
		
		}
	}
		if(klong.bounds1().intersects(o.mogubounds())) {//�������Ģ�����ͱ�ɴ����	
			//****************************************С�����������ؼ��ж����������������ײ����Ծ��icon������Ҫ��
			klong.turnbig();//�����Ǵ�Ļ�С��ȫ��bigstate�ж�,���н�bigstate��Ϊtrue
		}
		
		
		
}

	
	
	
	
	

	// ��Ϸ����
	public void gameOver() {
		finish = true;
		g2.drawImage(background.image_over, background.x_over, background.y_over, null);
		

	}

	@Override
	public void paint(Graphics g) {
		painImage();
		g.drawImage(image, 0, 0, this);
	}

	public boolean isFinish() {//�ж���Ϸ����״̬,��finish��getter
		return finish;
	}

	// ��Ծ���̼���
	@Override
	public void keyTyped(KeyEvent e) {// ��������

	}

	@Override
	public void keyPressed(KeyEvent e) {// ��������
		int code = e.getKeyCode();// ��ȡ���µı���
		if (code == KeyEvent.VK_SPACE&klong.bigstate==false) {//����ո񱻰����ҽ����С��10�������С��������Ծ
			klong.jump();}
		else if (code == KeyEvent.VK_SPACE&klong.bigstate==true) {//����ո񱻰����ҽ��������100������ô��������Ծ
			klong.bigjump();
		}
		else if(code==KeyEvent.VK_NUMPAD0) {
			klong.fire();
			}
	

	}


	@Override
	public void keyReleased(KeyEvent e) {// ����̧��
	}
}
