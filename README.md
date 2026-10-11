# Travel Reservation – Spring Boot + Angular

## Backend
Prérequis : Java 17, Maven, MySQL (user `root` / mot de passe `root`, modifiable dans `application.properties`).

    cd backend
    mvn spring-boot:run          # http://localhost:8080

Compte admin créé au démarrage : `admin@travel.com` / `admin123` (+ 3 voyages de démo).

## Frontend (Angular 17+)
    ng new frontend --standalone --routing=false --style=css --skip-git
    # puis copier le contenu de frontend/src/ de cette archive par-dessus (remplacer les fichiers)
    cd frontend && ng serve      # http://localhost:4200

## API
| Méthode | URL | Accès |
|---|---|---|
| POST | /api/auth/register | public |
| GET | /api/auth/me | connecté (sert de login, Basic Auth) |
| GET | /api/voyages?destination=&date= | public |
| POST/PUT/DELETE | /api/voyages[/{id}] | ADMIN |
| POST | /api/reservations | connecté |
| GET | /api/reservations/mes | connecté |
| GET | /api/reservations | ADMIN |
| DELETE | /api/reservations/{id} | propriétaire ou ADMIN (annule) |
