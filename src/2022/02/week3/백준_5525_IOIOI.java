// 20232KB, 176ms

package bj5525;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	
	public static int N;
	public static int M;
	public static String s;
	
	public static void main(String[] args) throws IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		s = br.readLine();
		br.close();
		
		System.out.println(countPn());
	}

	public static int countPn() {
		int ret = 0;
		
		int idx = 0;
		while (idx < s.length()) {
			// I를 찾으면
			if (s.charAt(idx) == 'I') {
				// 다음 위치부터 OI의 개수를 센다
				int k = 0;
				while (idx + 2 < s.length() && s.charAt(idx + 1) == 'O' && s.charAt(idx + 2) == 'I') {
					k++;
					idx = idx + 2;
				}
				
				// 지금까지 센 부분에 P(N)이 몇 군데 포함되는지를 리턴값에 추가
				if (N <= k) {
					ret += (k - N + 1);
				}
			}
			
			idx = idx + 1;
		}
		
		return ret;
	}
}
