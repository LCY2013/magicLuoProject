package com.lcydream.project.designmode.prototype.greatestsage;

import java.io.*;
import java.util.Date;

/**
 * TheGreatestSage
 *  齐天大圣
 * @author Luo Chun Yun
 * @date 2018/6/24 20:19
 */
public class TheGreatestSage extends Monkey implements Cloneable,Serializable {

	/**
	 * 金箍棒
	 */
	private GoldRingedStaff goldRingedStaff;

	/**
	 * 从石头缝蹦出来
	 */
	public TheGreatestSage(){
		this.goldRingedStaff = new GoldRingedStaff();
		this.setBirthday(new Date());
		this.setHeight(150);
		this.setWeight(30);
        System.out.println("-----------克隆不会走构造方法，是通过字节码复制来实现的----------");
	}

	/**
	 * 复写克隆，72变，深度克隆
	 * @return
	 * @throws CloneNotSupportedException
	 */
	@Override
	protected Object clone(){
		//深度克隆
		ByteArrayOutputStream bos=null;
		ObjectOutputStream oos=null;
		ByteArrayInputStream bis=null;
		ObjectInputStream ois=null;
		try{
			//序列化
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(this);

			//反序列化
			bis = new ByteArrayInputStream(bos.toByteArray());
			ois = new ObjectInputStream(bis);
			TheGreatestSage theGreatestSage = (TheGreatestSage)ois.readObject();
			theGreatestSage.setBirthday(new Date());
			return theGreatestSage;
		      /**
		       * return super.clone(); 这种默认的克隆是浅克隆，会克隆的类型是八大基本类型和String类型，
		       * 其他的引用类型直接复制的引用也就是和被复制的对象是同一个对象，不会参数新对象
		       */
    } catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			try {
				bos.close();
				oos.close();
				bis.close();
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 变化
	 */
	public void change(){
		TheGreatestSage theGreatestSage = (TheGreatestSage)clone();
        System.out.println("大圣本尊生日是："+this.getBirthday().getTime());
        System.out.println("克隆大圣生日是："+theGreatestSage.getBirthday().getTime());
        System.out.println("大圣本尊和克隆大圣是否是同一个对象："+(this == theGreatestSage));
        System.out.println("大圣本尊和克隆大圣的金箍棒是否是同一个对象："+(this.getGoldRingedStaff() == theGreatestSage.getGoldRingedStaff()));

	}

	public GoldRingedStaff getGoldRingedStaff() {
		return goldRingedStaff;
	}

	public void setGoldRingedStaff(GoldRingedStaff goldRingedStaff) {
		this.goldRingedStaff = goldRingedStaff;
	}
}
