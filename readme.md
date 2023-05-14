compare reactive framework

|                 | vertx   pg     | reactor  pg     | vertx  mongo      | reactor  mongo  |
|-----------------|----------------|-----------------|-------------------|-----------------|
| jar size        | 17,2 mb        | 16,6 mb         | 22 mb             | 18.8 mb         |
| startup         | ---            | ---             | ---               | ---             |
| ------- time    | 0.63 s         | 0.55 s          | PT1.08            | PT0.85          |
| ------- memory  | 24 mb          | 27 mb           | 41 mb             | 49  mb          |
| ------- cpu     | 11.3 %         | 7.5%            | 27%               | 15%             |
| rps c20         | ---            | ---             | ---               | ---             |
| ------- rps     | 663.13 [#/sec] | 869.79 [#/sec]  | 2044.64 [#/sec]   | 1869.81 [#/sec] |
| ------- latency | 30.160 [ms]    | 22.994 [ms]     | 9.782 [ms] (mean) | 10.696 [ms]     |
| ------- memory  | 44 mb          | 60  mb          | 33 mb             | 38 mb           |
| ------- cpu     | 8%             | 11.4%           | 12%               | 27%             |
| rps c50         | ---            | ---             | ---               | ---             |
| ------- rps     | 776.19 [#/sec] | 1099.34 [#/sec] | 2213.48 [#/sec]   | 2462.34 [#/sec] |
| ------- latency | 64.417 [ms]    | 45.482 [ms]     | 22.589 [ms]       | 20.306 [ms]     |
| ------- memory  | 48 mb          | 61 mb           | 50 mb             | 30 mb           |
| ------- cpu     | 7.2 %          | 7.8%            | 17%               | 13%             |
| rps c100        | ---            | ---             | ---               | ---             |
| ------- rps     | 809.89 [#/sec] | 1429.77 [#/sec] | 2192.92 [#/sec]   | 2692.39 [#/sec] |
| ------- latency | 123.473 [ms]   | 69.941 [ms]     | 45.601 [ms]       | 37.142 [ms]     |
| ------- memory  | 36 mb          | 57 mb           | 58 mb             | 60 mb           |
| ------- cpu     | 5.9%           | 6,7%            | 12 %              | 22%             |
| docker size     | 62.91 MB       | 62.28 MB        | 67.82 MB          | 64.53 MB        |
| docker startup  | ---            | ---             | ---               | ---             |
| ------- time    | 4.2 s          | 4.1 s           | 6.202 s           | 5.45 s          |
| ------- memory  | 9 mb           | 9 mb            | 10 mb             | 10 mb           |
| ------- cpu     | 7.7%  gc       | 1.4%            | ?                 | ?               |
| docker rps c10  | ---            | ---             | ---               | ---             |
| ------- rps     | 393.33 [#/sec] | 149.50 [#/sec]  | 106.74 [#/sec]    | 88.12 [#/sec]   |
| ------- latency | 25.424 [ms]    | 66.889 [ms]     | 93.689 [ms]       | 113.481 [ms]    |
| ------- memory  | 13 mb          | 17 mb           | 13 mb             | 15 mb           |
| ------- cpu     | 0              | 0               | 0.4 %gc           | 3% gc           |
| docker rps c20  | ---            | ---             | ---               | ---             |
| ------- rps     | 325.27 [#/sec] | 262.72 [#/sec]  | 542.01 [#/sec]    | 224.81 [#/sec]  |
| ------- latency | 61.488 [ms]    | 76.128 [ms]     | 36.900 [ms]       | 88.966 [ms]     |
| ------- memory  | 13 mb          | 17 mb           | 15 mb             | 16 mb           |
| ------- cpu     | 0              | 0               | 7% gc             | 7% gc           |
| docker rps c50  | ---            | ---             | ---               | ---             |
| ------- rps     | 403.66 [#/sec] | 212.30 [#/sec]  | 604.73 [#/sec]    | 271.53 [#/sec]  |
| ------- latency | 123.866 [ms]   | 235.519 [ms]    | 82.681 [ms]       | 184.143 [ms]    |
| ------- memory  | 13 mb          | 18 mb           | 15 mb             | 20 mb           |
| ------- cpu     | 0              | 8%  gc          | 5% gc             | 0               |


example run
./gradlew shadowJar
cd build/libs/
java -jar starter.jar

load test
ab -n2000 -c50 -r 127.0.0.1:8080/hello

./gradlew shadowJar
docker-compose build
docker-compose up