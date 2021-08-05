# oauth2
Spring Cloud with Oauth2
Contains ConfigServer, AuthServer, ZuulProxy, Eurka Service Discovery, AdminServer and multiple Services.

# Starting
* Start the ConfigServer first
* Start all other services and open the browser `http://localhost:8000` for playing around.

# Endpoints
* UI: `http://localhost:8000`
* Metrics: `http://localhost:8080`
* Eureka: `http://localhost:8761`
* ConfigServer: `http://localhost:8888/{service-name}.[json|yml]` e.g. `http://localhost:8888/account-service.yml`


