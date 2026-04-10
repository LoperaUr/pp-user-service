# User Service Microservicio

Microservicio encargado de autenticacion y gestion de usuarios (owner, employee y client).

## Documentacion consolidada

- Requisitos compartidos: `..\requirements.md`
- Coleccion Postman unica: `..\postman_collections\plazoleta-pragma.postman_collection.json`

## Estado funcional vs requirements

| HU | Endpoint / Regla | Estado | Evidencia en el repo |
|---|---|---|---|
| #1 | Crear propietario (ADMIN) | Implementada | `POST /users/owner` en `UserController` |
| #5 | Autenticacion y autorizacion por rol | Parcial | `POST /auth/login` en `AuthController` y `@PreAuthorize` en endpoints de `UserController` |
| #6 | Crear empleado (OWNER) | Implementada | `POST /users/employee` en `UserController` |
| #8 | Crear cliente | Implementada | `POST /users/client` en `UserController` |
| Soporte interno | Obtener usuario por id | Implementada | `GET /users/{id}` en `UserController` |

### Alcance actual

- Este repo cubre principalmente HU de identidad y acceso de usuarios.
- Las HU de restaurantes, platos, pedidos y reportes viven en `pp-food-court-service`.

## Guia para construir y desplegar la imagen Docker en AWS ECR

### 1. Construir la imagen Docker

```sh
docker build -t nombre-imagen:tag .
```

### 2. Crear un repositorio en ECR (si no existe)

```sh
aws ecr create-repository --repository-name nombre-repositorio
```

### 3. Autenticarse en ECR

```sh
aws ecr get-login-password --region tu-region | docker login --username AWS --password-stdin <tu-id-cuenta>.dkr.ecr.<tu-region>.amazonaws.com
```

### 4. Etiquetar la imagen

```sh
docker tag nombre-imagen:tag <tu-id-cuenta>.dkr.ecr.<tu-region>.amazonaws.com/nombre-repositorio:tag
```

### 5. Subir la imagen a ECR

```sh
docker push <tu-id-cuenta>.dkr.ecr.<tu-region>.amazonaws.com/nombre-repositorio:tag
```

> Reemplaza `nombre-imagen`, `tag`, `nombre-repositorio`, `tu-region` y `tu-id-cuenta` por los valores correspondientes.

