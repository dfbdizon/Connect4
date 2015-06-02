import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.net.*;
import javax.sound.sampled.*;

public class UI extends JFrame {
    int start = 0;
    int playerNum = 2;
    int turn = 2;
    boolean flag = false;
    String name = "";
    JFrame gameFrame;

    JPanel boardPanel;
    JPanel statusPanel;

    JButton col1;
    JButton col2;
    JButton col3;
    JButton col4;
    JButton col5;
    JButton col6;
    JButton col7;

    JLabel playerName;
    JLabel boardL;
    JLabel statusL;

    Dimension sSize;

    JLayeredPane statPanel;

    ImageIcon board;
    ImageIcon status;
    ImageIcon playerToken;
    ImageIcon aiToken;

    BufferedImage colIcon;

    Graphics g;

    HashMap<Integer, String> config;
    Connect4 gameLogic;
     
    final Color bgColor;
     
    public void init(){
        //System.out.println("turn: " + turn);
        gameFrame = new JFrame();
        try {
            String btnHover = "./assets/button_column_hover.png";
            String btnPressed = "./assets/button_column_click.png";
            colIcon = ImageIO.read(new File("./assets/button_column.png"));
            col1 = new JButton(new ImageIcon(colIcon));
            col2 = new JButton(new ImageIcon(colIcon));
            col3 = new JButton(new ImageIcon(colIcon));
            col4 = new JButton(new ImageIcon(colIcon));
            col5 = new JButton(new ImageIcon(colIcon));
            col6 = new JButton(new ImageIcon(colIcon));
            col7 = new JButton(new ImageIcon(colIcon));
            col1.setRolloverIcon(new ImageIcon(btnHover));
            col1.setPressedIcon(new ImageIcon(btnPressed));
            col2.setRolloverIcon(new ImageIcon(btnHover));
            col2.setPressedIcon(new ImageIcon(btnPressed));
            col3.setRolloverIcon(new ImageIcon(btnHover));
            col3.setPressedIcon(new ImageIcon(btnPressed));
            col4.setRolloverIcon(new ImageIcon(btnHover));
            col4.setPressedIcon(new ImageIcon(btnPressed));
            col5.setRolloverIcon(new ImageIcon(btnHover));
            col5.setPressedIcon(new ImageIcon(btnPressed));
            col6.setRolloverIcon(new ImageIcon(btnHover));
            col6.setPressedIcon(new ImageIcon(btnPressed));
            col7.setRolloverIcon(new ImageIcon(btnHover));
            col7.setPressedIcon(new ImageIcon(btnPressed));

            board = new ImageIcon("./assets/board.png");
            status = new ImageIcon("./assets/status.png");
            boardL = new JLabel();
            statusL = new JLabel();
            playerName = new JLabel();
            statPanel = new JLayeredPane();

            playerToken = new ImageIcon("./assets/token_red.png");
            aiToken = new ImageIcon("./assets/token_yellow.png");

            playerName.setText(name);
            playerName.setFont(new Font("Helvetica", Font.PLAIN, 35));
            Dimension plSize = playerName.getPreferredSize();
            playerName.setBounds(30, 95, plSize.width, plSize.height);

            statusL.setIcon(status);
            sSize = statusL.getPreferredSize();
            statusL.setBounds(0, 0, sSize.width, sSize.height);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        col1.setBorder(BorderFactory.createEmptyBorder());
        col1.setContentAreaFilled(false);
        col2.setBorder(BorderFactory.createEmptyBorder());
        col2.setContentAreaFilled(false);
        col3.setBorder(BorderFactory.createEmptyBorder());
        col3.setContentAreaFilled(false);
        col4.setBorder(BorderFactory.createEmptyBorder());
        col4.setContentAreaFilled(false);
        col5.setBorder(BorderFactory.createEmptyBorder());
        col5.setContentAreaFilled(false);
        col6.setBorder(BorderFactory.createEmptyBorder());
        col6.setContentAreaFilled(false);
        col7.setBorder(BorderFactory.createEmptyBorder());
        col7.setContentAreaFilled(false);

        col1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                //System.out.println("Add token on col1");
                if(turn == playerNum) addToken(1, 2);
            }
        });          
        col2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                //System.out.println("Add token on col2");
                if(turn == playerNum) addToken(2, 2);
            }
        });          
        col3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                //System.out.println("Add token on col3");
                if(turn == playerNum) addToken(3, 2);
            }
        });          
        col4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                //System.out.println("Add token on col4");
                if(turn == playerNum) addToken(4, 2);
            }
        });          
        col5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                //System.out.println("Add token on col5");
                if(turn == playerNum) addToken(5, 2);
            }
        });          
        col6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                //System.out.println("Add token on col6");
                if(turn == playerNum) addToken(6, 2);
            }
        });          
        col7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                //System.out.println("Add token on col7");
                if(turn == playerNum) addToken(7, 2);
            }
        });
    }
     
    public UI(String name, HashMap<Integer, String> config, Connect4 gameLogic){
        init();
        this.name = name;
        this.config = config; 
        this.gameLogic = gameLogic;

        gameFrame.setTitle("Connect4");
        gameFrame.setSize(900, 575);
        gameFrame.setResizable(false);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setLayout(null);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        bgColor = new Color(38, 38, 38);
        gameFrame.getContentPane().setBackground(bgColor);

        newGame();
        gameFrame.setVisible(true);
    }

    public void newGame(){
        final int colBtnWidth = 78;
        final int colBtnHeight = 78;

        boardL.setIcon(board);
        Dimension bSize = boardL.getPreferredSize();
        boardL.setBounds(29, 65, bSize.width, bSize.height);

        final int colBtnW = 50, colBtnH = 50;
        int initPos = 80, addPos = colBtnW+20, yPos = 15;
        col1.setBounds(initPos, yPos, colBtnW, colBtnH);
        col2.setBounds(initPos + addPos, yPos, colBtnW, colBtnH);
        col3.setBounds(initPos + addPos*2, yPos, colBtnW, colBtnH);
        col4.setBounds(initPos + addPos*3, yPos, colBtnW, colBtnH);
        col5.setBounds(initPos + addPos*4, yPos, colBtnW, colBtnH);
        col6.setBounds(initPos + addPos*5, yPos, colBtnW, colBtnH);
        col7.setBounds(initPos + addPos*6, yPos, colBtnW, colBtnH);

        gameFrame.add(boardL);
        gameFrame.add(statusL);
        gameFrame.add(col1);
        gameFrame.add(col2);
        gameFrame.add(col3);
        gameFrame.add(col4);
        gameFrame.add(col5);
        gameFrame.add(col6);
        gameFrame.add(col7);

        updateStatus(1);
        gameFrame.repaint();
    }
     
    public void addToken(int col, int player) {
        JLabel tokenL = new JLabel();
        System.out.println("Turn: " + player);
        /* If human player */
        if(turn == playerNum) tokenL.setIcon(playerToken);
        else tokenL.setIcon(aiToken);

        Dimension tSize = tokenL.getPreferredSize();
        int occupiedRow = getOccupiedRow(col) + 1;
        if (occupiedRow < 6) {
            final int tokenW = 59, tokenH = 60;
            int initPos = 74, addPos = tokenW+11;
            int yPos = 397 - (occupiedRow * (tokenH + 3)) ; // temporary
            tokenL.setBounds(initPos + addPos * (col - 1), yPos, tokenW, tokenH);
            
            gameFrame.add(tokenL);
            gameFrame.repaint();

            turn = gameLogic.addPiece(col, occupiedRow, player);
            //gameLogic2.addPiece(col, occupiedRow, player);
            System.out.println("returned: " + turn);
        }
        updateStatus(turn);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try{
                    if(turn == 1) gameLogic.moveAI();
                    //if(turn == 1) gameLogic1.moveAI();
                    //else if (turn == 2) gameLogic2.moveAI();
                    //System.out.println("MOVINGMOVINGMOVING");
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        //if(turn == 1) gameLogic.moveAI(1);
        //else gameLogic.moveAI(2);
    }
    
    public int getOccupiedRow(int col){
        String currentCol = config.get(col);
        for(int i = 0; i < 6; i++){
            if(currentCol.charAt(i) == '0') return(i - 1);
        }
        return 5;
    }
    public void updateStatus(int turn){
        this.turn = turn;
        gameFrame.remove(statPanel);
        statPanel.removeAll();
        gameFrame.repaint();

        statPanel.add(statusL, JLayeredPane.DEFAULT_LAYER);
        statPanel.add(playerName, JLayeredPane.MODAL_LAYER);
        statPanel.setBounds(649, 65, sSize.width, sSize.height);

        JLabel stat = new JLabel();
        if(turn == 3){
            stat.setText("You Win!");
            stat.setFont(new Font("Serif", Font.PLAIN, 40));
            Dimension stSize = stat.getPreferredSize();
            stat.setBounds(25, 250, stSize.width, stSize.height);
            Main.playAgain(2);

        }
        else if(turn == 4){
            stat.setText("You Lose.");
            stat.setFont(new Font("Serif", Font.PLAIN, 40));
            Dimension stSize = stat.getPreferredSize();
            stat.setBounds(25, 250, stSize.width, stSize.height);
            Main.playAgain(1);
        }
        else if(turn == 5){
            stat.setText("Draw");
            stat.setFont(new Font("Serif", Font.PLAIN, 40));
            Dimension stSize = stat.getPreferredSize();
            stat.setBounds(25, 250, stSize.width, stSize.height);
            Main.playAgain(3);
        }
        else if(playerNum == turn) {
            stat.setText("Your Turn");
            stat.setFont(new Font("Serif", Font.PLAIN, 40));
            Dimension stSize = stat.getPreferredSize();
            stat.setBounds(25, 250, stSize.width, stSize.height);
        }
        else{
            stat.setText("Computer's Turn");
            stat.setFont(new Font("Serif", Font.PLAIN, 29));
            Dimension stSize = stat.getPreferredSize();
            stat.setBounds(15, 250, stSize.width, stSize.height);
        }

        statPanel.add(stat, JLayeredPane.MODAL_LAYER);
        gameFrame.add(statPanel);
        gameFrame.repaint();
        //System.out.println("stat updated");
    }

    public void playerWins(){
        updateStatus(3);
    }
     
    public void playerLoses(){
        updateStatus(4);
    }
     
    public void draw(){
        updateStatus(5);
    }     
}