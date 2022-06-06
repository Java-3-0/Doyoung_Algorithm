// 638452KB, 3468ms

package bj25240;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	static Map<String, Set<String>> userGroupToUsers = new HashMap<>();
	static Map<String, MyFile> fileNameToFile = new HashMap<>();

	static class MyFile {
		int ownerAuth;
		int groupAuth;
		int otherAuth;

		String owner;
		String ownedGroup;

		public MyFile(int ownerAuth, int groupAuth, int otherAuth, String owner, String ownedGroup) {
			super();
			this.ownerAuth = ownerAuth;
			this.groupAuth = groupAuth;
			this.otherAuth = otherAuth;
			this.owner = owner;
			this.ownedGroup = ownedGroup;
		}

		@Override
		public String toString() {
			return "MyFile [ownerAuth=" + ownerAuth + ", groupAuth=" + groupAuth + ", otherAuth=" + otherAuth
					+ ", owner=" + owner + ", ownedGroup=" + ownedGroup + "]";
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 유저 수, 파일 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		int U = Integer.parseInt(st.nextToken());
		int F = Integer.parseInt(st.nextToken());

		// 유저 정보 입력
		for (int i = 0; i < U; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			String userName = st.nextToken();
			
			// 유저 이름 자체를 그룹명으로
			if (!userGroupToUsers.containsKey(userName)) {
				userGroupToUsers.put(userName, new HashSet<>());
			}
			userGroupToUsers.get(userName).add(userName);
			
			// 입력받은 그룹명들 처리
			boolean existInputUserGroup = st.hasMoreTokens();
			if (existInputUserGroup) {
				String userGroups = st.nextToken();
				
				st = new StringTokenizer(userGroups, ",");
				while (st.hasMoreTokens()) {
					String group = st.nextToken();
					if (!userGroupToUsers.containsKey(group)) {
						userGroupToUsers.put(group, new HashSet<>());
					}
					
					userGroupToUsers.get(group).add(userName);
				}
			}
		}
		
		// 파일 정보 입력
		for (int i = 0; i < F; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			String fileName = st.nextToken();

			String auth = st.nextToken();
			int ownerAuth = auth.charAt(0) - '0';
			int groupAuth = auth.charAt(1) - '0';
			int otherAuth = auth.charAt(2) - '0';

			String owner = st.nextToken();
			String ownedGroup = st.nextToken();

			fileNameToFile.put(fileName, new MyFile(ownerAuth, groupAuth, otherAuth, owner, ownedGroup));
		}

		// 쿼리 개수 입력
		int Q = Integer.parseInt(br.readLine());

		// 쿼리 수행
		for (int q = 0; q < Q; q++) {
			st = new StringTokenizer(br.readLine(), " ");
			String userName = st.nextToken();
			String fileName = st.nextToken();
			String operation = st.nextToken();

			MyFile file = fileNameToFile.get(fileName);

			boolean isPossible = false;

			if (operation.equals("R")) {
				if (file.owner.equals(userName) && canRead(file.ownerAuth)) {
					isPossible = true;
				}
				if (userGroupToUsers.get(file.ownedGroup).contains(userName) && canRead(file.groupAuth)) {
					isPossible = true;
				}
				if (canRead(file.otherAuth)) {
					isPossible = true;
				}
			} else if (operation.equals("W")) {
				if (file.owner.equals(userName) && canModify(file.ownerAuth)) {
					isPossible = true;
				}
				if (userGroupToUsers.get(file.ownedGroup).contains(userName)  && canModify(file.groupAuth)) {
					isPossible = true;
				}
				if (canModify(file.otherAuth)) {
					isPossible = true;
				}
			} else if (operation.equals("X")) {
				if (file.owner.equals(userName) && canExecute(file.ownerAuth)) {
					isPossible = true;
				}
				if (userGroupToUsers.get(file.ownedGroup).contains(userName)  && canExecute(file.groupAuth)) {
					isPossible = true;
				}
				if (canExecute(file.otherAuth)) {
					isPossible = true;
				}
			}

			if (isPossible) {
				sb.append(1).append("\n");
			} else {
				sb.append(0).append("\n");
			}

		} // end for q

		System.out.print(sb.toString());

	} // end main

	/** 권한 번호가 주어졌을 때 읽기가 가능한지 여부를 리턴 */
	public static boolean canRead(int auth) {
		switch (auth) {
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
			return true;
		default:
			return false;
		}
	}

	/** 권한 번호가 주어졌을 때 수정이 가능한지 여부를 리턴 */
	public static boolean canModify(int auth) {
		switch (auth) {
		case 2:
		case 3:
		case 6:
		case 7:
			return true;
		default:
			return false;
		}
	}

	/** 권한 번호가 주어졌을 때 실행이 가능한지 여부를 리턴 */
	public static boolean canExecute(int auth) {
		switch (auth) {
		case 1:
		case 3:
		case 5:
		case 7:
			return true;
		default:
			return false;
		}
	}

}