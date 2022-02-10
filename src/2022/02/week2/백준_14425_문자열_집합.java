// 31468KB, 420ms

package baek14425;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		// 자료를 빠르게 검색하기 위해 BST형태의 TreeSet 이용
		Set<String> mySet = new TreeSet<String>();
		
		// mySet에 문자열들을 추가
		for (int i = 0; i < N; i++) {
			String line = br.readLine();
			mySet.add(line);
		}
		
		// mySet에서 문자열들을 탐색해서 찾은 개수 출력
		int count = 0;
		for (int i = 0; i < M; i++) {
			String line = br.readLine();
			if (mySet.contains(line)) {
				count++;
			}
		}
		
		System.out.println(count);
	}
}
