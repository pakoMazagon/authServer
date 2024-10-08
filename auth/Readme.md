This project is a example of OIDC Authorization Server

# Getting Started

On the first place, you need execute docker-compose Database and docker-compose liquibase:
    
    - docker compose -f docker-compose.yml up -d
    - docker compose -f docker-compose.liquibase.yml --env-file ./.env.local up
