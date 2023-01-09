
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class GamePanel extends JPanel implements Runnable{

    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int)(1000 * (0.555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
    static final int BALL_DIA = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;

    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;

    GamePanel(){

        newPaddel();
        newBall();
        score = new Score(GAME_WIDTH,GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();
    }
    public void newBall(){

        ball = new Ball((GAME_WIDTH/2) - (BALL_DIA/2),(GAME_HEIGHT/2) - (BALL_DIA/2),BALL_DIA,BALL_DIA);

    }


    public void newPaddel(){

        paddle1 = new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
        paddle2 = new Paddle((GAME_WIDTH-PADDLE_WIDTH),(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);
    }

    public void paint(Graphics g){

        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);
    }

    public void draw(Graphics g){
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
    }

    public void move(){

        paddle1.move();
        paddle2.move();
        ball.move();
    }

    public void checkCollision(){
        //stops @ edge
        if(paddle1.y <= 0){
            paddle1.y = 0;
        }
        if(paddle1.y >= (GAME_HEIGHT-PADDLE_HEIGHT)){
            paddle1.y = GAME_HEIGHT-PADDLE_HEIGHT;
        }

        if(paddle2.y <= 0){
            paddle2.y = 0;
        }
        if(paddle2.y >= (GAME_HEIGHT-PADDLE_HEIGHT)){
            paddle2.y = GAME_HEIGHT-PADDLE_HEIGHT;
        }
        //bounce the ball @ edge

        if(ball.y <= 0){
            ball.setYDirection(-ball.yVelocity);
        }
        if(ball.y >= (GAME_HEIGHT-BALL_DIA))
        {
            ball.setYDirection(-ball.yVelocity);
        }

        //paddle bounce

        if(ball.intersects(paddle1)){
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++;
            if(ball.yVelocity > 0) ball.yVelocity++;
            else ball.yVelocity--;

            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        if(ball.intersects(paddle2)){
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++;
            if(ball.yVelocity > 0) ball.yVelocity++;
            else ball.yVelocity--;

            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        //stops the ball and gives point

        if(ball.x < 0){
            score.player2++;
            newPaddel();
            newBall();
            System.out.println(score.player2);
        }
        if(ball.x > (GAME_WIDTH-BALL_DIA)){
            score.player1++;
            newPaddel();
            newBall();
            System.out.println(score.player1);
        }
    }

    public void run(){

        //gameloop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while(true){
            long now = System.nanoTime();
            delta +=  (now-lastTime)/ns;
            lastTime = now;
            if(delta >= 1)
            {
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }

    public class AL extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e) {
            paddle1.KeyPressed(e);
            paddle2.KeyPressed(e);

        }

        @Override
        public void keyReleased(KeyEvent e) {
            paddle1.KeyReleased(e);
            paddle2.KeyReleased(e);
        }
    }
}
