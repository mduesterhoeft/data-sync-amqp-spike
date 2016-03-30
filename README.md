# Spike Contents

The spike demonstrates lazy initialization of reference data in a microservice using asynchronous communication over amqp.

A service keeps data that is needs from another service in a local copy to be able to access it without needing to query the other service.

Usually this data is propagated using events. But if a new service is introduced into a running system it starts with empty reference data. This spike shows a solution to load missing reference data on demand and store it on first use.


See [EPNG-520](https://epages.atlassian.net/browse/EPNG-520)

## Running the spike

### start the server side

```bash
cd data-synchronization-spring-amqp-server
gradle bootRun
```

The server side will come up on port 8098

### start the client side

```bash
cd data-synchronization-spring-amqp-client
gradle bootRun
```

The client side will come up on port 8099

### Create a businessunit on server side

```bash
http POST :8098/businessUnits/ tenantId=1 hostname=me.some.de
```

### Create a site on client side

```bash
http POST :8099/sites tenantId=3
```
