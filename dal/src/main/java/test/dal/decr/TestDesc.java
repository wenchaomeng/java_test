package test.dal.decr;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import test.AbstractTest;

/**
 * @author wenchao.meng
 *         <p>
 *         Feb 02, 2018
 */
public class TestDesc extends AbstractTest {

    @Test
    public void test() {
        String entr = "EhcANAIREXkjGBgwDQAlNgA6ADATHScASy87EBAuXAgHahIVNkc3AFosNlwPACEtFQ8tNDlsJS0pWBUKHhVPXHxUWF1\\/JwsNfwAwKy8FABQtGjQVOl5oBBU3BTM8BgF7dREdHS4mOyElLzNQFD8AMk0BJxUCAwQaI1EiDQADBQUEBgIWBhECGyoXJiZXQkRlY2l2cmVTYXRhRElCdGxG=";
        System.out.println(decrypt(entr));

    }

    private String decrypt(String dataSource) {
        if (dataSource == null || dataSource.length() == 0) {
            return "";
        }
        byte[] sources = Base64.decodeBase64(dataSource);
        int dataLen = sources.length;
        int keyLen = (int) sources[0];
        int len = dataLen - keyLen - 1;
        byte[] datas = new byte[len];
        int offset = dataLen - 1;
        int i = 0;
        int j = 0;
        byte t;
        for (int o = 0; o < len; o++) {
            i = (i + 1) % keyLen;
            j = (j + sources[offset - i]) % keyLen;
            t = sources[offset - i];
            sources[offset - i] = sources[offset - j];
            sources[offset - j] = t;
            datas[o] =
                    (byte) (sources[o + 1] ^ sources[offset - ((sources[offset - i] + sources[offset - j]) % keyLen)]);
        }
        return new String(datas);
    }

}
