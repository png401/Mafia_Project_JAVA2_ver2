package view;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import model.Player;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class View extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea chatArea;
	private JScrollPane chatAreaScroll;  
	private JTextField inputField;
	private JButton enterButton;
	private JTextField skillField;
	private JList playerList;
	private JButton skillButton;
	private JLabel roleName;
	
	private DefaultListModel<String> enteredPlayer;	
	public List <Player> players;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View frame = new View();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */	
	public View() {		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 980, 580);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		chatArea = new JTextArea();
		chatArea.setBounds(12, 10, 778, 481);
				
		chatAreaScroll = new JScrollPane(chatArea);
		chatAreaScroll.setBounds(12, 10, 778, 481);
		contentPane.add(chatAreaScroll);  
		
		inputField = new JTextField();
		inputField.setBounds(12, 508, 673, 21);
		contentPane.add(inputField);
		inputField.setColumns(10);
		
		inputField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JTextField input = (JTextField) e.getSource(); 
				chatArea.append(input.getText() + '\n'); 
				chatArea.setCaretPosition(chatArea.getDocument().getLength()); 
				input.setText(""); 
			}
		}); 
		
		enterButton = new JButton("ENTER");
		enterButton.setBounds(697, 508, 93, 21);
		contentPane.add(enterButton);		
		 
		playerList = new JList<Player>();
		playerList.setBounds(817, 10, 137, 323);
		playerList.setVisibleRowCount(6);
						
		DefaultListCellRenderer centerRenderer = new DefaultListCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER); // 가운데 정렬
		playerList.setCellRenderer(centerRenderer);
		
		contentPane.add(playerList);
		
		skillField = new JTextField();
		skillField.setBounds(817, 508, 75, 21);
		contentPane.add(skillField);
		skillField.setColumns(10);
		
		skillButton = new JButton("skill name");
		skillButton.setBounds(906, 508, 48, 21);
		contentPane.add(skillButton);
		
		JLabel roleImage = new JLabel("New label");
		roleImage.setIcon(new ImageIcon(View.class.getResource("/image/mafia.png")));
		roleImage.setBounds(817, 343, 127, 127);
		contentPane.add(roleImage);
		
		roleName = new JLabel("Mafia");
		roleName.setHorizontalAlignment(JLabel.CENTER);
		roleName.setBounds(817, 480, 127, 15);
		contentPane.add(roleName);

	}

	public void setPlayersModel(DefaultListModel<String> enteredPlayer) {
		this.enteredPlayer = enteredPlayer;
			
	}
	
	public void setPlayers(List <Player> players) {
		this.players = players;
		playerList.setModel((ListModel) players);	
	}

}
