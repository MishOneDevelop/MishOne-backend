# MishOne-backend

Backend Spring Boot del sitio/landing de la empresa **Mish One**. Expone una API REST para
administrar el portafolio de proyectos, catálogo de servicios y mensajes de contacto del
front (`mish-one-frontend`, Angular, desplegado en Vercel).

## Stack

- Java 21, Spring Boot 3.4.2 (`spring-boot-starter-web`, `spring-boot-starter-data-jpa`)
- Postgres (Supabase, plan gratuito) como base de datos, vía driver `org.postgresql`
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

- **Conexión a Supabase vía env vars**: `spring.datasource.url/username/password` en
  `application.properties` leen `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` (setear en Render
  y en local). `DB_URL` es una URL JDBC (`jdbc:postgresql://...`), no la que da Supabase
  para clientes tipo `psql`.
- **`spring.jpa.hibernate.ddl-auto=update`**: Hibernate crea/ajusta las tablas solas a
  partir de las entidades. Cómodo para este proyecto chico, pero implica que un cambio de
  entidad puede alterar el esquema en producción sin pasar por una migración explícita —
  si esto crece, considerar Flyway/Liquibase o pasar a `validate`.
- **Auto-ping**: `AutoPingTask` golpea `/api/ping` cada 10 min contra la URL fija de
  Render (`https://mishone-backend.onrender.com`). `PingController` hace además una query
  trivial (`ProyectoRepository.count()`) para que el proyecto de Supabase (plan gratuito)
  no se pause por inactividad — no quitar esa query sin poner otro mecanismo de keep-alive
  para la base.
- **CORS**: orígenes permitidos hardcodeados en `CorsConfig`
  (`localhost:4200` y el dominio de Vercel del front). Actualizar ahí si cambia el
  dominio del frontend.
- Ya no hay archivo `.db` local ni respaldo a S3 (se eliminó todo ese circuito:
  `SQLiteS3Initializer`, `S3UploadService`, `SQLiteS3Service`, `DailyBackupTask`,
  `BackupController`) — la persistencia la maneja Supabase directamente.

## Convenciones del código existente

- Comentarios y mensajes de log/respuesta en español, con emojis en logs (✅/❌/⏫/🧪).
  Seguir el mismo estilo al tocar ese código.
- Controllers delgados: validan poco, delegan todo a la capa `service`.
- Repos son `JpaRepository` simples; sin capa de mapeo DTO (las entidades se exponen
  directo en los controllers).
