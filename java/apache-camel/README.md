# Ejemplos de consumo de la API de Veronica con Apache Camel
En este ejemplo se integra el uso de rutas de Apache Camel para cubrir todo el proceso de emisión de un comprobante, cargando el XML a Verónica API, envíandolo al SRI, autorizando la clave de acceso y finalmente notificando a los clientes mediante un correo electrónico.
```bash
mvn spring-boot:run
```

POST: http://localhost:8080/api/comprobantes/
```json
{
    "contenidoXmlCodificadoBase64":"",
    "notificar":true
}
```