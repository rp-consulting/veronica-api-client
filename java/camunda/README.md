# Ejemplos de consumo de la API de Veronica con Camunda BPMN
En este ejemplo se integra el uso de Camunda BPMN para cubrir todo el proceso de emisión de un comprobante, cargando el XML a Verónica API, envíandolo al SRI, autorizando la clave de acceso y finalmente notificando a los clientes mediante un correo electrónico.
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

## Procesos Camunda
Este proceso se utiliza para realizar un llamado a un servicio RESTful.
<p align="center">
<img src="https://raw.githubusercontent.com/rp-consulting/veronica-api-client/main/java/camunda/static/proceso-llamar-restful.jpeg" width="400">
</p>

El proceso proceso-emitir-comprobante permite cubrir todo el flujo de emisión de un comprobante electrónico en Ecuador.
<p align="center">
<img src="https://raw.githubusercontent.com/rp-consulting/veronica-api-client/main/java/camunda/static/proceso-emitir-comprobante.jpeg" width="800">
</p>
