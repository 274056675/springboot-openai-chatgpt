package org.springblade.mng.cgform.model.generate.util;

import org.apache.commons.lang.RandomStringUtils;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class NonceUtils
{
    private static final SimpleDateFormat sdf;
    private static final String[] b;
    private static Date c;
    private static int d;

    public static String a(int n) {
        return RandomStringUtils.randomAlphanumeric(n);
    }

    public static int a() {
        return new SecureRandom().nextInt();
    }

    public static String b() {
        return Integer.toHexString(a());
    }

    public static long c() {
        return new SecureRandom().nextLong();
    }

    public static String d() {
        return Long.toHexString(c());
    }

    public static String e() {
        return UUID.randomUUID().toString();
    }

    public static String f() {
        return NonceUtils.sdf.format(new Date());
    }

    public static long g() {
        return System.currentTimeMillis();
    }

    public static String h() {
        return Long.toHexString(g());
    }

    public static synchronized String i() {
        final Date c = new Date();
        if (c.equals(NonceUtils.c)) {
            ++NonceUtils.d;
        }
        else {
            NonceUtils.c = c;
            NonceUtils.d = 0;
        }
        return Integer.toHexString(NonceUtils.d);
    }

    public static String a(String s, int n) {
        int i = n - s.length();
        StringBuilder sb = new StringBuilder();
        while (i >= 8) {
            sb.append(NonceUtils.b[3]);
            i -= 8;
        }
        for (int j = 2; j >= 0; --j) {
            if ((i & 1 << j) != 0x0) {
                sb.append(NonceUtils.b[j]);
            }
        }
        sb.append(s);
        return sb.toString();
    }

   /* public static void main(final String[] args) throws IOException {
        System.out.println(c() + g());
    }*/

    static {
    	sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        b = new String[] { "0", "00", "0000", "00000000" };
        NonceUtils.d = 0;
    }
}
