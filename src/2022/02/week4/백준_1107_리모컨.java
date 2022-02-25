// 299576KB, 944ms

package bj1107;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static final int MAX_N = 500000;
	public static final int DIGITS = 10;
	public static int lenN;
	
	public static int N, M;
	public static boolean[] isBroken = new boolean[DIGITS];
	public static int[] permu;
	
	public static int minButtons = Integer.MAX_VALUE;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 이동하려는 채널 번호, 고장난 버튼 수 입력
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		
		lenN = String.valueOf(N).length();
		
		// 고장난 버튼들 입력
		if (M != 0) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 0; i < M; i++) {
				int input = Integer.parseInt(st.nextToken());
				isBroken[input] = true;
			}
		}
		
		// 순열을 저장할 공간 할당
		permu = new int[lenN + 1];
		
		// 숫자 버튼 안 누르고 화살표 버튼만 누르는 경우로 초기화
		minButtons = Math.abs(N - 100);
		
		// 모든 순열을 만들어 보면서 최소 버튼 클릭 수 계산
		permutation(0);
		
		// 출력
		System.out.println(minButtons);
		
	} // end main

	/** 모든 순열을 재귀적으로 만들어보면서 최소 버튼 클릭 수를 계산해서 minButtons를 업데이트 */
	public static void permutation(int cnt) {
		if (cnt != 0) {
			// 만든 순열에 들어 있는 수 계산
			int accum = 0;
			for (int i = 0; i < cnt; i++) {
				int num = permu[i];
				accum *= 10;
				accum += num;
			}
			
			// 그 수를 만드는 데 필요한 숫자 버튼 클릭 수 + 그 이후로 필요한 화살표 버튼 수 계산
			String accumStr = String.valueOf(accum);
			int buttons = accumStr.length() + Math.abs(accum - N);
			minButtons = Math.min(minButtons, buttons);
		}
		
		// 순열을 계속 만들어봄
		if (cnt < lenN + 1) {
			for (int i = 0; i < DIGITS; i++) {
				if (!isBroken[i]) {
					permu[cnt] = i;
					permutation(cnt + 1);
				}
			}
		}
	}
}