package javabasic.util;

import org.junit.Test;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wenchao.meng
 *         <p>
 *         Nov 29, 2018
 */
public class PkgResend extends AbstractPkg {

    private String file = "/Users/mengwenchao/Documents/tmp/pkgloss/txt/clientC";

    private String sourceIp = "0.28.223.209";

    private Map<Package, List<String>> all = new ConcurrentHashMap<>();

    @Test
    public void test() throws FileNotFoundException {

        all = readDupPackage(file);

        final AtomicInteger count = new AtomicInteger();
        all.forEach((pkg, lines) -> {

            if (lines.size() > 1) {
                if (sourceIp != null && pkg.source.indexOf(sourceIp) < 0) {
                    return;
                }

                count.incrementAndGet();
                lines.forEach(line -> System.out.println(line));
                System.out.println();
            }
        });

        System.out.println(count);
    }


}
