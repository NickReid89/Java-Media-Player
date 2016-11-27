/*
Author: Nickolas Reid

Purpose: To use the JMF framework and create a media player.
*/
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MediaPlayer extends JFrame {

    //Contains the control to make the player video and control bar.
    Player player = null;
    //Next three controls are to create a menu so a user can use the application multiple times.
    static JMenu menu = new JMenu("file");
    static JMenuBar menuBar = new JMenuBar();
    static JMenuItem jmOpen = new JMenuItem("open");
    //Component object to hold player components.
    static Component comp;
    //I needed this panel so I can wipe the area to load a new video or song.
    static JPanel videoContainer = new JPanel(new BorderLayout());
    //Flag that's triggered after te first song is played.
    static boolean replace = false;

    //This is for the menu button to load a new song or video.
    private void setUpButton() {
        //Action after the user presses open.
        jmOpen.addActionListener((ActionEvent e) -> {
            //Create a file chooser for user.
            JFileChooser fc = new JFileChooser();
            //Take out the all choice.
            fc.setAcceptAllFileFilterUsed(false);
            //Only allow the user to pick files that I have tested to work.
            fc.addChoosableFileFilter(new FileNameExtensionFilter("Media Files", "mov", "mp3", "wav", "avi", "mpg", "mid"));
            //Get the users choice.
            int option = fc.showOpenDialog(null);
            //If user picked a file.
            if (option == JFileChooser.APPROVE_OPTION) {
                try {
                    //Grab where the file came from.
                    URL url1 = fc.getSelectedFile().toURL();
                    //If the user made it here it means they already picked a file prior.
                    replace = true;
                    //Remove the old player and add a new one.
                    createPlayer(url1);
                } catch (MalformedURLException mal) {
                    JOptionPane.showMessageDialog(null, "Application cannot follow the directory to your file. Please try again.");
                } catch (IOException | NoPlayerException | CannotRealizeException ex) {
                    Logger.getLogger(MediaPlayer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    //This sets up the Frame and adds the controls.
    public MediaPlayer(String title, URL url) {
        //Make a title.
        this.setTitle(title);
        Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, true);

        try {

            //enable open button.
            setUpButton();
            //Set up the menu bar.
            menuBar.add(menu);
            menu.add(jmOpen);
            add(menuBar, BorderLayout.NORTH);
            //Add the video container that should have a video or song ready.
            add(videoContainer, BorderLayout.CENTER);
            //Set up player.
            createPlayer(url);
            //Tell the frame how to close.
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //Let the user see the application.
            setVisible(true);

        } catch (CannotRealizeException | NoPlayerException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Something went wrong with the file you tried to load. Please try again.");
        }

    }

    //Set the video or song up for the user.
    private void createPlayer(URL url) throws IOException, NoPlayerException, CannotRealizeException {

        //If the user loaded new media.
        if (replace) {
            //Stop the player.
            player.stop();
            //Strip the player.
            videoContainer.removeAll();
        }
        //Create a new player with the new media.
        player = Manager.createRealizedPlayer(url);

        //If it's a video, then add the visual component.
        if ((comp = player.getVisualComponent()) != null) {
            videoContainer.add(comp, BorderLayout.CENTER);
        }
        //This should load no matter what, but as a safe guard it's good to make sure it's not null.
        if ((comp = player.getControlPanelComponent()) != null) {
            videoContainer.add(comp, BorderLayout.SOUTH);
        }
        //Get rid of the old video image.
        videoContainer.repaint();
        //Clean it up.
        pack();
        //Start the media for user.
        player.start();
    }

    public static void main(String args[]) throws CannotRealizeException, MalformedURLException {
        //We start the program by welcoming the user and then requesting a piece of media.
        JOptionPane.showMessageDialog(null, "Welcome to my media player! please choose a file.");
        //Open up the file chooser.
        JFileChooser fc = new JFileChooser();
        //Show which files a user can pic.
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Media Files", "mov", "mp3", "wav", "avi", "mpg","mid"));
        //Collect user response.
        int option = fc.showOpenDialog(null);
        //If the user picked a file.
        if (option == JFileChooser.APPROVE_OPTION) {
            try {
                //Grab where the file is.
                URL url = fc.getSelectedFile().toURL();
                //Create new MediaPlayer object to play it.
                MediaPlayer mp = new MediaPlayer("Media Player", url);

            } catch (MalformedURLException mal) {
                JOptionPane.showMessageDialog(null, "Application cannot follow the directory to your file. Please try again.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "You did not pick a file. Shutting down.");
            System.exit(0);
        }

    }

}
