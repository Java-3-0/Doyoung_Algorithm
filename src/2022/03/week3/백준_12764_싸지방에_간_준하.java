// 60144KB, 880ms

package bj12764;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

	static final int MAX_N = 100000;
	static final int MAX_TIME = 1000000;

	static int N;
	static User[] users;
	static int[] endTimes;
	static int[] cntUsed;

	static class User implements Comparable<User> {
		int startTime;
		int endTime;

		public User(int startTime, int endTime) {
			super();
			this.startTime = startTime;
			this.endTime = endTime;
		}

		/** 시작 시간 오름차순으로, 시작 시간이 같다면 끝나는 시간 오름차순으로 정렬하기 위한 비교함수 */
		@Override
		public int compareTo(User u) {
			if (this.startTime == u.startTime) {
				return this.endTime - u.endTime;
			}
			return this.startTime - u.startTime;
		}

		@Override
		public String toString() {
			return "User [startTime=" + startTime + ", endTime=" + endTime + "]";
		}
	}

	static class Seat implements Comparable<Seat> {
		int seatNum;
		int endTime;

		public Seat(int seatNum, int endTime) {
			super();
			this.seatNum = seatNum;
			this.endTime = endTime;
		}

		/** 끝나는 시간 오름차순으로 정렬하기 위한 비교함수 */
		@Override
		public int compareTo(Seat s) {
			return this.endTime - s.endTime;
		}

		@Override
		public String toString() {
			return "Seat [seatNum=" + seatNum + ", endTime=" + endTime + "]";
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N 입력
		N = Integer.parseInt(br.readLine());

		// 메모리 할당
		endTimes = new int[N];
		cntUsed = new int[N];
		users = new User[N];

		// 각 유저의 사용 시간 정보 입력
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int startTime = Integer.parseInt(st.nextToken());
			int endTime = Integer.parseInt(st.nextToken());
			users[i] = new User(startTime, endTime);
		}

		// 유저들을 1. 사용 시작 시간 오름차순 2. 사용 끝 시간 오름차순으로 정렬
		Arrays.sort(users);

		PriorityQueue<Integer> availables = new PriorityQueue<>();
		PriorityQueue<Seat> seatsUsed = new PriorityQueue<>();

		// 모든 자리가 -1의 시간에 사용이 종료된다고 초기화
		for (int i = 0; i < N; i++) {
			seatsUsed.add(new Seat(i, -1));
		}

		int curTime = 0;

		// N명의 유저를 처리
		for (int u = 0; u < N; u++) {
			curTime = users[u].startTime;

			// 사용중이던 자리들 중 사용이 끝난 자리들을 seatsUsed에서 뽑아내고, availbles에 그 자리 번호를 넣는다.
			while (!seatsUsed.isEmpty() && seatsUsed.peek().endTime <= curTime) {
				Seat seatFree = seatsUsed.poll();
				availables.add(seatFree.seatNum);
			}

			// 가장 번호가 작은 자리를 사용한다.
			int smallestSeatNum = availables.poll();
			Seat seatToUse = new Seat(smallestSeatNum, users[u].endTime);
			seatsUsed.add(seatToUse);
			cntUsed[smallestSeatNum]++;
		}

		// 1번 이상 사용된 자리 수를 X에 세고, 각 자리 이용 횟수를 sb에 담는다.
		int X = 0;
		for (int i = 0; i < N; i++) {
			int cnt = cntUsed[i];
			if (cnt == 0) {
				break;
			} else {
				X++;
				sb.append(cnt).append(" ");
			}
		}
		sb.append("\n");

		// 출력
		System.out.println(X);
		System.out.print(sb.toString());
	}

}
