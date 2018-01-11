### Get Demo

```
$ curl -H "X-Role-Auth: client_id=A;role=get" http://localhost:8080/demo?fields=a,b
{"a":"A","b":"B"}
```

```
$ curl -H "X-Role-Auth: client_id=A;role=get" http://localhost:8080/demo?fields=a
{"a":"A"}
```

```
$ curl -H "X-Role-Auth: client_id=A;role=get" http://localhost:8080/demo?fields=b
{"b":"B"}
```

```
$ curl -H "X-Role-Auth: client_id=A;role=get" http://localhost:8080/demo?fields=a,b,c
{"timestamp":1515683382065,"status":403,"error":"Forbidden","exception":"org.springframework.security.access.AccessDeniedException","message":"Access is denied","path":"/demo"}
```

### Set Demo

```
$ curl -H "X-Role-Auth: client_id=B;role=set" -H "Content-Type: application/json" http://localhost:8080/demo -d '{"fields": {"c":"testC"}}'
{"a":"A","b":"B","c":"testC"}
```

```
$ curl -H "X-Role-Auth: client_id=B;role=set" -H "Content-Type: application/json" http://localhost:8080/demo -d '{"fields": {"a":"testA","b":"testB","c":"testC"}}'
{"timestamp":1515683697399,"status":403,"error":"Forbidden","exception":"org.springframework.security.access.AccessDeniedException","message":"Access is denied","path":"/demo"}
```