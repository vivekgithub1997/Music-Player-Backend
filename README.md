Music Player  – Spring Boot Backend

A complete Music Management backend built using Spring Boot, supporting:

- User Management
- Playlist Management
- Song Management
- Playback (Play, Pause, Resume, Stop)
- Swagger API Documentation

---------------------------------------------------
TECH STACK
---------------------------------------------------
Backend Framework: Spring Boot

Security: Simple role-based (ADMIN / USER)

Database: MySQL

ORM: Spring Data JPA

Build Tool: Maven

API Docs: Swagger / Springdoc OpenAPI

Java Version: 17

---------------------------------------------------
INSTALLATION & SETUP GUIDE
---------------------------------------------------

1. Clone the Repository
--------------------------------
git clone https://github.com/vivekgithub1997/Music-Player-Backend

2. Install Java & Maven
--------------------------------
Make sure you have Java 17+ and Maven installed.
Check versions:
java -version
mvn -version

3. Configure MySQL Database
--------------------------------
CREATE DATABASE mymusicdb;

4. Update application.properties
--------------------------------
spring.datasource.url=jdbc:mysql://localhost:3306/mymusicdb

spring.datasource.username=root
spring.datasource.password=yourPassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

springdoc.swagger-ui.path=/swagger-ui

5. Build the project
--------------------------------
mvn clean install

6. Run the application
--------------------------------
mvn spring-boot:run

7. Open Swagger UI
--------------------------------
http://localhost:8080/swagger-ui

<img width="1687" height="925" alt="resume " src="https://github.com/user-attachments/assets/b55e64ea-6959-4227-be97-00703ef7f3d9" />
<img width="1332" height="741" alt="db1" src="https://github.com/user-attachments/assets/b3fc252f-89c4-409f-be47-b3727c7c54b0" />
<img width="1587" height="927" alt="create play list" src="https://github.com/user-attachments/assets/4937b107-ad41-49b0-8f92-17aff37d2c0b" />
<img width="1822" height="927" alt="1" src="https://github.com/user-attachments/assets/ea5e5a93-4649-49b4-b993-a333ccd23d55" />
<img width="1593" height="927" alt="song added to playlist" src="https://github.com/user-attachments/assets/63e37ef8-2099-44ed-8b7d-4a275cd942ec" />


---------------------------------------------------
API OVERVIEW
---------------------------------------------------

USER APIs
--------------------------------
POST /api/users/register   → Register new user
POST /api/users/login      → Login and get userId

SONG APIs
--------------------------------
POST /api/songs/add?userId=      → Add song (ADMIN only)
GET  /api/songs/list             → List all songs
GET  /api/songs/{id}             → Get song by id
GET  /api/songs/search?q=        → Search song

PLAYLIST APIs
--------------------------------
POST /api/playlists/create?userId=       → Create playlist
GET  /api/playlists?userId=              → Get playlists of user
POST /api/playlists/{playlistId}/add/{songId}?userId=     → Add song to playlist
POST /api/playlists/{playlistId}/remove/{songId}?userId=  → Remove song from playlist

PLAYBACK APIs
--------------------------------
POST /api/playback/play/{songId}     → Start playback
POST /api/playback/pause             → Pause playback
POST /api/playback/resume            → Resume
POST /api/playback/stop              → Stop
GET  /api/playback/current           → Check current playback

---------------------------------------------------
SAMPLE REQUESTS
---------------------------------------------------

Add Song (ADMIN only)
--------------------------------
POST /api/songs/add?userId=1
{
  "title": "My Song",
  "artist": "Arijit",
  "genre": "Romantic"
}

Create Playlist
--------------------------------
POST /api/playlists/create?userId=2
{
  "name": "My Favorites"
}

Add Song to Playlist
--------------------------------
POST /api/playlists/10/add/3?userId=2

Play a Song
--------------------------------
POST /api/playback/play/5
Header: userId: 2

Pull requests are welcome. Open an issue for discussion.

