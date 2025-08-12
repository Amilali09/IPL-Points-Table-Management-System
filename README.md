# IPL 2025 Points Table Simulation

This Java project simulates the **Indian Premier League (IPL) 2025** season, generating match results, updating the points table, and calculating **Net Run Rate (NRR)** accurately.

---

## 📂 Project Overview

The program:
- Generates fixtures for all IPL 2025 matches.
- Simulates results using the `addM` function.
- Updates team standings after each match.
- Calculates **Points**, **Wins**, **Losses**, and **Net Run Rate (NRR)**.

---

## ⚙️ Key Functions

### 1. `addM(String team1, String team2, int runs1, int overs1, int runs2, int overs2)`
This function **adds a match result** to the tournament records.

**Steps:**
1. Updates total runs and overs for both teams.
2. Awards **2 points** for a win, **0 points** for a loss.
3. Updates **Net Run Rate (NRR)** using the formula:

```
NRR = (Total Runs Scored / Total Overs Faced) - (Total Runs Conceded / Total Overs Bowled)
```

4. Records match result in the fixture list.

---

### 2. `displayPointsTable()`
- Sorts teams based on **Points**, then **NRR**.
- Displays the updated standings after each match.

---

### 3. `main()`
- Creates the fixtures list.
- Calls `addM()` for each match.
- Displays the table after all matches.

---

## 📸 Output

Screenshots of the real points table output are available in the `screenshots/` folder.

---

## 📦 Installation & Run

```bash
# Clone this repository
git clone https://github.com/Amilali09/IPL2025-PointsTable.git

# Compile the Java program
javac Main.java

# Run the simulation
java Main
```

---

## 🧑‍💻 Author
- **Name:** Syed Amil Ali
- **GitHub:** [Amilali09](https://github.com/Amilali09)
- **Email:** aamilakil@gmail.com

---

## 📜 License
This project is licensed under the MIT License.


## Function Arguments

### `addM(team1, team2, team1Runs, team2Runs, team1Overs, team2Overs)`
Adds a match result to the simulation.

**Arguments:**
- `team1` *(String)* → Name of the first team.
- `team2` *(String)* → Name of the second team.
- `team1Runs` *(int)* → Runs scored by team1.
- `team2Runs` *(int)* → Runs scored by team2.
- `team1Overs` *(float)* → Overs faced by team1 (e.g., 20.0 or 19.3).
- `team2Overs` *(float)* → Overs faced by team2.

This function updates the points table, calculates the winner, and adjusts the Net Run Rate (NRR) for both teams.

---

### `displayTable()`
Displays the updated points table after matches are added.

---

### `main(String[] args)`
The entry point of the program where fixtures are generated and matches are simulated.

**Arguments:**
- `args` *(String[])* → Can be used to pass custom fixture or match details if running from command line.
