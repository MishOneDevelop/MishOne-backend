# MishOne-backend

Backend Spring Boot del sitio/landing de la empresa **Mish One**. Expone una API REST para
administrar el portafolio de proyectos, catálogo de servicios y mensajes de contacto del
front (`mish-one-frontend`, Angular, desplegado en Vercel).

## Stack

- Java 21, Spring Boot 3.4.2 (`spring-boot-starter-web`, `spring-boot-starter-data-jpa`)
- SQLite como base de datos (driver `xerial/sqlite-jdbc` + `hibernate-community-dialects`,
  con dialecto propio en `config/SQLiteCustomDialect.java` para soportar
  `last_insert_rowid()`)
- AWS SDK v2 (S3) para respaldo/restauración del archivo `.db`
- Lombok, Springdoc OpenAPI (Swagger UI)
- Despliegue: Docker (`Dockerfile`, build multi-stage) sobre Render

## Arquitectura

Capas clásicas por feature: `controller` -> `service` (interfaz + `*ServiceImpl`) ->
`repository` (Spring Data JPA) -> `model` (entidades JPA con `@PrePersist`/`@PreUpdate`
para timestamps automáticos).

Entidades (`src/main/java/com/mishone/mishone/model`):
- `Proyecto` (tabla `portafolio`) — ítems del portafolio, relacionado a `Servicio`.
- `Servicio` (tabla `servicios`) — catálogo de servicios ofrecidos, con `categoria`
  apuntando a `ValorParametro`.
- `DetalleServicio` — detalle extendido de un `Servicio` (tecnologías y modalidad se
  guardan como JSON plano en columnas `TEXT`).
- `Parametro` / `ValorParametro` — tabla de parametrización genérica tipo
  catálogo-de-catálogos (ej. estados, categorías) usada como FK desde otras entidades
  en vez de enums fijos.
- `Contacto` — mensajes del formulario de contacto; `estado` es FK a `ValorParametro`.

## Particularidades importantes

- **SQLite vive en un solo archivo**, cuya ruta viene de la env var `SQLITE_LOCAL_PATH`
  (ver `application.properties`). No hay servidor de BD tradicional.
- **Persistencia real via S3, no Git**: al arrancar, `SQLiteS3Initializer` (registrado
  como `ApplicationContextInitializer` en `META-INF/spring.factories`, corre *antes* que
  el contexto de Spring) descarga el `.db` desde S3 solo si `LOAD_DB=true`. `DailyBackupTask`
  sube el archivo a S3 cada noche (cron `0 0 0 * * *`, zona `America/Bogota`).
  `BackupController` (`/api/db/backup`, `/api/db/actualizardb`) permite disparar ambas
  operaciones manualmente. Variables de entorno requeridas:
  `AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY`, `AWS_REGION`, `S3_BUCKET_NAME`,
  `S3_OBJECT_KEY`, `SQLITE_LOCAL_PATH`, `LOAD_DB`.
- **Auto-ping**: `AutoPingTask` golpea `/api/ping` cada 10 min contra la URL fija de
  Render (`https://mishone-backend.onrender.com`) — es un keep-alive para el free tier,
  no borrar sin más si se cambia de host.
- **CORS**: orígenes permitidos hardcodeados en `CorsConfig`
  (`localhost:4200` y el dominio de Vercel del front). Actualizar ahí si cambia el
  dominio del frontend.
- Hay lógica AWS S3 duplicada entre `S3UploadService`/`SQLiteS3Service` (servicios) y
  `SQLiteS3Initializer` (config) — mismo patrón de credenciales/descarga repetido tres
  veces; tenerlo en cuenta antes de tocar la parte de S3, para no arreglar solo una copia.
- Carpeta `db/` (con `mishone.db`) está en el working tree pero sin trackear en git
  (`?? db/` en status) — es el archivo local de desarrollo, no se sube al repo.

## Convenciones del código existente

- Comentarios y mensajes de log/respuesta en español, con emojis en logs (✅/❌/⏫/🧪).
  Seguir el mismo estilo al tocar ese código.
- Controllers delgados: validan poco, delegan todo a la capa `service`.
- Repos son `JpaRepository` simples; sin capa de mapeo DTO (las entidades se exponen
  directo en los controllers).
