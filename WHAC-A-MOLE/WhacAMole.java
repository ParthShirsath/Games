import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

class game extends JFrame implements ActionListener{
    JPanel topPanel=new JPanel();
    JPanel boardPanel=new JPanel();

    JButton tiles[]=new JButton[9];

    JLabel l1=new JLabel();

    int boardHeight=650;
    int boardWidth=600;

    ImageIcon monty;
    ImageIcon piranha;

    JButton currentMontyTile;
    JButton currentPiranhaTile;

    Random r=new Random();

    Timer setMontyTimer;
    Timer setPiranhaTimer;
    int Score = 0;

    game(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocation(400,25);
        setSize(new Dimension(boardWidth,boardHeight));

        Font f1=new Font("Calibari",Font.BOLD,20);
        l1.setFont(f1);
        l1.setText("Start The Game");
        l1.setHorizontalAlignment(JLabel.CENTER);
        topPanel.setLayout(new BorderLayout());
        topPanel.add(l1);
        add(topPanel,BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3,3));
        boardPanel.setBackground(Color.gray);
        add(boardPanel);

        Image piranhaImage = new ImageIcon(getClass().getResource("./piranha.png")).getImage();
        piranha =new ImageIcon(piranhaImage.getScaledInstance(150,150,java.awt.Image.SCALE_SMOOTH));
        
        Image montyImage =new ImageIcon(getClass().getResource("./monty.png")).getImage();
        monty=new ImageIcon(montyImage.getScaledInstance(150,150,java.awt.Image.SCALE_SMOOTH));


        for(int i=0;i<9;i++){
            JButton tile=new JButton();
            tiles[i]=tile;
            boardPanel.add(tile);
            tile.setFocusable(false);
            tile.addActionListener(this);
        }
        setMontyTimer=new Timer(1000,new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(currentMontyTile != null){
                    currentMontyTile.setIcon(null);
                    currentMontyTile=null;
                }

                int num=r.nextInt(9);
                JButton tile1=tiles[num];

                if(currentPiranhaTile == tile1) return;

                currentMontyTile=tile1;
                currentMontyTile.setIcon(monty);
            }
        });
        setMontyTimer.start();

        setPiranhaTimer=new Timer(1500,new ActionListener(){
            public void actionPerformed(ActionEvent a){
                if(currentPiranhaTile != null){
                    currentPiranhaTile.setIcon(null);
                    currentPiranhaTile=null;
                }
                int num=r.nextInt(9);

                JButton tile1=tiles[num];

                
                if(currentMontyTile == tile1) return;

                currentPiranhaTile=tile1;
                currentPiranhaTile.setIcon(piranha);

            }
        });

        setPiranhaTimer.start();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton tile=(JButton) e.getSource();
        if(tile== currentMontyTile){
            Score+=10;
            l1.setText("Score:"+Score);
    }
        else if (tile == currentPiranhaTile){
            l1.setText("GameOver"+Score);
            setMontyTimer.stop();            
            setPiranhaTimer.stop(); 
            for(int i=0;i<9;i++){
                tiles[i].setEnabled(false);
            }
            int retry=JOptionPane.showConfirmDialog(this,"Do you want to retry");
            if(retry==JOptionPane.YES_OPTION){
                l1.setText("Start The Game");
                setMontyTimer.start();            
                setPiranhaTimer.start(); 
                for(int i=0;i<9;i++){
                    tiles[i].setEnabled(true);
                }
                Score=0;
            }
            else if(retry==JOptionPane.NO_OPTION){
                dispose();
            }
        }
    }
}
class main_game{
    public static void main(String abc[])
    {
        new game();
    }
}