// LoginForm.java
package login;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginForm extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private UsersData users;
    private JLabel idLabel;
    private JLabel pwLabel;
    private static JTextField idTxt;
    private JPasswordField pwTxt;
    private JButton logBtn;
    private JButton joinBtn;
    private LayoutManager flowLeft;
    private InformationForm informationForm; // InformationForm 인스턴스 필드 추가

    public LoginForm() {
        users = UsersData.getInstance();
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        init();
        setDisplay();
        addListeners();
        showFrame();
    }

    public void init() {
        Dimension labelSize = new Dimension(80, 30);
        int txtSize = 10;
        Dimension btnSize = new Dimension(100, 25);

        idLabel = new JLabel("아이디");
        idLabel.setPreferredSize(labelSize);
        pwLabel = new JLabel("암호");
        pwLabel.setPreferredSize(labelSize);

        idTxt = new JTextField(txtSize);
        pwTxt = new JPasswordField(txtSize);

        logBtn = new JButton("로그인");
        logBtn.setPreferredSize(btnSize);
        joinBtn = new JButton("회원가입");
        joinBtn.setPreferredSize(btnSize);

        flowLeft = new FlowLayout(FlowLayout.LEFT);
    }

    public UsersData getUsers() {
        return users;
    }

    public static String getUserId() {
        return idTxt.getText();
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setDisplay() {
        JPanel loginPanel = new JPanel(new BorderLayout());

        JPanel northPanel = new JPanel(new GridLayout(0, 1));
        JPanel idPanel = new JPanel(flowLeft);
        idPanel.add(idLabel);
        idPanel.add(idTxt);

        JPanel pwPanel = new JPanel(flowLeft);
        pwPanel.add(pwLabel);
        pwPanel.add(pwTxt);

        northPanel.add(idPanel);
        northPanel.add(pwPanel);

        JPanel southPanel = new JPanel();
        southPanel.add(logBtn);
        southPanel.add(joinBtn);

        northPanel.setBorder(new EmptyBorder(0, 20, 0, 20));
        southPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        loginPanel.add(northPanel, BorderLayout.NORTH);
        loginPanel.add(southPanel, BorderLayout.SOUTH);

        mainPanel.add(loginPanel, "loginForm");

        informationForm = new InformationForm(this); // InformationForm 인스턴스 생성
        mainPanel.add(informationForm, "informationForm");

        add(mainPanel);
    }

    public void addListeners() {
        joinBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new JoinForm(LoginForm.this);
                idTxt.setText("");
                pwTxt.setText("");
            }
        });

        logBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idTxt.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(LoginForm.this, "아이디를 입력하세요", "LOGIN", JOptionPane.WARNING_MESSAGE);
                } else if (users.contains(new User(idTxt.getText()))) {
                    if (String.valueOf(pwTxt.getPassword()).isEmpty()) {
                        JOptionPane.showMessageDialog(LoginForm.this, "암호를 입력하세요", "LOGIN", JOptionPane.WARNING_MESSAGE);
                    } else if (!users.getUser(idTxt.getText()).getPw().equals(String.valueOf(pwTxt.getPassword()))) {
                        JOptionPane.showConfirmDialog(LoginForm.this, "암호가 일치하지 않습니다", "RETRY", JOptionPane.WARNING_MESSAGE);
                    } else {
                        users.setCurrentUser(users.getUser(idTxt.getText())); // 현재 사용자 설정
                        informationForm.setcheck(users.getCurrentUserId()); // 정보 업데이트
                        cardLayout.show(mainPanel, "informationForm");
                        idTxt.setText("");
                        pwTxt.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this, "존재하지 않는 ID 입니다", "LOGIN", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                int choice = JOptionPane.showConfirmDialog(LoginForm.this, "프로그램을 종료합니다", "BYE", JOptionPane.OK_CANCEL_OPTION);
                if (choice == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    public void showFrame() {
        setTitle("Treasurer Hunter");
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}