# Smart Note

A **smart console-based note-taking application** built with Java.

## Features
- Category & priority system (URGENT, WORK, PERSONAL, etc.)
- LRU Cache for fast access
- Auto-save with background thread
- Persistent storage (`notes.dat`)
- Thread-safe operations
- Configurable via `config.properties`

## Tech Stack
- Java 17+
- `java.time` for date handling
- `CopyOnWriteArrayList` for thread safety
- `ObjectOutputStream` for serialization

## How to Run
```bash
# Compile
javac -cp . -d out src/main/java/**/*.java

# Run
java -cp out Main