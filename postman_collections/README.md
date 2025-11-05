Colección Postman - User Service

Instrucciones:

1) Importar la colección:
   - En Postman: File > Import > Upload Files > seleccionar `UserService.postman_collection.json` ubicado en `postman_collections/`.

2) Variables de entorno (opcional):
   - La colección usa la variable `baseUrl` con valor por defecto `http://localhost:8081` (lee `application-dev.yml`).
   - Otra variable útil: `userId` (valor por defecto `1`) para la petición GET.

3) Endpoints incluidos:
   - POST {{baseUrl}}/users/owner   -> Crear owner (body JSON: name, lastName, identificationNumber, phoneNumber, birthDate (YYYY-MM-DD), email, password)
   - POST {{baseUrl}}/users/employee -> Crear employee (mismo body que owner)
   - POST {{baseUrl}}/users/client -> Crear client (mismo body que owner)
   - GET  {{baseUrl}}/users/{{userId}} -> Obtener usuario por id

4) Encabezados:
   - Las peticiones POST incluyen `Content-Type: application/json`.

5) Consejos:
   - Asegúrate de que el microservicio esté corriendo en el puerto indicado (por defecto 8081 en `application-dev.yml`).
   - Si usas Docker/Compose o una base de datos, confirma que las dependencias están disponibles antes de crear usuarios.

Si quieres, puedo:
 - Añadir tests de Postman (scripts) para validar respuestas.
 - Generar un entorno de Postman (.postman_environment.json) listo para importar con `baseUrl` configurado.
 - Añadir ejemplos de respuestas según el contrato DTO.
{
  "info": {
    "_postman_id": "b1f6e2e3-0000-4a1b-9f0a-123456789abc",
    "name": "User Service",
    "description": "Colección Postman para el microservicio User Service (endpoints: create owner/employee/client, get user by id).",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "variable": [
    {
      "key": "baseUrl",
      "value": "http://localhost:8081",
      "type": "string"
    },
    {
      "key": "userId",
      "value": "1",
      "type": "string"
    }
  ],
  "item": [
    {
      "name": "Create Owner",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"name\": \"Carlos\",\n  \"lastName\": \"Pérez\",\n  \"identificationNumber\": \"12345678\",\n  \"phoneNumber\": \"3001234567\",\n  \"birthDate\": \"1990-05-20\",\n  \"email\": \"carlos.perez@example.com\",\n  \"password\": \"Secret123!\"\n}"
        },
        "url": {
          "raw": "{{baseUrl}}/users/owner",
          "host": [
            "{{baseUrl}}"
          ],
          "path": [
            "users",
            "owner"
          ]
        }
      },
      "response": []
    },
    {
      "name": "Create Employee",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"name\": \"Ana\",\n  \"lastName\": \"Gómez\",\n  \"identificationNumber\": \"87654321\",\n  \"phoneNumber\": \"3117654321\",\n  \"birthDate\": \"1992-08-15\",\n  \"email\": \"ana.gomez@example.com\",\n  \"password\": \"Passw0rd!\"\n}"
        },
        "url": {
          "raw": "{{baseUrl}}/users/employee",
          "host": [
            "{{baseUrl}}"
          ],
          "path": [
            "users",
            "employee"
          ]
        }
      },
      "response": []
    },
    {
      "name": "Create Client",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"name\": \"María\",\n  \"lastName\": \"López\",\n  \"identificationNumber\": \"11223344\",\n  \"phoneNumber\": \"3209876543\",\n  \"birthDate\": \"1988-12-01\",\n  \"email\": \"maria.lopez@example.com\",\n  \"password\": \"ClientPass1#\"\n}"
        },
        "url": {
          "raw": "{{baseUrl}}/users/client",
          "host": [
            "{{baseUrl}}"
          ],
          "path": [
            "users",
            "client"
          ]
        }
      },
      "response": []
    },
    {
      "name": "Get User by Id",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "{{baseUrl}}/users/{{userId}}",
          "host": [
            "{{baseUrl}}"
          ],
          "path": [
            "users",
            "{{userId}}"
          ]
        }
      },
      "response": []
    }
  ]
}

