package com.example.demo.service.impl;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HWAPI {

    private String token = "MIIWtgYJKoZIhvcNAQcCoIIWpzCCFqMCAQExDTALBglghkgBZQMEAgEwghUEBgkqhkiG9w0BBwGgghT1BIIU8XsidG9rZW4iOnsiZXhwaXJlc19hdCI6IjIwMTgtMTItMTBUMTQ6MDc6MDUuNTY3MDAwWiIsIm1ldGhvZHMiOlsicGFzc3dvcmQiXSwiY2F0YWxvZyI6W10sInJvbGVzIjpbeyJuYW1lIjoidGVfYWRtaW4iLCJpZCI6ImUyZDc1NDI5NTQ5MDQ0ZDJiMzEzZmJkZDExNTIwZjdkIn0seyJuYW1lIjoib3BfZ2F0ZWRfZWNzX2dwdV9wMnYiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9laXBfaXB2NiIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Fpc19kZWZvZyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Fpc19zdXBlcl9yZXNvbHV0aW9uIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfYWlzX3BhdGhfcHJvZ3JhbSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Nlc19hZ3QiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9mYWNlX2xpdmUiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9ibXNfaHBjX2gybGFyZ2UiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9lY3NfZ3B1X3YxMDAiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9haXNfb2NyX2JhbmtfY2FyZCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Nic19xYWJvdCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Nic19xaSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2R3c19wb2MiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9jYnNfYm90bGFiIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZXJzIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfaW90LXRyaWFsIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfbGl2ZSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Zjc19CaW90ZWNoIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZWNzcXVpY2tkZXBsb3kiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9ydHMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9yZHNfc3Fsc2VydmVyMjAxNyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3ZjYyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3NtcyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2FwcF9jb25zaXN0ZW5jeSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Fpc19iZ21fcmNnbiIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2RwcCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3ZjciIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3NlcnZpY2VzdGFnZV9zcHJpbmdjbG91ZCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Fpc19iaW5fcGFja2luZyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Vjc19yZWN5Y2xlYmluIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfY2xvdWRpZGUiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9pZWZfZnVuY3Rpb24iLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9iYXRjaCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2ZpbmVfZ3JhaW5lZCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX202bXQiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9ldnNfcmV0eXBlIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfYWFkX2ZyZWUiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9yZHNfcGc5NCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3Rpa3YiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9zZnN0dXJibyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2NzYnNfdGFnIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfYV9jbi1ub3J0aC00YyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2h2X3ZlbmRvciIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2FfY24tbm9ydGgtNGIiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9lY3NfaGkzIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfcm9tYV9saW5rIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfYV9jbi1ub3J0aC00ZSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2FfY24tbm9ydGgtNGQiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9lY3NfZ3B1X3A0IiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfcmRzaTMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9haXNfaW1hZ2VfcmVjYXB0dXJlX2RldGVjdCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Nic190YXNrX3NjIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfYV9jbi1ub3J0aC00ZiIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2hzc19jZ3MiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9ia2xsZCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Ric3NfZnJlZXRyYWlsIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfY2NlX2lzdGlvIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZWNzX2gxIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfc2R3YW4iLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9haXNfdHRzIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfaW90MDEiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9lY3NfZGlza2ludGVuc2l2ZSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Fpc19hc3Jfc2VudGVuY2UiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF91bnZlcmlmaWVkIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZGxzX3ByZWRpY3QiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9jc2JzX3JlcF9hY2NlbGVyYXRpb24iLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9kc3NfbW9udGgiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9HQ1MiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9jc3MiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9jY2VfdGVzdCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3ZpcF9iYW5kd2lkdGgiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9WSVMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9haXNfb2NyX2JhcmNvZGUiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9hcGlleHBsb3JlciIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Fpc19kaXN0b3J0aW9uX2NvcnJlY3QiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9haXNfaW1hZ2VfY2xhcml0eV9kZXRlY3QiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9iYW5kd2lkdGhfcGFja2FnZSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Nsb3VkX2Nvbm5lY3QiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9jcnMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9sbGQiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9pdnNjcyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2lwdjZfZHVhbHN0YWNrIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfaWVmIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfTFRTIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfcGcxMSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2dhdGVkX2Vjc19yZWN5Y2xlYmluIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfc2RycyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Fpc19kaXNwYXRjaF9wbGFubmluZyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Fpc190aW1lX2Fub21hbHkiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9kY3NfZGNzMiIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Vjc19oaWdoYmFuZCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2xlZ2FjeSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Fpc19waWNrdXBfcGxhbm5pbmciLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9jbWMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9ldnNfcG9vbF9jYSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2FfQ04tU09VVEgtMyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Vjc19zcG90IiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfTW9kZWxhcnRzIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZ3NzX2ZyZWVfdHJpYWwiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9kZHNfaHdJbnN0YW5jZSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2VwcyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Fpc19vY3JfcXJfY29kZSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Rjc19pbWRnIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZmNzX3BheSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3RtcyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3ZwY2VwIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfc21uX2FwcCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3NlcnZpY2VzdGFnZV9jYXMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9zbyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Fpc19kYXJrX2VuaGFuY2UiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9pZWZfZGV2aWNlX2RpcmVjdCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3Jkc19jcmVhdGVHUklucyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Vjc192Z3B1X2c1IiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfYWlzX3ZjbSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX3Jlc3RyaWN0ZWQiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9jc2Vfc2VydmljZWNvbWJfcGh5c2ljYWwiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9jbG91ZHRlc3RfcHRfaHdJbnN0YW5jZSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Fpc19vY3JfcGxhdGVfbnVtYmVyIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfY2NlX3dpbiIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3VsYl9taWl0X3Rlc3QiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9PQlNfZmlsZV9wcm90b2NvbCIsImlkIjoiMCJ9XSwicHJvamVjdCI6eyJkb21haW4iOnsibmFtZSI6IkNoZXR0d2luIiwiaWQiOiIxMTc5OWU0NjFkOTg0MzQwYWQ3OGU2ZGZlYjFkZWY1OCJ9LCJuYW1lIjoiY24tbm9ydGgtMSIsImlkIjoiNWYxODc5ZDAwODU2NGJkMDk4MWQ0MGQxMmYwOGNkNWEifSwiaXNzdWVkX2F0IjoiMjAxOC0xMi0wOVQxNDowNzowNS41NjcwMDBaIiwidXNlciI6eyJkb21haW4iOnsibmFtZSI6IkNoZXR0d2luIiwiaWQiOiIxMTc5OWU0NjFkOTg0MzQwYWQ3OGU2ZGZlYjFkZWY1OCJ9LCJuYW1lIjoiQ2hldHR3aW4iLCJpZCI6Ijc4ZWMwZTI0ZjUxZjRlYmZiMzUzMjJjODA3OTIyMTQyIn19fTGCAYUwggGBAgEBMFwwVzELMAkGA1UEBhMCVVMxDjAMBgNVBAgMBVVuc2V0MQ4wDAYDVQQHDAVVbnNldDEOMAwGA1UECgwFVW5zZXQxGDAWBgNVBAMMD3d3dy5leGFtcGxlLmNvbQIBATALBglghkgBZQMEAgEwDQYJKoZIhvcNAQEBBQAEggEADSkV0-NacBvYJ1C93O4AvIt8GmEdd4KS+-uxeu2SA6U0qpNLUIrlznnC+tZScr7OlaUQSJZc+fsLS4SdKbgw1WTF0C83Q7Sj6EPpu41A58XkTWAA0Od8uGdh-mvxhfSWjwkS6-gREyPKqE2XBK6F5RYRpgzEjSoPVxWujtdX2y3iFphPbVQr-XiNNXPAsdSAZ-S3y71xE3TAgEKbQwu4VNB37QJHp84Mq10uDwZ5P3xQbg9V3j78h7RTuC167bpZ3muhlgulXHbP52eNfM-0oMgmKg8U-cpuAKoCXZM3y8evo-dE+B6Of0mCG7LtKCfQTe1lr2++lGMYIeDcSxpLLg==";

    private static class DefaultTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

    }

    public String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        String charSet = "utf-8";
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0],
                    new TrustManager[] { new DefaultTrustManager() },
                    new SecureRandom());
            SSLContext.setDefault(ctx);
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpsURLConnection conn = (HttpsURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("X-Auth-Token", token);// 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应

            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),charSet));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
}
