// 25032KB, 384ms

package bj3683;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_C = 100, MAX_D = 100, MAX_V = 500;
	static final int NOT_MATCHED = -1;

	static int C, D, V;
	/** 각 시청자의 투표 정보 */
	static List<Vote> votes = new ArrayList<Vote>();
	/** 정의역 -> 공역으로의 간선 정보 */
	static List<Integer>[] adjList = new ArrayList[MAX_V + 1];
	/** 공역의 각 원소를 다른 정의역과 매칭하려는 시도를 해 봤는지 여부를 나타내는 배열 */
	static boolean[] isChecked = new boolean[MAX_V + 1];
	/** 공역의 각 원소가 정의역에 어떤 원소와 매칭되었는지를 나타내는 배열 */
	static int[] isMatchedTo = new int[MAX_V + 1];

	static class Vote {
		String like;
		String dislike;

		public Vote(String like, String dislike) {
			super();
			this.like = like;
			this.dislike = dislike;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 전역변수 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			// 전역변수 메모리 초기화
			initMemory();

			// 고양이 수, 개 수, 시청자 수 입력
			st = new StringTokenizer(br.readLine(), " ");
			C = Integer.parseInt(st.nextToken());
			D = Integer.parseInt(st.nextToken());
			V = Integer.parseInt(st.nextToken());

			// 시청자들 투표 정보 입력
			for (int i = 0; i < V; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				String like = st.nextToken();
				String dislike = st.nextToken();
				votes.add(new Vote(like, dislike));
			}
			
			// 시청자들 중 의견 충돌이 생기는 쌍마다 간선 생성 (고양이 좋아하는 사람 -> 개 좋아하는 사람 방향으로)
			for (int i = 0; i < V; i++) {
				for (int j = i + 1; j < V; j++) {
					Vote v1 = votes.get(i);
					Vote v2 = votes.get(j);
					
					if (v1.like.equals(v2.dislike) || v1.dislike.equals(v2.like)) {
						if (v1.like.charAt(0) == 'C') {
							adjList[i].add(j);
						}
						else {
							adjList[j].add(i);
						}
					}
				}
			}
			
			// 간선 정보 오름차순정렬
			for (int i = 0; i < adjList.length; i++) {
				Collections.sort(adjList[i]);
			}
			
			// 최대 독립 집합의 수를 구한다(정점 개수 - 최대 매칭)
			int answer = V - getMaxMatches();

			// 출력용 스트링빌더에 추가
			sb.append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 전역변수 메모리 초기화 */
	public static void initMemory() {
		votes.clear();
		for (int i = 0; i < adjList.length; i++) {
			adjList[i].clear();
		}
		Arrays.fill(isChecked, false);
		Arrays.fill(isMatchedTo, NOT_MATCHED);
	}

	/** 현재 adjList에서 가능한 최대 매칭 수를 구해서 리턴 */
	public static int getMaxMatches() {
		int ret = 0;

		for (int x = 0; x < V; x++) {
			Arrays.fill(isChecked, false);

			if (dfs(x)) {
				ret++;
			}
		}

		return ret;
	}

	/** dfs를 수행하면서 매칭 시도 */
	public static boolean dfs(int x) {
		for (int y : adjList[x]) {
			if (!isChecked[y]) {
				isChecked[y] = true;
				if (isMatchedTo[y] == NOT_MATCHED || dfs(isMatchedTo[y])) {
					isMatchedTo[y] = x;
					return true;
				}
			}
		}

		return false;
	}

}