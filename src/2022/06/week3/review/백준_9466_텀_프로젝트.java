// 330720KB, 1988ms

package bj9466;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 100000;
	static final int EMPTY = -1;

	/** 정점 수 */
	static int V;
	/** 그래프 인접 관계 */
	static int[] adjTo = new int[MAX_V + 1];
	/** 정점 발견 순서 */
	static int[] discovered = new int[MAX_V + 1];
	/** scc로 지정된 정점인지 여부 */
	static boolean[] isAssigned = new boolean[MAX_V + 1];
	/** scc들의 리스트 */
	static List<List<Integer>> sccs = new ArrayList<>();
	/** dfs하며 정점들을 담을 스택 */
	static Stack<Integer> stack = new Stack<>();
	/** 정점 발견 순서 카운팅을 위한 카운터 */
	static int vertexCounter = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			// 메모리 초기화
			initMemory();

			// 학생 수 입력
			V = Integer.parseInt(br.readLine());

			// 간선 정보 입력
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 1; i <= V; i++) {
				adjTo[i] = Integer.parseInt(st.nextToken());
			}

			// 타잔 알고리즘으로 SCC 구하기
			tarjan();

			// 자기 자신 혼자만 SCC인 정점들 중, 자기 자신으로의 간선이 없는 것의 개수를 센다
			int answer = 0;
			for (List<Integer> scc : sccs) {
				if (scc.size() == 1) {
					int v = scc.get(0);
					if (adjTo[v] != v) {
						answer++;
					}
				}
			}

			// 출력용 스트링빌더에 정답 추가
			sb.append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 테스트케이스마다 초기화해야 할 전역변수 메모리 초기화 */
	public static void initMemory() {
		Arrays.fill(adjTo, EMPTY);
		Arrays.fill(discovered, EMPTY);
		Arrays.fill(isAssigned, false);
		sccs.clear();
		stack.clear();
		vertexCounter = 0;
	}

	/** 타잔 알고리즘으로 scc를 구한다 */
	public static void tarjan() {
		for (int start = 1; start <= V; start++) {
			if (discovered[start] == EMPTY) {
				dfs(start);
			}
		}
	}

	/** now 이하 서브트리로부터 올라갈 수 있는 정점의 최소 발견 순서를 리턴하면서 scc를 찾는다 */
	public static int dfs(int now) {
		// 방문 처리
		discovered[now] = ++vertexCounter;
		int ret = discovered[now];

		// 스택에 담기
		stack.push(now);

		// 다음 정점
		int next = adjTo[now];

		// 트리 간선인 경우
		if (discovered[next] == EMPTY) {
			ret = Math.min(ret, dfs(next));
		}

		// 아직 scc로 묶이지 않은 정점으로 가는 교차 간선인 경우
		else if (!isAssigned[next]) {
			ret = Math.min(ret, discovered[next]);
		}

		// now보다 더 위로 올라갈 수 없는 경우, now까지가 하나의 scc가 된다
		if (ret == discovered[now]) {
			List<Integer> scc = new ArrayList<>();

			// 지금까지 스택에 담긴 것들을 scc로 묶는다
			while (!stack.isEmpty()) {
				int top = stack.peek();
				stack.pop();
				scc.add(top);
				isAssigned[top] = true;
				if (top == now) {
					sccs.add(scc);
					break;
				}
			}
		}

		return ret;
	}

}