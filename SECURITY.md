# Security Policy

## Supported Versions

We release patches for security vulnerabilities for the following versions:

| Version | Supported          |
| ------- | ------------------ |
| 0.0.x   | :white_check_mark: |

## Reporting a Vulnerability

**Please do not report security vulnerabilities through public GitHub issues.**

If you discover a security vulnerability, please send an email to the maintainers with:

1. **Description** of the vulnerability
2. **Steps to reproduce** the issue
3. **Potential impact** of the vulnerability
4. **Suggested fix** (if you have one)

### What to expect

- We will acknowledge your email within 48 hours
- We will provide a more detailed response within 7 days
- We will work on a fix and keep you updated on progress
- Once fixed, we will release a security advisory

## Security Best Practices

### For Users

1. **Never commit sensitive data**:
   - Do not commit `application.properties` with real credentials
   - Use `application.properties.example` as a template
   - Keep certificates and keys out of version control

2. **Certificate Management**:
   - Store certificates securely
   - Use environment-specific certificates
   - Regularly rotate certificates

3. **Environment Variables**:
   - Use environment variables for sensitive configuration
   - Never hardcode credentials in code

4. **Network Security**:
   - Use HTTPS in production
   - Configure CORS appropriately
   - Implement rate limiting

5. **Session Management**:
   - Consider Redis or database for session storage in production
   - Implement session timeout
   - Clear sessions after use

### For Contributors

1. **Code Review**:
   - All code changes must be reviewed
   - Security-sensitive changes require extra scrutiny

2. **Dependencies**:
   - Keep dependencies up to date
   - Regularly run security audits: `mvn dependency-check:check`
   - Review dependency vulnerabilities

3. **Encryption**:
   - Use strong encryption algorithms
   - Never implement custom cryptography
   - Follow current security standards

4. **Input Validation**:
   - Validate all user inputs
   - Sanitize data before processing
   - Use parameterized queries

5. **Logging**:
   - Never log sensitive data (keys, passwords, session tokens)
   - Use appropriate log levels
   - Sanitize logs in production

## Known Security Considerations

### Session Storage
The application currently uses in-memory session storage, which is not suitable for production distributed environments. Consider:
- Redis for distributed session storage
- Database-backed sessions
- Token-based authentication

### File Storage
Signed PDFs are temporarily stored on the filesystem. Ensure:
- Appropriate file permissions
- Regular cleanup of temporary files
- Secure file storage location

### API Security
The API endpoints should be protected with:
- Authentication
- Authorization
- Rate limiting
- CSRF protection

## Security Updates

Security updates will be released as soon as possible after a vulnerability is confirmed and fixed. Users will be notified through:
- GitHub Security Advisories
- Release notes
- Email (for critical vulnerabilities)

## Compliance

This application handles sensitive documents and signatures. Users are responsible for ensuring compliance with:
- Local data protection regulations
- Industry-specific security standards
- Organizational security policies

## Security Tools

We recommend using the following tools:

```bash
# Check for dependency vulnerabilities
mvn dependency-check:check

# OWASP Dependency Check
mvn org.owasp:dependency-check-maven:check

# Find security issues
mvn spotbugs:check
```

## Credits

We thank the security researchers and contributors who help keep this project secure.

---

**Remember**: Security is a shared responsibility. Always follow security best practices when deploying and using this application.
