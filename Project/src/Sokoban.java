import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.border.BevelBorder;


public class Sokoban extends JFrame implements ActionListener{

    private static final long serialVersionUID = 1L;
    JMenuBar menuBar;
    JMenu mnuStyles,mnuSet,mnuHelp;
    JMenuItem miStyle1, miStyle2, miStyle3, miExit;
    JMenuItem miMusic1,miMusic2,miMusic3,miMusic4,miMusic5;
    JMenuItem miHelp;
    Boolean Completed = false, menu = true;
    String styles[] = {
    		"style1",
    		"style2",
    		"style3"
    };
    String currStyle = styles[0];
    //music file
    String sMusic[] = {
            "casino.mid",
            "forest.mid",
            "luf2casino.mid",
            "party.mid",
            "tom.mid"
    };
    String musicName[]={
            "Casino",
            "Forest",
            "Jumper",
            "Party",
            "Go ahead"

    };
    String saveName[] = {
    	"Select Save",
    	"Save 1",
    	"Save 2",
    	"Save 3"
    };
    Music music;
    GameEngine game;
    Container c;

    //game panel
    gamePanel mainPanel;
    menuPanel menuPanel;
    JLabel lblTitle;
    JPanel cardLayout;
    
    /**
     * Constructor for the GUI frame.
     * @param currGame
     */
    public Sokoban(GameEngine currGame){
        super("Warehouse Boss");

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("pic/0.png");
        //set icon
        this.setIconImage(image);
        c = this.getContentPane();
        c.setLayout(new BorderLayout());
        c.setBackground(Color.orange);

        lblTitle = new JLabel(new ImageIcon("pic/game_logo.png"));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
        c.add(lblTitle, BorderLayout.PAGE_START);
        setMenus();
        Completed = false;

        music = new Music();

        game = currGame;
        cardLayout = new JPanel();
        cardLayout.setLayout(new CardLayout());
        menuPanel = new menuPanel(cardLayout);
        mainPanel = new gamePanel(cardLayout);
        cardLayout.add(menuPanel, "Main Menu");
        
        c.add(cardLayout);
        
        this.setContentPane(c);
        this.pack();
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);

        //keyboard input code
        addKeyListener(new TAdapter());
        setFocusable(true);
        Timer timer = new Timer(10, this);
        timer.start();
        
        this.setVisible(true);
    }


    /**
     * Creates the menu-bar for the GUI window.
     */
    public void setMenus(){
        mnuStyles = new JMenu("Styles");
        miExit = new JMenuItem("Exit");
        miStyle1 = new JMenuItem("Style 1");
        miStyle2 = new JMenuItem("Style 2");
        miStyle3 = new JMenuItem("Style 3");

        mnuStyles.add(miStyle1);
        mnuStyles.add(miStyle2);
        mnuStyles.add(miStyle3);
        mnuStyles.addSeparator();
        mnuStyles.add(miExit);

        mnuSet = new JMenu("Music");
        miMusic1 = new JMenuItem("Casino");
        miMusic2 = new JMenuItem("Forest");
        miMusic3 = new JMenuItem("Jumper");
        miMusic4 = new JMenuItem("Party");
        miMusic5 = new JMenuItem("Go Ahead");
        mnuSet.add(miMusic1);
        mnuSet.add(miMusic2);
        mnuSet.add(miMusic3);
        mnuSet.add(miMusic4);
        mnuSet.add(miMusic5);

        mnuHelp = new JMenu("Help");
        mnuHelp.setMnemonic('H');
        miHelp = new JMenuItem("About us...");
        mnuHelp.add(miHelp);

        miStyle1.addActionListener(this);
        miStyle2.addActionListener(this);
        miStyle3.addActionListener(this);
        miExit.addActionListener(this);
        miMusic1.addActionListener(this);
        miMusic2.addActionListener(this);
        miMusic3.addActionListener(this);
        miMusic4.addActionListener(this);
        miMusic5.addActionListener(this);
        miHelp.addActionListener(this);

        menuBar = new JMenuBar();
        menuBar.add(mnuStyles);
        menuBar.add(mnuSet);
        menuBar.add(mnuHelp);
        this.setJMenuBar(menuBar);
    }

    /**
     * Detects actions made by the user.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(game.isFinished() && !Completed){
            Completed = true;
            Object messages[] = {
            	"OK",
            	"New Game"
            };
            int n = JOptionPane.showOptionDialog(this,
				    "You Win!",
				    "Congratulations!",
				    JOptionPane.YES_NO_OPTION,
				    JOptionPane.INFORMATION_MESSAGE,
				    null,
				    messages,
				    messages[0]);
            if (n == 1) {
            	game.newGame();
				Completed = false;
				menu = false;
				mainPanel = new gamePanel(cardLayout);
	        	cardLayout.add(mainPanel);
	        	((CardLayout)cardLayout.getLayout()).next(cardLayout);
            }
        }
        if(e.getSource().equals(miHelp)){
            String str = "COMP2911\n";
            str +="Assignment3\n";
            JOptionPane.showMessageDialog(this, str, "Help", JOptionPane.INFORMATION_MESSAGE);
        } else if(e.getSource().equals(miExit)){
            System.exit(0);
        } else if (e.getSource().equals(miStyle1)) {
        	currStyle = styles[0];
        	mainPanel = new gamePanel(cardLayout);
        	cardLayout.add(mainPanel);
        	((CardLayout)cardLayout.getLayout()).next(cardLayout);
        } else if (e.getSource().equals(miStyle2)) {
        	currStyle = styles[1];
        	mainPanel = new gamePanel(cardLayout);
        	cardLayout.add(mainPanel);
        	((CardLayout)cardLayout.getLayout()).next(cardLayout);
        } else if (e.getSource().equals(miStyle3)) {
        	currStyle = styles[2];
        	mainPanel = new gamePanel(cardLayout);
        	cardLayout.add(mainPanel);
        	((CardLayout)cardLayout.getLayout()).next(cardLayout);
        } else if(e.getSource().equals(miMusic1)){
            music.setMusic(sMusic[0]);
            if(music.isPlay()){
                music.stopMusic();
            }
            music.loadSound();
        } else if(e.getSource().equals(miMusic2)){
            music.setMusic(sMusic[1]);
            if(music.isPlay()){
                music.stopMusic();
            }
            music.loadSound();
        } else if(e.getSource().equals(miMusic3)){
            music.setMusic(sMusic[2]);
            if(music.isPlay()){
                music.stopMusic();
            }
            music.loadSound();
        } else if(e.getSource().equals(miMusic4)){
            music.setMusic(sMusic[3]);
            if(music.isPlay()){
                music.stopMusic();
            }
            music.loadSound();
        } else if(e.getSource().equals(miMusic5)){
            music.setMusic(sMusic[4]);
            if(music.isPlay()){
                music.stopMusic();
            }
            music.loadSound();
        }
    }

    /**
     * Detects keyboard input from the user, specifically the arrow keys.
     */
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                game.makeMove(Map.MOVE_LEFT);               
            }

            if (key == KeyEvent.VK_RIGHT) {
                game.makeMove(Map.MOVE_RIGHT);
            }

            if (key == KeyEvent.VK_UP) {
                game.makeMove(Map.MOVE_UP);
            }

            if (key == KeyEvent.VK_DOWN) {
                game.makeMove(Map.MOVE_DOWN);
            }
            
            if (menu == false) {
            	mainPanel = new gamePanel(cardLayout);
        		cardLayout.add(mainPanel);
        		((CardLayout)cardLayout.getLayout()).next(cardLayout);
        		c.revalidate();
        		c.repaint();
        	}
        }
    }



    /**
     *
     */
     class MyPanel extends JPanel{

        private static final long serialVersionUID = 1L;
        private int[][] oriMap;
        private int[][] tempMap;

        Toolkit kit = Toolkit.getDefaultToolkit();
        Image mapimg[] = {
                kit.getImage("pic/" + currStyle + "/0.jpg"),
                kit.getImage("pic/" + currStyle + "/1.png"),
                kit.getImage("pic/" + currStyle + "/2.png"),
                kit.getImage("pic/" + currStyle + "/3.png"),
                kit.getImage("pic/" + currStyle + "/4.png"),
                kit.getImage("pic/" + currStyle + "/5.png"),
                kit.getImage("pic/" + currStyle + "/6.png"),
                kit.getImage("pic/" + currStyle + "/7.png"),
                kit.getImage("pic/" + currStyle + "/8.png"),
                kit.getImage("pic/" + currStyle + "/9.png"),
                kit.getImage("pic/" + currStyle + "/10.png"),
                kit.getImage("pic/" + currStyle + "/11.png"),
                kit.getImage("pic/" + currStyle + "/12.png")
        };
        public int[][] getOriMap() {
            return oriMap;
        }
        public void setOriMap(int[][] oriMap) {
            this.oriMap = oriMap;
        }
        public MyPanel(int[][] map){
            readMap(map);
            this.setOpaque(true);
            this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            this.requestFocus();
            repaint();
        }
        public void readMap(int[][] map){
            this.oriMap = map;
            this.tempMap = map;
        }


        @Override
        public void paintComponent(Graphics g){
        	g.setColor(Color.ORANGE);
        	g.fillRect(0, 0, getWidth(), getHeight());
        	g.setColor(getForeground());
            for(int i = 0; i < 15; i++){
                for(int j = 0; j< 15;j++){
                    g.drawImage(mapimg[this.tempMap[j][i]], i*30, j*30, 30,30,this);
                }
            }
        }
        
        @Override
    	public Dimension getPreferredSize() {
    		return (new Dimension(452, 452));
    	}
    }
    
    /**
     * JPanel for the main menu.
     */
    class menuPanel extends JPanel {
    	
		private static final long serialVersionUID = 1L;
		private JButton btnNew, btnLoad, btnExit;
		Object[] options = {
				"Load Slot 1",
                "Load Slot 2",
                "Load Slot 3"
        };
    	
    	public menuPanel(JPanel contentPane) {
    		
    		this.setLayout(new GridLayout(4, 2, 0, 50));
    		this.setBackground(Color.orange);
    		
    		btnNew = new JButton(new ImageIcon("pic/new_button.png"));
    		btnNew.setBorder(BorderFactory.createEmptyBorder());
    		btnNew.setContentAreaFilled(false);
    		btnNew.setPressedIcon(new ImageIcon("pic/new_selected.png"));
    		btnLoad = new JButton(new ImageIcon("pic/load_button.png"));
    		btnLoad.setBorder(BorderFactory.createEmptyBorder());
    		btnLoad.setContentAreaFilled(false);
    		btnLoad.setPressedIcon(new ImageIcon("pic/load_selected.png"));
    		btnExit = new JButton(new ImageIcon("pic/exit_button.png"));
    		btnExit.setBorder(BorderFactory.createEmptyBorder());
    		btnExit.setContentAreaFilled(false);
    		btnExit.setPressedIcon(new ImageIcon("pic/exit_selected.png"));
    		
    		btnNew.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				game.newGame();
    				Completed = false;
    				menu = false;
    				mainPanel = new gamePanel(cardLayout);
    	        	cardLayout.add(mainPanel);
    	        	((CardLayout)cardLayout.getLayout()).next(cardLayout);
    			}
    		});
    		btnLoad.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				int n = JOptionPane.showOptionDialog(contentPane,
    					    "Choose your game:",
    					    "Load Game",
    					    JOptionPane.YES_NO_CANCEL_OPTION,
    					    JOptionPane.QUESTION_MESSAGE,
    					    null,
    					    options,
    					    options[2]);
    				Completed = false;
    				menu = false;
    				Save saveFile = new Save();
    				if (saveFile.loadGame(n + 1, game)) {
    					mainPanel = new gamePanel(cardLayout);
    					cardLayout.add(mainPanel);
        	        	((CardLayout)cardLayout.getLayout()).next(cardLayout);
    				} else {
    					JOptionPane.showMessageDialog(contentPane, "Load does not exist.", "Load Game", JOptionPane.WARNING_MESSAGE);
    				}
    			}
    		});
    		btnExit.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				System.exit(0);
    			}
    		});
    		
    		//Stops game freezing after button press.
    		btnNew.setFocusable(false);
    		btnLoad.setFocusable(false);
    		btnExit.setFocusable(false);
    		
    		add(btnNew);
    		add(btnLoad);
    		add(btnExit);
    		
    		this.setBorder(BorderFactory.createEmptyBorder(50, 100, 0, 100));
    	}
    	
    	@Override
    	public Dimension getPreferredSize() {
    		return (new Dimension(600, 600));
    	}
    }
    
    /**
     * JPanel for the gameplay screen.
     */
    class gamePanel extends JPanel implements ItemListener{
    	
		private static final long serialVersionUID = 1L;
		private JButton btnNewGame,btnReset, btnBack, btnMusic, btnMenu;
    	private JComboBox<String> cbMusic, cbSave;
    	private JPanel buttons;
    	
    	public gamePanel(JPanel contentPane) {
    		this.setLayout(new GridBagLayout());
    		this.setBackground(Color.orange);
    		GridBagLayout g = (GridBagLayout)this.getLayout();
    		
    		GridBagConstraints gbc = new GridBagConstraints();
    		gbc.gridx = 0;
    		gbc.gridy = 0;
    		gbc.anchor = GridBagConstraints.NORTH;
    		MyPanel mP= new MyPanel(game.getState().getCurrMap().getLocations());
    		g.setConstraints(mP, gbc);
    		add(mP);
            buttons = new JPanel();
            buttons.setLayout(new GridLayout(9, 1, 0, 10));
            buttons.setBackground(Color.orange);
    		
            btnNewGame = new JButton("New Game");
            btnReset = new JButton("Reset");
            btnBack = new JButton("Back");
            btnMusic = new JButton("Music Off");
            JLabel lblMusic = new JLabel("Select Music:");
            cbMusic = new JComboBox<String>(musicName);
            cbMusic.addItemListener(this);
            JLabel lblSave = new JLabel("Save Game:");
            cbSave = new JComboBox<String>(saveName);
            cbSave.addItemListener(this);
            btnMenu = new JButton("Main Menu");            
    		
            btnNewGame.addActionListener(new ActionListener(){
            	public void actionPerformed(ActionEvent e) {
            		game.newGame();
            		mainPanel = new gamePanel(cardLayout);
    	        	cardLayout.add(mainPanel);
    	        	((CardLayout)cardLayout.getLayout()).next(cardLayout);
    	        	c.revalidate();
    	            c.repaint(); 
            	}
            });
    		btnReset.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				game.resetState();
    				mainPanel = new gamePanel(cardLayout);
    	        	cardLayout.add(mainPanel);
    	        	((CardLayout)cardLayout.getLayout()).next(cardLayout);
    	        	c.revalidate();
    	            c.repaint();    				
    			}
    		});
    		btnBack.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				game.prevState();
    				mainPanel = new gamePanel(cardLayout);
    	        	cardLayout.add(mainPanel);
    	        	((CardLayout)cardLayout.getLayout()).next(cardLayout);
    	        	c.revalidate();
    	            c.repaint();
    			}
    		});
    		btnMusic.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				String state = btnMusic.getText();
    		        if(state.equals("Music Off")){
    		        	if(music.isPlay()){
    		        		music.stopMusic();
    		            }
    		        	btnMusic.setText("Music On");
    		        } else {
    		        	btnMusic.setText("Music Off");
    		            music.loadSound();
    		        }
    			}
    		});
    		btnMenu.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				menu = true;
    				cardLayout.add(menuPanel);
    				((CardLayout)cardLayout.getLayout()).next(cardLayout);
    	        	c.revalidate();
    	            c.repaint();
    			}
    		});
    		
    	//Stops game freezing after button press.
    		btnNewGame.setFocusable(false);
    		btnReset.setFocusable(false);
            btnBack.setFocusable(false);
            btnMusic.setFocusable(false);
            cbMusic.setFocusable(false);
            cbSave.setFocusable(false);
            btnMenu.setFocusable(false);
            
            buttons.add(btnNewGame);
            buttons.add(btnReset);
            buttons.add(btnBack);
            buttons.add(btnMusic);
            buttons.add(lblMusic);
            buttons.add(cbMusic);
            buttons.add(lblSave);
            buttons.add(cbSave);
            buttons.add(btnMenu);
            
            gbc.gridx++;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0, 50, 0, 0);
            g.setConstraints(buttons, gbc);
            add(buttons);
    	}
    	
    	@Override
    	public Dimension getPreferredSize() {
    		return (new Dimension(600, 600));
    	}
    	
    	@Override
        public void itemStateChanged(ItemEvent e) {
    		if (e.getSource().equals(cbMusic)) {
    			int index = cbMusic.getSelectedIndex();
    			String fileName = sMusic[index];
    			music.setMusic(fileName);
    			if(music.isPlay()){
    				music.stopMusic();
    				music.loadSound();
    			}
    		} else if (e.getSource().equals(cbSave)) {
    			Save saveFile = new Save();
    			saveFile.saveGame(saveFile.matrixToString(game.getState().getCurrMap()), cbSave.getSelectedIndex());
    		}
        }
    }

}
