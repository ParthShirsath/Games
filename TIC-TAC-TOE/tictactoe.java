import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
class tictactoe extends JFrame implements ActionListener{
    
    JLabel l1=new JLabel();
    JPanel p1=new JPanel();

    JPanel board=new JPanel();

    JButton boardbutton[][]=new JButton[3][3];

    String playerx="X";
    String playero="O";
    String currplayer=playerx;

    boolean gameover=false;

    int cnt=0; 
    tictactoe(){
        setVisible(true);
        setSize(600,650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Tic-Tac-Toe");
        setLayout(new BorderLayout());

        l1.setBackground(Color.gray);
        //l1.setForeground(Color.white);
        l1.setHorizontalAlignment(JLabel.CENTER);
        l1.setText("TIC-TAC-TOE");
        Font f1=new Font("Arial",Font.BOLD,50);
        l1.setFont(f1);

        p1.setLayout(new BorderLayout());
        p1.add(l1);
        add(p1,BorderLayout.NORTH);

        board.setLayout(new GridLayout(3,3));
        board.setBackground(Color.gray);
        add(board);

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                JButton box=new JButton();
                boardbutton[i][j]=box;
                board.add(box);
                Font f2=new Font("Arial",Font.BOLD,120);
                box.setFont(f2);
                box.addActionListener(this);
            }
        }
    }
    public void actionPerformed(ActionEvent ae){

        if(gameover)
        return;

        JButton box=(JButton) ae.getSource();
        if(cnt==8)
        l1.setText("Its Tie Match");
        if(box.getText()== ""){
        box.setText(currplayer);
        cnt++;
        check();
        if(!gameover){
            if(currplayer==playerx)
            currplayer=playero;
            else
            currplayer=playerx;

            l1.setText(currplayer+"'s turn.");
            }
        }  
    }
    void check(){
        for(int i=0;i<3;i++){
            if(boardbutton[i][0].getText()=="")
            continue;
            if(boardbutton[i][0].getText()==boardbutton[i][1].getText() && boardbutton[i][1].getText()==boardbutton[i][2].getText())
            {
                for(int r=0;i<3;r++){
                    setwinner(boardbutton[i][r]);
                }
                gameover=true;
                return;
            }
        }
        for(int j=0;j<3;j++){
            if(boardbutton[0][j].getText()=="")
            continue;
            if(boardbutton[0][j].getText()==boardbutton[1][j].getText() && boardbutton[1][j].getText()==boardbutton[2][j].getText())
            {
                for(int c=0;c<3;c++){
                    setwinner(boardbutton[c][j]);
                }
                gameover=true;
                return;
            }
        }
        if(boardbutton[0][0].getText()==boardbutton[1][1].getText() && 
           boardbutton[1][1].getText()==boardbutton[2][2].getText() &&
           boardbutton[0][0].getText()!="")
           {
            for(int i=0;i<3;i++)
            setwinner(boardbutton[i][i]);

            gameover=true;
            return;
           }
           if(boardbutton[0][2].getText()==boardbutton[1][1].getText() && 
           boardbutton[1][1].getText()==boardbutton[2][0].getText() &&
           boardbutton[0][2].getText()!="")
           {
            setwinner(boardbutton[0][2]);
            setwinner(boardbutton[1][1]);
            setwinner(boardbutton[2][0]);

            gameover=true;
            return;
           }
           if(cnt==9){
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                   settie(boardbutton[i][j]);
                }
            }
            gameover=true;
        }
    }
    void setwinner(JButton box){
        box.setBackground(Color.red);
        box.setForeground(Color.green);
        l1.setText("Player "+currplayer+" is Winner");
    }
    void settie(JButton box){
        box.setBackground(Color.gray);
        box.setForeground(Color.red);
        l1.setText("It's Tie!");
    }
}
class demo{
	public static void main(String[] args) {
        new tictactoe();
	}
}