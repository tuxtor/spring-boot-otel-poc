# Executing docker builders

## Simple mode

```bash
docker build -f src/main/docker/Dockerfile.simple -t tuxtor/springboot-demo:simple .
```

## Layered mode

```bash
mvn clean package
mkdir target/extract
java -Djarmode=layertools -jar target/springboot-demo-0.0.1-SNAPSHOT.jar extract --destination target/extracted
docker build -f src/main/docker/Dockerfile.layers -t tuxtor/springboot-demo:layers .
```

## Multistaged

```bash
docker build -f src/main/docker/Dockerfile.multistaged -t tuxtor/springboot-demo:multistaged .   
```