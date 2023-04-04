package termproject;

import java.awt.*;   
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.MatteBorder;

class Img{//버튼에 사용할 이미지 가지는 클래스
	ImageIcon bt[] = new ImageIcon[12];
	String imgName;
	
	Img() {//생성시 이미지 받아옴
		for(int i = 0; i<10; i++) {//0부터 9까지 숫자 이미지
			imgName = String.valueOf(i) + ".jpg";
			bt[i] = new ImageIcon(getClass().getClassLoader().getResource(imgName));
		}
		bt[10] = new ImageIcon(getClass().getClassLoader().getResource("dot.jpg"));//.이미지
		bt[11] = new ImageIcon(getClass().getClassLoader().getResource("c.jpg"));//=이미지
	}
}
class Imgp{//버튼 눌렸을때 사용할 이미지 가지는 클래스
	ImageIcon bt[] = new ImageIcon[12];
	String imgName;
	
	Imgp() {//생성시 이미지 받아옴
		for(int i = 0; i<10; i++) {//0부터 9까지 숫자 이미지
			imgName = String.valueOf(i) + "p.jpg";
			bt[i] = new ImageIcon(getClass().getClassLoader().getResource(imgName));
		}
		bt[10] = new ImageIcon(getClass().getClassLoader().getResource("dotp.jpg"));//.이미지
		bt[11] = new ImageIcon(getClass().getClassLoader().getResource("cp.jpg"));//=이미지
	}
}

public class JavaCalculator {

	public static void main(String[] args) {
		Img img = new Img();
		Imgp imgp = new Imgp();
		
		JButton button[] = new JButton[28];//연산기호와 숫자 들어갈 버튼
		String st[] = {"+/-", "(", ")", "<-", "1/x", "^2", "^", "√", "%", "C", "π", "÷", "7", "8", "9", "×", "4", "5", "6", "-", "1", "2", "3", "+", "100", "0", ".", "="};//연산기호와 숫자 넣을 순서대로 문자열
		Color color = new Color(0x424242);//연산기호버튼에 사용할 색
		Color color1 = new Color(0x4BA5D9);//"100", "π"버튼에 사용할 색
		//Color color2 = new Color(0xEA6832);//"="버튼에 사용할 색-이미지로 대체
		Color color3 = new Color(0xe0e0e0);//배경에 사용할 색
		//Color color4 = new Color(0xE9EAEB);//숫자버튼에 사용할 색-이미지로 대체
		Color color5 = new Color(0x952F3C);//"<-"버튼, "C"버튼에 사용할 색
		
		MatteBorder b = new MatteBorder(3,3,3,3,color3);//버튼및 텍스트필드 테두리 크기및 색 변경
		
		JFrame frame = new JFrame("java 계산기");
		frame.setPreferredSize(new Dimension(400,600));//크기 400,600
		frame.setResizable(false);//프레임크기 변경 불가능 설정
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//화면크기
		
		frame.setLocation((int)(screenSize.getWidth()/2 - 200),(int)(screenSize.getHeight()/2 - 300));//화면크기를 이용해 화면 정중앙에 팝업
		
		Container contentPane = frame.getContentPane();
		
		contentPane.setLayout(new BorderLayout(5,5));
		contentPane.setBackground(color3);
		
		JTextField text = new JTextField();//식입력 칸
		JLabel label = new JLabel("", JLabel.RIGHT);//답 출력
		
		text.setBackground(color3);
		text.setFont(new Font(null, Font.BOLD, 50));
		text.setBorder(b);//텍스트필드 테두리 크기및 색 변경
		text.setHorizontalAlignment(JTextField.RIGHT);//텍스트필드 글자 오른쪽 정렬되서 출력
		text.setOpaque(true);
		
		label.setBackground(color3);
		label.setFont(new Font(null, 0, 30));
		label.setHorizontalAlignment(JTextField.RIGHT);//라벨 글자 오른쪽 정렬되서 출력
		label.setOpaque(true);
		
		
		JPanel resultP = new JPanel();//출력 패널
		resultP.setBorder(b);
		resultP.setBackground(color3);
		resultP.setLayout(new GridLayout(2,1));
		
		resultP.add(label);
		resultP.add(text);
		contentPane.add(resultP, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);//스크롤 패널
		resultP.add(scrollPane);
		scrollPane.setBorder(b);//스크롤패널 테두리 크기및 색 변경
		
		resultP.setPreferredSize(new Dimension(800,200));
		
		JPanel numP = new JPanel();//숫자판 패널
		numP.setBorder(b);
		numP.setBackground(color3);
		numP.setLayout(new GridLayout(7,4));
		ActionListener listener = new ConfirmButtonActionListener(text, label, button);
		
		
		//이미지로 버튼 생성
		for(int i = 12; i<15;i++) {
			button[i] = new JButton(img.bt[i - 5]);//버튼 이미지
			button[i].setPressedIcon(imgp.bt[i - 5]);//버튼 눌릴때 이미지
		}
		for(int i = 16; i<19;i++) {
			button[i] = new JButton(img.bt[i - 12]);//버튼 이미지
			button[i].setPressedIcon(imgp.bt[i - 12]);//버튼 눌릴때 이미지
		}
		for(int i = 20; i<23;i++) {
			button[i] = new JButton(img.bt[i - 19]);//버튼 이미지
			button[i].setPressedIcon(imgp.bt[i - 19]);//버튼 눌릴때 이미지
		}
		button[25] = new JButton(img.bt[0]);
		button[25].setPressedIcon(imgp.bt[0]);
		button[26] = new JButton(img.bt[10]);
		button[26].setPressedIcon(imgp.bt[10]);
		button[27] = new JButton(img.bt[11]);
		button[27].setPressedIcon(imgp.bt[11]);
		
		for(int i=0;i<28;i++) {
			if(i>=12&&i<=14) {//이미지로 버튼만든 것들

			}
			else if(i>=16&&i<=18) {//이미지로 버튼만든 것들
				
			}
			else if(i>=20&&i<=22) {//이미지로 버튼만든 것들
				
			}
			else if(i>=25&&i<=27) {//이미지로 버튼만든 것들
				
			}
			else {
				button[i] = new JButton(String.valueOf(st[i]));//나머지 버튼 생성
				button[i].setFont(new Font(null, 0, 24));
			}
			
			button[i].setBorder(b);// 버튼 테두리 크기및 색 변경
			
			if (i == 24 || i == 10) {//100, π가 들어가는 버튼
				button[i].setBackground(color1);//색 변경
				button[i].setForeground(Color.WHITE);
				button[i].setFont(new Font(null, Font.BOLD | Font.ITALIC, 24));
			}
			else if(i == 3 || i == 9) {//<-, C버튼   
				button[i].setBackground(color5);//색 변경
				button[i].setForeground(Color.WHITE);
				if(i == 3)
					button[i].setFont(new Font("monospaced", Font.BOLD, 24));//"<-"가 깔끔한 폰트 사용
				else
					button[i].setFont(new Font(null, Font.BOLD, 24));
			}
			else {
				if (i < 12 || i % 4 == 3){//연산기호가 들어가는 버튼
					button[i].setBackground(color);//색 변경
					button[i].setForeground(Color.WHITE);
				}
			}
			button[i].addActionListener(listener);//버튼동작
			numP.add(button[i]);//패널에 버튼 추가
		}
		
		contentPane.add(numP, BorderLayout.CENTER);
		
		text.setEditable(false);//키보드 입력되면 버튼이 눌리게 됨으로 텍스트필드에 키보드로 입력되면 2번입력됨 -> 입력안되게 setEditable(false)
		
		text.addKeyListener(new KeyListener() {//키보드 입력들어오면 버튼을 누르는 것으로 키보드 입력 구현
			private int pressShift = 0;//쉬프트 누르고 있으면 1로 바뀌어 쉬프트 구분
			
			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyPressed(KeyEvent e) {//키누르면 반응
				if(e.getKeyCode() == KeyEvent.VK_ENTER) { //엔터 입력 시 "="버튼
					button[27].doClick();
				}
				else if(e.getKeyCode() == KeyEvent.VK_EQUALS) {
					if(pressShift == 0)//쉬프트가 안눌러진 상태면 "="버튼
						button[27].doClick();
					else {
						button[23].doClick();//쉬프트가 눌러진 상태면 "+"버튼
					}
				}
				else if(e.getKeyCode() == KeyEvent.VK_ADD || e.getKeyCode() == KeyEvent.VK_PLUS) {//백스페이스 옆의 +, 숫자패드 + 입력 시 "+"버튼
					button[23].doClick();
				}
				else if(e.getKeyCode() == KeyEvent.VK_SLASH || e.getKeyCode() == 111) {//  /, 111(숫자패드의 /) 입력 시 나누기 버튼
					button[11].doClick();
				}
				else if(e.getKeyCode() == KeyEvent.VK_PERIOD || e.getKeyCode() == 110) {//110(숫자패드의 .), .입력 시 "."버튼
					button[26].doClick();
				}
				else if(e.getKeyCode() == KeyEvent.VK_MINUS || e.getKeyCode() == 109) {//109(숫자패드의 -), - 입력 시 "-"버튼
					button[19].doClick();
				}
				else if(e.getKeyCode() == KeyEvent.VK_MULTIPLY) {//숫자패드*입력시 곱하기 버튼
					button[15].doClick();
				}
				else if(e.getKeyCode() == KeyEvent.VK_0) {//키보드 위의 숫자 0입력시
					if(pressShift == 0)//쉬프트가 안눌러진 상태면 "0"버튼
						button[25].doClick();
					else {
						button[2].doClick();//쉬프트가 눌러진 상태면 ")"버튼
					}
				}
				else if(e.getKeyCode() == KeyEvent.VK_NUMPAD0) {//숫자패드 0 입력 시 "0"버튼
					button[25].doClick();
				}
				else if(e.getKeyCode() == KeyEvent.VK_1 || e.getKeyCode() == KeyEvent.VK_NUMPAD1) {//키보드 위의 숫자 1, 숫자패드 1 입력 시 "1"버튼
					button[20].doClick();
				}
				else if(e.getKeyCode() == KeyEvent.VK_2 || e.getKeyCode() == KeyEvent.VK_NUMPAD2) {//키보드 위의 숫자 2, 숫자패드 2 입력 시 "2"버튼
					button[21].doClick();
				}
				else if(e.getKeyCode() == KeyEvent.VK_3 || e.getKeyCode() == KeyEvent.VK_NUMPAD3) {//키보드 위의 숫자 3, 숫자패드 3 입력 시 "3"버튼
					button[22].doClick();
				}
				else if(e.getKeyCode() == KeyEvent.VK_4 || e.getKeyCode() == KeyEvent.VK_NUMPAD4) {//키보드 위의 숫자 4, 숫자패드 4 입력 시 "4"버튼
					button[16].doClick();
				}
				else if(e.getKeyCode() == KeyEvent.VK_5) {//키보드 위의 숫자 5입력시
					if(pressShift == 0)//쉬프트가 안눌러진 상태면 "5"버튼
						button[17].doClick();
					else {
						button[8].doClick();//쉬프트가 눌러진 상태면 "%"버튼
					}
				}
				else if(e.getKeyCode() == KeyEvent.VK_NUMPAD5) {//숫자패드 5 입력 시 "5"버튼
					button[17].doClick();
				}
				else if(e.getKeyCode() == KeyEvent.VK_6) {//키보드 위의 숫자 6입력시
					if(pressShift == 0)//쉬프트가 안눌러진 상태면 "6"버튼
						button[18].doClick();
					else {
						button[6].doClick();//쉬프트가 눌러진 상태면 "^"버튼
					}
				}
				else if(e.getKeyCode() == KeyEvent.VK_NUMPAD6) {//숫자패드 6 입력 시 "6"버튼
					button[18].doClick();
				}
				else if(e.getKeyCode() == KeyEvent.VK_7 || e.getKeyCode() == KeyEvent.VK_NUMPAD7) {//키보드 위의 숫자 7, 숫자패드 7 입력 시 "7"버튼
					button[12].doClick();
				}
				else if(e.getKeyCode() == KeyEvent.VK_8) {//키보드 위의 숫자 8입력시
					if(pressShift == 0)//쉬프트가 안눌러진 상태면 "8"버튼
						button[13].doClick();
					else {
						button[15].doClick();//쉬프트가 눌러진 상태면 곱하기 버튼
					}
				}
				else if(e.getKeyCode() == KeyEvent.VK_NUMPAD8) {//숫자패드 8 입력 시 "8"버튼
					button[13].doClick();
				}
				else if(e.getKeyCode() == KeyEvent.VK_9) {//키보드 위의 숫자 9입력시
					if(pressShift == 0)//쉬프트가 안눌러진 상태면 "9"버튼
						button[14].doClick();
					else {
						button[1].doClick();//쉬프트가 눌러진 상태면 "(" 버튼
					}
				}
				else if(e.getKeyCode() == KeyEvent.VK_NUMPAD9) {//숫자패드 9 입력 시 "9"버튼
					button[14].doClick();
				}
				else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {//백스페이스 입력 시 "<-"버튼
					button[3].doClick();
				}
				else if(e.getKeyCode() == KeyEvent.VK_DELETE) {//delete키 입력 시 "C"버튼
					button[9].doClick();
				}
				else if(e.getKeyCode() == KeyEvent.VK_SHIFT) {//쉬프트를 누르는 경우 pressShift를 1로
					pressShift = 1;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {//키를 눌렀다 뗄때 신호
				if(e.getKeyCode() == KeyEvent.VK_SHIFT) {//쉬프트에서 손을 떼면 다시 pressShift를 0으로
					pressShift = 0;
				}
			}
		});
		
		text.setText("0");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

}
