package view;

import client.ClientManager;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

public class MafiaChatView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField mafiaInputField;
    private JTextArea mafiaChatArea;
    private JScrollPane scrollPane;

    private ClientManager clientManager;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MafiaChatView frame = new MafiaChatView();
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
    public MafiaChatView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 580);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        mafiaChatArea = new JTextArea();
        scrollPane = new JScrollPane(mafiaChatArea);
        mafiaChatArea.setBounds(12, 10, 358, 478);
        contentPane.add(mafiaChatArea);

        mafiaInputField = new JTextField();
        mafiaInputField.setBounds(12, 512, 251, 21);
        contentPane.add(mafiaInputField);
        mafiaInputField.setColumns(10);

        JButton mafiaEnterButton = new JButton("ENTER");
        mafiaEnterButton.setBounds(275, 511, 95, 23);
        contentPane.add(mafiaEnterButton);

    }

    public MafiaChatView(ClientManager clientManager) {
        this();
        this.clientManager = clientManager;
        
        ActionListener sendAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = mafiaInputField.getText();
                
                // 빈 값 전송 방지
                if(msg.trim().isEmpty()) return;

                // 서버로 "Mafia_message:내용" 보냄
                clientManager.sendMessage("Mafia_message:" + msg);
                
                // 입력창 비우기
                mafiaInputField.setText("");
                mafiaInputField.requestFocus();
            }
        };

        mafiaInputField.addActionListener(sendAction);
    }

    // 서버에서 온 마피아 채팅을 화면에 띄어주기
    public void mafiaChat(String msg) {
        mafiaChatArea.append(msg + "\n");
        mafiaChatArea.setCaretPosition(mafiaChatArea.getDocument().getLength());

        contentPane.repaint();
    }
}