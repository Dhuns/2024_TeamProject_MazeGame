package login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

// 필수 입력 정보
// ID 중복 안됨
// 비밀번호는 두번 확인 하도록 설정

public class JoinForm extends JDialog {

    private LoginForm loginForm;
    private UsersData users;

    private JLabel titleLabel;
    private JLabel idLabel;
    private JLabel pwLabel;
    private JLabel reLabel;
    private JLabel nameLabel;
    private JLabel nickNameLabel;

    private JTextField idTxt;
    private JTextField pwTxt;
    private JTextField reTxt;
    private JTextField nameTextField;
    private JTextField nickNameTextField;

    private JButton joinBtn;
    private JButton cancelBtn;
    private JPanel mainCPanel;

    // 로그인 첫 화면
    public JoinForm(LoginForm loginForm) {
        this.loginForm = loginForm;
        this.users = loginForm.getUsers();

//		super(loginForm, "JAVA JOIN", true);
//		this.loginForm = loginForm;
//		users = loginForm.getUsers();

        init();
        setDisplay();
        addListeners();
        showFrame();

    }

    private void init() {
        int txtSize = 10;

        Dimension labelSize = new Dimension(80, 30);
        // btnSize 모든 GUI 통일
        Dimension btnSize = new Dimension(100, 25);

        titleLabel = new JLabel("- 개인 정보를 입력하세요 -", JLabel.CENTER);

        idLabel = new JLabel("아이디", JLabel.LEFT);
        idLabel.setPreferredSize(labelSize);

        pwLabel = new JLabel("암호", JLabel.LEFT);
        pwLabel.setPreferredSize(labelSize);

        reLabel = new JLabel("암호 재입력", JLabel.LEFT);
        reLabel.setPreferredSize(labelSize);

        nameLabel = new JLabel("이름", JLabel.LEFT);
        nameLabel.setPreferredSize(labelSize);

        idTxt = new JTextField(txtSize);
        pwTxt = new JPasswordField(txtSize);
        reTxt = new JPasswordField(txtSize);
        nameTextField = new JTextField(txtSize);
        nickNameTextField = new JTextField(txtSize);

        joinBtn = new JButton("JOIN");
        joinBtn.setPreferredSize(btnSize);
        cancelBtn = new JButton("CANCEL");
        cancelBtn.setPreferredSize(btnSize);

        // 레이아웃의 열과 행을 설정, 0은 열의 값을 제한하지 않는다
        mainCPanel = new JPanel(new GridLayout(0, 1));
    }

    private void setDisplay() {

        FlowLayout flowLeft = new FlowLayout(FlowLayout.LEFT);

        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        northPanel.add(titleLabel);

        JPanel idPanel = new JPanel(flowLeft);
        idPanel.add(idLabel);
        idPanel.add(idTxt);

        JPanel pwPanel = new JPanel(flowLeft);
        pwPanel.add(pwLabel);
        pwPanel.add(pwTxt);

        JPanel rePanel = new JPanel(flowLeft);
        rePanel.add(reLabel);
        rePanel.add(reTxt);

        JPanel namePanel = new JPanel(flowLeft);
        namePanel.add(nameLabel);
        namePanel.add(nameTextField);

        mainCPanel.add(idPanel);
        mainCPanel.add(pwPanel);
        mainCPanel.add(rePanel);
        mainCPanel.add(namePanel);

        JPanel southPanel = new JPanel();
        southPanel.add(joinBtn);
        southPanel.add(cancelBtn);

        mainCPanel.add(northPanel, BorderLayout.NORTH);
        mainCPanel.add(southPanel, BorderLayout.SOUTH);

        mainCPanel.setBorder(new EmptyBorder(0, 20, 0, 20));

        add(northPanel, BorderLayout.NORTH);
        add(mainCPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

    }

    private void addListeners() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                dispose();
                loginForm.setVisible(true);
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
                loginForm.setVisible(true);
            }
        });


        joinBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(isBlank()) {
                    JOptionPane.showMessageDialog(JoinForm.this, "모든 정보를 입력해주세요","RETRY",JOptionPane.WARNING_MESSAGE);
                } else {
                    if(users.isIdOverlap(idTxt.getText())) {
                        JOptionPane.showMessageDialog(JoinForm.this, "이미 존재하는 아이디입니다","RETRY",JOptionPane.WARNING_MESSAGE);
                        idTxt.requestFocus();
                    } else if (!String.valueOf(((JPasswordField) pwTxt).getPassword()).equals(String.valueOf(((JPasswordField) reTxt).getPassword()))) {
                        JOptionPane.showMessageDialog(JoinForm.this, "암호가 일치하지 않습니다");
                        pwTxt.requestFocus();
                    } else {
                        users.addUser(new User(idTxt.getText(),
                                String.valueOf(((JPasswordField) pwTxt) .getPassword()),
                                nameTextField.getText()));
                        JOptionPane.showMessageDialog(JoinForm.this, "회원가입을 완료했습니다","WELCOME",JOptionPane.PLAIN_MESSAGE);
                        dispose();
                        loginForm.setVisible(true);
                    }
                }
            }
        });

    }

    public boolean isBlank() {
        boolean result = false;
        if(idTxt.getText().isEmpty()) {
            idTxt.requestFocus();
            return true;
        }
        if(String.valueOf(pwTxt.getText()).isEmpty()) {
            pwTxt.requestFocus();
            return true;
        }
        if(String.valueOf(reTxt.getText()).isEmpty()) {
            reTxt.requestFocus();
            return true;
        }
        if(nameTextField.getText().isEmpty()) {
            nameTextField.requestFocus();
            return true;
        }
        return result;
    }

    private void showFrame() {
        pack();
        setLocationRelativeTo(loginForm); 						// loginForm이 있는 위치를 기준으로 위치를 조정함
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // 다이얼로그를 닫을 때 해당 다이얼로그만 닫히고 프로그램이 종료되지는 않음
        setResizable(false); 													// 창 크기를 조절할 수 없도록 설정함
        setVisible(true);

    }


}