// 13122KB, 340ms

package bj20055;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100;
	static final int MAX_K = 2 * MAX_N;

	static int N, K;
	static Position[] belt;
	static int beltLength;

	static class Position {
		int durability;
		boolean hasRobot;

		public Position(int durability, boolean hasRobot) {
			super();
			this.durability = durability;
			this.hasRobot = hasRobot;
		}

		@Override
		public String toString() {
			return "[dur=" + durability + ", has=" + hasRobot + "]";
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		beltLength = 2 * N;

		// 벨트 초기 상태 입력
		st = new StringTokenizer(br.readLine(), " ");
		belt = new Position[beltLength];
		for (int i = 0; i < beltLength; i++) {
			int Ai = Integer.parseInt(st.nextToken());
			belt[i] = new Position(Ai, false);
		}

		int answer = 0;
		while (true) {
			answer++;

			// 벨트 한 칸 회전
			rotateBelt();

			// 로봇 한 칸씩 이동
			tryMoveRobots();

			// 첫 칸에 로봇 올리기
			putRobotAtFront();
			
			int cnt = countZeros();
			if (cnt >= K) {
				break;
			}
		}
		
		System.out.println(answer);

	} // end main

	/** 벨트 상태 출력 */
	public static void printBelt() {
		System.out.println(Arrays.toString(belt));
	}

	/** 벨트를 한 칸 회전시키는 함수 */
	public static void rotateBelt() {
		// 2N번째 칸 임시저장
		Position tmp = belt[beltLength - 1];
		// 2N번째 칸부터 1번 칸까지를 채운다.
		for (int i = beltLength - 2; i >= 0; i--) {
			belt[(i + 1) % beltLength] = belt[i];
		}
		// 1번 칸에 임시저장해두었던 칸의 정보를 넣는다.
		belt[0] = tmp;
		
		// 마지막 칸에 로봇이 있다면 내린다.
		belt[N - 1].hasRobot = false;

		return;
	}

	/** 벨트 위에 존재하는 모든 로봇의 이동을 시도하는 함수 */
	public static void tryMoveRobots() {
		for (int i = N - 2; i >= 0; i--) {
			// 칸에 로봇이 있고, 다음 칸에 로봇이 없고, 다음 칸의 내구도가 있다면 이동
			if (belt[i].hasRobot && !belt[i + 1].hasRobot && belt[i + 1].durability > 0) {
				belt[i + 1].durability--;
				belt[i].hasRobot = false;
				belt[i + 1].hasRobot = true;
			}
		}

		// 마지막 칸에 로봇이 있다면 내린다.
		belt[N - 1].hasRobot = false;

		return;
	}

	/** 첫 칸에 로봇을 올리는 함수 */
	public static void putRobotAtFront() {
		if (belt[0].durability > 0) {
			belt[0].hasRobot = true;
			belt[0].durability--;
		}
	}

	/** 벨트 위에 내구도가 0인 칸의 개수를 세서 리턴하는 함수 */
	public static int countZeros() {
		int ret = 0;
		for (Position p : belt) {
			if (p.durability == 0) {
				ret++;
			}
		}

		return ret;
	}
}