package local.bjelke.aoc25.day8;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day8B {
    static void main() {
        new Day8B().run();
    }

    record Point3D(float x, float y, float z) {
        static Point3D from(String str) {
            String[] split = str.split(",");
            return new Point3D(
                    Float.parseFloat(split[0]),
                    Float.parseFloat(split[1]),
                    Float.parseFloat(split[2])
            );
        }
        static double distance(Point3D p1, Point3D p2) {
            return Math.sqrt(
                    (p1.x - p2.x) * (p1.x - p2.x) +
                    (p1.y - p2.y) * (p1.y - p2.y) +
                    (p1.z - p2.z) * (p1.z - p2.z)
            );
        }
    }

    record PointPair(Point3D p1, Point3D p2) {
    }

    record PPD(PointPair pp, double distance) {}

    class ConnectedPoint {
        Point3D point;
        HashSet<ConnectedPoint> linked = new HashSet<>();

        public ConnectedPoint(Point3D point) {
            this.point = point;
        }

        @Override
        public int hashCode() {
            return this.point.hashCode();
        }
    }

    File file = new File("day8.txt");

    void run() {
        try(var fileReader = new FileReader(file)) {
            var jBoxList = fileReader.readAllLines().stream().map(Point3D::from).toList();

            var pairHash = new HashMap<PointPair, Double>();

            for (int i = 0; i < jBoxList.size(); i++) {
                for (int j = 0; j < jBoxList.size(); j++) {
                    if (i != j) {
                        var p1 = jBoxList.get(i);
                        var p2 = jBoxList.get(j);
                        if (!pairHash.containsKey(new PointPair(p2, p1))) {
                            double distance = Point3D.distance(jBoxList.get(i), jBoxList.get(j));
                            pairHash.put(new PointPair(p1, p2), distance);
                        }
                    }
                }
            }

            var sortedPPDList = pairHash.entrySet().stream()
                    .map(kv -> new PPD(kv.getKey(), kv.getValue()))
                    .sorted(Comparator.comparingDouble(p -> p.distance))
                    .toList();

            for (PPD ppd : sortedPPDList) {
                System.out.println(ppd);
            }

            var modPPDList = new ArrayList<>(sortedPPDList);

            var first = modPPDList.getFirst();
            modPPDList.removeFirst();

            List<ConnectedPoint> connectedPoints = new ArrayList<>();

            var conn1 = new ConnectedPoint(first.pp.p1);
            var conn2 = new ConnectedPoint(first.pp.p2);

            conn1.linked.add(conn2);
            conn2.linked.add(conn1);

            connectedPoints.add(conn1);
            connectedPoints.add(conn2);

            int connectionCount = 1;

            long lastAddedXProduct = 0;
            float lx = 0;
            float ly = 0;

            while (connectedPoints2(conn1, new ArrayList<>()).size() < jBoxList.size()) {
                var conn = modPPDList.getFirst();
                var p1 = conn.pp.p1;
                var p2 = conn.pp.p2;
                modPPDList.removeFirst();

                if (conn.pp.p1.x == 216.0 && conn.pp.p2.x == 117.0) {
                    System.out.println("A");
                }
                if (conn.pp.p2.x == 216.0 && conn.pp.p1.x == 117.0) {
                    System.out.println("B");
                }

                var foundP1 = connectedPoints.stream().filter(c -> c.point.equals(p1)).findFirst();
                var foundP2 = connectedPoints.stream().filter(c -> c.point.equals(p2)).findFirst();

                if (foundP1.isPresent() && foundP2.isPresent()) {
                    var points1 = connectedPoints2(foundP1.get(), new ArrayList<>());
                    var points2 = connectedPoints2(foundP2.get(), new ArrayList<>());
                    // deny if already in set
                    if (points1.stream().anyMatch(points2::contains)) {
                        continue;
                    }

                    foundP1.get().linked.add(foundP2.get());
                    foundP2.get().linked.add(foundP1.get());

                    lx = p1.x;
                    ly = p1.y;

                    connectionCount++;
                    System.out.println("Merge " + connectionCount);
                    continue;
                }

                if (foundP1.isPresent()) {
                    var newP2 = new ConnectedPoint(p2);
                    foundP1.get().linked.add(newP2);
                    newP2.linked.add(foundP1.get());
                    connectedPoints.add(newP2);

                    lx = foundP1.get().point.x;
                    ly = newP2.point.x;


                    connectionCount++;
                    System.out.println("Append1 " + connectionCount);
                    continue;
                }

                if (foundP2.isPresent()) {

                    var newP1 = new ConnectedPoint(p1);
                    foundP2.get().linked.add(newP1);
                    newP1.linked.add(foundP2.get());
                    connectedPoints.add(newP1);

                    lx = foundP2.get().point.x;
                    ly = newP1.point.x;

                    connectionCount++;
                    System.out.println("Append2 " + connectionCount);
                    System.out.println(modPPDList.size());
                    continue;
                }

                var cc1 = new ConnectedPoint(p1);
                var cc2 = new ConnectedPoint(p2);

                cc1.linked.add(cc2);
                cc2.linked.add(cc1);

                lx = cc1.point.x;
                ly = cc2.point.x;

                connectedPoints.add(cc1);
                connectedPoints.add(cc2);
                connectionCount++;
            }

            System.out.println("Last append " + ((long)lx) * ((long)ly));
            System.out.println(lx);
            System.out.println(ly);

            System.out.println("Connections: " + connectionCount);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    HashSet<Point3D> connectedPoints2(ConnectedPoint connectedPoint, ArrayList<ConnectedPoint> memo) {
        HashSet<Point3D> points = new HashSet<>();

        memo.add(connectedPoint);

        var linkedCopy = new ArrayList<>(connectedPoint.linked);
        for (ConnectedPoint point : memo) {
            linkedCopy.remove(point);
        }

        while (!linkedCopy.isEmpty()) {
            var first = linkedCopy.stream().findFirst().get();
            linkedCopy.remove(first);
            points.addAll(connectedPoints2(first, memo));
        }

        points.add(connectedPoint.point);
        return points;
    }

}
