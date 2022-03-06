// 33516KB, 364ms

package bj3665;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 500;
	static final int MAX_M = 25000;

	static int N, M;
	static boolean[][] adjMatrix = new boolean[MAX_N + 1][MAX_N + 1];
	static int[] incoming = new int[MAX_N + 1];

	static class ImpossibleRankException extends Exception {
		private static final long serialVersionUID = 5765637016928254069L;
	}

	static class AmbiguousRankException extends Exception {
		private static final long serialVersionUID = 8144201397136959607L;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());
		for (int testCase = 1; testCase <= TESTS; testCase++) {
			// 메모리 초기화
			initMemory();
			
			// 스트링빌더 초기화
			sb.setLength(0);
			
			// 작년 순위 입력
			N = Integer.parseInt(br.readLine());
			st = new StringTokenizer(br.readLine(), " ");
			int[] teams = new int[N + 1];
			for (int i = 1; i <= N; i++) {
				int teamNum = Integer.parseInt(st.nextToken());
				teams[i] = teamNum;
			}

			for (int i = 1; i <= N; i++) {
				for (int j = i + 1; j <= N; j++) {
					adjMatrix[teams[i]][teams[j]] = true;
					incoming[teams[j]]++;
				}
			}

			// 순위가 바뀐 팀 쌍 정보 입력
			M = Integer.parseInt(br.readLine());
			for (int i = 1; i <= M; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int teamA = Integer.parseInt(st.nextToken());
				int teamB = Integer.parseInt(st.nextToken());

				// 작년에 teamA -> teamB였던 경우
				if (adjMatrix[teamA][teamB]) {
					adjMatrix[teamA][teamB] = false;
					adjMatrix[teamB][teamA] = true;
					incoming[teamB]--;
					incoming[teamA]++;
				}
				// 작년에 teamB -> teamA였던 경우
				else {
					adjMatrix[teamB][teamA] = false;
					adjMatrix[teamA][teamB] = true;
					incoming[teamA]--;
					incoming[teamB]++;
				}
			}
			
			// 순위를 찾고 형식에 맞게 출력
			try {
				List<Integer> order = getOrder();
				for (int teamNum : order) {
					sb.append(teamNum).append(" ");
				}
				sb.append("\n");
				System.out.print(sb.toString());
			} catch (AmbiguousRankException e) {
				System.out.println("?");
			} catch (ImpossibleRankException e) {
				System.out.println("IMPOSSIBLE");
			}
			
			
		} // end for testCase

	} // end main

	/** 메모리 초기화 */
	public static void initMemory() {
		for (int i = 0; i < adjMatrix.length; i++) {
			Arrays.fill(adjMatrix[i], false);
		}
		
		Arrays.fill(incoming, 0);
	}
	
	/** 현재 adjMatrix와 incoming에서의 순위 순서를 리스트 형태로 리턴 */
	public static List<Integer> getOrder() throws AmbiguousRankException, ImpossibleRankException {
		List<Integer> ret = new ArrayList<>();
		Queue<Integer> q = new LinkedList<>();

		for (int v = 1; v <= N; v++) {
			if (incoming[v] == 0) {
				q.offer(v);
			}
		}
		
		while (!q.isEmpty()) {
			if (q.size() > 1) {
				throw new AmbiguousRankException();
			}

			int from = q.poll();
			ret.add(from);
			
			for (int to = 1; to <= N; to++) {
				if (adjMatrix[from][to]) {
					incoming[to]--;
					if (incoming[to] == 0) {
						q.offer(to);
					}
				}
			}
			incoming[from]--;
		}
		
		if (ret.size() != N) {
			throw new ImpossibleRankException();
		}

		return ret;
	}
}