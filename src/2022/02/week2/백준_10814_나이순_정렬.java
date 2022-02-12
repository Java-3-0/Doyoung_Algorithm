// 57268KB, 1372ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

class Person implements Comparable<Person> {
	int idx;
	int age;
	String name;

	public Person(int idx, int age, String name) {
		super();
		this.idx = idx;
		this.age = age;
		this.name = name;
	}

	public int compareTo(Person p) {
		if (this.age == p.age) {
			return this.idx - p.idx;
		}
		return this.age - p.age;
	}

	@Override
	public String toString() {
		return age + " " + name + "\n";
	}
	
	
}

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		List<Person> people = new ArrayList<>();
		
		int N = Integer.parseInt(br.readLine());
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int age = Integer.parseInt(st.nextToken());
			String name = st.nextToken();
			
			people.add(new Person(i, age, name));
		}
		
		Collections.sort(people);
		
		for (Person p : people) {
			System.out.print(p.toString());
		}
	}
}
