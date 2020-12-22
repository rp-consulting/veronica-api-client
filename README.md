# Veronica Rest API client examples

## Sandbox
- API Documentation

[https://veronica-api-sbox.rolandopalermo.com/swagger-ui.html](https://veronica-api-sbox.rolandopalermo.com/swagger-ui.html)

- API Authentication

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