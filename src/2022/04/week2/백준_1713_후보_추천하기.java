// 12448KB, 100ms

package bj1713;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static final int INF = 987654321;

	static class Picture {
		/** 게시된 시각 */
		int postedTime;
		/** 추천 수 */
		int recommends;

		public Picture(int postedTime, int recommends) {
			super();
			this.postedTime = postedTime;
			this.recommends = recommends;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N, M 입력
		int N = Integer.parseInt(br.readLine());
		int M = Integer.parseInt(br.readLine());

		// (학생 번호 -> 해당 학생의 게시되어 있는 사진 정보)로의 매핑
		Map<Integer, Picture> board = new HashMap<>();

		// N개의 추천 정보 입력받으면서 사진 게시 시뮬레이션
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < M; i++) {
			int studentNum = Integer.parseInt(st.nextToken());
			// 1. 이미 사진이 걸려있는 학생인 경우
			if (board.containsKey(studentNum)) {
				// 걸려 있던 사진의 추천 수 1 증가
				Picture p = board.get(studentNum);
				board.put(studentNum, new Picture(p.postedTime, p.recommends + 1));
			}

			// 2. 새로 걸어야 하는데 빈 공간이 있는 경우
			else if (board.size() < N) {
				// 사진 새로 걸기
				board.put(studentNum, new Picture(i, 1));
			}

			// 3. 새로 걸어야 하는데 빈 공간이 없는 경우
			else {
				// 가장 추천 수가 적은 사진 내리기 (동률이면 가장 예전에 걸린 사진)
				board.remove(findStudentToDelete(board));

				// 사진 새로 걸기
				board.put(studentNum, new Picture(i, 1));
			}
		}

		// 최종 상태에서 보드에 걸려있는 학생 번호들을 우선순위 큐에 담는다. (오름차순으로 출력하기 위함)
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		for (int studentNum : board.keySet()) {
			pq.offer(studentNum);
		}

		// 정답을 출력 스트링빌더에 추가
		while (!pq.isEmpty()) {
			sb.append(pq.poll()).append(" ");
		}
		sb.append("\n");

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 게시판에서 사진을 삭제할 학생의 번호를 리턴 */
	public static int findStudentToDelete(Map<Integer, Picture> board) {
		int minRecom = INF;
		int minTime = INF;
		int ret = 0;

		// 사진이 게시되어 있는 모든 학생 탐색
		for (int studentNum : board.keySet()) {
			Picture p = board.get(studentNum);

			// 추천 수가 적은 경우
			if (p.recommends < minRecom) {
				minRecom = p.recommends;
				minTime = p.postedTime;
				ret = studentNum;
			}

			// 추천 수는 같지만, 게시 시작 시각이 더 오래 전인 경우
			else if (p.recommends == minRecom) {
				if (p.postedTime < minTime) {
					minTime = p.postedTime;
					ret = studentNum;
				}
			}
		}

		return ret;
	}

}