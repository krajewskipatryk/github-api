# Github Integration API

This project is a simple RESTful API written in Java using Spring Boot, allowing users to retrieve a list of GitHub repositories without forks.

## Installation and Running

1. Clone the repository to your local system:

    - [HTTPS](https://github.com/krajewskipatryk/github-api.git)
    - [SSH](git@github.com:krajewskipatryk/github-api.git)
    - [GithubCLI](gh repo clone krajewskipatryk/github-api.git)

2. Navigate to the project directory:

    cd github-api

3. Run the application using Maven:

    mvn spring-boot:run

The application will be launched on the default port 8080.

## API Usage

### Retrieving a List of Repositories

An HTTP GET request to the endpoint `/api/v1/github/{username}/repos` returns a list of GitHub repositories for the specified user without forks.

Example:

GET http://localhost:8080/api/v1/github/krajewskipatryk/repos

Response:
```
[
    {
        "name": "livechat",
        "branches": [
            {
                "branchName": "master",
                "sha": "a73f522f5c18a9192f9db174c95d43c212e5c33f"
            }
        ]
    }
]
```

### Supported Parameters

- `{username}`: The GitHub username for which you want to retrieve repositories.

## System Requirements

- Java 21
- Maven

## Support and Contact

For questions or issues, please contact:

- Author: Patryk Krajewski
- Email: krajewskipatryk@onet.pl