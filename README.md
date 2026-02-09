# emSigner Integration - Spring Boot

A Spring Boot application that integrates with [emSigner](https://www.emsigner.com/) to provide electronic signature capabilities for PDF documents. This application provides REST APIs to handle PDF signing workflows with secure encryption and session management.

## Features

- **PDF Signing Integration**: Seamless integration with emSigner for electronic signatures
- **Secure Session Management**: AES-256 encryption for session keys and data transmission
- **REST API**: Clean RESTful endpoints for signature workflows
- **Callback Handling**: Support for success, failure, and cancellation callbacks
- **PDF Download**: Automatic handling and download of signed documents
- **RSA Encryption**: Public key encryption using X.509 certificates
- **SHA-256 Hashing**: Secure hash generation for data integrity

## Tech Stack

- **Java 17**
- **Spring Boot 3.4.0**
- **Maven**
- **Bouncy Castle** (for encryption)
- **Gson** (for JSON processing)
- **Lombok**

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- emSigner account and certificates
- X.509 certificate file (.cer) for encryption

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/emudhra-integration-sdk/emSignerGateway_SpringBoot.git
cd emSignerGateway_SpringBoot
```

### 2. Configure Application Properties

Copy `application.properties.example` to `application.properties`:

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

Update the following properties in `application.properties`:

```properties
server.port=9090

# Certificate file path (required for RSA encryption)
CERT_FILE=/path/to/your/certificate.cer

# Temporary file storage
TEMP_FILE=/path/to/temp/

# PDF file path (for testing)
PDF=/path/to/sample.pdf

# PFX certificate path (if needed)
PFX=/path/to/certificate.pfx

# Log folder
LOG_FOLDER=/path/to/logs/

# Response URL (your callback URL)
RESPONSE_URL=http://localhost:9090/api/success
```

### 3. Build the Project

```bash
mvn clean install
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:9090`

## API Endpoints

### POST `/api/getEmSignerParams`

Generate encrypted parameters for emSigner integration.

**Request Body:**
```json
{
  "referenceNumber": "unique-reference-id",
  "fileBase64": "base64-encoded-pdf-content",
  "fileName": "document.pdf"
  // Add other required fields based on DataEntity structure
}
```

**Response:**
```json
{
  "Parameter1": "encrypted-session-key",
  "Parameter2": "encrypted-json-data",
  "Parameter3": "encrypted-hash",
  "SessionKey": "base64-session-key",
  "Status": true
}
```

### POST `/api/success`

Callback endpoint for successful signature operations.

**Query Parameters:**
- `ReferenceNumber`: The reference number from the original request
- `ReturnStatus`: Status of the operation
- `Returnvalue`: Encrypted signed document
- `FileType`: Type of file (PDF)

### POST `/api/failed`

Callback endpoint for failed signature operations.

### POST `/api/canceled`

Callback endpoint for canceled signature operations.

### GET `/api/downloadpdf?ref={referenceNumber}`

Download the signed PDF document.

**Query Parameters:**
- `ref`: Reference number of the signed document

## Project Structure

```
demo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           ├── configurations/
│   │   │           │   └── PropertyInputs.java
│   │   │           ├── controllers/
│   │   │           │   ├── emSignerController.java
│   │   │           │   └── PageLoaders.java
│   │   │           ├── demo/
│   │   │           │   ├── DemoApplication.java
│   │   │           │   └── ServletInitializer.java
│   │   │           └── utils/
│   │   │               ├── CorsConfig.java
│   │   │               ├── DataEntity.java
│   │   │               ├── Utilities.java
│   │   │               └── WebConfig.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
└── pom.xml
```

## Security

- **Encryption**: Uses AES-256 for session key encryption and RSA for public key encryption
- **Hashing**: SHA-256 hashing for data integrity
- **Session Management**: Secure session key storage with reference numbers
- **CORS Configuration**: Configurable CORS settings

## Configuration

### CORS

CORS is configured in `CorsConfig.java`. Update allowed origins as needed:

```java
@CrossOrigin(origins = "http://localhost:4200")
```

### Session Storage

The application uses in-memory session storage (`ConcurrentHashMap`). For production, consider using:
- Redis
- Database-backed session storage
- Distributed cache

## Development

### Running Tests

```bash
mvn test
```

### Building for Production

```bash
mvn clean package
```

The built JAR will be available in the `target/` directory.

## Deployment

### As Standalone JAR

```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

### As WAR (Tomcat)

The application includes `ServletInitializer.java` for WAR deployment to external Tomcat servers.

```bash
mvn clean package
# Deploy target/demo-0.0.1-SNAPSHOT.war to Tomcat
```

## Troubleshooting

### Common Issues

1. **Certificate not found**: Ensure `CERT_FILE` path is correct and the certificate exists
2. **Port already in use**: Change `server.port` in `application.properties`
3. **Decryption errors**: Verify the session key matches between request and callback

### Debug Mode

Enable debug logging in `application.properties`:

```properties
logging.level.org.springframework.web=DEBUG
logging.level.com.example=DEBUG
```

## Contributing

Contributions are welcome! Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- [emSigner](https://www.emsigner.com/) for electronic signature services
- [Bouncy Castle](https://www.bouncycastle.org/) for cryptography support
- Spring Boot community

## Support

For issues and questions:
- Open an issue on GitHub
- Check existing issues for similar problems
- Refer to [emSigner documentation](https://www.emsigner.com/documentation)

## Roadmap

- [ ] Add Redis session storage
- [ ] Implement comprehensive test coverage
- [ ] Add Docker support
- [ ] Add API documentation with Swagger/OpenAPI
- [ ] Support for multiple file formats
- [ ] Batch signing capabilities
- [ ] Webhook retry mechanism

---

**Note**: This is an integration project for emSigner. You need a valid emSigner account and certificates to use this application.
