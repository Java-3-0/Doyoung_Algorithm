// 198584KB, 1064ms

package baek18115;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;
import java.util.StringTokenizer;

class MyDeque {
	Deque<Integer> cards = new LinkedList<>();

	/** 손의 맨 위에 카드를 넣는다 */
	void addFirst(int cardNum) {
		cards.addFirst(cardNum);
	}

	/** 손의 맨 위에서 두번째에 카드를 넣는다 */
	void addSecond(int cardNum) {
		// 처음 것을 잠시 없앤다
		int tmp = cards.pollFirst();
		// 맨 앞에 추가한다
		cards.addFirst(cardNum);
		// 잠시 없애 두었던 원래 처음 것을 돌려놓는다
		cards.addFirst(tmp);
	}

	/** 손의 맨 밑에 카드를 넣는다 */
	void addLast(int cardNum) {
		cards.addLast(cardNum);
	}

	/** skillType에 해당하는 스킬을 시전한다 */
	void doSkill(int skillType, int cardNum) {
		switch (skillType) {
		case 1:
			addFirst(cardNum);
			break;
		case 2:
			addSecond(cardNum);
			break;
		case 3:
			addLast(cardNum);
			break;
		default:
			break;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		// 손에 있는 카드들의 숫자 추가
		for (Integer cardNum : cards) {
			sb.append(cardNum).append(" ");
		}
		
		// 마지막 공백 제거
		if (sb.charAt(sb.length() - 1) == ' ') {
			sb.setLength(sb.length() - 1);
		}
		
		// 개행
		sb.append("\n");
		
		return sb.toString();
	}
}

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// 카드 수 입력
		int N = Integer.parseInt(br.readLine());

		// 사용한 스킬 순서를 입력받음
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		Stack<Integer> skills = new Stack<>(); // 나중에 쓴 스킬을 먼저 rollback하기 위해서 스택 자료구조 이용
		for (int i = 0; i < N; i++) {
			skills.push(Integer.parseInt(st.nextToken()));
		}

		// 사용한 반대 순서로 rollback하여 내 손의 myDeque를 만듦
		MyDeque myDeque = new MyDeque();
		for (int cardNum = 1; cardNum <= N; cardNum++) {
			int skillType = skills.pop();
			myDeque.doSkill(skillType, cardNum);
		}
		
		// 내 손의 myDeque를 출력
		System.out.print(myDeque.toString());
	}
}