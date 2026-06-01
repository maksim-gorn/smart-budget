# Deployment Guide for pseudo_api

This guide covers building, running, and deploying the `pseudo_api` container.

## Prerequisites

- Docker Engine 20.10+
- Docker Compose 2.0+ (for compose deployment)
- At least 1GB free disk space
- Ports 8080 available on host

## Building the Image

### Build locally

```bash
docker build -t pseudo_api:latest .
```

### Build with specific tag (for versioning)

```bash
docker build -t pseudo_api:1.0.0 .
docker build -t pseudo_api:latest .
```

### Build with BuildKit (faster caching)

```bash
DOCKER_BUILDKIT=1 docker build -t pseudo_api:latest .
```

## Running the Container

### Using docker run (single instance)

```bash
docker run -d \
  --name pseudo_api \
  -p 8080:8080 \
  -e JAVA_OPTS="-Xmx512m -Xms256m" \
  --restart unless-stopped \
  pseudo_api:latest
```

### View logs

```bash
docker logs -f pseudo_api
```

### Stop the container

```bash
docker stop pseudo_api
```

### Remove the container

```bash
docker rm pseudo_api
```

## Using Docker Compose

### Start the service

```bash
docker compose up -d
```

### View logs

```bash
docker compose logs -f api
```

### Stop the service

```bash
docker compose down
```

### Stop and remove volumes

```bash
docker compose down -v
```

## Environment Variables

Configure Java memory settings by modifying the `JAVA_OPTS` environment variable:

### Default (from docker-compose.yml)
- Heap min: 256MB
- Heap max: 512MB

### Adjust for your environment

```bash
docker run -d \
  --name pseudo_api \
  -p 8080:8080 \
  -e JAVA_OPTS="-Xmx1g -Xms512m" \
  pseudo_api:latest
```

Or update `docker-compose.yml`:

```yaml
services:
  api:
    environment:
      - JAVA_OPTS=-Xmx1g -Xms512m
```

## Health Checks

The container includes a health check that verifies the application is running:

```bash
docker ps --filter "name=pseudo_api"
```

Check status column for `healthy`, `unhealthy`, or `starting`.

### Manual health check

```bash
curl -f http://localhost:8080/actuator/health || exit 1
```

## Monitoring

### Container resource usage

```bash
docker stats pseudo_api
```

### Container details

```bash
docker inspect pseudo_api
```

### View running containers

```bash
docker ps -a --filter "name=pseudo_api"
```

## Troubleshooting

### Container won't start

Check logs for errors:

```bash
docker logs pseudo_api
```

Common issues:
- Port 8080 already in use: `docker ps` to check, or use `-p 9090:8080` to map to different port
- Out of memory: increase `JAVA_OPTS` heap size
- Missing dependencies: rebuild image with `docker build --no-cache -t pseudo_api:latest .`

### Container keeps restarting

View logs to identify the crash:

```bash
docker logs pseudo_api | tail -50
```

Check container state:

```bash
docker inspect pseudo_api | grep -A 10 "State"
```

### High memory usage

Monitor with:

```bash
docker stats pseudo_api
```

Adjust heap settings:

```bash
docker stop pseudo_api
docker rm pseudo_api
docker run -d \
  --name pseudo_api \
  -p 8080:8080 \
  -e JAVA_OPTS="-Xmx256m -Xms128m" \
  pseudo_api:latest
```

## Pushing to Registry

### Docker Hub

```bash
docker tag pseudo_api:latest <your-username>/pseudo_api:latest
docker login
docker push <your-username>/pseudo_api:latest
```

### Private Registry

```bash
docker tag pseudo_api:latest registry.example.com/pseudo_api:latest
docker push registry.example.com/pseudo_api:latest
```

## Production Deployment

### Using with reverse proxy (nginx)

Add to your nginx configuration:

```nginx
upstream pseudo_api {
    server localhost:8080;
}

server {
    listen 80;
    server_name api.example.com;

    location / {
        proxy_pass http://pseudo_api;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

### Using Docker Compose with volume persistence

```yaml
services:
  api:
    build: .
    container_name: pseudo_api
    ports:
      - "8080:8080"
    environment:
      - JAVA_OPTS=-Xmx512m -Xms256m
    restart: unless-stopped
    volumes:
      - api_logs:/app/logs
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/actuator/health"]
      interval: 30s
      timeout: 10s
      retries: 3
      start_period: 40s

volumes:
  api_logs:
```

### Using with docker-compose and external network

```yaml
services:
  api:
    build: .
    container_name: pseudo_api
    ports:
      - "8080:8080"
    environment:
      - JAVA_OPTS=-Xmx512m -Xms256m
    restart: unless-stopped
    networks:
      - app_network
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/actuator/health"]
      interval: 30s
      timeout: 10s
      retries: 3
      start_period: 40s

networks:
  app_network:
    driver: bridge
```

## Cleanup

### Remove stopped containers

```bash
docker container prune
```

### Remove unused images

```bash
docker image prune
```

### Remove all pseudo_api related resources

```bash
docker stop pseudo_api 2>/dev/null || true
docker rm pseudo_api 2>/dev/null || true
docker rmi pseudo_api:latest 2>/dev/null || true
```

## Image Information

- **Base Image (runtime)**: eclipse-temurin:21-jre-alpine
- **Build Image**: maven:3.9-eclipse-temurin-21
- **Java Version**: 21
- **Default Port**: 8080 (mapped from internal 8081)
- **Application Name**: pseudo_api
- **Build Tool**: Maven

## Additional Resources

- [Docker Documentation](https://docs.docker.com/)
- [Spring Boot Docker Guide](https://spring.io/guides/gs/spring-boot-docker/)
- [Docker Compose Reference](https://docs.docker.com/compose/compose-file/)
