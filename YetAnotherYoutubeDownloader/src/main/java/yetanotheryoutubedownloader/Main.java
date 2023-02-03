/**
 * If you were expecting nice looking code, look away
 * I don't know what you are expecting, but it's probably wrong
 */
package yetanotheryoutubedownloader;

import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentListener;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Main implements ActionListener, ComponentListener, Runnable {
    //TODO: Version of thie piece of crap
    public static final String VERSION = "0";

    public static final String[] CHANGELOG = {
        "Made the entire thing"
    };

    public Thread mainThread;

    public static final int ROOM = 20;


    public static final String TITLE = "Yet Another Youtube Downloader";

    
    public static void main(String[] args) {
        new Main().start();
    }

    private JFrame frame = new JFrame("insert title here");
    private JLabel mainLabel = new JLabel("<html>Do we need another youtube downloader?<br>yes.</html>");
    private JLabel aboutLabel = new JLabel("<html><h1>About</h1><br />Hi, i'm Canary<br />I got tired of those Youtube downloader websites that send a million virus links alongside <i>maybe</i> what you actually want<br /><br />lets put an end to that :3</html>");

    private JButton startButton = new JButton("Start");
    private JButton aboutButton = new JButton("About");
    private JButton backButton = new JButton("Back");

    private JLabel versionLabel = new JLabel("Ver: " + Main.VERSION);
    private JButton changelogButton = new JButton("Changelog");
    private JLabel changelogLabel = new JLabel();

    private JLabel dud = new JLabel("");

    @Override
        public void run() {
            make();
        }

    private void make() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startButton.addActionListener(this);
        aboutButton.addActionListener(this);
        backButton.addActionListener(this);
        changelogButton.addActionListener(this);

        frame.addComponentListener(this);
        frame.setResizable(false);

        String temp = "<html><h1>Changelog</h1>" + Main.VERSION;
        for (int i = 0; i < Main.CHANGELOG.length; i++) {
            temp += "<br />•" + Main.CHANGELOG[i];
        }
        temp += "</html>";

        changelogLabel.setText(temp);

        mainLabel.setBounds(0, 0, 250, 50);
        startButton.setBounds(50, 60, 100, 30);
        aboutButton.setBounds(50, 90, 100, 30);

        aboutLabel.setBounds(0, 0, 400, 200);

        changelogButton.setBounds(0, 200, 100, 30);


        versionLabel.setBounds(100, 200, 100, 30);

        changelogLabel.setBounds(0, 0, 400, Main.CHANGELOG.length * 40 + 50);
        changelogLabel.setVerticalAlignment(SwingConstants.TOP);


        mainMenu();
        frame.setVisible(true);
    }

    private void mainMenu() {
        frame.getContentPane().removeAll();


        frame.getContentPane().add(mainLabel);
        frame.getContentPane().add(startButton);
        frame.getContentPane().add(aboutButton);
        frame.getContentPane().add(dud);

        frame.setSize(230 + Main.ROOM, 160 + Main.ROOM);
    }
    
    private void aboutMenu() {
        frame.getContentPane().removeAll();
        backButton.setBounds(300, 200, 100, 30);
        
        frame.getContentPane().add(aboutLabel);
        frame.getContentPane().add(versionLabel);
        frame.getContentPane().add(backButton);
        frame.getContentPane().add(changelogButton);
        frame.getContentPane().add(dud);

        frame.setSize(400 + Main.ROOM, 300 + Main.ROOM);
    }

    private void changelogMenu() {
        frame.getContentPane().removeAll();

        frame.getContentPane().add(backButton, BorderLayout.PAGE_END);

        frame.getContentPane().add(changelogLabel);
        frame.getContentPane().add(dud);

        frame.setResizable(true);
        frame.setSize(400 + Main.ROOM, Main.CHANGELOG.length * 30 + 140 + Main.ROOM);

    }


    private void startApp() {
        frame.dispose();
        new Search(this).start();
    }


    public void start() {
        if (mainThread == null) {
            mainThread = new Thread(this, "mainThread");
            mainThread.start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.setResizable(false);

        if (e.getSource() == startButton)
            startApp();
        if (e.getSource() == backButton)
            mainMenu();
        if (e.getSource() == aboutButton)
            aboutMenu();
        if (e.getSource() == changelogButton)
            changelogMenu();
    }

    @Override
    public void componentResized(ComponentEvent e) {
        changelogLabel.setBounds(0, 0, e.getComponent().getWidth() - ROOM, e.getComponent().getHeight() - ROOM); 
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        
    }

    @Override
    public void componentShown(ComponentEvent e) {
        
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        
    }
}