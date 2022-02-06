// 174120KB, 1120ms

package baek17298;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

// stack에 수열 원소의 값만 담아 두면 NGE(idx)를 알기 어려우므로, index와 값을 함께 담아두는 객체로 만든다.
class Element {
	/** 수열의 원소의 index */
	int idx;
	/** 수열의 원소의 값 */
	int number;

	public Element(int idx, int number) {
		super();
		this.idx = idx;
		this.number = number;
	}

	public int getIdx() {
		return idx;
	}

	public int getNumber() {
		return number;
	}
}

public class Main {
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		// 입력을 받아 수열 생성
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int seqSize = Integer.parseInt(br.readLine());

		String[] tokens = br.readLine().split(" ");
		Element[] sequence = new Element[seqSize];
		for (int i = 0; i < seqSize; i++) {
			sequence[i] = new Element(i, Integer.parseInt(tokens[i]));
		}
		br.close();
		
		// 수열의 각 원소들의 오큰수를 배열로 생성
		int[] arrNGE = getNGEs(sequence);

		// 출력
		StringBuilder sb = new StringBuilder();
		for (int nge : arrNGE) {
			sb.append(nge).append(" ");
		}
		
		System.out.print(sb.toString());
		
	}

	/** 
	 * 주어진 수열의 NGE(i)가 차례대로 담겨있는 오큰수 배열을 리턴
	 * @param sequence : 오큰수를 찾을 수열
	 * @return 오큰수 배열
	 */
	public static int[] getNGEs(Element[] sequence) {
		int length = sequence.length;
		// 오큰수를 찾기 위해 대기중인 원소들이 담길 스택 생성
		Stack<Element> stack = new Stack<Element>();
		// 수열 각 원소의 오큰수를 저장할 배열 생성
		int[] nextGreaterElement = new int[length];

		// 수열에 존재하는 모든 수에 대해 실행
		for (int i = 0; i < length; i++) {
			// element는 수열에서 이번에 만난 원소, num은 그 원소의 값
			Element element = sequence[i];
			int num = element.getNumber();

			// 스택에 쌓여있는 수 중 num보다 작은 수들의 오큰수는 num이 됨
			while (!stack.empty() && stack.peek().getNumber() < num) {
				// 오큰수를 찾았으니 스택에서 제거
				Element e = stack.pop();
				// 오큰수 값 갱신
				nextGreaterElement[e.getIdx()] = num;
			}

			// 이번에 만난 원소도 오큰수를 찾아야 하니 stack에 push
			stack.push(element);
		}
		
		
		// 루프를 다 돌고 났는데 스택에 남아있는 수들은 오큰수가 존재하지 않으므로 -1
		while (!stack.empty()) {
			Element e = stack.pop();
			nextGreaterElement[e.getIdx()] = -1;
		}
		
		return nextGreaterElement;
	}
}
