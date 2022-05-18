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
const ll GROUPSIZE = 2;

void fft(vector<cpx> &f, bool isReverse);
vector<ll> multiply(vector<cpx>& f, vector<cpx>& g);
void printVector(const vector<ll> v);
void print(vector<ll> v);

int main(void)
{
    // ���ڿ� �Է�
    string s1, s2;
    cin >> s1 >> s2;

    int newSize1 = s1.size() / GROUPSIZE;
    if (s1.size() % GROUPSIZE != 0) {
        newSize1++;
    }

    int newSize2 = s2.size() / GROUPSIZE;
    if (s2.size() % GROUPSIZE != 0) {
        newSize2++;
    }

    // �� ���ڿ��� ���� ������ ���׽����� �����
    vector<cpx> f(newSize1, 0);
    vector<cpx> g(newSize2, 0);

    // �ð����⵵�� ������ �ʰ� �ϱ� ���ؼ� ��� ó��
    int size1 = s1.size();
    for (int i = 0; i < size1; i++) {
        f[i / GROUPSIZE] += (s1[size1 - 1 - i] - '0') * pow(10, i % GROUPSIZE);
    }

    int size2 = s2.size();
    for (int i = 0; i < size2; i++) {
        g[i / GROUPSIZE] += (s2[size2 - 1 - i] - '0') * pow(10, i % GROUPSIZE);
    }

    // ���׽��� �� ���
    vector<ll> product = multiply(f, g);

    // ���
    print(product);

    return 0;
}

/** ���׽� f�� ��� ǻ���� ��ȯ�� ����� f�� ���� */
void fft(vector<cpx> &f, bool isReverse) {
    int n = f.size();

    // �����ϱ�
    for (int i = 1, j = 0; i < n; i++) {
        int bit = n / 2;
        while (j >= bit) {
            j -= bit;
            bit /= 2;
        }
        j += bit;
        if (i < j) {
            swap(f[i], f[j]);
        }
    }

    // ��� ��� for������ Ǯ� �޸� �Ƴ���
    for (int len = 2; len <= n; len <<= 1) {
        double angle = 2.0 * PI / len;
        if (isReverse) {
            angle = -angle;
        }

        cpx wLen(cos(angle), sin(angle));
        for (int i = 0; i < n; i += len) {
            cpx w(1.0, 0.0);
            for (int j = 0; j < len / 2; j++) {
                cpx u = f[i + j];
                cpx v = f[i + j + len / 2] * w;
                f[i + j] = u + v;
                f[i + j + len / 2] = u - v;
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

/** ���׽� f�� g�� ���� ����ؼ� ���� */
vector<ll> multiply(vector<cpx>& f, vector<cpx>& g) {
    int size1 = f.size();
    int size2 = g.size();

    // 2�� ������ �� f * g�� ���� �� �ִ� �ּ� ũ�⸦ ���Ѵ�
    int n = 1;
    while (n < size1 + size2) {
        n *= 2;
    }
    n *= 2;

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

    // �Ǽ� �κи� round ó���ؼ� �����Ѵ�
    vector<ll> ret(n);
    for (int i = 0; i < n; i++) {
        ret[i] = (ll) (round(f[i].real()));
    }
    return ret;
}

/** ���͸� ����Ѵ� */
void print(vector<ll> v) {
    ll carry = 0;
    for (int i = 0; i < v.size(); i++) {
        v[i] += carry;

        carry = v[i] / (ll) pow(10, GROUPSIZE);
        v[i] %= (ll) pow(10, GROUPSIZE);
    }

    string ret = "";

    // �� �� �κ� 0���� �����ϰ� ���
    bool meetNonZero = false;

    for (int i = v.size() - 1; i >= 0; i--) {
        if (meetNonZero) {
            cout.width(GROUPSIZE);
            cout.fill('0');
            cout << v[i];
            continue;
        }
        else {
            if (v[i] != 0) {
                cout << v[i];
                meetNonZero = true;
            }
        }
    }

    // 0�� �ƴ� �� �ƿ� ���ٸ� �� �� ��ü�� 0�̴�
    if (!meetNonZero) {
        cout << 0;
    }

    return;
}
