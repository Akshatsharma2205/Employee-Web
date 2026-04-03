# UrbanPulse 2026: Comprehensive Project Documentation & Analysis

> **Note**: This document provides a complete architectural analysis and detailed functional breakdown of the UrbanPulse ecosystem, describing how the React-based frontend interacts with its dual-backend Spring Boot microservices.

## 1. Executive Summary
**UrbanPulse** is an AI-powered architectural decision simulator designed for civic policymakers. It generates explicit real-time models showcasing how urban policies impact traffic, economic health, ecology, and citizen sentiment. By leveraging Google's Gemini 2.5 Flash and Gemini 3.1 Flash LLMs dynamically, UrbanPulse moves beyond static algorithms, replacing them with live predictive intelligence.

The project employs a decoupled architecture:
1. **urbanpulse-frontend**: A rich, interactive React application using Vite, Recharts, and custom animations.
2. **UrbanPulse Backend**: A Spring Boot service dedicated to single-policy impacts, A/B comparisons, and live multi-stakeholder debates.
3. **UrbanPulse-Backend (Master Plan Engine)**: A specialized Spring Boot service for multi-phase, 10-year cumulative trajectory forecasting and City DNA Intelligence.

---

## 2. Frontend Architecture (`urbanpulse-frontend`)

The frontend is a single-page React application that serves as the graphical interface (CLI/Dashboard hybrid) for the urban intelligence. 

### Core Views & Components

#### Landing Page (`LandingPage.jsx`)
The immersive entry point for the user.
- **Visuals**: Features a real-time reactive HTML5 Canvas particle network and animated CRT scanlines.
- **Features**: Educates the user on the 4-step process and capabilities.
- **Routing**: Links to both the active Simulation terminal (`App.jsx`) and the newly designed `DNA Archive`.

#### Simulation Wizard (`SimulationWizard.jsx`)
The isolated policy testing environment.
- **Phase 1 (Setup)**: Uses `CityLoader` to define the baseline city metrics (population, generic rules).
- **Phase 2 (Canvas)**: Allows selection of a single radical policy (e.g., Ban Cars, Hyper-Density).
- **Phase 3 (Debate)**: Connects to `/api/debate` to stream a live, character-by-character debate between a Business Owner, Citizen, and Environmentalist protesting or praising the policy.
- **Phase 4 (Results)**: Visualizes the 10-year trajectory of the policy across Traffic, Economy, and Ecology using Recharts line graphs.

#### Master Plan Mode (`MasterPlanMode.jsx`)
The advanced, multi-phase compounding simulation environment.
- **Purpose**: Instead of testing one policy, the user builds a multi-year chronological plan.
- **Functionality**: Re-routes payloads to `/api/masterplan/simulate`. Sends an array of actions occurring over a decade.
- **Data Rendering**: The impact from Phase 1 acts as the baseline for Phase 2. The results present a massive compound trajectory.

#### City DNA Database (`CityDnaDatabase.jsx`)
The Intelligence Archive cache.
- **Purpose**: Exposes the cached macro-profiles mapped out by the AI.
- **Functionality**: Pulls from `/api/masterplan/city-profile/all`. Showcases a massive standalone modal with an interactive structural radar chart detailing Density class, Transit quality, and Policy Friction vectors.

---

## 3. Backend #1: Core Simulation Engine (`UrbanPulse`)

The original generic backend designed around `SimulationController`. 

### Responsibilities
- **Single-Event Simulation**: Handling isolated testing.
- **Agentic Debates**: Emulating local town hall dynamics.
- **A/B Comparisons**: Taking two disparate policies and returning a mathematically sound winner based on risk parameters.

### Key Endpoints
| Endpoint | Method | Purpose |
|----------|--------|---------|
| `/api/simulate` | POST | Returns a 10-year trajectory and textual insights for a given policy in a given city. |
| `/api/compare` | POST | Judges two policies and outputs an AI decision on which yields higher localized ROI. |
| `/api/debate` | POST | Generates the multi-agent debate scripts (Stakeholder opinions). |
| `/api/save` & `/scenario/{id}` | POST/GET | Persists simulation contexts into an in-memory map for quick retrieval. |

---

## 4. Backend #2: Master Plan Engine (`UrbanPulse-Backend`)

A highly specialized Spring Boot application specifically focused on compounding metrics and deep character profiling. Instead of simple inputs, it handles chronological arrays of actions.

### Key Features
- **Deterministic Math Rendering**: The backend calculates impacts cumulatively. If Economy grows by 10% in Year 1, Year 3's policy calculates its impact off the *new* 110% value, not the original 100%.
- **City Profile Caching**: Generates a rigorous background lore and numerical topology (Density Class, Geo Risk) of a city before simulation even begins.
- **Environment Agnostic**: Binds to cloud provider `$PORT` variables natively and has explicit CORS mappings to the Vercel deployed frontend.

### Key Endpoints
| Endpoint | Method | Purpose |
|----------|--------|---------|
| `/api/masterplan/simulate` | POST | Accepts a list of phase executions and returns a chronological Compound Master Plan metric map. |
| `/api/masterplan/city-profile/{city}`| GET | Contacts Gemini AI to return a comprehensive structural dossier of the requested city (creates baselines). |
| `/api/masterplan/city-profile/all` | GET | Returns the full `ConcurrentHashMap` history of all invoked city profiles during the active session. | 

---

## 5. Data Flow & AI Orchestration (How It Works)

1. **User Execution**: The user selects "Tokyo" and inputs the "Build Highway" policy for 2026 and "Add Metro" for 2029 via `MasterPlanMode.jsx`.
2. **DNA Orchestration**: The frontend sends a GET request to `/api/masterplan/city-profile/Tokyo`. 
3. **AI Injection**: The Spring Boot backend contacts Gemini, generating a weighted JSON map of Tokyo's infrastructure, assigning it values (e.g., Transit: 90/100, Economy: 85/100). This sets the *True Baseline*.
4. **Simulation Processing**: The frontend POSTs the phase list to the backend. The backend prompts Gemini with the True Baseline + The Policies. Gemini returns the exact metric deltas (e.g. Economy: +5).
5. **Frontend Visualization**: The React suite receives the data map and populates Recharts graphs, timelines, and UI modals to show the 10-year timeline visually.

## 6. Technical Stack Summary
- **Client**: React 18, Vite, Recharts, standard layout CSS. Hosted on Vercel.
- **Server**: Spring Boot 3, Java 17, `RestTemplate` for Google Gemini endpoints. Hosted on Render/Railway.
- **Database**: Ephemeral session caching (`ConcurrentHashMap`). No persistent disk DB required, ensuring lightning-fast response times.
