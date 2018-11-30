package javabasic.util;

import org.junit.Test;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wenchao.meng
 *         <p>
 *         Nov 29, 2018
 */
public class PkgSlowOrLossFromDockerToHost extends AbstractPkg {

    @Test
    public void test() throws FileNotFoundException {

        check("/Users/mengwenchao/Documents/tmp/pkgloss/txt/serverH", "/Users/mengwenchao/Documents/tmp/pkgloss/txt/serverC", 0, true);

//        check("/Users/mengwenchao/Documents/tmp/pkgloss/txt/clientC", "/Users/mengwenchao/Documents/tmp/pkgloss/txt/clientH", 0, true);
//         check("/Users/mengwenchao/Documents/tmp/pkgloss/txt/clientH", "/Users/mengwenchao/Documents/tmp/pkgloss/txt/serverH", 1530, false);

    }

    private void check(String file1, String file2, int time2Sub1, boolean checkDelay) {

        List<Package> pkg1 = readPackages(file1);
        List<Package> pkg2 = readPackages(file2);

        System.out.println(String.format("from %s to %s", file1, file2));
        checkSlow(pkg1, pkg2, time2Sub1, checkDelay);

        System.out.println(String.format("from %s to %s", file2, file1));
        checkSlow(pkg2, pkg1, -time2Sub1, checkDelay);


    }

    private void checkSlow(List<Package> pkgsSource, List<Package> pkgsDst, int time2Sub1, boolean checkDelay) {

        Map<Package, Package> setPkgDst = new HashMap<>();

        pkgsDst.forEach(pkg -> setPkgDst.put(pkg, pkg));


        final AtomicInteger countLoss = new AtomicInteger();
        final AtomicInteger countDelay = new AtomicInteger();
        final AtomicInteger totalIntersect = new AtomicInteger();

        boolean firstMatch = false;

        List<Package> nullMatch = new LinkedList<>();

        for (Package pkgSource : pkgsSource) {

            Package pkgDst = setPkgDst.get(pkgSource);

            if(pkgDst != null){
                firstMatch = true;
                totalIntersect.incrementAndGet();

                nullMatch.forEach(pkg -> {
                    countLoss.incrementAndGet();
                    totalIntersect.incrementAndGet();
                    System.out.println();
                    System.out.println("pkg loss");
                    System.out.println(pkg.rawData);
                });
                nullMatch.clear();
            }

            if (pkgDst == null) {
                if(firstMatch){
                    nullMatch.add(pkgSource);
                }
                continue;
            }



            long time = pkgDst.getTime().getTime() - pkgSource.getTime().getTime() - time2Sub1;
            if (checkDelay && time > 200) {
                countDelay.incrementAndGet();
                System.out.println();
                System.out.println("pkg delay");
                System.out.println("serverContainer:" + pkgSource.rawData);
                System.out.println("     serverHost:" + pkgDst.rawData);
            }
        }

        System.out.println("loss:" + countLoss + ", countDelay:" + countDelay + ", totalIntersect:" + totalIntersect.get());
        System.out.println("loss rate:" + (double)(countLoss.get() + countDelay.get())/totalIntersect.get());
    }

}
