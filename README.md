# MyAgent

MyAgent is an intelligent multi-agent system built with Spring Boot that leverages LLM (Large Language Model) capabilities, web search, OCR (Optical Character Recognition), and Redis caching to answer user queries. It can process both text and image-based queries, extract relevant information, and provide AI-generated responses.

## Features

- **AI-Powered Responses:** Uses Google Gemini (via LangChain4j) to generate answers to user queries.
- **Web Search Integration:** Automatically fetches and summarizes web results for queries that require up-to-date information.
- **Image Text Extraction:** Accepts image URLs, extracts text using an external OCR API, and uses the extracted text as part of the query.
- **Caching with Redis:** Stores query-response pairs in Redis for fast retrieval and reduced API usage.
- **REST API:** Exposes endpoints for user interaction.
- **Swagger UI:** Provides interactive API documentation.

## Architecture Overview

- **UserQueryController:** Handles incoming user requests, orchestrates image download, text extraction, and query processing.
- **AgentManager:** Central orchestrator that decides whether to use cached responses, perform web search, or call the AI model.
- **MemoryAgent:** Manages Redis caching for queries and responses.
- **WebSearchAgent:** Integrates with Google Custom Search API to fetch web results.
- **AiResponseAgent:** Connects to Google Gemini via LangChain4j for LLM-powered responses.
- **ImageExtractionAPI:** Calls an external OCR API to extract text from images.

## How It Works

1. **User submits a query** (optionally with an image URL) via the `/user/ask` endpoint.
2. If an image is provided, the system downloads it and extracts text using OCR.
3. The extracted text (if any) is combined with the user's query.
4. The system checks Redis for a cached response.
5. If not cached, it determines if the query needs a web search (e.g., contains "latest", "news", etc.).
6. The processed query is sent to the Gemini LLM for a response.
7. The response is cached in Redis and returned to the user.

## API Endpoints

### `POST /user/ask`

- **Request Body:**  
  ```
  {
    "query": "What is the latest news about AI?",
    "image": "https://example.com/image.png" // optional
  }
  ```
- **Response:**  
  Returns the AI-generated answer, possibly including information extracted from the image and/or web search.

### `GET /`

- Redirects to Swagger UI for API exploration.

## Configuration

Set the following environment variables (or define them in your application properties/yaml):

- `GEMINI_API_KEY` - API key for Google Gemini.
- `WEB_SEARCH_API_KEY` - API key for Google Custom Search.
- `WEB_SEARCH_ID` - Search engine ID for Google Custom Search.
- `OCR_USERNAME` - Username for the OCR API.
- `OCR_LICENCE` - License code for the OCR API.
- `OCR_URL` - Endpoint URL for the OCR API.
- `REDIS_HOST` - Redis server hostname.
- `REDIS_PORT` - Redis server port.
- `REDIS_PASSWORD` - Redis server password.

## Running the Project

### Prerequisites

- Java 21+
- Maven
- Redis server running and accessible

### Build and Run

```sh
# Build the project
./mvnw clean package

# Run the application
java -jar target/MyAgent-0.0.1-SNAPSHOT.jar
```

Or use Docker:

```sh
docker build -t myagent .
docker run -e ... -p 8080:8080 myagent
```

## Technologies Used

- Spring Boot
- LangChain4j (Google Gemini integration)
- Google Custom Search API
- Redis (Jedis client)
- Lombok
- WebFlux (WebClient)
- Jackson (JSON processing)
- Swagger/OpenAPI

## Example Usage

- Ask a question:  
  `POST /user/ask` with `{ "query": "Summarize the latest AI trends" }`
- Extract text from an image and ask:  
  `POST /user/ask` with `{ "image": "https://example.com/text-image.png" }`
- Combine both:  
  `POST /user/ask` with `{ "query": "What does this say?", "image": "https://example.com/text-image.png" }`

## License

This project is licensed under the Apache License 2.0.

---

**Note:**  
You must provide valid API keys and configure Redis for the application.
