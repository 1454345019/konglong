package tn;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

/**
 * 恐龙类
 */
public class kLong {
	public int x, y;// 小恐龙坐标
	public int X, Y;// 大恐龙坐标
	public int xfire, yfire;// 火焰坐标
	BufferedImage image;// 主图片
	BufferedImage fireimage;//火焰图片
	BufferedImage image1, image2, image3, image_over, big1, big2, big3,fire;
	int stepTime = 0;// 计时器
	int fresh = GamePanl.FRESH;
	Graphics2D g2;
	GamePanl p;

	
	boolean firestate =false;//火焰状态
	boolean bigstate = false;// 变大状态
	boolean jumpState = false;// 跳跃的状态
	boolean jumpbigState = false;//大恐龙跳跃状态
	int jumpHeight = 120;// 跳跃高度
	int jumpbigHeight=110;//变大后的跳跃高度
	final int LOWEST_Y = 200;// 最低坐标
	final int bigLOWEST_Y = 170;
	int jumpValue = 1;// 跳跃的增变量

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
		x = 50;//小恐龙起始坐标
		y = LOWEST_Y;
		X = 70;//大恐龙起始坐标
		Y = bigLOWEST_Y;
		xfire=-27;
		yfire=190;
		
	}

	public void move() {// 移动
		step();// 踏步
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
		
		if(firestate) {//如果按键被按下，火焰状态置为true
			if(xfire<800){//如果火焰还没飞出屏幕外
				fireimage=fire;//firestate为true才会画出火焰,要用fireimage内存区
				xfire+=1;//移动速度
			}
			if(xfire>=800) {
				xfire=-27;//如果火焰超出屏幕外，就返回原始位置
				firestate=false;//firestate置为false，等待重新按键
			}
		}
	}

	
	
	
	// 踏步
	void step() {
		int tmp = stepTime / 100 % 2;
		if (tmp == 1) {
			image = image1;
		} else {
			image = image2;
		}
	

		stepTime += fresh;

	}


//***************小恐龙碰撞
	public Rectangle bounds1() {//头
		return new Rectangle(x + 20, y, 20, 10);
	}

	public Rectangle bounds2() {//脚
		return new Rectangle(x + 5, y + 35, 20, 10);
	}

	
//***************大恐龙碰撞
	public Rectangle Bounds1() {//头
		return new Rectangle(X+40,Y,35,25);
	}
	
	public Rectangle Bounds2() {//脚
		return new Rectangle(X+13,Y+60,24,15);
	}
	
//****************火焰碰撞
	public Rectangle firebounds() {
		return new Rectangle(xfire,yfire,fire.getWidth(),fire.getWidth());
	}
	
	
	
	
	
	//变大的替换图片函数,功能与step相同,实现大恐龙踏步
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

	//实现大恐龙跳跃
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
	
	
	public void turnbig() {//大恐龙状态值置为true
		bigstate=true;
	}
	
	
	public void bigjump() {// 大恐龙跳跃
		jumpbigState = true;//大恐龙跳跃值置为true
	}
	

	public void jump() {// 小恐龙跳跃
		jumpState = true;
	}
	
	public void fire() {
		firestate=true;
	}
	

	
}
	
	
	
