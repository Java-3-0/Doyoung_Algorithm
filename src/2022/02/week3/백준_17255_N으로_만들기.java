// 11564KB, 76ms

package bj17255;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main {
	public static String target;
	public static Set<String> set = new HashSet<>();
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력
		target = br.readLine();

		// target이라는 수를 만드는 과정을 백트래킹
		backtrackNumber (target, "");
		
		// set에 넣으면 중복되는 값이 사라지므로, 이 set의 크기가 중복되지 않는 답의 개수
		int answer = set.size();
		
		// 정답 출력
		System.out.println(answer);
	}

	/** s라는 수를 만드는 과정을 역추적하면서 그 과정을 accum이라는 파라미터에 담아서 전달하고, 최종적으로 역추적이 끝나면 accum을 set에 추가. */
	public static void backtrackNumber (String s, String accum) {
		int len = s.length();
		
		// 역추적이 끝난 경우
		if (len == 0) {
			set.add(accum);
			return;
		}
		
		// 마지막으로 적은 수가 맨 앞 수인 경우
		backtrackNumber(s.substring(1), accum + s.substring(1));
		
		// 마지막으로 적은 수가 맨 뒤 수인 경우
		backtrackNumber(s.substring(0, len - 1), accum + s.substring(0, len - 1));
	}
}