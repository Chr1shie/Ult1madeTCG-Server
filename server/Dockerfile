# Läuft laut CONCEPT.md (Phase 1e) als Docker-Container - Build-Kontext ist
# das Repo-Root (braucht gradlew + alle Module), z.B.:
#   docker build -f server/Dockerfile -t ult1made-tcg-server .
#   docker run -p 8080:8080 ult1made-tcg-server
FROM eclipse-temurin:11-jdk AS build
WORKDIR /workspace
COPY . .
# BuildKit-Cache-Mount (17.08., Nutzer-Beschwerde "Rebuild dauert echt
# lange"): /root/.gradle überlebt zwischen Builds - Gradle-Distribution,
# alle Abhängigkeiten UND der lokale Build-Cache (org.gradle.caching=true in
# gradle.properties) bleiben erhalten. Unveränderte Module werden dadurch zu
# Cache-Treffern statt Voll-Kompilaten; vorher lud jeder Build die komplette
# Welt neu herunter und kompilierte alles von Null. Zusammen mit der neuen
# .dockerignore (Build-Kontext ~3,8 GB -> wenige MB) der Kern des Speedups.
# Braucht BuildKit - bei jedem halbwegs aktuellen docker compose Standard.
RUN --mount=type=cache,target=/root/.gradle ./gradlew :server:installDist --no-daemon

FROM eclipse-temurin:11-jre
WORKDIR /app
COPY --from=build /workspace/server/build/install/server /app
EXPOSE 8080
ENTRYPOINT ["/app/bin/server"]
