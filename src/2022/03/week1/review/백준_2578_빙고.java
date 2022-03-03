// 12988KB, 128ms

package baek2578;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

class Position {
	/** y좌표 */
	int y;
	/** x좌표 */
	int x;
	/** y좌표, x좌표를 입력받아 객체를 생성하는 생성자 */
	public Position(int y, int x) {
		super();
		this.y = y;
		this.x = x;
	}
}

public class Main {
	public static final int BINGO_SIZE = 5;

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		StringTokenizer st;
		
		// 입력을 받아서 수 -> 빙고판에서의 위치로의 매핑 생성
		Map<Integer, Position> board = new HashMap<> ();
		for (int y = 0 ; y < BINGO_SIZE; y++) {
			for (int x = 0; x < BINGO_SIZE; x++) {
				board.put(sc.nextInt(), new Position(y, x));
			}
		}
		
		// 입력을 받아서 사회자가 부르는 수의 배열 생성
		int[] calls = new int[BINGO_SIZE * BINGO_SIZE];
		for (int y = 0 ; y < BINGO_SIZE; y++) {
			for (int x = 0; x < BINGO_SIZE; x++) {
				calls[BINGO_SIZE * y + x] = sc.nextInt();
			}
		}
		
		
		int[] sumY = new int[BINGO_SIZE];
		int[] sumX = new int[BINGO_SIZE];
		int[] sumDiagonal = new int[2];
		
		int countBingo = 0;
		int answer = 0;
		for (int i = 0; i < calls.length; i++) {
			Position p = board.get(calls[i]);
			int y = p.y;
			int x = p.x;
			
			if (++sumY[y] == BINGO_SIZE) {
				countBingo++;
			}
			if (++sumX[x] == BINGO_SIZE) {
				countBingo++;
			}
			if (y == x) {
				if (++sumDiagonal[0] == BINGO_SIZE) {
					countBingo++;
				}
			}
			if (y == BINGO_SIZE - 1 - x) {
				if (++sumDiagonal[1] == BINGO_SIZE) {
					countBingo++;
				}
			}
			
			if (countBingo >= 3) {
				answer = i + 1;
				break;
			}
		}
		
		System.out.println(answer);
		
	}

}
