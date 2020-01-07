package tn;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * 障碍物类
 */
public class object {
	kLong klong = new kLong();
	Random r = new Random();
	int x=800, y=209;//障碍物初始位置,也是地面金币初始位置
	int xmogu=700,ymogu=90;//蘑菇初始位置




	public boolean judge = false;
	BufferedImage image;
	BufferedImage imageicon;
	BufferedImage imagemogu;
	BufferedImage cactus01, cactus02, cactus03, bird1, bird2,icon,mogu,paotai;// 创建仙人掌+鸟+金币+蘑菇
	int speed = BackgroundImage.SPEED-1;
	public int temp = r.nextInt(4) + 1;// 1-2-3

	public object() {
        //读取图片
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
        
        imageicon=icon;//直接调用g2画出地面金币

        //随机输出障碍物
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

	// 鸟的运动
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

	// 滚动方法（障碍物和地面金币）
	public void move() {
		x -= speed*2;

	}

	
	public void iconmove() {//金币消失方法
		y-=700;
	}
	
	public void mogumove() {//蘑菇运动方法
		xmogu-=speed;
	}
	
	

	
	// 死亡区域
	public Rectangle cucbounds() {//仙人掌的碰撞区域
		return new Rectangle(x, y + 2, image.getWidth() - 2, image.getHeight() - 10);//随机会出现的用image内存空间
		
	}
	
	public Rectangle birdbounds() {//鸟的碰撞区域
		return new Rectangle(x,y,image.getWidth(),image.getHeight());
	}
	
	
	public Rectangle upiconbounds() {//障碍物上方金币的碰撞区域
		return new Rectangle(x,y-100,icon.getWidth(),icon.getHeight());//一定会出现的直接调用图片的大小
	}
	
	
	
	public Rectangle undergroundbounds() {//地面金币的碰撞区域
		return new Rectangle(x,y,icon.getWidth(),icon.getHeight());
	}
	
	public Rectangle mogubounds() {//蘑菇的碰撞区域
		return new Rectangle(xmogu,ymogu-25,mogu.getWidth(),mogu.getHeight()-26);
	}
	
	
	
		
}
