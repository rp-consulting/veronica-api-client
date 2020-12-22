# Ejemplos de consumo de la API de Veronica
En este proyecto encontrarás ejemplos de implementaciones con las tecnologías más populares del mercado para el consumo y utilización de la API REST de Verónica para la emisión de comprobantes electrónicos en Ecuador.
## Ambiente de Sandbox

### Documentación

[https://veronica-api-sbox.rolandopalermo.com/swagger-ui.html](https://veronica-api-sbox.rolandopalermo.com/swagger-ui.html)

### Obtención de token

| Attribute          | Value                 |
|--------------------|-----------------------|
| username           | Veronica support team |
| password           | Veronica support team |
| client_credentials | Veronica support team |

```bash
curl --location --request POST 'https://veronica-api-sbox.rolandopalermo.com/api/v1.0/oauth/token?username=${username}&password=${password}&grant_type=password' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic ${client_credentials}' \
--data-raw ''
```

### Videotutoriales
- [Carga de certificados digitales y creación de plantillas](https://www.youtube.com/watch?v=3nSb7Lr15Z8&t=15s)