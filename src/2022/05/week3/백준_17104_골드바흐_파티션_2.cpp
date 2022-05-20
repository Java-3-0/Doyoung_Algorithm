// 60020KB, 400ms

#include <iostream>
#include <cstring>
#include <vector>
#include <cmath>
#include <complex>

using namespace std;

typedef complex<double> cpx;
typedef long long ll;

const double PI = M_PI;
const int MAX_N = 1000000;

bool isPrime[MAX_N + 1];

void precalcPrimes();
void fft(vector<cpx> &f, bool isReverse);
vector<ll> square(vector<ll>& f);

int main(void)
{
    precalcPrimes();

    // 소수 다항식
    vector<ll> f(MAX_N + 1, 0);
    for (int i = 0; i <= MAX_N; i++) {
        if (isPrime[i]) {
            f[i] = 1;
        }
    }

    // 다항식의 곱 계산
    vector<ll> product = square(f);

    // 테스트케이스 수 입력
    int T;
    scanf("%d", &T);

    // 테스트 케이스마다 N을 입력받아서 처리
    int N;
    for (int i = 0; i < T; i++) {
        scanf("%d", &N);

        // 순서만 다른 것을 중복으로 2번씩 세게 되므로 절반으로 나눈다
        ll answer = product[N] / 2;

        // 같은 수를 두 번 더해서 나온 답의 경우에는 원래도 1번씩만 셌으므로, 나눠준 걸 복구한다.
        if (isPrime[N / 2]) {
            answer++;
        }

        // 출력
        printf("%lld\n", answer);
    }

    return 0;
}

/** MAX_N까지의 소수 여부를 미리 계산하여 isPrime[] 배열에 저장 */
void precalcPrimes() {
    memset(isPrime, true, sizeof(isPrime));
    isPrime[0] = false;
    isPrime[1] = false;

    for (int div = 2; div <= 1000; div++) {
        if (isPrime[div]) {
            for (int num = div * div; num <= MAX_N; num += div) {
                isPrime[num] = false;
            }
        }
    }
}

/** 다항식 f를 고속 퓨리에 변환한 결과를 f에 저장 */
void fft(vector<cpx> &f, bool isReverse) {
    int n = f.size();

    // 스왑하기
    for (int i = 1, j = 0; i < n; i++) {
        int bit = (n >> 1);
        while(!((j ^= bit) & bit)) {
            bit >>= 1;
        }

        if (i < j) {
            swap(f[i], f[j]);
        }
    }

    // 재귀 대신 for문으로 풀어서 메모리 아끼기
    for (int len = 2; len <= n; len <<= 1) {
        int halfLen = (len >> 1);

        double angle = 2 * PI / len;
        if (isReverse) {
            angle *= -1;
        }

        cpx wLen(cos(angle), sin(angle));
        for (int i = 0; i < n; i += len) {
            cpx w(1, 0);
            for (int j = 0; j < halfLen; j++) {
                cpx u = f[i + j];
                cpx v = f[i + j + halfLen] * w;
                f[i + j] = u + v;
                f[i + j + halfLen] = u - v;
                w *= wLen;
            }
        }
    }

    // 역변환이면 n으로 나눠주기
    if (isReverse) {
        for (int i = 0; i < n; i++) {
            f[i] /= n;
        }
    }

    return;
}

/** 다항식 f의 제곱을 계산해서 리턴 */
vector<ll> square(vector<ll>& fll) {
    vector<cpx> f(fll.begin(), fll.end());

    int size = fll.size();

    // 2의 제곱수 중 f * f를 담을 수 있는 최소 크기를 구한다
    int n = 1;
    while (n < size + size) {
        n <<= 1;
    }

    // 그 크기로 배열 길이를 늘린다
    f.resize(n);

    // 다항식을 FFT 변환한다
    fft(f, false);

    // FFT한 값들끼리 곱한다
    for (int i = 0; i < n; i++) {
        f[i] *= f[i];
    }

    // 얻은 결과를 FFT 역변환한다
    fft(f, true);

    // 실수 부분만 round 처리해서 리턴한다
    vector<ll> ret(n);
    for (int i = 0; i < n; i++) {
        ret[i] = (ll) (round(f[i].real()));
    }
    return ret;
}
