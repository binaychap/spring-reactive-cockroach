## spring boot reactive and cockroach db 

### cockroach db creation with docker command

```agsl
docker network create -d bridge roachnet
docker volume create roach1
docker volume create roach2
docker volume create roach3
docker run -d --name=roach1 --hostname=roach1 --net=roachnet -p 26257:26257 -p 8080:8080 -v "roach1:/cockroach/cockroach-data" cockroachdb/cockroach:v23.1.11 start   --advertise-addr=roach1:26357   --http-addr=roach1:8080   --listen-addr=roach1:26357   --sql-addr=roach1:26257   --insecure   --join=roach1:26357,roach2:26357,roach3:26357
docker run -d   --name=roach2   --hostname=roach2   --net=roachnet   -p 26258:26258   -p 8081:8081   -v "roach2:/cockroach/cockroach-data"   cockroachdb/cockroach:v23.1.11 start     --advertise-addr=roach2:26357     --http-addr=roach2:8081     --listen-addr=roach2:26357     --sql-addr=roach2:26258     --insecure     --join=roach1:26357,roach2:26357,roach3:26357
docker run -d   --name=roach3   --hostname=roach3   --net=roachnet   -p 26259:26259   -p 8082:8082   -v "roach3:/cockroach/cockroach-data"   cockroachdb/cockroach:v23.1.11 start     --advertise-addr=roach3:26357     --http-addr=roach3:8082     --listen-addr=roach3:26357     --sql-addr=roach3:26259     --insecure     --join=roach1:26357,roach2:26357,roach3:26357
docker exec -it roach1 ./cockroach --host=roach1:26357 init --insecure
docker exec -it roach1 grep 'node starting' /cockroach/cockroach-data/logs/cockroach.log -A 11
docker exec -it roach1 ./cockroach sql --host=roach2:26258 --insecure

docker container stop <name>
docker container start <name>
```
### UI 
http://localhost:8080

### CRUD API
```agsl
POST http://localhost:9191/api/account

body json
{
   
    "type": "checking",
    "name": "Binay",
    "balance":1224.9
}

```

```agsl
GET http://localhost:9191/api/account

response
[
    {
        "id": 906376423640006657,
        "name": "Ujjalo",
        "type": "Saving",
        "balance": 1.20
    },
    {
        "id": 906376738361081857,
        "name": "normal",
        "type": "checking",
        "balance": 100.90
    },
    {
        "id": 906376778665623553,
        "name": "Hari",
        "type": "checking",
        "balance": 33.90
    },
    {
        "id": 906376802475704321,
        "name": "Binay",
        "type": "checking",
        "balance": 1224.90
    }
]
```



