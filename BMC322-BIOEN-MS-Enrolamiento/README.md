# BMC322-BIOEN-MS-Enrolamiento


## Despliegue local en docker

docker build -t bio-ms-enrolamiento:${version}
docker run --net=host bio-ms-enrolamiento:${version}
