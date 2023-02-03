package yetanotheryoutubedownloader;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.TextPage;
import com.gargoylesoftware.htmlunit.WebClient;

public class Search implements Runnable {
    public Thread t;
    private Main main;
    private JFrame frame = new JFrame(Main.TITLE);

    public Search(Main main) {
        this.main = main;
    }

    @Override
    public void run() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String video = userInput();
        String target = determineTarget();
    }

    private String userInput() {
        make();
        while (true) {
            show();
            try {
                parseURL();
                break;
            } catch (NotAYoutubeURLException e) {
                JOptionPane.showMessageDialog(frame, "Not a Youtube link, enter a valid link", Main.TITLE, JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }

    private JLabel prompt = new JLabel("Paste your video link here");
    private JTextField url_field = new JTextField();
    private JButton next_button = new JButton("Next");

    private JLabel dud = new JLabel();

    private void make() {
        prompt.setBounds(Main.ROOM, Main.ROOM + 0, 200, 30);
        url_field.setBounds(Main.ROOM, Main.ROOM + 30, 200, 30);
        next_button.setBounds(Main.ROOM + 100, Main.ROOM + 60, 100, 30);

        next_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (t) {
                    t.notifyAll();
                }
            }
        });
        
        frame.getContentPane().add(prompt);
        frame.getContentPane().add(url_field);
        frame.getContentPane().add(next_button);
        frame.getContentPane().add(dud);

        frame.setResizable(false);
        frame.setSize(200 + Main.ROOM * 2, 90 + Main.ROOM * 3);
    }

    private void show() {
        frame.setVisible(true);
        synchronized (t) {
            try {
                t.wait();
            } catch (InterruptedException e) {}
        }
        frame.setVisible(false);
    }

    private String parseURL() {
        String value = url_field.getText();
        value = value.replaceAll("https://", "");
        try {
            if (!value.isBlank() && (value.contains("youtube") ^ value.contains("youtu.be"))) {
                value = value.split("/")[1];
                value = value.replaceAll("watch\\?v=", "");
                value = value.substring(0, 11);
                return value;
            } else throw new NotAYoutubeURLException();
        } catch (Exception e) {
            throw new NotAYoutubeURLException();
        }
    }

    private String determineTarget() {
        try {
            WebClient client = new WebClient(BrowserVersion.CHROME);
            client.getOptions().setJavaScriptEnabled(false);
            client.getOptions().setAppletEnabled(false);
            client.getOptions().setCssEnabled(false);
            TextPage page = client.getPage("null");
        }
    }

    public void start() {
        if (t == null) {
            t = new Thread(this, "Searcher");
            t.start();
        }
    }
}
