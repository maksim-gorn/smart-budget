# Smart Budget - Distribution Package

## To launch this stack:

1. **Extract the contents** to a folder

2. **Build the backend image:**
   ```bash
   docker compose build
   ```

3. **Start the stack:**
   ```bash
   docker compose up -d
   ```

4. **Access the application:**
   - Backend API: `http://localhost:8080`
   - Pseudo API: `http://localhost:8081`

## Configuration

Edit `.env` to customize:
- `POSTGRES_PASSWORD` - Database password
- `JWT_SECRET` - JWT signing secret (change this in production!)

## Stopping the stack

```bash
docker compose down
```

## Removing volumes (resets database)

```bash
docker compose down -v
```

## Troubleshooting

**Backend won't start?**
- Check logs: `docker compose logs backend`
- Ensure PostgreSQL is running: `docker compose logs postgres`
- Rebuild from scratch: `docker compose down && docker compose build --no-cache && docker compose up`

**Port already in use?**
- Modify ports in `docker-compose.yml` (e.g., `8081:8080` for backend)

**Database connection error?**
- Ensure `.env` file exists in the same directory as `docker-compose.yml`
- Verify database credentials match between `.env` and `docker-compose.yml`
