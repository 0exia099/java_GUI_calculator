package termproject;
import java.util.LinkedList; 
import java.util.Queue;
import java.util.Stack;

public class Postfix {
//                (  )  +  -  ×  ÷  %  ^  eos 순서
	int isp[] = { 0,19,12,12,13,13,13,18,0 };//스택에 저장된 연산자의 우선순위
	int icp[] = { 20,19,12,12,13,13,13,18,0 };//입력 연산자의 우선순위
	Stack<String> postfixStack = new Stack<String>();
	Stack<Double> Ds = new Stack<Double>();
	Queue<String> q = new LinkedList<String>();//postfix형식으로 큐에 다항식 넣음
	String postfix = "";
	String[] token;
	
	Postfix(String infix){//생성자
		this.token = infix.split("");//infix다항식을 각 문자별로 분리
	}
	
	boolean isNum(String str) {//문자열이 숫자면 true리턴하는 메소드
		try {
			Double.parseDouble(str);
			return true;
		}catch(NumberFormatException e) {
			return false;
		}
	}
	
	int tokenIspIcp(String token1) {//해당 문자열이 isp, icp에서 몇 번째 인덱스인지를 알려주는 메소드
		if(token1.equals("(")) {
			return 0;
		}
		else if(token1.equals(")")) {
			return 1;
		}
		else if(token1.equals("+")) {
			return 2;
		}
		else if(token1.equals("-")) {
			return 3;
		}
		else if(token1.equals("×")) {
			return 4;
		}
		else if(token1.equals("÷")) {
			return 5;
		}
		else if(token1.equals("%")) {
			return 6;
		}
		else if(token1.equals("^")) {
			return 7;
		}
		else {
			return 8;
		}
	}
	double eval() {//postfix형식으로 저장한 큐를 이용해 하나씩 꺼내며 postfix 계산
		String token1 = "1";
		postfixStack.clear();//스택비움
		double op1 = 0;
		double op2 = 0;
		int ChangeOp2 = 0;
		double data = 1;
		
		while(true) {
			token1 = q.poll();//큐에서 꺼냄
			if(token1 == null)
				break;
			if(token1.substring(0,1).equals("@")) {//숫자 앞에 @붙여둠
				Ds.push(Double.parseDouble(token1.substring(1)));//숫자는 @떼고 push
			}
			else {//연산자면 해당 연산자에 맞게 계산
				if (!Ds.isEmpty())
					op2 = Ds.pop();
				if (!Ds.isEmpty())
					op1 = Ds.pop();
				if (token1.equals("+")) {
					Ds.push(op1 + op2);
				} else if (token1.equals("-")) {
					Ds.push(op1 - op2);
				} else if (token1.equals("×")) {
					Ds.push(op1 * op2);
				} else if (token1.equals("÷")) {
					Ds.push(op1 / op2);
				} else if (token1.equals("%")) {
					Ds.push(op1 % op2);
				} else if (token1.equals("^")) {
					data = 1;
					ChangeOp2 = (int) Math.round(op2);
					if(ChangeOp2>=0)
						for(int i = 0; i < ChangeOp2; i++) {
							data = data * op1;
						}
					else {
						for(int i = 0; i > ChangeOp2; i--) {
							data = data / op1;
						}
					}
					Ds.push(data);//연산자 계산후 계산값 스택에 push
				}
			}
		}
		return Ds.pop();//마지막 계산결과 pop
	}
	void toPostfix() {//postfix형식으로 큐에 저장하는 메소드
		boolean negative = true;//전 token이 ')'가 아닌 연산자, 또는 처음 token이면 참이 되고 negative가 참일때 다음 token이 -면 음수이다.
		//ex) -8+5, 6*-2, (-5+7)
		//이러한 경우 -붙은 숫자는 음수 (6+2)-4, 4-5이러한 경우는 -가 음수를 나타내는게 아닌 연산기호.
		boolean oneDot = false;
		int j;
		int count = 0;
		
		for(int i = 0; i<token.length; i++) {
			if (token[i].equals("0") || isNum(token[i])) {//숫자면
				negative = false;
				postfix = "";
				if (i != token.length - 1 && (isNum(token[i + 1]) || token[i + 1].equals("."))) {// 다음 토큰도 숫자라면 여러자리 숫자
					for (j = 0; j < token.length - i && ((isNum(token[i + j]) || token[i + j].equals("."))); j++) {
						// 다음 토큰이 숫자가 아닐때까지 반복하여 여러자리 숫자 인식.
						//.은 소수점이므로 숫자에 포함
						if(token[i+j].equals(".")&&oneDot==false) {//처음.은 소수점
							oneDot = true;
							postfix = postfix + token[i + j];
						}
						else if (token[i+j].equals(".")&&oneDot==true) {//두번째 .부터는 잘못 입력된것. 숫자에 포함하지 않음.
							;
						}
						else
							postfix = postfix + token[i + j];
					}
					oneDot = false;
					i += j - 1;
				} else {
					postfix = postfix + token[i];
				}
				q.add("@" + postfix);//숫자는 @붙여 계산할때 구분 쉽도록 함.
			}
			else if(token[i].equals("π")) {
				negative = false;
				q.add("@" + "3.14159265359");//숫자는 @붙여 계산할때 구분 쉽도록 함.
			}
			else if(token[i].equals("√")) {//루트는 루트안 계산 후 postfix로 변경
				negative = true;
				count = 0;
				String a = "";
				for (j = i + 2; j < token.length; j++) {//루트도 괄호로 끝을 표현함으로 루트 안에 괄호열고 닫는 갯수 계산하여 루트끝 괄호까지 구하기
					if(token[j].equals("("))
						count++;
					if(count != 0 && token[j].equals(")"))
						count--;
					else if(count == 0 && token[j].equals(")"))
						break;
					
					a = a + token[j];//루트 안 infix식 a에 저장
				}
				i = j;
				Postfix Pf = new Postfix(a);//루트 안 식을 postfix로 계산하여 큐에 숫자로 add
				Pf.toPostfix();
				q.add("@" + String.valueOf(Math.sqrt(Pf.eval()))); //숫자는 @붙여 계산할때 구분 쉽도록 함.
			}
			else if (token[i].equals(")")) {// ")" 라면
				postfix = "";
				negative = false;// ')'이므로 nagative = 0
				if (!postfixStack.isEmpty()) {
					while (!postfixStack.peek().equals("(")) {// 괄호 여는기호"(" 전까지pop해서 postfix에 저장
						q.add(postfixStack.pop());
						if(postfixStack.isEmpty())
							break;
					}
				}
				if (!postfixStack.isEmpty()) {
					postfixStack.pop();// 괄호 여는기호는 삭제
				}
			}
			else {
	            if (token[i].equals("-") && negative == true) {//negative가 1이고 token이 '-'이면 음수를 나타내는'-'이므로 숫자로 처리
	            	postfix = "";
	                negative = false;//숫자이므로 negative = 0
	                postfix = postfix + "-";//음수
	                i++;
					for (j = 0; j < token.length - i && (isNum(token[i + j])|| token[i + j].equals(".")); j++) {
						//다음 토큰이 숫자가 아닐때까지 반복하여 여러자리 숫자 인식
						if(token[i+j].equals(".")&&oneDot==false) {//처음.은 소수점
							oneDot = true;
							postfix = postfix + token[i + j];
						}
						else if (token[i+j].equals(".")&&oneDot==true) {//두번째 .부터는 잘못 입력된것. 숫자에 포함하지 않음.
							;
						}
						else
							postfix = postfix + token[i + j];
					}
					oneDot = false;
					i += j - 1;
					q.add("@" + postfix);//숫자는 @붙여 계산할때 구분 쉽도록 함.
	            }
	            else {
	                negative = true;//연산자 이므로 negative = 1
	                if(!postfixStack.isEmpty()) {
	                	while (isp[tokenIspIcp(postfixStack.peek())] >= icp[tokenIspIcp(token[i])]) {//나머지는 token의 우선순위가 스택의 top에있는 연산자보다 우선순위가 낮거나 같다면
	                		q.add(postfixStack.pop());//스택의 top의 우선순위가 token보다 낮아질때까지 우선순위가 높은 연산자 pop해서 저장
	                		if(postfixStack.isEmpty())
	                			break;
	                	}
	                	postfixStack.push(token[i]);//스택의 top의 우선순위가 token의 우선순위보다 낮으면 token을 스택에 push
	                }
	                else {
	                	postfixStack.push(token[i]);
	                }
	            }
	        }
		}
		while(!postfixStack.isEmpty()) {//나머지 전부꺼내어 q에 추가 
			q.add(postfixStack.pop());
		}
	}
	
}
