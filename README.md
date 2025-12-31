## Project Overview
This project is a simple 2D physics engine built in Java using Swing. It simulates the motion and interaction of multiple particles (balls) under forces such as gravity, collisions, and user interaction. The goal of this project is educational: to explore basic physics simulation concepts, real-time rendering, and interactive systems.

The project emphasizes understanding how physical behaviors can be approximated computationally rather than creating a physically perfect engine.

---

## Learning Objectives
Through this project, I aimed to:
- Understand basic physics simulation concepts (gravity, velocity, restitution)
- Implement collision detection and response between objects
- Simulate real-time motion using a game loop (timer-based updates)
- Explore user interaction with physics systems (mouse forces, UI controls)
- Practice object-oriented design in Java

---

## Technologies Used
- Java
- Java Swing (GUI and rendering)
- Java AWT (graphics and events)
- Timers for real-time simulation
- Basic physics mathematics (vectors, forces, collisions)

---

## How the Simulation Works
Each ball in the simulation has position, velocity, gravity, restitution, and lifetime properties. On every update cycle:
- Gravity is applied to all balls
- Positions are updated using velocity
- Collisions between balls are detected and resolved
- Boundary collisions with walls are handled
- Additional forces may be applied based on mouse interaction

The simulation runs continuously using a timer to approximate real-time behavior.

---

## Key Features
- Real-time gravity simulation with adjustable gravity slider
- Ball-to-ball collision detection and response
- Boundary collision handling
- Mouse interaction that applies force to nearby particles
- Continuous spawning of special particles using a button
- Visual feedback for collisions and interactions
- FPS counter for performance monitoring

---

## User Interaction
- **Gravity Slider:** Adjusts the gravity strength in real time
- **Mouse Press:** Applies a radial force that pushes nearby particles
- **Button (+):** Continuously spawns special particles while pressed

---

## Physics Concepts Implemented
- Gravity acceleration
- Velocity and position integration
- Elastic collisions using restitution
- Impulse-based collision response
- Simple surface tension–like behavior near the top of the screen

---

## Limitations
- Physics calculations are simplified and not physically exact
- No spatial partitioning (performance may degrade with many particles)
- Fixed time step approximation using timers
- Designed for learning and experimentation, not production use

---

## Future Improvements
- More accurate time-step integration
- Spatial partitioning (quad-trees or grids) for performance
- Better collision resolution stability
- Additional forces (wind, drag)
- Separation of physics logic and rendering

---

## Disclaimer
This project was created for educational purposes to explore physics simulation and interactive systems in Java.

