package app;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import data.GeoInfo;
import data.Location;


public class Graphics {
	CardLayout crd = new CardLayout();
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	JFrame window;
	JPanel mainPanel;
	JPanel startScreen;
	JPanel mainPane;
	JPanel settingsPane;
	JPanel convertPane;

    AppHandler ah;
    protected Location issloc;
    protected GeoInfo g;

	public String link;
	public JComboBox<String> curr;
	public JTextField amount;
	public Boolean enableLog = false;
	
	//Initialize JPanels and JFrame	
	public void init() {
		issloc = ah.getISSLoc();
		g = ah.getGeoInfo();
		mainPane = new JPanel();
		settingsPane = new JPanel(new BorderLayout());
		convertPane = new JPanel(new BorderLayout());
		startScreen = new JPanel(new BorderLayout());
		mainPanel = new JPanel(crd);
		window = new JFrame("ISSTracker");
	}
	
	//Setup JPanels basic hierarchy
	public void setup() {
		startScreen.add(mainPane);
		mainPanel.add(startScreen, "1");
		mainPanel.add(convertPane, "2");
		mainPanel.add(settingsPane, "3");
		window.add(mainPanel);
	}
	
	/*
	 * creates Initial standard screen setup using given title and JPanel
	 */
	public void makeScreen(String title, JComponent JP) {
		JP.setBackground(Color.BLACK);
		JLabel lab = new JLabel(title, SwingConstants.CENTER);
		lab.setForeground(Color.GREEN);
		lab.setFont(new Font(null, Font.BOLD, 24));
		JP.add(lab, BorderLayout.NORTH);
		if(JP != mainPane) {
			buttonSetup(JP);
		}
		
	}

	//Sets up each seperate screen with a standard back button
	public void buttonSetup(JComponent b) {
		JPanel butPanel = new JPanel();
		butPanel.setBackground(Color.DARK_GRAY);

		JButton back = new JButton("Back");
		setButtonColor(back, true);
		back.setFocusable(false);
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				crd.first(mainPanel);
			}
			
		});
			
		butPanel.add(back);
		b.add(butPanel, BorderLayout.SOUTH);
	}
	
	//Sets standard button colors for given button and inverts if appropriate
	public void setButtonColor(JButton bt, Boolean invert) {
		if(!invert) {
			bt.setBackground(Color.DARK_GRAY);	
		} else {
			bt.setBackground(Color.BLACK);
		}

		bt.setForeground(Color.GREEN);
	}


	public void setComboBox(List<String> vlatues, JComboBox<String> combo) {
		for(String vlatue: vlatues)	{
			combo.addItem(vlatue);
		}
	}


	//Sets up the converter screen
	public void converterSetup() {
		JPanel convertContent = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(2, 2, 2, 2);

		convertContent.setBackground(Color.BLACK);

		JLabel convertL = new JLabel("ISS Location", SwingConstants.CENTER);
		convertL.setForeground(Color.GREEN);
		convertL.setFont(new Font(null, Font.BOLD, 24));

		JLabel[] uLabel = new JLabel[8];

		uLabel[0] = new JLabel("Latitude:");
		uLabel[1] = new JLabel("Longitude:");
		uLabel[2] = new JLabel("Quadrant:");
		uLabel[3] = new JLabel("Altitude:");
		uLabel[4] = new JLabel("Velocity:");
		uLabel[5] = new JLabel("Orbital Period:");
		uLabel[6] = new JLabel("Timezone:");
		uLabel[7] = new JLabel("Country Code:");

		final JLabel[] cLabel = new JLabel[8];

		cLabel[0] = new JLabel(String.valueOf(ah.issloc.getLat()));
		cLabel[1] = new JLabel(String.valueOf(ah.issloc.getLon()));
		cLabel[2] = new JLabel(ah.issloc.getQuad());	
		cLabel[3] = new JLabel(String.valueOf(ah.issloc.getAlt()) + " km");
		cLabel[4] = new JLabel(String.valueOf(ah.issloc.getVel()) + " km/h");
		cLabel[5] = new JLabel(String.valueOf(ah.issloc.getOrbitalPeriod()) + " minutes");
		cLabel[6] = new JLabel(ah.g.getTimezone());	
		cLabel[7] = new JLabel(ah.g.getCountryCode());

		link = ah.g.getMapURL();

		JButton goTo = new JButton("Google Maps");
		setButtonColor(goTo, false);
		goTo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(link));
				} catch(IOException | URISyntaxException er) {
					er.printStackTrace();
				}
			}
		});	
		
		JButton enter = new JButton("Update");
		setButtonColor(enter, false);
		enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cLabel[0].setText(String.valueOf(ah.issloc.getLat()));
				cLabel[1].setText(String.valueOf(ah.issloc.getLon()));
				cLabel[2].setText(ah.issloc.getQuad());
				cLabel[3].setText(String.valueOf(ah.issloc.getAlt()) + " km");
				cLabel[4].setText(String.valueOf(ah.issloc.getVel()) + " km/h");
				cLabel[5].setText(String.valueOf(ah.issloc.getOrbitalPeriod()) + " minutes");
				cLabel[6].setText(ah.g.getTimezone());
				cLabel[7].setText(ah.g.getCountryCode());

				link = ah.g.getMapURL();
			}
		});

		c.gridx = 0;
		for(int i = 0; i < 8; i++) {
			c.gridy = i;
			uLabel[i].setForeground(Color.GREEN);
			convertContent.add(uLabel[i], c);
		}

		c.gridx = 1;
		for(int i = 0; i < 8; i++) {
			c.gridy = i;
			cLabel[i].setForeground(Color.GREEN);
			convertContent.add(cLabel[i], c);
		}

		c.insets = new Insets(50, 2, 2, 2);
		c.gridy = 9;
		goTo.setFocusable(false);
		convertContent.add(goTo, c);
		c.gridy = 10;
		enter.setFocusable(false);
		convertContent.add(enter, c);
		
		//convertPane.add(convertL, BorderLayout.NORTH);
		convertPane.add(convertContent, BorderLayout.CENTER);

		
	}

	public void settingsSetup() {
		JPanel settings = new JPanel(new FlowLayout());
		settings.setBackground(Color.BLACK);
		
		JButton log = new JButton("Enable/Disable Console Log");
		setButtonColor(log, false);
		log.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//inverts this boolean for console log
				enableLog = !enableLog;
				ah.setLog(enableLog);
			}
			
		});
		
		settings.add(log);
		settingsPane.add(settings, BorderLayout.CENTER);
	}

	//Sets up main start screen
	public void makeStartScreen() {
		init();

		JButton one = new JButton("ISS Info");
		one.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				crd.show(mainPanel, "2");
				
			}
		});
		
		JButton two = new JButton("More Info");
		two.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				crd.show(mainPanel, "3");
			}
		});
		
		
		makeScreen("ISS Location", convertPane);
		makeScreen("Settings", settingsPane);
		makeScreen("Welcome", mainPane);

	 	converterSetup();
		settingsSetup();

		JPanel bPanel = new JPanel();
		bPanel.setBackground(Color.DARK_GRAY);

		one.setFocusable(false);
		setButtonColor(one, true);
		two.setFocusable(false);
		setButtonColor(two, true);

		bPanel.add(one);
		bPanel.add(two);

		startScreen.add(bPanel, BorderLayout.SOUTH);

		setup();

		window.setSize(screenSize);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
	}
	
	
	public void run() {
		makeStartScreen();
		
	}
	
	public Graphics(AppHandler ah) {
        this.ah = ah;
		run();
		
	}
	
}

