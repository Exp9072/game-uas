public class MainApplication {
    private static JPanel panel;
    
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        
        JFrame frame = new JFrame("Star-Hawk Invasion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        StarHawk game = new StarHawk();
        JPanel mainMenuPanel = new JPanel(new GridBagLayout());
        

        JPanel mainPanel = new JPanel(new CardLayout());

        ImageIcon ll = new ImageIcon(MainApplication.class.getClassLoader().getResource("BScoreboardBGLeft.png"));
        Image BgScore; 
        BgScore = ll.getImage();
  
        JPanel scoreboardPanel = new JPanel(new BorderLayout()){
            @Override
            protected void paintComponent(Graphics g ){
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(BgScore, 0, 0, getWidth(), getHeight(), this);
            }
        };


        SoundMain.playBackgroundMusic();
        

        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setOpaque(false);  
        GridBagConstraints titleGbc = new GridBagConstraints();
        titleGbc.insets.set(0, 50, 0, 50);
        titleGbc.gridwidth = GridBagConstraints.REMAINDER;
        titleGbc.anchor = GridBagConstraints.NORTH;
        titleGbc.fill = GridBagConstraints.NONE;


        URL titleImageUrl = MainApplication.class.getClassLoader().getResource("BTitle.png"); 
        ImageIcon titleImageIcon = new ImageIcon(titleImageUrl);

        JLabel gameNameLabel = new JLabel(titleImageIcon);
        
        titlePanel.add(gameNameLabel);
        
        ImageIcon ii = new ImageIcon(MainApplication.class.getClassLoader().getResource("BgMain+BackStory(WarpedGrey).png"));
        Image BgMain = ii.getImage();
        
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(BgMain, 0, 0, getWidth(), getHeight(), this); 

            }
        };

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        
        frame.setLayout(new BorderLayout()); 
        panel.setBounds(0, 0, width, height); 
        frame.add(panel);
        frame.setSize(width, height);
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        CardLayout cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);
        mainPanel.add(scoreboardPanel, "scoreboard");
        
        SCRButton scoreboardButton = new SCRButton(cardLayout, mainPanel, scoreboardPanel, mainMenuPanel, frame, panel, gameNameLabel);

        RTOMainMenu mainMenu = new RTOMainMenu(frame, mainPanel, panel, cardLayout);
        mainPanel.add(mainMenuPanel, "mainMenu");

        game.setReturnMenu(mainMenu);

        
        JPanel buttonPanel = new JPanel(new GridBagLayout());

        StartButton startButton = new StartButton(frame, game);
        ExitButton exitButton = new ExitButton();
        
        Dimension buttonSize = new Dimension(200, 50);

        startButton.setPreferredSize(buttonSize);
        scoreboardButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);
        
        GridBagConstraints buttonGbc = new GridBagConstraints();
        buttonGbc.insets.set(0, 5, 0, 5);
        buttonGbc.anchor = GridBagConstraints.CENTER;
        buttonGbc.fill = GridBagConstraints.HORIZONTAL;

        buttonPanel.add(startButton, buttonGbc);
        buttonPanel.add(scoreboardButton, buttonGbc);
        buttonPanel.add(exitButton, buttonGbc);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;

        mainMenuPanel.add(gameNameLabel, titleGbc);
        mainMenuPanel.add(titlePanel);
        mainPanel.add(mainMenuPanel, "mainMenu");
        
        int buttonPanelY = frame.getHeight() - 200; 
        
        gameNameLabel.setBounds(0, 0, frame.getWidth(), 100);
        buttonPanel.setBounds(0, buttonPanelY, frame.getWidth(), 200);

        panel.add(gameNameLabel);
        panel.add(startButton);
        panel.add(scoreboardButton);
        panel.add(exitButton);
        frame.add(panel);
        frame.getContentPane().add(mainPanel);
        frame.revalidate();
        frame.setResizable(false);
        frame.setVisible(true);
        frame.repaint();
        panel.revalidate();
        panel.setVisible(true);
        panel.repaint();
        buttonPanel.revalidate();
        buttonPanel.setVisible(true);
        buttonPanel.repaint();
    }
}