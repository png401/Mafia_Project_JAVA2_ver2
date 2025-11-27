package view;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import client.ClientManager;
import model.Player;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.ImageIcon;


public class View2 extends JFrame {

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
   private JLabel roleImage;
   
   private ClientManager clientManager;

   private DefaultListModel<String> players;
       
   public View2() throws IOException{
      
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
      ///////////////////////////////////
      //-- 크기세팅하고 화면 중앙에 배치하도록 세팅.
      setSize(980,580);
      setLocationRelativeTo(null);
      ///////////////////////////////////
      
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);
           
      inputField = new JTextField();
      inputField.setBounds(12, 508, 673, 21);
      contentPane.add(inputField);
      inputField.setColumns(10);      
            
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
      skillField.setBounds(817, 508, 42, 21);
      contentPane.add(skillField);
      skillField.setColumns(10);      
           
      /////////////////////////////////////////////////////////////////////   
  
      setImagePanel();
   }
   
   public View2(ClientManager clientManager) throws IOException {
	   this();
	   this.clientManager = clientManager;
	   
	   inputField.addActionListener(new ActionListener() {
	         
	         @Override
	         public void actionPerformed(ActionEvent e) {
	            // TODO Auto-generated method stub
	            JTextField input = (JTextField) e.getSource();	           
	            clientManager.sendMessage("Message:"+input.getText());	            
	            input.setText("");
	            input.requestFocus();
	         }
	      }); 
   }

   public void setPlayersModel(DefaultListModel<String> players) {
      this.players = players;
      playerList.setModel(players);
   }
   
   public void allChat(String message) {	 
	   chatArea.append(message + '\n');
	   chatArea.setCaretPosition(chatArea.getDocument().getLength());
	   
	   contentPane.repaint();
   }

   //사용자의 role에 따라 다르게 나와야함. 예외처리는 throws로 .
   public void setRoleView(String role) throws IOException {                     
      roleImage = new JLabel();
      roleImage.setIcon(new ImageIcon(View2.class.getResource("/image/" + role + ".png")));
      roleImage.setBounds(817, 343, 127, 127);
      contentPane.add(roleImage);

      /////////////////////////////////////////////////
      img= ImageIO.read(View2.class.getResource("/image/"+role +".png"));
      bgPanel.setSourceImage(img); // 이미지 교체 반영하기!       
      
      roleName = new JLabel(role);
      roleName.setHorizontalAlignment(JLabel.CENTER);
      roleName.setBounds(817, 480, 127, 15);
      contentPane.add(roleName);
      contentPane.repaint();
   }   
   
   public void setSkillButton(String skill) {
      skillButton = new JButton(skill);
      skillButton.setBounds(863, 508, 91, 21);
      contentPane.add(skillButton);
      
      contentPane.repaint();
   }
   
   ////////////////////////////////////////////////////////////////////////////
   //-- textArea 배경으로 이미지깔기
   BufferedImage img ;
   ImagePanel bgPanel ;
   JPanel overlay;
   /////////////////////////////////////////////////////////////////////////////
   
   public void setImagePanel() throws IOException {
	      img= ImageIO.read(View2.class.getResource("/image/citizen.png"));
	      bgPanel = new ImagePanel(img);
	      bgPanel.setBounds(12,10,778+10,481+10); //기존 textArea크기보다 조금 더 크게
	      bgPanel.setLayout(null);
	      
	      chatArea = new JTextArea();
	      chatArea.setBounds(12, 10, 778, 481);
	      chatArea.setOpaque(false);
	      chatAreaScroll = new JScrollPane(chatArea);
	      chatAreaScroll.setBounds(12, 10, 778, 481);
	      chatAreaScroll.setOpaque(false);
	      chatAreaScroll.getViewport().setOpaque(false);
	      
	      overlay = new JPanel() {
	          @Override
	          protected void paintComponent(Graphics g) {
	              // 반투명 배경 (선택)
	              g.setColor(new Color(255, 240,240, 200));
	              g.fillRect(0, 0, getWidth(), getHeight());
	              super.paintComponent(g);
	          }
	      };
	      overlay.setLayout(null);
	      overlay.setBounds(0,0,778, 481);   
	      overlay.setForeground(new Color(192, 192, 192));      
	      overlay.setOpaque(false);
	      overlay.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

	      bgPanel.add(overlay);
	      overlay.add(chatAreaScroll);
	      contentPane.add(bgPanel);
   }
   
   ////////////////////////////////////////////////////////////////
   static class ImagePanel extends JPanel {
       private BufferedImage srcImage;
       private BufferedImage scaledCache;
       private Dimension lastSize = new Dimension();

       public ImagePanel(BufferedImage srcImage) {
           this.srcImage = srcImage;
           setLayout(new BorderLayout());
       }
       
       public void setSourceImage(BufferedImage img) {
           this.srcImage = img;
           this.scaledCache = null; // 캐시 무효화
           this.lastSize.setSize(0,0);
           revalidate();
           repaint();
       }  

       @Override
       protected void paintComponent(Graphics g) {
           super.paintComponent(g);
           if (srcImage == null) return;

           int pw = getWidth();
           int ph = getHeight();
           if (pw <= 0 || ph <= 0) return;

           // 캐시된 스케일 이미지가 없거나 패널 크기가 바뀌면 재생성
           if (scaledCache == null || lastSize.width != pw || lastSize.height != ph) {
               // 목표: 패널 크기의 70%를 차지하되 원본 비율 유지
               double targetRatio = 0.7; // 70%
               double maxW = pw * targetRatio;
               double maxH = ph * targetRatio;

               double imgW = srcImage.getWidth();
               double imgH = srcImage.getHeight();

               double scale = Math.min(maxW / imgW, maxH / imgH);

               int newW = Math.max(1, (int) Math.round(imgW * scale));
               int newH = Math.max(1, (int) Math.round(imgH * scale));

               // 고품질 스케일링
               BufferedImage tmp = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
               Graphics2D g2 = tmp.createGraphics();
               g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
               g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
               g2.drawImage(srcImage, 0, 0, newW, newH, null);
               g2.dispose();

               scaledCache = tmp;
               lastSize.setSize(pw, ph);
           }

           // 중앙에 그리기
           int x = (pw - scaledCache.getWidth()) / 2;
           int y = (ph - scaledCache.getHeight()) / 2;
           g.drawImage(scaledCache, x, y, this);
       }
   }
}