package Define;

import java.util.Random;

import javax.swing.ImageIcon;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Dice {
    private int dice1;
    private int dice2;
    private Random random; 

    public Dice(){
        random=new Random();
    }
    public void roll(){
        dice1=random.nextInt(6)+1;
        dice2=random.nextInt(6)+1;
    }

    public int getDie1() {
        return dice1;
    }

    public int getDie2() {
        return dice2;
    }

    public int getTotal() {
        return dice1 + dice2;
    }

    public boolean isDouble() {
        return dice1 == dice2;
    }

    public static Map<Integer,ImageIcon> diceMap(){
        Map<Integer,ImageIcon> map=new HashMap<>();
        List<BufferedImage> diceList=image.diceList();
        for(int i=0;i<6;i++){
            map.put(i+1,new ImageIcon(diceList.get(i)));
        }
        return map;
    }
    
}
