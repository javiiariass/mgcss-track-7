1. Desarrollas en rama feature/xxx
2. Haces PR a main con Conventional Commits
3. Merge a main
4. Cuando decides liberar:
   git tag vMAJOR.MINOR.PATCH
        o 
        git tag -a vMAJOR.MINOR.PATCH
   git push origin v1.0.2  (por ejemplo)
5. El workflow release.yml se dispara automaticamente:
   - Quality Gate (tests + Sonar) -> si falla, se detiene
   - Build .jar
   - Build + push imagen Docker a Docker Hub
   - Publica Release en GitHub con .jar adjunto