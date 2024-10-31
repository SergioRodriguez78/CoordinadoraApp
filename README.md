# CoordinadoraApp 🚚

Este proyecto es una aplicación Android desarrollada en **Jetpack Compose** siguiendo el patrón **MVVM**. 
La aplicación integra múltiples funcionalidades avanzadas, como el uso de Google Maps, Firestore, Firebase Distribution, y manejo de documentos PDF con Volley. 
Este repositorio también incluye configuraciones de **GitHub Actions** para pruebas automatizadas y despliegue continuo.

---

## 📹 Demostración de Funcionamiento

### En API 24

[![API 24 Demo](https://img.youtube.com/vi/QV7C0uvM5nM/0.jpg)](https://youtube.com/shorts/QV7C0uvM5nM)

### En API 33

[![API 33 Demo](https://img.youtube.com/vi/LL_dFNyqzHA/0.jpg)](https://youtube.com/shorts/LL_dFNyqzHA)

---

## 🚀 Funcionalidades Principales

- **Google Maps**: Integra mapas interactivos y marcadores.
- **Firestore**: Utiliza Firestore en la nube para el almacenamiento de datos.
- **Descarga de PDF**: Los archivos PDF se descargan mediante la biblioteca Volley y se guardan en la carpeta de descargas del dispositivo.
- **GitHub Actions**: Configuración de un flujo de trabajo para ejecutar pruebas automatizadas y desplegar versiones de la app en Firebase Distribution.
- **Firebase Distribution**: Proceso automatizado de distribución de versiones para testers.

---

## 🔄 Flujo de Trabajo de GitHub Actions

Este proyecto incluye un flujo de trabajo de GitHub Actions que realiza los siguientes pasos:

1. Ejecuta pruebas unitarias y de instrumentación.
2. Si las pruebas pasan, genera un archivo APK y lo despliega automáticamente en Firebase App Distribution.
3. Envía notificaciones de las nuevas versiones a los testers de Firebase.

Para más detalles, revisa el archivo `.github/workflows/android-build.yml`.

---

## 📑 Librerías Utilizadas

- **Jetpack Compose**: Para construir la interfaz de usuario de forma declarativa.
- **Firestore**: Para almacenamiento de datos en la nube.
- **Firebase Distribution**: Para distribuir versiones a testers.
- **Google Maps SDK**: Para mostrar mapas interactivos dentro de la app.
- **Volley**: Para manejar solicitudes HTTP y descargar archivos PDF.

---
