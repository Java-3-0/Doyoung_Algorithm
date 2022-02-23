// 11484KB, 88ms

package bj20436;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
	/** 키보드 상의 문자 -> 위치로의 매핑 */
	static Map<Character, Position> keyboard = new HashMap<Character, Position> () {
		private static final long serialVersionUID = 5643983287822933092L;
		{
			// 윗 줄
			put('q', new Position(0, 0));
			put('w', new Position(0, 1));
			put('e', new Position(0, 2));
			put('r', new Position(0, 3));
			put('t', new Position(0, 4));
			put('y', new Position(0, 5));
			put('u', new Position(0, 6));
			put('i', new Position(0, 7));
			put('o', new Position(0, 8));
			put('p', new Position(0, 9));
			
			put('a', new Position(1, 0));
			put('s', new Position(1, 1));
			put('d', new Position(1, 2));
			put('f', new Position(1, 3));
			put('g', new Position(1, 4));
			put('h', new Position(1, 5));
			put('j', new Position(1, 6));
			put('k', new Position(1, 7));
			put('l', new Position(1, 8));
			
			put('z', new Position(2, 0));
			put('x', new Position(2, 1));
			put('c', new Position(2, 2));
			put('v', new Position(2, 3));
			put('b', new Position(2, 4));
			put('n', new Position(2, 5));
			put('m', new Position(2, 6));
		}
	};
	
	/** 위치 객체 */
	static class Position {
		/** y좌표 */
		int y;
		/** x좌표 */
		int x;
		
		/** y좌표, x좌표를 파라미터로 받아서 위치 객체를 생성하는 생성자 */
		public Position(int y, int x) {
			super();
			this.y = y;
			this.x = x;
		}
		
		/** 왼손으로 입력하는 위치이면 true, 아니면 false를 리턴 */
		public boolean isLeftHand() {
			if (this.y <= 1) {
				if (this.x <= 4) {
					return true;
				}
				return false;
			}
			else {
				if (this.x <= 3) {
					return true;
				}
				return false;
			}
		}
		
		public int getDistanceTo(Position p) {
			int yDist = Math.abs(this.y - p.y);
			int xDist = Math.abs(this.x - p.x);
			
			return yDist + xDist;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 각 손의 시작 위치 입력
		st = new StringTokenizer(br.readLine(), " ");
		char leftStart = st.nextToken().charAt(0);
		char rightStart = st.nextToken().charAt(0);

		
		Position leftPos = keyboard.get(leftStart);
		Position rightPos = keyboard.get(rightStart);
		// 타이핑할 문자열 입력
		String s = br.readLine();
		
		
		int len = s.length();
		int totalTime = len;
		for (int i = 0; i < len; i++) {
			char target = s.charAt(i);
			Position targetPos = keyboard.get(target);
			if (targetPos.isLeftHand()) {
				totalTime += leftPos.getDistanceTo(targetPos);
				leftPos = targetPos;
			} else {
				totalTime += rightPos.getDistanceTo(targetPos);
				rightPos = targetPos;
			}
		}
		
		System.out.println(totalTime);
	} // end main
}