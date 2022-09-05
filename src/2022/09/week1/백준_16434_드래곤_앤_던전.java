// 56224KB, 700ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_N = 123456;
    static final int MAX_ATTACK = 1000000;
    static final long DIE = -1L;
    static final int MONSTER = 1, POTION = 2;

    static class Room {
        int roomType;
        long attack;
        long health;

        public Room(int roomType, long attack, long health) {
            this.roomType = roomType;
            this.attack = attack;
            this.health = health;
        }
    }

    static int N;
    static Room[] rooms;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // 방 개수, 시작 공격력 입력
        st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        long playerAttack = Long.parseLong(st.nextToken());

        // 방 정보 입력
        rooms = new Room[N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int roomType = Integer.parseInt(st.nextToken());
            long attack = Long.parseLong(st.nextToken());
            long health = Long.parseLong(st.nextToken());

            rooms[i] = new Room(roomType, attack, health);
        }

        // 이분탐색으로 최소 체력 계산
        long left = 0L;
        long right = (long) MAX_N * (long) MAX_ATTACK * (long) MAX_ATTACK + 1L;

        while (left < right) {
            long mid = (left + right) / 2L;
            if (isPossible(mid, playerAttack)) {
                right = mid;
            } else {
                left = mid + 1L;
            }
        }

        // 정답 출력
        long answer = right;
        System.out.println(answer);

    }

    public static boolean isPossible(long maxPlayerHealth, long playerAttack) {
        long curPlayerHealth = maxPlayerHealth;
        long curPlayerAttack = playerAttack;

        // 방을 순서대로 모두 방문
        for (Room room : rooms) {
            switch (room.roomType) {
                // 몬스터와 전투
                case MONSTER:
                    long healthAfterFight = fight(curPlayerAttack, curPlayerHealth, room.attack, room.health);
                    if (healthAfterFight <= 0) {
                        return false;
                    }
                    else {
                        curPlayerHealth = healthAfterFight;
                    }
                    break;
                // 포션 마시기
                case POTION:
                    curPlayerAttack += room.attack;
                    curPlayerHealth += room.health;
                    curPlayerHealth = Math.min(curPlayerHealth, maxPlayerHealth);
                    break;
                default:
                    break;
            }
        }

        return true;
    }

    /**
     * 몬스터와 전투 후 남은 체력을 리턴
     */
    public static long fight(long playerAttack, long playerHealth, long monsterAttack, long monsterHealth) {
        long cntAttack = monsterHealth % playerAttack == 0 ? monsterHealth / playerAttack : monsterHealth / playerAttack + 1;
        long cntDefense = cntAttack - 1;

        long remainingHealth = playerHealth - monsterAttack * cntDefense;
        if (remainingHealth > 0) {
            return remainingHealth;
        }
        else {
            return DIE;
        }
    }
}
