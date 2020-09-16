# spring-memcache-runner

## Options for Runner (getopts)
* `--host=<memcached_host>` to set the memcache host to use (default: `localhost`)
* `--port=<memcached_port>` to set the memcache port to use (default: `11211`)
* `--key="<key_name>"` for the memcache key (default `HelloWorld`)
* `--ttl="<key_expiry>"` expiry for the memcache key (default `60`)
* `--value="<value>"` to set the value for the memcache key
* `--use.random=true` to include a random number in your memcache value (default `false`)
## HowTo Run
1. compile the java package by running:
```shell script
./mvnw clean package
```
1. run the jar with your options (see example below using default host/port of localhost/11211)
```shell script
java -jar target/memcahe-client-0.0.1-SNAPSHOT.jar --key="MyTest" --value="Stuff2" --use.random=true
11:43:37.055 [main] INFO com.flickr.memcaheclient.MemcaheClientApplication - starting runner

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.3.3.RELEASE)

2020-09-16 11:43:37.690  INFO 99186 --- [           main] c.f.m.MemcaheClientApplication           : Starting MemcaheClientApplication v0.0.1-SNAPSHOT on Ben-SM-MacBook-Pro.local with PID 99186 (/Users/benf/Seafile/flickr_workspace/flickr_git/memcahe-client/target/memcahe-client-0.0.1-SNAPSHOT.jar started by benf in /Users/benf/Seafile/flickr_workspace/flickr_git/memcahe-client)
2020-09-16 11:43:37.692  INFO 99186 --- [           main] c.f.m.MemcaheClientApplication           : No active profile set, falling back to default profiles: default
2020-09-16 11:43:38.913  INFO 99186 --- [           main] c.f.m.MemcaheClientApplication           : Started MemcaheClientApplication in 1.69 seconds (JVM running for 2.205)
2020-09-16 11:43:38.957 INFO net.spy.memcached.MemcachedConnection:  Setting retryQueueSize to -1
2020-09-16 11:43:38.975 INFO net.spy.memcached.MemcachedConnection:  Added {QA sa=localhost/127.0.0.1:11211, #Rops=0, #Wops=0, #iq=0, topRop=null, topWop=null, toWrite=0, interested=0} to connect queue
2020-09-16 11:43:38.978  INFO 99186 --- [           main] c.f.m.MemcaheClientApplication           : using random number generator - num=[4882]
2020-09-16 11:43:38.980  INFO 99186 --- [           main] c.f.m.MemcaheClientApplication           : setting value=[Stuff2::4882] for key=[MyTest] expiry=[60]
2020-09-16 11:43:38.988  INFO 99186 --- [           main] c.f.m.MemcaheClientApplication           : testing value for key=[MyTest]
2020-09-16 11:43:38.995  INFO 99186 --- [           main] c.f.m.MemcaheClientApplication           : value=[Stuff2::4882]

```

## Docker HowTo
There is a `docker-compose.yaml` file in the root of the repo that consists of a memcached daemon as well as three different clients
* java
* python
* php

### Build The Clients
The following command will build the three client containers
```shell script
docker-compose build
```

### Client Info
In the following examples the `--host="memcached"` or similar references the memcached container name from the `docker-compose.yaml` file.

#### Run Java Client
Using the `docker-compose run` sub command we can run the various options of the client, here is an example:

```shell script
docker-compose run java-client java -jar memcahe-client-0.0.1-SNAPSHOT.jar --host="memcached" --key="MyTest"

```

#### Run Python Client
```shell script
docker-compose run python-client python memcache_client.py --host "memcached" --key "MyTest" --value "Ben123"
```

#### Run PHP Client
 ```shell script
docker-compose run php-client php memcache_client.php --host="memcached" --key="MyTest"
``` 
