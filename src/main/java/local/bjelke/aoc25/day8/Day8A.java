package local.bjelke.aoc25.day8;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day8A {
    static void main() {
        new Day8A().run();
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
        double distance(Point3D p) {
            return distance(this, p);
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
        int connections = 1000;
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

            while (connectionCount < connections) {
                var conn = modPPDList.getFirst();
                var p1 = conn.pp.p1;
                var p2 = conn.pp.p2;
                modPPDList.removeFirst();

                if (p1.x == 52.0) {
                    System.out.println(p1);
                }

                if (p2.x == 52.0) {
                    System.out.println(p2);
                }

                var foundP1 = connectedPoints.stream().filter(c -> c.point.equals(p1)).findFirst();
                var foundP2 = connectedPoints.stream().filter(c -> c.point.equals(p2)).findFirst();

                if (foundP1.isPresent() && foundP2.isPresent()) {
                    foundP1.get().linked.add(foundP2.get());
                    foundP2.get().linked.add(foundP1.get());
                    connectionCount++;
                    continue;
                }

                if (foundP1.isPresent()) {
                    var newP2 = new ConnectedPoint(p2);
                    foundP1.get().linked.add(newP2);
                    newP2.linked.add(foundP1.get());
                    connectedPoints.add(newP2);

                    connectionCount++;
                    continue;
                }

                if (foundP2.isPresent()) {
                    var newP1 = new ConnectedPoint(p1);
                    foundP2.get().linked.add(newP1);
                    newP1.linked.add(foundP2.get());
                    connectedPoints.add(newP1);

                    connectionCount++;
                    continue;
                }

                var cc1 = new ConnectedPoint(p1);
                var cc2 = new ConnectedPoint(p2);

                cc1.linked.add(cc2);
                cc2.linked.add(cc1);

                connectedPoints.add(cc1);
                connectedPoints.add(cc2);
                connectionCount++;
            }

            // Find node groups
            var groups = new ArrayList<HashSet<Point3D>>();

            for (ConnectedPoint connectedPoint : connectedPoints) {

                if (connectedPoint.point.x == 52.0) {
                    System.out.println(connectedPoint.point);
                }

                // Escape if group already processed
                if (groups.stream().anyMatch(g -> g.contains(connectedPoint.point))) {
                    continue;
                }

                // get all points recursive
                var points = connectedPoints(connectedPoint);

                groups.add(points);
            }

            for (HashSet<Point3D> group : groups) {
                System.out.println(group);
            }

            long result = 1;

            for (Integer i : groups.stream().map(HashSet::size).sorted(Comparator.reverseOrder()).limit(3).toList()) {
                result *= i;
            }

            groups.stream().map(HashSet::size).sorted(Comparator.reverseOrder()).forEach(System.out::println);

            System.out.println(result);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    HashSet<Point3D> connectedPoints(ConnectedPoint connectedPoint) {
        HashSet<Point3D> points = new HashSet<>();
        while (!connectedPoint.linked.isEmpty()) {
            var first = connectedPoint.linked.stream().findFirst().get();
            connectedPoint.linked.remove(first);
            points.addAll(connectedPoints(first));
        }

        points.add(connectedPoint.point);
        return points;
    }

}
