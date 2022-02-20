// 17800KB, 220ms

package bj17615;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 입력
		int N = Integer.parseInt(br.readLine());
		String balls = br.readLine();

		// 최소 이동 횟수 계산
		int answer = minMoves(balls);
		
		// 출력
		System.out.println(answer);
	}
	
	/** 공을 이동시키는 최소 횟수를 리턴 */
	public static int minMoves(String balls) {
		int blueToLeft = moveToLeft(balls, 'B');
		int blueToRight = moveToRight(balls, 'B');
		int redToLeft = moveToLeft(balls, 'R');
		int redToRight = moveToRight(balls, 'R');
		
		int min1 = Math.min(blueToLeft, blueToRight);
		int min2 = Math.min(redToLeft, redToRight);
		int ret = Math.min(min1, min2);
		
		return ret;
	}
	
	/** color 색의 공을 맨 왼쪽으로 보내는 데 필요한 최소 이동 횟수 */
	public static int moveToLeft(String balls, char color) {
		int len = balls.length();
		
		boolean metOtherColor = false;
		int ret = 0;
		for (int idx = 0; idx < len; idx++) {
			char here = balls.charAt(idx);
			if (here != color) {
				metOtherColor = true;
			}
			
			if (here == color && metOtherColor) {
				ret++;
			}
		}
		
		return ret;
	}
	
	/** color 색의 공을 맨 오른쪽으로 보내는 데 필요한 최소 이동 횟수 */
	public static int moveToRight(String balls, char color) {
		int len = balls.length();
		int lastIdx = len - 1;
		
		boolean metOtherColor = false;
		int ret = 0;
		for (int idx = lastIdx; idx >= 0; idx--) {
			char here = balls.charAt(idx);
			if (here != color) {
				metOtherColor = true;
			}
			
			if (here == color && metOtherColor) {
				ret++;
			}
		}
		
		return ret;
	}
}