// 100488KB, 584ms

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

void fft(vector<cpx> &f, bool isReverse);
vector<ll> multiply(vector<ll>& f, vector<ll>& g);


int main(void)
{
    // 소수 여부들을 미리 계산
    bool isPrime[MAX_N + 1];
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

    // 홀수 소수 다항식
    vector<ll> f(MAX_N + 1, 0);
    for (int i = 3; i <= MAX_N; i += 2) {
        if (isPrime[i]) {
            f[i] = 1;
        }
    }

    // 짝수 세미소수 다항식
    vector<ll> g(MAX_N + 1, 0);
    g[4] = 1;
    for (int i = 3; i <= MAX_N / 2; i += 2) {
        if (isPrime[i]) {
            g[2 * i] = 1;
        }
    }

    // 다항식의 곱 계산
    vector<ll> product = multiply(f, g);

    int T;
    scanf("%d", &T);

    int N;
    for (int i = 0; i < T; i++) {
        scanf("%d", &N);
        printf("%lld\n", product[N]);
    }

    return 0;
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

/** 다항식 f와 g의 곱을 계산해서 리턴 */
vector<ll> multiply(vector<ll>& fll, vector<ll>& gll) {
    vector<cpx> f(fll.begin(), fll.end());
    vector<cpx> g(gll.begin(), gll.end());

    int size1 = fll.size();
    int size2 = gll.size();

    // 2의 제곱수 중 f * g를 담을 수 있는 최소 크기를 구한다
    int n = 1;
    while (n < size1 + size2) {
        n <<= 1;
    }

    // 그 크기로 배열 길이를 늘린다
    f.resize(n);
    g.resize(n);

    // 각각의 다항식을 FFT 변환한다
    fft(f, false);
    fft(g, false);

    // FFT한 값들끼리 곱한다
    for (int i = 0; i < n; i++) {
        f[i] *= g[i];
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
