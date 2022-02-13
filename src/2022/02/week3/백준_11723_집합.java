// 323780KB, 1064ms

package bj11723;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

class MySet {
	Set<Integer> set = new HashSet<>();

	void add(int x) {
		set.add(x);
	}

	void remove(int x) {
		set.remove(x);
	}

	int check(int x) {
		return set.contains(x) ? 1 : 0;
	}

	void toggle(int x) {
		if (check(x) == 1) {
			remove(x);
		} else {
			add(x);
		}
	}

	void all() {
		empty();
		for (int i = 1; i <= 20; i++) {
			add(i);
		}
	}

	void empty() {
		set.clear();
	}
}

public class Main {
	public static MySet mySet = new MySet();
	public static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 명령 개수 입력
		int M = Integer.parseInt(br.readLine());

		// 명령을 입력 받아서 처리
		for (int i = 0; i < M; i++) {
			String command = br.readLine();
			handleCommand(command);
		}

		// 결과 출력
		System.out.println(sb.toString());
	}

	/** 각각의 명령을 처리하는 함수 */
	public static void handleCommand(String command) {
		StringTokenizer st = new StringTokenizer(command, " ");

		String commandType = st.nextToken();
		int commandNum = 0;
		if (st.hasMoreTokens()) {
			commandNum = Integer.parseInt(st.nextToken());
		}

		switch (commandType) {
		case "add":
			mySet.add(commandNum);
			break;
		case "remove":
			mySet.remove(commandNum);
			break;
		case "check":
			sb.append(mySet.check(commandNum)).append("\n");
			break;
		case "toggle":
			mySet.toggle(commandNum);
			break;
		case "all":
			mySet.all();
			break;
		case "empty":
			mySet.empty();
			break;
		default:
			break;
		}
	}
}
