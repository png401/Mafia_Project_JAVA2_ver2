package view;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;

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
	
	//List<Player> players;

	/**
	 * Launch the application.
	 */
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
	}

	/**
	 * Create the frame.
	 */
	public View() {
		//this.players = players;
		
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
				JTextField input = (JTextField) e.getSource(); //�Է� �޾Ƽ�
				chatArea.append(input.getText() + '\n'); //chatArea�� ���̰�
				chatArea.setCaretPosition(chatArea.getDocument().getLength()); //�� �Ʒ��� ��ũ��
				input.setText(""); //inputField�� ����
			}
		}); 
		
		enterButton = new JButton("ENTER");
		enterButton.setBounds(697, 508, 93, 21);
		contentPane.add(enterButton);
		
		playerList = new JList();
		playerList.setBounds(817, 10, 137, 481);
		contentPane.add(playerList);
		
		skillField = new JTextField();
		skillField.setBounds(817, 508, 75, 21);
		contentPane.add(skillField);
		skillField.setColumns(10);
		
		skillButton = new JButton("skill name");
		skillButton.setBounds(906, 508, 48, 21);
		contentPane.add(skillButton);

	}
}
