package javabasic.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * tcpdump -S -tttt -r xx.pcap
 * @author wenchao.meng
 *         <p>
 *         Nov 29, 2018
 */
public class AbstractPkg {


    protected Map<Package, List<String>> readDupPackage(String file) {

        Map<Package, List<String>> all = new ConcurrentHashMap<>();
        List<Package> packages = readPackages(file);

        packages.forEach(pkg -> {

            DupPackage dupPackage = new DupPackage(pkg);

            List<String> dupPackages = all.get(dupPackage);

            if (dupPackages == null) {
                dupPackages = new LinkedList<>();
                all.put(dupPackage, dupPackages);
            }
            dupPackages.add(pkg.rawData);

        });

        return all;
    }

    protected List<Package> readPackages(String file) {

        List<Package> packages = new LinkedList<>();

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    processLine(line, packages);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return packages;
    }

    private void processLine(String line, List<Package> packages) {

        String[] split = line.split("\\s+");
        if (split.length < 19) {
//            System.out.println("wrong format:" + split.length + "," + line);
            return;
        }

        String time = split[0] + " " + split[1];
        String source = split[4 - 1];
        String dst = split[6 - 1];
        String seq = null;
        String ack = null;
        String timestamp = null;
        String ecr = null;

        for (int i = 0; i < split.length; i++) {

            String sp = split[i];
            if (sp.equalsIgnoreCase("seq")) {
                seq = split[i + 1];
            }
            if (sp.equalsIgnoreCase("val")) {
                timestamp = split[i + 1];
            }
            if (sp.equalsIgnoreCase("ack")) {
                ack = split[i + 1].replaceAll(",", "");
            }
            if (sp.equalsIgnoreCase("ecr")) {
                ecr = split[i + 1].replace("],", "");
            }
        }


        Package pkg = new Package(time, source, dst, seq, ack, timestamp, ecr, line);
        packages.add(pkg);
    }

    public static class Package {

        Date time = null;
        String source = null;
        String dst = null;
        String seq = null;
        String ack = null;
        String timestamp = null;
        String ecr = null;
        String rawData = null;

        public Package(String time, String source, String dst, String seq, String ack, String timestamp, String ecr, String rawData) {
            this.time = parse(time);
            this.source = source;
            this.dst = dst;
            this.seq = seq;
            this.ack = ack;
            this.timestamp = timestamp;
            this.ecr = ecr;
            this.rawData = rawData;
        }

        private Date parse(String time) {
            try {
                return DateUtil.stringAsDate(time);
            } catch (ParseException e) {
                throw new IllegalArgumentException(time);
            }
        }

        public Package(Package pkg) {
            this.source = pkg.source;
            this.dst = pkg.dst;
            this.seq = pkg.seq;
            this.ack = pkg.ack;
            this.timestamp = pkg.timestamp;
            this.ecr = pkg.ecr;
            this.rawData = pkg.rawData;
        }

        public Date getTime() {
            return time;
        }

        @Override
        public boolean equals(Object obj) {

            if (!(obj instanceof Package)) {
                return false;
            }

            Package other = (Package) obj;


            return Objects.equals(this.source, other.source) &&
                    Objects.equals(this.dst, other.dst) &&
                    Objects.equals(this.seq, other.seq) &&
                    Objects.equals(this.ack, other.ack) &&
                    Objects.equals(this.ecr, other.ecr) &&
                    Objects.equals(this.timestamp, other.timestamp);
        }

        @Override
        public int hashCode() {
            return Objects.hash(source, dst, seq, ack, ecr, timestamp);
        }
    }

    public static class DupPackage extends Package {

        public DupPackage(String time, String source, String dst, String seq, String ack, String timestamp, String ecr, String rawData) {
            super(time, source, dst, seq, ack, timestamp, ecr, rawData);
        }

        public DupPackage(Package pkg) {
            super(pkg);
        }

        @Override
        public boolean equals(Object obj) {

            if (!(obj instanceof Package)) {
                return false;
            }

            Package other = (Package) obj;

            return Objects.equals(this.source, other.source) &&
                    Objects.equals(this.dst, other.dst) &&
                    Objects.equals(this.seq, other.seq) &&
                    Objects.equals(this.ack, other.ack) &&
                    Objects.equals(this.ecr, other.ecr);
        }

        @Override
        public int hashCode() {
            return Objects.hash(source, dst, seq, ack, ecr);
        }

    }

}
