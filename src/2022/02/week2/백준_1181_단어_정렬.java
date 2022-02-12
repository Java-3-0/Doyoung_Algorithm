// 28124KB, 264ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class Main {

	public static void main(String[] args) throws IOException {
//		Scanner sc = new Scanner(System.in);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int N = Integer.parseInt(br.readLine());

		// 정렬 기준 생성
		Comparator<String> myComparator = new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				if (o1.length() == o2.length()) {
					return o1.compareTo(o2);
				}
				return o1.length() - o2.length();
			}
		};

		// 이 정렬 기준으로 트리셋 생성
		Set<String> set = new TreeSet<>(myComparator);

		// 트리셋에 문자열 추가
		for (int i = 0; i < N; i++) {
			set.add(br.readLine());
		}

		// 트리셋에 있는 순서대로 출력
		for (String s : set) {
			sb.append(s).append("\n");
		}
		System.out.print(sb);
	}

}
