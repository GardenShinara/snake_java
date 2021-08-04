import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable,KeyListener{
	
	public Body[] bodySnake = new Body[10];
	
	public boolean left,right,up,down;
	
	public int score = 0;
	
	public int macaX = 0, macaY = 0;
	
	public int spd = 10;
	public int framespd = 10;
	
	public Game() {
		this.setPreferredSize(new Dimension(540,540));
		for(int i = 0; i < bodySnake.length; i++) {
			bodySnake[i] = new Body(0,0);
		}
		this.addKeyListener(this);
	
	}

	public void base() {
		for(int i = bodySnake.length - 1; i > 0; i--) {
			bodySnake[i].x = bodySnake[i-1].x;
			bodySnake[i].y = bodySnake[i-1].y;
		}
		
		if(bodySnake[0].x + 10 < 0) {
			bodySnake[0].x=540;
		}else if(bodySnake[0].x >= 540) {
			bodySnake[0].x= -10;
		}
		if(bodySnake[0].y + 10 < 0) {
			bodySnake[0].y=540;
		}else if(bodySnake[0].y >= 540) {
			bodySnake[0].y= -10;
		}
		
		
		if(right) {
			bodySnake[0].x+=spd;
			collision();
		}else if(up) {
			bodySnake[0].y-=spd;
			collision();
		}else if(down) {
			bodySnake[0].y+=spd;
			collision();
		}else if(left) {
			bodySnake[0].x-=spd;
			collision();
		}
		
		if(new Rectangle(bodySnake[0].x, bodySnake[0].y,10,10).intersects(new Rectangle(macaX,macaY,10,10))) {
		macaX = new Random().nextInt(540-10);
		macaY = new Random().nextInt(540-10);
		score++;
		framespd++;
		System.out.println("Pontos: "+ score);
		}
	}
	

	
	public void collision() {
		for(int i = 0; i < bodySnake.length; i++){
			if(i == 0) continue;
			Rectangle box1 = new Rectangle(bodySnake[0].x, bodySnake[0].y,10,10);
			Rectangle box2 = new Rectangle(bodySnake[i].x, bodySnake[i].y,10,10);
			
			if(box1.intersects(box2)){
				System.out.println("Game Over! haha");
				framespd = 20;
				score = 0;
				right = false;
				left = false;
				down =  false;
				up = false;
				for(int o = 0; o < bodySnake.length; o++) {
					bodySnake[o] = new Body(0,0);
				}	
			}
		}
	}
	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, 540, 540);
		for(int i = 0; i < bodySnake.length; i++) {
			g.setColor(Color.black);
			g.fillRect(bodySnake[i].x,bodySnake[i].y, 10, 10);
		}
		
		g.setColor(Color.white);
		g.fillOval(macaX, macaY, 10, 10);
		
		g.dispose();
		bs.show();
		
		
	}
	
	

	public static void main(String args[]) {
		Game game = new Game();
		JFrame frame = new JFrame("Snake");
		frame.add(game);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		new Thread(game).start();
	
	
	}
	
	@Override
	public void run() {
		while(true){
			base();
			render();
			try {
				Thread.sleep(1000/framespd);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()== KeyEvent.VK_RIGHT) {
			right = true;
			left = false;
			up = false;
			down = false;
		}else if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			left = true;
			right = false;
			up = false;
			down = false;
		}else if(e.getKeyCode()==KeyEvent.VK_UP) {
			up = true;
			right = false;
			down = false;
			left = false;
		}else if(e.getKeyCode()==KeyEvent.VK_DOWN) {
			down = true;
			up = false;
			left = false;
			right = false;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
