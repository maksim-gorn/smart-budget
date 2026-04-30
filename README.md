ЗАПУСК КОНТЕЙНЕРА БЭКЭНДА
для запуска необходима JDK 21 и установленный Docker

В терминале выполняются следущие команды:
  1. Собираем jar
.\mvnw.cmd clean package -DskipTests

  2. Запускаем контейнер
docker compose up --build
