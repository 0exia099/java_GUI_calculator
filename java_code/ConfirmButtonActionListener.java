package termproject;
import java.awt.Font;   
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;



public class ConfirmButtonActionListener implements ActionListener {
	JTextField text;//gui 텍스트필드
	JLabel label;//gui 라벨
	JButton bu[];//입력받는 버튼 배열
	static Stack<String> stack = new Stack<>();//백스페이스 구현을 위한 스택
	static String infix = "";//출력할 문자열을 가지는 문자열
	static double resultD;//계산 결과를 받는 double
	boolean oneDot = false;//입력중인 숫자에 .이 하나 이상있으면 true없으면 false
	
	ConfirmButtonActionListener(JTextField text, JLabel label, JButton[] button){//생성되면 gui의 텍스트필드와 라벨을 가져와 버튼입력되면 변화에 사용
		this.text = text;
		this.label = label;
		this.bu = button;//입력받는 버튼의 배열
	}
	
	public void actionPerformed(ActionEvent e) {
		
		JButton b = (JButton) e.getSource();//몇몇 버튼은 이미지를 사용하기때문에 getSource로 어떤 버튼인지 확인
		
		if(b == bu[0]) {//"+/-"버튼 - 식 계산후 양수는 음수로 음수는 양수로
			oneDot = false;//숫자끝 oneDot 초기화
			Postfix ps = new Postfix(infix);
			//문자열 길이에따라 글자크기 변경
			if(infix.length()>42) {
				label.setFont(new Font(null, Font.BOLD, 13));
			}
			else if(infix.length()>30) {
				label.setFont(new Font(null, Font.BOLD, 15));
			}
			else if(infix.length()>25) {
				label.setFont(new Font(null, Font.BOLD, 17));
			}
			else if(infix.length()>20) {
				label.setFont(new Font(null, Font.BOLD, 20));
			}
			else if(infix.length()>15) {
				label.setFont(new Font(null, Font.BOLD, 23));
			}
			else if(infix.length()>12) {
				label.setFont(new Font(null, Font.BOLD, 26));
			}
			else {
				label.setFont(new Font(null, Font.BOLD, 30));
			}
			label.setText("-("+infix+")=");//라벨공간 출력
			ps.toPostfix();
			resultD = -1 * ps.eval();//계산
			
			if (resultD % 1 == 0)//%1해서 0이면 정수, 아니면 실수
				infix = String.valueOf((int)resultD);//정수는 소수점아래 자리 없애기위해 int형으로 변경
			else
				infix = String.valueOf(resultD);
			
			stack.clear();//스택의 내용 필요없기때문에 스택비움
			for(String a : infix.split("")) {
				if(a.equals("."))//출력결과에 .있으면 실수라 .있는 숫자.
					oneDot = true;
				stack.push(a);//출력결과를 stack한 자리씩 push
			}
		}
		else if(b == bu[1]) {//"("버튼
			oneDot = false;//숫자끝 oneDot 초기화
			if(stack.isEmpty()) {//스택이 비었으면 아무내용 없는것
				stack.push("(");
				infix = "" + "(";//아무내용이 없으면 0이
			}
			else {
				if(infix.equals("0")) {
					stack.clear();
					stack.push("(");
					infix = "(";
				}
				else {
					stack.push("(");
					infix = infix + "(";
				}
			}
		}
		else if(b == bu[2]) {//")"버튼
			oneDot = false;//숫자끝 oneDot 초기화
			if(stack.isEmpty()) {
				stack.push(")");
				infix = "" + ")";
			}
			else {
				if(infix.equals("0")) {
					stack.clear();
					stack.push(")");
					infix = ")";
				}
				else {
					stack.push(")");
					infix = infix + ")";
				}
			}
		}
		else if(b == bu[3]) {//"<-"버튼 - 현재 텍스트필드에 표시되는 문자열에서 마지막 문자 하나 삭제
			if(!stack.isEmpty()) {//스택이 비지 않았다면 하나 pop
				String s = stack.pop();
				if(s.equals("."))
					oneDot = false;//.지웠으므로 oneDot 초기화
				infix = infix.substring(0,infix.length() - s.length());//pop한 문자의 크기만큼 뒤에서 분리
			}
		}
		else if(b == bu[4]) {// "1/x"버튼 - 해당 수를 분모로 하고 분자가 1인 수로 변경
			oneDot = false;//숫자끝 oneDot 초기화
			Postfix ps = new Postfix(infix);
			if(infix.length()>41) {
				label.setFont(new Font(null, Font.BOLD, 13));
			}
			else if(infix.length()>29) {
				label.setFont(new Font(null, Font.BOLD, 15));
			}
			else if(infix.length()>24) {
				label.setFont(new Font(null, Font.BOLD, 17));
			}
			else if(infix.length()>19) {
				label.setFont(new Font(null, Font.BOLD, 20));
			}
			else if(infix.length()>14) {
				label.setFont(new Font(null, Font.BOLD, 23));
			}
			else if(infix.length()>11) {
				label.setFont(new Font(null, Font.BOLD, 26));
			}
			else {
				label.setFont(new Font(null, Font.BOLD, 30));
			}
			label.setText("1/("+infix+")=");
			ps.toPostfix();
			resultD = 1 / ps.eval();
			if (resultD % 1 == 0)
				infix = String.valueOf((int)resultD);
			else
				infix = String.valueOf(resultD);
			stack.clear();//스택비움
			for(String a : infix.split("")) {
				if(a.equals("."))//출력결과에 .있으면 실수라 .있는 숫자.
					oneDot = true;
				stack.push(a);
			}
		}
		else if(b == bu[5]) {// "x^2"버튼 - 2제곱 계산
			oneDot = false;//숫자끝 oneDot 초기화
			stack.push("^");
			stack.push("2");
			infix = infix + "^2";
		}
		else if(b == bu[6]) {// "^"버튼 - ^입력. 다음에 오는 수에 따라 몇 제곱인지 계산
			oneDot = false;//숫자끝 oneDot 초기화
			stack.push("^");
			infix = infix + "^";
		}
		else if(b == bu[7]) {// "√x"버튼 - 해당 수의 제곱근 계산
			oneDot = false;//숫자끝 oneDot 초기화
			if(infix.equals("0")) {
				stack.clear();
				stack.push("√(");
				infix = "√(";
			}
			else {
				stack.push("√(");
				infix = infix + "√(";
			}
		}
		else if(b == bu[8]) {// "%"버튼 - %계산(나머지 구하는 계산)
			oneDot = false;//숫자끝 oneDot 초기화
			stack.push("%");
			infix = infix + "%";
		}
		else if(b == bu[9]) {//"C"버튼 - 모든 식 제거
			oneDot = false;//숫자끝 oneDot 초기화
			stack.clear();//스택비움
			infix = "";//문자열 비움
			label.setText(infix);
		}
		else if(b == bu[10]) {//"π"버튼은 계산시 3.14159265359로 계산되고 식에는 π로 그대로 표현함으로 숫자와 같이 그냥 push로 문자열에 추가
			oneDot = true;//파이는 실수라 .있음.
			if(stack.isEmpty()) {
				stack.push("π");
				infix = "" + "π";
			}
			else {
				if(infix.equals("0")) {
					stack.clear();
					stack.push("π");
					infix = "π";
				}
				else {
					stack.push("π");
					infix = infix + "π";
				}
			}
		}
		else if(b == bu[11]){// "÷"버튼 - 나누기
			oneDot = false;//숫자끝 oneDot 초기화
			stack.push("÷");
			infix = infix + "÷";
		}
		else if(b == bu[12]) {//"7"버튼
			if(stack.isEmpty()) {//아무것도 없을때
				stack.push("7");
				infix = "" + "7";
			}
			else {
				if(infix.equals("0")) {//0만 입력되어있을때
					stack.clear();
					stack.push("7");
					infix = "7";
				}
				else {
					stack.push("7");
					infix = infix + "7";
				}
			}
		}
		else if(b == bu[13]) {//"8"버튼
			if(stack.isEmpty()) {
				stack.push("8");
				infix = "" + "8";
			}
			else {
				if(infix.equals("0")) {
					stack.clear();
					stack.push("8");
					infix = "8";
				}
				else {
					stack.push("8");
					infix = infix + "8";
				}
			}
		}
		else if(b == bu[14]) {//"9"버튼
			if(stack.isEmpty()) {
				stack.push("9");
				infix = "" + "9";
			}
			else {
				if(infix.equals("0")) {
					stack.clear();
					stack.push("9");
					infix = "9";
				}
				else {
					stack.push("9");
					infix = infix + "9";
				}
			}
		}
		else if(b == bu[15]){// "×"버튼 - 곱하기
			oneDot = false;//숫자끝 oneDot 초기화
			stack.push("×");
			infix = infix + "×";
		}
		else if(b == bu[16]) {//"4"버튼
			if(stack.isEmpty()) {
				stack.push("4");
				infix = "" + "4";
			}
			else {
				if(infix.equals("0")) {
					stack.clear();
					stack.push("4");
					infix = "4";
				}
				else {
					stack.push("4");
					infix = infix + "4";
				}
			}
		}
		else if(b == bu[17]) {//"5"버튼
			if(stack.isEmpty()) {
				stack.push("5");
				infix = "" + "5";
			}
			else {
				if(infix.equals("0")) {
					stack.clear();
					stack.push("5");
					infix = "5";
				}
				else {
					stack.push("5");
					infix = infix + "5";
				}
			}
		}
		else if(b == bu[18]) {//"6"버튼
			if(stack.isEmpty()) {
				stack.push("6");
				infix = "" + "6";
			}
			else {
				if(infix.equals("0")) {
					stack.clear();
					stack.push("6");
					infix = "6";
				}
				else {
					stack.push("6");
					infix = infix + "6";
				}
			}
		}
		else if(b == bu[19]){// "-"버튼 - 빼기
			oneDot = false;//숫자끝 oneDot 초기화
			if(infix.equals("0")) {
				stack.clear();
				stack.push("-");
				infix = "-";
			}
			else {
				stack.push("-");
				infix = infix + "-";
			}
		}
		else if(b == bu[20]) {//"1"버튼
			if(stack.isEmpty()) {
				stack.push("1");
				infix = "" + "1";
			}
			else {
				if(infix.equals("0")) {
					stack.clear();
					stack.push("1");
					infix = "1";
				}
				else {
					stack.push("1");
					infix = infix + "1";
				}
			}
		}
		else if(b == bu[21]) {//"2"버튼
			if(stack.isEmpty()) {
				stack.push("2");
				infix = "" + "2";
			}
			else {
				if(infix.equals("0")) {
					stack.clear();
					stack.push("2");
					infix = "2";
				}
				else {
					stack.push("2");
					infix = infix + "2";
				}
			}
		}
		else if(b == bu[22]) {//"3"버튼
			if(stack.isEmpty()) {
				stack.push("3");
				infix = "" + "3";
			}
			else {
				if(infix.equals("0")) {
					stack.clear();
					stack.push("3");
					infix = "3";
				}
				else {
					stack.push("3");
					infix = infix + "3";
				}
			}
		}
		else if(b == bu[23]){// "+"버튼 - 더하기
			oneDot = false;//숫자끝 oneDot 초기화
			stack.push("+");
			infix = infix + "+";
		}
		else if(b == bu[24]) {//"100"버튼 - 100입력. 하나씩 떼서 push
			if(stack.isEmpty()) {
				stack.push("1");
				stack.push("0");
				stack.push("0");
				infix = "" + "100";
			}
			else {
				if(infix.equals("0")) {
					stack.clear();
					stack.push("1");
					stack.push("0");
					stack.push("0");
					infix = "100";
				}
				else {
					stack.push("1");
					stack.push("0");
					stack.push("0");
					infix = infix + "100";
				}
			}
		}
		else if(b == bu[25]) {//"0"버튼
			if(stack.isEmpty()) {
				stack.push("0");
				infix = "" + "0";
			}
			else {
				if(infix.equals("0")) {
					stack.clear();
					stack.push("0");
					infix = "0";
				}
				else {
					stack.push("0");
					infix = infix + "0";
				}
			}
		}
		else if(b == bu[26]){// "."버튼 - 소수점
			if(infix.equals("")) {
				stack.push("0");
				stack.push(".");
				infix = "0" + ".";
				oneDot = true;
			}
			else if(stack.peek().equals(".")) {
				//앞에 .입력했으면 더 입력 못함.
			}
			else if(oneDot == true) {
				//입력숫자에 . 있으면 .더 입력 못함.
			}
			else {
				stack.push(".");
				infix = infix + ".";
				oneDot = true;
			}
		}
		else if(b == bu[27]) {//"="버튼 - 입력된 식 계산
			oneDot = false;
			if(infix.equals("")) {
				label.setText("0=");
			}
			else {
				Postfix ps = new Postfix(infix);
				if(infix.length()>45) {
					label.setFont(new Font(null, Font.BOLD, 13));
				}
				else if(infix.length()>33) {
					label.setFont(new Font(null, Font.BOLD, 15));
				}
				else if(infix.length()>28) {
					label.setFont(new Font(null, Font.BOLD, 17));
				}
				else if(infix.length()>23) {
					label.setFont(new Font(null, Font.BOLD, 20));
				}
				else if(infix.length()>18) {
					label.setFont(new Font(null, Font.BOLD, 23));
				}
				else if(infix.length()>15) {
					label.setFont(new Font(null, Font.BOLD, 26));
				}
				else {
					label.setFont(new Font(null, Font.BOLD, 30));
				}
				label.setText(infix + "=");
				ps.toPostfix();
				resultD = ps.eval();
				if (resultD % 1 == 0)
					infix = String.valueOf((int)resultD);
				else
					infix = String.valueOf(resultD);
				stack.clear();//스택비움
				for(String a : infix.split("")) {
					if(a.equals("."))//출력결과에 .있으면 실수라 .있는 숫자.
						oneDot = true;
					stack.push(a);
				}
			}
		}
		
		if(infix.equals(""))//아무 값 없으면 0출력
			text.setText("0");
		else {
			//출력 글자 수에 따라 폰트 크기 변경
			if(infix.length()>43) {
				text.setFont(new Font(null, Font.BOLD, 20));
			}
			else if(infix.length()>31) {
				text.setFont(new Font(null, Font.BOLD, 16));
			}
			else if(infix.length()>26) {
				text.setFont(new Font(null, Font.BOLD, 20));
			}
			else if(infix.length()>21) {
				text.setFont(new Font(null, Font.BOLD, 25));
			}
			else if(infix.length()>16) {
				text.setFont(new Font(null, Font.BOLD, 30));
			}
			else if(infix.length()>13) {
				text.setFont(new Font(null, Font.BOLD, 40));
			}
			else {
				text.setFont(new Font(null, Font.BOLD, 50));
			}
			text.setText(infix);
		}
		
		text.requestFocus();//버튼을 클릭해도 다시 키보드로 입력받을 수 있도록 포커스를 text로 설정
	}

	
}
