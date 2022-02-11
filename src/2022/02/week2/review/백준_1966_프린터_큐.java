// 22668KB, 320ms

package baek1966;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/** 문서를 객체로 만들기 위한 클래스 */
class Document {
	/** 문서의 idx */
	int idx;
	/** 문서의 중요도 */
	int priority;
	public Document(int idx, int priority) {
		super();
		this.idx = idx;
		this.priority = priority;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
}

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		// 테스트케이스 개수
		int T = sc.nextInt();
		for (int test_case = 0; test_case < T; test_case++) {
			// 문서의 개수
			int numDocuments = sc.nextInt();
			// 인쇄 순서를 알아내고자 하는 문서의 idx
			int targetIdx = sc.nextInt();

			// 문서의 중요도를 입력받음
			Queue<Document> priorities = new LinkedList<> ();
			for (int i = 0; i < numDocuments; i++) {
				priorities.add(new Document(i, sc.nextInt()));
			}
			
			// targetIdx의 문서가 몇 번째로 인쇄되는지 계산
			int answer = getPrintOrder(priorities, targetIdx);
			
			// 출력
			System.out.println(answer);
			
		}
		sc.close();
	}

	public static int getPrintOrder(Queue<Document> documents, int targetIdx) {
		// 문서들의 우선순위를 내림차순으로 정렬한 리스트를 생성
		List<Integer> desc = new ArrayList<Integer> (documents.size());
		for (Document d : documents) {
			desc.add(d.getPriority());
		}
		Collections.sort(desc, Collections.reverseOrder());
		
		// 인쇄한 문서의 수를 담을 변수
		int count = 0;
		
		// 모든 문서를 인쇄할 때까지 반복
		while (!documents.isEmpty()) {
			// 맨 앞에 있는 문서를 인쇄할 수 있는 경우 -> 이 문서를 인쇄하고, 이 문서가 target이었다면 인쇄된 순서를 리턴
			if(documents.peek().getPriority() == desc.get(count)) {
				Document docuToPrint = documents.poll();
				count++;
				
				if (docuToPrint.getIdx() == targetIdx) {
					return count;
				}
			}
			// 맨 앞에 있는 문서를 인쇄할 수 없는 경우 -> 이 문서를 큐의 맨 뒤로 보냄
			else {
				documents.add(documents.poll());
			}
		}
		
		return -1;
	}
}
