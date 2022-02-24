// 42604KB, 376ms

package bj15787;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_TRAINS = 100000;
	static final int MAX_COMMANDS = 100000;
	static final int MAX_SEATS = 20;

	static class Train {
		int seats;

		public Train() {
			super();
			seats = 0;
		}

		/** seatNum칸에 사람이 없다면 사람을 태운다 */
		public void fun1(int seatNum) {
			if (seatNum <= 0 || seatNum > MAX_SEATS) {
				return;
			}
			
			seats |= (1 << (seatNum - 1));
		}

		/** seatNum칸에 사람이 있다면 내린다 */
		public void fun2(int seatNum) {
			if (seatNum <= 0 || seatNum > MAX_SEATS) {
				return;
			}
			
			seats &= ~(1 << (seatNum - 1));
		}

		/** 모든 승객이 한 칸씩 뒤로 간다 */
		public void fun3() {
			seats = (seats << 1);
			seats &= ((1 << MAX_SEATS) - 1);
		}

		/** 모든 승객이 한 칸씩 앞으로 간다 */
		public void fun4() {
			seats = (seats >> 1);
			seats &= ((1 << MAX_SEATS) - 1);
		}

		/** 명령을 받아서 처리 */
		public void handleCommand(int commandType, int seatNum) {
			switch (commandType) {
			case 1:
				fun1(seatNum);
				break;
			case 2:
				fun2(seatNum);
				break;
			case 3:
				fun3();
				break;
			case 4:
				fun4();
				break;
			default:
				break;
			}
		}

		@Override
		public String toString() {
			return "Train [seats=" + seats + "]";
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		final int N = Integer.parseInt(st.nextToken());
		final int M = Integer.parseInt(st.nextToken());

		// 기차 객체들을 생성해서 배열에 저장
		Train[] trains = new Train[N + 1];
		for (int i = 0; i < trains.length; i++) {
			trains[i] = new Train();
		}

		// 명령 개수만큼 입력받아서 수행
		for (int commandIdx = 0; commandIdx < M; commandIdx++) {
			st = new StringTokenizer(br.readLine(), " ");
			int commandType = Integer.parseInt(st.nextToken());
			int trainNum = Integer.parseInt(st.nextToken());
			int seatNum = -1;
			if (commandType <= 2) {
				seatNum = Integer.parseInt(st.nextToken());
			}

			trains[trainNum].handleCommand(commandType, seatNum);
		}

		// 기차마다 set에 존재하면 못 지나가고, set에 존재하지 않으면 지나가면서 set에 추가한다.
		Set<Integer> passed = new HashSet<>();
		int count = 0;
		for (int i = 1; i <= N; i++) {
			Train t = trains[i];
			int seatStatus = (t.seats);

			if (!passed.contains(seatStatus)) {
				count++;
				passed.add(seatStatus);
			}
		}
		
		// 지나간 기차의 개수 출력
		System.out.println(count);

	} // end main
}