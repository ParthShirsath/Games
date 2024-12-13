import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;


class flappybird1 extends JPanel implements ActionListener,KeyListener{

    Image background,bird,toppipe,bottompipe;
    int birdx=360/8;
    int birdy=640/2;
    int birdwidth=34;
    int birdheight=24;

    class Bird{
        int x=birdx;
        int y=birdy;
        int width=birdwidth;
        int height=birdheight;

        Image img;
        Bird(Image img){
            this.img=img;
        }
    }

    int pipex=360;
    int pipey=0;
    int pipewidth=64;
    int pipeheight=512;
    class Pipe{
        int x=pipex;
        int y=pipey;
        int width=pipewidth;
        int height=pipeheight;
        Image img;
        boolean pass=false;
        Pipe(Image img){
            this.img=img;
        }
    }



    Bird bird1;

    ArrayList<Pipe> pipes;

    Timer loop;

    Timer placePipe;

    Boolean gameover=false;
    int speedx=-4;
    int speedy=0;
    int gravity=1;

    double score=0;

    Random r=new Random();

    flappybird1(){
        Dimension d=new Dimension(360,640);
        setPreferredSize(d);

        setFocusable(true);

        addKeyListener(this);

        background=new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        bird=new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        toppipe=new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottompipe=new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        bird1=new Bird(bird);

        pipes=new ArrayList<Pipe>();
        placePipe=new Timer(1500,new ActionListener() {
           public void actionPerformed(ActionEvent ae){
            placepipe();
           }
        });
        placePipe.start();
        loop=new Timer(1000/60,this);
        loop.start();

        
    }
    
    public void placepipe(){
        
        int randomPipeY=(int)(pipey - pipeheight/4 - Math.random()*(pipeheight/2));

        int passingSpace =640/4;

        Pipe toppipes=new Pipe(toppipe);
        pipes.add(toppipes);
        
        toppipes.y=randomPipeY;

        Pipe bottompipes=new Pipe(bottompipe);
        bottompipes.y=toppipes.y+pipeheight+passingSpace;

        pipes.add(bottompipes);

    }
    
    public void paintComponent(Graphics g){  //method of JPanel class
        super.paintComponent(g);  //parameterized constructor of paintComponent class
        draw(g);
    }
    
    
    public void draw(Graphics g){
        g.drawImage(background,0,0,360,640,null);
        g.drawImage(bird1.img,bird1.x,bird1.y,bird1.width,bird1.height,null);

        for(int i=0;i<pipes.size();i++){
            Pipe pipe=pipes.get(i);
            g.drawImage(pipe.img,pipe.x,pipe.y,pipe.width,pipe.height,null);
        }

        g.setColor(Color.black);
        Font f1=new Font("Arial",Font.BOLD,20);
        g.setFont(f1);
        if(gameover){
            g.drawString("Game Over"+String.valueOf((int)score),10,35);
            g.drawString("Restart Game using Spacebar",10,60);
        }
        else{
            g.drawString("Score"+String.valueOf((int)score),10,35);;
        }
    }

    public void move(){
        speedy+=gravity;

        bird1.y=bird1.y+speedy;
        
        bird1.y=Math.max(bird1.y,0);

        for(int i=0;i<pipes.size();i++){
            Pipe pipe=pipes.get(i);
            pipe.x+=speedx;

            if(!pipe.pass && bird1.x > pipe.x + pipe.width){
                pipe.pass=true;
                score+=0.5;//0.5 for top pipe and 0.5 for bottom pipe therefore 0.5*2=1
            }

            if(collision(bird1,pipe)){
            gameover=true;
            }
        }

        if(bird1.y>640){
            gameover=true;
        }
        if(bird1.y==0){
            gameover=true;
        }

    }

    public boolean collision(Bird a,Pipe b){
        return a.x < b.x + b.width && a.x + a.width > b.x && a.y < b.y + b.height && a.y + a.height > b.y;
    }

    public void actionPerformed(ActionEvent ae) {
        move();
        repaint();
        if(gameover==true){
            placePipe.stop();
            loop.stop();
        }
    }


    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_SPACE){
            speedy=-9;

            if(gameover==true){
                //restart the game 
                bird1.y=birdy;
                speedy=0;
                pipes.clear();
                score=0;
                gameover = false;
                loop.start();
                placePipe.start();
            }
        }
    }

    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}

public class flappybird extends JFrame{
    flappybird(){
        setLocation(10,10);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(360,640);
        setTitle("FlappyBird");
        setResizable(false);
        
        flappybird1 f1=new flappybird1();

        add(f1);
        pack();
        setVisible(true);
        f1.requestFocus();

    }
}

class flappydemo{
    public static void main(String abc[]) throws Exception{
        flappybird ob=new flappybird();
    }
}
