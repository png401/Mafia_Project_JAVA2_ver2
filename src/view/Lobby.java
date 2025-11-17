package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.ClientManager;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import java.awt.Color;
import javax.swing.JTextField;

public class Lobby extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel titleTestLabel;
	private JButton startButton;
	private JLabel playerTextLabel;
	private JList<String> playerList;
	private JTextField textField;
	private JLabel lblNewLabel;
	
	private DefaultListModel<String> player = new DefaultListModel<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Lobby frame = new Lobby();
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
	public Lobby() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 980, 580);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		titleTestLabel = new JLabel("마피아 게임 프로젝트");
		titleTestLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleTestLabel.setFont(new Font("굴림", Font.PLAIN, 80));
		titleTestLabel.setBounds(0, 43, 966, 102);
		contentPane.add(titleTestLabel);
		
		startButton = new JButton("시작하기");
		startButton.setFont(new Font("굴림", Font.PLAIN, 50));
		startButton.setBounds(312, 395, 334, 102);
		contentPane.add(startButton);
		
		playerTextLabel = new JLabel("입장한 플레이어");
		playerTextLabel.setFont(new Font("굴림", Font.PLAIN, 30));
		playerTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerTextLabel.setBounds(312, 186, 334, 49);
		contentPane.add(playerTextLabel);
										
		playerList = new JList<String>();
		playerList.setBackground(new Color(192, 192, 192));
		playerList.setBounds(182, 237, 596, 135);
		playerList.setVisibleRowCount(6);
		playerList.setModel(player);
		
		DefaultListCellRenderer centerRenderer = new DefaultListCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER); // 가운데 정렬
		playerList.setCellRenderer(centerRenderer);
		
		contentPane.add(playerList);
		
		textField = new JTextField();
		textField.setBounds(483, 155, 106, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		//닉네임 입력받아 ClientManager에게 보내기
		textField.addActionListener(new ActionListener() { 			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JTextField nicknameField = (JTextField) e.getSource(); 	
				String nickname = nicknameField.getText();
				//ClientManager.sendMessage(nickname); 
				System.out.println("닉네임: "+ nickname + " ClientManeger에게 전달완료");
				player.addElement(nickname);
				nicknameField.setText("");
						
			}
		});
				
		lblNewLabel = new JLabel("닉네임을 입력하세요");
		lblNewLabel.setBounds(359, 158, 112, 15);
		contentPane.add(lblNewLabel);

	}
	
	
}