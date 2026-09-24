# Air Traffic Simulation 🛫

## 📌 Overview
A Java application simulating air traffic management system developed as a school project for the **Object-Oriented Programming 2** course at the Faculty of Electrical Engineering, University of Belgrade.

This project demonstrates the application of OOP principles through a complex system that enables airport and flight data entry, map visualization, and real-time flight simulation.

## 🎯 Implementation Phases

### ✅ **Phase A** - Data Input and Display
- Airport data entry (name, code, coordinates)
- Flight data entry (departure, destination, departure time, duration)
- Save/load data from CSV and JSON files
- Data validation with clear error messages
- Auto-logout after 60 seconds of inactivity

### 🔄 **Phase B** - Map Visualization
- Display airports as squares on a 2D map
- Click airport to select (blinking red highlight)
- Filter airports with checkboxes
- Control visibility of objects on the map

### 🚀 **Phase C** - Flight Simulation
- Dynamic flight simulation with time scaling (1s = 10min)
- Display aircraft as blue circles moving across the map
- Simulation controls (play, pause, reset)
- Queue rule: max 1 aircraft can depart every 10-minute slot from each airport

## 🛠️ Technologies
- **Language:** Java
- **GUI Framework:** AWT
- **File Formats:** CSV, JSON parsing
- **Architecture:** OOP with clear separation (Model, Logic, View)

## 📋 Requirements
- Java JDK 8 or newer
- Compiler: `javac`

## 🚀 Running the Program

To run the program, compile and execute the Java files from the `src` directory.

## 📁 Project Structure

```
AirTrafficSimulation/
├── src/                        # Java source code
├── README.md                   # Documentation
├── .gitignore                  # Git configuration
└── OOP2_2026_projekat.pdf      # Project assignment and requirements
```

## 👤 Author
**Sofija Spahic** - 2nd Year Student  
Faculty of Electrical Engineering, University of Belgrade

## 📚 Reference
Detailed project requirements available in: [OOP2_2026_projekat.pdf](./OOP2_2026_projekat.pdf)

## 📝 Notes
- Code is carefully organized with clear comments
- All errors are caught and displayed to the user in an understandable manner
- System is modular and easily extensible for future modifications
