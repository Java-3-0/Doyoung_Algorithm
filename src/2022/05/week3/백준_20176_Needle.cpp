// 8616KB, 48ms

#include <iostream>
#include <string>
#include <cstring>
#include <vector>
#include <map>
#include <stack>
#include <queue>
#include <algorithm>
#include <climits>
#include <cfloat>
#include <cmath>
#include <complex>

using namespace std;

typedef complex<double> cpx;
typedef long long ll;

const double PI = M_PI;
const int MAX_N = 50000;
const int MAX_POS= 60000; // ��ġ�� -30000 ~ 30000 ��� 30000�� ���ؼ� 0 ~ 60000���� ����Ѵ�

void fft(vector<cpx> &f, bool isReverse);
vector<ll> multiply(const vector<ll>& fll, const vector<ll>& gll);

int main(void)
{
    // �� ���� ��Ÿ�� �� ���� ����
    vector<ll> f(MAX_POS + 1);
    vector<ll> g(MAX_POS + 1);
    vector<ll> h(MAX_POS + 1);

    // f ���� �Է�
    int n, x;
    scanf("%d", &n);
    for (int i = 0; i < n; i++) {
        scanf("%d", &x);
        f[x + 30000]++;
    }

    // g ���� �Է�
    scanf("%d", &n);
    for (int i = 0; i < n; i++) {
        scanf("%d", &x);
        g[x + 30000]++;
    }

    // h ���� �Է�
    scanf("%d", &n);
    for (int i = 0; i < n; i++) {
        scanf("%d", &x);
        h[x + 30000]++;
    }

    // b - a = c - b�� �����ϸ� a + c = 2 * b �̴�
    // ���׽� f�� h�� ���ؼ� ����� ����� �׵��� ������ a + c�� �����̴�
    // ���׽� f�� h�� ���� ���Ѵ�
    vector<ll> product = multiply(f, h);

    int answer = 0;
    for (int b = 0; b <= MAX_POS; b++) {
        // ������ b���鿡 ���ؼ� product[2 * b]�� ���� ���Ѵ�
        if (g[b] > 0) {
            if (2 * b < product.size() && product[2 * b] > 0) {
                answer += (g[b] * product[2 * b]);
            }
        }
    }

    // ���� ���
    printf("%d\n", answer);
}

/** ���׽� f�� ��� Ǫ���� ��ȯ�� ����� f�� ���� */
void fft(vector<cpx> &f, bool isReverse) {
    int n = f.size();

    // �����ϱ�
    for (int i = 1, j = 0; i < n; i++) {
        int bit = (n >> 1);
        while(!((j ^= bit) & bit)) {
            bit >>= 1;
        }

        if (i < j) {
            swap(f[i], f[j]);
        }
    }

    // ��� ��� for������ Ǯ� �޸� �Ƴ���
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

    // ����ȯ�̸� n���� �����ֱ�
    if (isReverse) {
        for (int i = 0; i < n; i++) {
            f[i] /= n;
        }
    }

    return;
}

/** ���׽� fll�� gll�� ���� ����ؼ� ���� */
vector<ll> multiply(const vector<ll>& fll, const vector<ll>& gll) {
    vector<cpx> f(fll.begin(), fll.end());
    vector<cpx> g(gll.begin(), gll.end());

    int size1 = f.size();
    int size2 = g.size();

    // 2�� ������ �� f * g�� ���� �� �ִ� �ּ� ũ�⸦ ���Ѵ�
    int n = 1;
    while (n < size1 + size2) {
        n <<= 1;
    }

    // �� ũ��� �迭 ���̸� �ø���
    f.resize(n);
    g.resize(n);

    // ������ ���׽��� FFT ��ȯ�Ѵ�
    fft(f, false);
    fft(g, false);

    // FFT�� ���鳢�� ���Ѵ�
    for (int i = 0; i < n; i++) {
        f[i] *= g[i];
    }

    // ���� ����� FFT ����ȯ�Ѵ�
    fft(f, true);

    // ������ �ݿø��ؼ� ����
    vector<ll> ret(n);
    for (int i = 0; i < n; i++) {
        ret[i] = (ll) round(f[i].real());
    }
    return ret;
}
