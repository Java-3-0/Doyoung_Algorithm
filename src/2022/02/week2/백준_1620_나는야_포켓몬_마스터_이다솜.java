// 51860KB, 880ms

package beak1620;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		int numPokemons = Integer.parseInt(st.nextToken());
		int numQuestions = Integer.parseInt(st.nextToken());

		// 이름->번호, 번호->이름으로 포켓몬을 찾기 위한 맵을 만듦
		Map<Integer, String> numToName = new TreeMap<Integer, String>();
		Map<String, Integer> nameToNum = new TreeMap<String, Integer>();
		for (int num = 1; num <= numPokemons; num++) {
			String name = br.readLine();
			numToName.put(num, name);
			nameToNum.put(name, num);
		}

		// 주어지는 질문 해결
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < numQuestions; i++) {
			String question = br.readLine();
			String answer = "";
			// 번호 입력일 경우 이름 찾기
			if (Character.isDigit(question.charAt(0))) {
				answer = numToName.get(Integer.parseInt(question));
			}
			// 이름 입력일 경우 번호 찾기
			else {
				answer = nameToNum.get(question).toString();
			}

			// 질문에 대한 답를 출력에 담기
			sb.append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());
	}

}
