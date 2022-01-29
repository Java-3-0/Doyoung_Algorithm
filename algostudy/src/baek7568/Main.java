// 덩치 문제
package baek7568;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		/** 사람을 담을 객체 */
		class Person {
			/** 체중 */
			public int weight;
			/** 키 */
			public int height;
			/** 덩치 순위 */
			public int rank;

			/** 랭크는 1로, 키와 체중은 주어진 대로 사람 객체를 생성하는 생성자 */
			public Person(int weight, int height) {
				super();
				this.weight = weight;
				this.height = height;
				this.rank = 1;
			}

			public int getWeight() {
				return weight;
			}

			public void setWeight(int weight) {
				this.weight = weight;
			}

			public int getHeight() {
				return height;
			}

			public void setHeight(int height) {
				this.height = height;
			}

			public int getRank() {
				return rank;
			}

			public void setRank(int rank) {
				this.rank = rank;
			}

		}

		// 입력을 받아서 사람 객체를 personList에 담음
		Scanner sc = new Scanner(System.in);
		int numPeople = sc.nextInt();
		List<Person> personList = new ArrayList<>(numPeople);
		for (int i = 0; i < numPeople; i++) {
			int weight = sc.nextInt();
			int height = sc.nextInt();
			Person p = new Person(weight, height);
			personList.add(p);
		}
		sc.close();

		// 모든 쌍을 비교
		for (int i = 0; i < numPeople; i++) {
			for (int j = i + 1; j < numPeople; j++) {
				Person p1 = personList.get(i);
				Person p2 = personList.get(j);

				// 한 쪽이 작다면 랭크++
				if (p1.getWeight() < p2.getWeight() && p1.getHeight() < p2.getHeight()) {
					p1.setRank(p1.getRank() + 1);
				} else if (p2.getWeight() < p1.getWeight() && p2.getHeight() < p1.getHeight()) {
					p2.setRank(p2.getRank() + 1);
				}
			}
		}

		for (int i = 0; i < numPeople; i++) {
			System.out.printf("%d ", personList.get(i).getRank());
		}
	}
}
