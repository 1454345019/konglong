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
 * 继承窗体面板的图片
 */
public class GamePanl extends JPanel implements KeyListener {
	// 主图片
	BufferedImage image;

	// 绘图工具g2
	Graphics2D g2;
	kLong klong;// 恐龙
	boolean finish = false;// 游戏结束

	static final int FRESH = 4;// 刷新时间，毫秒

	BackgroundImage background;

	Rectangle rt;

	List<object> list = new ArrayList<object>();// 障碍物集合
	List<object> list2 = new ArrayList<object>();//金币集合

	

	int addObjectTimer = 0;
	object o = new object();


	int score = 0;// 分数
	int icon=0;//金币
	int addScoreTimer = 0;//计算距离计时器
	int addiconTimer=0;//计算金币计时器


	// 构造方法
	public GamePanl() {
		image = new BufferedImage(734, 286, BufferedImage.TYPE_INT_BGR);
		g2 = image.createGraphics();
		klong = new kLong();// 实例化恐龙类
		background = new BackgroundImage();// 实例化背景图片



		FreshThread t = new FreshThread(this);// 刷新线程
		t.start();

	}

	private void painImage() {// 绘制图片
		klong.move();// 让小恐龙移动
		background.roll();// 调用滚动图片
		klong.bigmove();//让大恐龙移动
		g2.drawImage(background.image, 0, 0, this);// 绘制背景
		g2.drawImage(background.image_yun, background.x_yun, background.y_yun, this);// 绘制白云
		g2.drawImage(klong.fireimage, klong.xfire, klong.yfire, this);//画火焰
		g2.drawImage(o.paotai,-27, 154, this);//画炮台覆盖火焰，画在火焰后
		if(icon>=100) {//满一百个金币才会出现蘑菇奖励
		g2.drawImage(o.mogu, o.xmogu, o.ymogu, this);//画蘑菇
		o.mogumove();}//蘑菇移动方式
		
		g2.setColor(Color.BLACK);;//设置字体颜色
		g2.setFont(new Font("黑体",Font.BOLD,20));//设置字规格
		
		g2.drawString(String.format("%4d", +icon), 300, 35);//绘制计算金币的位置
		g2.drawString("距离：", 500, 35);//绘制距离标识
		g2.drawString(String.format("%05d",+score), 600, 35);//绘制计算行走距离的位置
		g2.drawImage(background.icon, 270, 16, this);// 绘制金币图标


		addiconTimer+=FRESH;
		addObjectTimer += FRESH;
		addScoreTimer += FRESH;

		
		if (addScoreTimer >= 50) {//记距离速率
			score += 1;//距离计算器每50ms加1
			addScoreTimer = 0;//距离计时器置零
		}
		
		
		
		if(klong.bigstate==false) {
		g2.drawImage(klong.image, klong.x+40, klong.y, this);// 绘制小恐龙
		}
		else  {g2.drawImage(klong.image, klong.X, klong.Y, this);}// 绘制大恐龙
		
		

		
		
//障碍物集合添加内容
		if (addObjectTimer >= 1500) {// 障碍物添加速率
			list.add(new object());//每1秒往障碍物集合里添加障碍物
		
			addObjectTimer = 0;
		}
		
//输出障碍物集合和障碍物上方的金币
		for (int i = 0; i < list.size(); i++) {
			object o = list.get(i);//o为障碍物类对象，用于g2调用画出障碍物类中的障碍物和障碍物上方的金币图
			o.move();//障碍物的左移方式
			o.brid();//生成鸟
			g2.drawImage(o.image, o.x, o.y, this);// 绘制障碍
			g2.drawImage(o.icon, o.x, o.y-80, this);//绘制障碍物上方的金币
//小恐龙碰撞： 双重限制判断，先判断是不是小恐龙，再判断有没有撞到
			if (klong.bigstate==false&(o.cucbounds().intersects(klong.bounds1()) || o.cucbounds().intersects(klong.bounds2())||o.birdbounds().intersects(klong.bounds1())||o.birdbounds().intersects(klong.bounds2()))) {//与障碍物的碰撞检测
				gameOver();// 撞到，游戏结束
			}
			if (klong.bigstate==false&(o.upiconbounds().intersects(klong.bounds1()) || o.upiconbounds().intersects(klong.bounds2()))) {//与障碍物上方金币的碰撞检测
				o.iconmove();//撞到后金币消失
				icon+=1;// 撞到，金币+1
			}
//大恐龙碰撞：	双重限制判断，先判断是不是大恐龙，再判断有没有撞到		
			if(klong.bigstate==true&(o.cucbounds().intersects(klong.Bounds1()) || o.cucbounds().intersects(klong.Bounds2())||o.birdbounds().intersects(klong.Bounds1())||o.birdbounds().intersects(klong.Bounds2()))) {
				gameOver();// 撞到，游戏结束
			}
			if(klong.bigstate==true&(o.upiconbounds().intersects(klong.Bounds1()) || o.upiconbounds().intersects(klong.Bounds2()))) {
				o.iconmove();//撞到后金币消失
				icon+=1;// 撞到，金币+1
			}
//障碍物与火焰碰撞			
			if(o.cucbounds().intersects(klong.firebounds())) {
				o.iconmove();//障碍物消失，因为障碍物的y就是障碍物上方金币的y，所以用iconmove障碍物一起消失
			}
			
		}
		
		
////**********************************************************测试			
//			rt = o.mogubounds();// 获取碰撞区域
//			g2.fillRect(rt.x, rt.y, rt.width, rt.height);// 画出边框（测试用）
////**********************************************************测试
			
			
		
//地面金币集合添加内容
		if (addiconTimer >= 100) {// 加金币速率
			list2.add(new object());//每50ms往金币集合里添加一个金币
		
			addiconTimer = 0;//计时器置零
			}
		
//输出地面金币集合（金币是特殊的障碍物）
		for(int i = 0; i < list2.size(); i++) {
			object o = list2.get(i);//o是障碍类的一个对象，用来调用画出障碍内里的金币图
			g2.drawImage(o.imageicon, o.x,o.y-5,this);
			o.move();//金币依次出现
		if (o.undergroundbounds().intersects(klong.bounds1()) || o.undergroundbounds().intersects(klong.bounds2())) {//恐龙与地面金币的碰撞检测，因为地面金币大恐龙小恐龙的碰撞体都会碰到，大恐龙的就没做
			o.iconmove();//吃到金币后金币消失
			icon+=1;//金币值+1
		
		}
	}
		if(klong.bounds1().intersects(o.mogubounds())) {//如果碰到蘑菇，就变成大恐龙	
			//****************************************小恐龙变大恐龙关键判断条件，这里改了碰撞和跳跃的icon条件都要变
			klong.turnbig();//恐龙是大的或小的全用bigstate判断,此行将bigstate置为true
		}
		
		
		
}

	
	
	
	
	

	// 游戏结束
	public void gameOver() {
		finish = true;
		g2.drawImage(background.image_over, background.x_over, background.y_over, null);
		

	}

	@Override
	public void paint(Graphics g) {
		painImage();
		g.drawImage(image, 0, 0, this);
	}

	public boolean isFinish() {//判断游戏结束状态,是finish的getter
		return finish;
	}

	// 跳跃键盘监听
	@Override
	public void keyTyped(KeyEvent e) {// 按键类型

	}

	@Override
	public void keyPressed(KeyEvent e) {// 按键按下
		int code = e.getKeyCode();// 获取按下的编码
		if (code == KeyEvent.VK_SPACE&klong.bigstate==false) {//如果空格被按下且金币数小于10，则调用小恐龙的跳跃
			klong.jump();}
		else if (code == KeyEvent.VK_SPACE&klong.bigstate==true) {//如果空格被按下且金币数大于100，则调用大恐龙的跳跃
			klong.bigjump();
		}
		else if(code==KeyEvent.VK_NUMPAD0) {
			klong.fire();
			}
	

	}


	@Override
	public void keyReleased(KeyEvent e) {// 按键抬起
	}
}
