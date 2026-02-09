# Contributing to emSigner Integration

First off, thank you for considering contributing to this project! It's people like you that make this integration better for everyone.

## Code of Conduct

This project and everyone participating in it is governed by our commitment to creating a welcoming and inclusive environment. By participating, you are expected to uphold this standard.

## How Can I Contribute?

### Reporting Bugs

Before creating bug reports, please check the existing issues to avoid duplicates. When you create a bug report, include as many details as possible:

- **Use a clear and descriptive title**
- **Describe the exact steps to reproduce the problem**
- **Provide specific examples**
- **Describe the behavior you observed and what you expected**
- **Include screenshots if relevant**
- **Note your environment** (OS, Java version, Spring Boot version)

### Suggesting Enhancements

Enhancement suggestions are tracked as GitHub issues. When creating an enhancement suggestion:

- **Use a clear and descriptive title**
- **Provide a detailed description of the proposed feature**
- **Explain why this enhancement would be useful**
- **List examples of how it would be used**

### Pull Requests

1. **Fork the repository** and create your branch from `main`
2. **Make your changes** with clear, concise commits
3. **Add tests** if you've added code that should be tested
4. **Ensure the test suite passes**
5. **Update documentation** as needed
6. **Follow the code style** of the project

## Development Process

### Setting Up Your Development Environment

1. Fork and clone the repository
```bash
git clone https://github.com/your-username/emsigner-integration.git
cd emsigner-integration
```

2. Install dependencies
```bash
mvn clean install
```

3. Configure your `application.properties`
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

4. Run the application
```bash
mvn spring-boot:run
```

### Coding Standards

- **Java Style**: Follow standard Java conventions
- **Naming**: Use descriptive variable and method names
- **Comments**: Add comments for complex logic
- **Lombok**: Use Lombok annotations where appropriate to reduce boilerplate
- **Error Handling**: Always handle exceptions appropriately
- **Logging**: Use appropriate log levels (DEBUG, INFO, WARN, ERROR)

### Commit Messages

- Use present tense ("Add feature" not "Added feature")
- Use imperative mood ("Move cursor to..." not "Moves cursor to...")
- Limit the first line to 72 characters or less
- Reference issues and pull requests after the first line

Example:
```
Add support for batch PDF signing

- Implement batch processing endpoint
- Add queue management for multiple documents
- Update documentation with batch usage examples

Closes #123
```

### Branch Naming

- `feature/description` - for new features
- `fix/description` - for bug fixes
- `docs/description` - for documentation updates
- `refactor/description` - for code refactoring

### Testing

- Write unit tests for new functionality
- Ensure all tests pass before submitting PR
- Aim for meaningful test coverage

```bash
mvn test
```

### Documentation

- Update README.md if you change functionality
- Add JavaDoc comments for public methods
- Update API documentation for endpoint changes
- Include examples for new features

## Security Issues

**Do not** open public issues for security vulnerabilities. Instead, email the maintainers directly with:
- Description of the vulnerability
- Steps to reproduce
- Potential impact
- Suggested fix (if any)

## Questions?

Feel free to open an issue with the `question` label if you need help or clarification.

## Recognition

Contributors will be recognized in the project's README. We appreciate all contributions, big and small!

## License

By contributing, you agree that your contributions will be licensed under the MIT License.

Thank you for contributing! 🎉
