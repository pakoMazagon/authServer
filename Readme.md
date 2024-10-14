This project is a example of OIDC Authorization Server

# Getting Started

1 - On the first place, you need execute docker-compose Database and docker-compose liquibase:
    
    - docker compose -f docker-compose.yml up -d
    - docker compose -f docker-compose.liquibase.yml --env-file ./.env.local up

[ExpFlujoOIDC](ExpFlujoOIDC.png)

2 - On the other hand, the users can be saved in Database. For this, you can
      consumes de endpoint POST /auth/create that will be created through openAPI generator plugin.
    This endpoint is public and is no login is required (added this in defaultSecurityFilterChain Bean)
   
   Due to the user is saved in BBDD, the bean UserDetailsService is not in config, and the UC implements
   this interface.


