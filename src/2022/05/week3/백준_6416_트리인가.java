// 13072KB, 116ms

package bj6416;

import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
	static Set<Integer> verticesAll = new HashSet<>();
	static Set<Integer> verticesTo = new HashSet<>();

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();

		int tc = 0;
		TEST_CASES: while (true) {
			tc++;
			boolean isTree = true;

			// 테스트케이스마다 set을 초기화
			verticesAll.clear();
			verticesTo.clear();

			// 테스트케이스 종료조건 판단
			int from = sc.nextInt();
			int to = sc.nextInt();
			if (from < 0 && to < 0) {
				break TEST_CASES;
			}

			// 간선 정보들을 입력받아서 set에 추가
			INPUT_EDGES: while (true) {
				if (from == 0 && to == 0) {
					break INPUT_EDGES;
				}

				// 들어오는 간선이 2개 이상이면 안 된다
				if (verticesTo.contains(to)) {
					isTree = false;
				}

				verticesAll.add(from);
				verticesAll.add(to);
				verticesTo.add(to);

				from = sc.nextInt();
				to = sc.nextInt();

			} // end INPUT_EDGES

			// 트리 여부 갱신
			if (verticesAll.size() != verticesTo.size() + 1) {
				isTree = false;
			}

			if (verticesAll.size() == 0) {
				isTree = true;
			}
			
			// 출력 스트링빌더에 추가
			sb.append("Case ").append(tc).append(" is ");
			if (!isTree) {
				sb.append("not ");
			} 
			sb.append("a tree.").append("\n");

		} // end TEST_CASES
		
		System.out.print(sb.toString());

		sc.close();
		
	} // end main
}