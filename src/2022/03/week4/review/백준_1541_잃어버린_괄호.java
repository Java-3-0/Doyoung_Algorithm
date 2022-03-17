// 11508KB, 80ms

package bj1541;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 입력
		String s = br.readLine();
		
		// 계산
		int answer = getMaxValue(s);
		
		// 출력
		System.out.println(answer);
	}
	
	/** 문자열 s가 주어졌을 때, 괄호를 쳐서 가능한 수식 최대 값을 리턴 */
	public static int getMaxValue(String s) {
		int ret = 0;
		boolean meetMinus = false;
		
		// 5자리까지의 수를 담아둘 스트링빌더
		StringBuilder sb = new StringBuilder();
		for (char c : s.toCharArray()) {
			// 연산자를 만나면 이전까지 sb에 쌓인 수를 처리
			if (c == '+' || c == '-') {
				int num = Integer.parseInt(sb.toString());
				if (meetMinus) {
					ret -= num;
				}
				else {
					ret += num;
				}
				
				sb.setLength(0);
			}
			
			// 숫자를 만나면 sb에 쌓는다
			else {
				sb.append(c);
			}
			
			// 마이너스를 한 번이라도 만났다면 meetMinus를 true로 세팅
			if (c == '-') {
				meetMinus = true;
			}
		}
		
		
		// 맨 마지막 수를 처리
		int num = Integer.parseInt(sb.toString());
		if (meetMinus) {
			ret -= num;
		}
		else {
			ret += num;
		}
		
		return ret;
	}

}
