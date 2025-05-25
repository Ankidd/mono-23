package Define;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class keyHandler implements KeyListener {
        public boolean roll_button=false;
        public boolean exit =false;

        public keyHandler(){

        }
        
        @Override
        public void keyTyped(KeyEvent e){

        }
    
        @Override
        public void keyPressed(KeyEvent e){
            int code=e.getKeyCode();

            if(code==KeyEvent.VK_ESCAPE){
                exit=true;
            }

            if(code==KeyEvent.VK_SPACE){
                roll_button=true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e){
            
        }
}
