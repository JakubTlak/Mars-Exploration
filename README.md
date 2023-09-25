# Mars Exploration Project

Welcome to the Mars Exploration project! 
In this project you will simulate colonization of the Red Planet. 
This README will guide you through the project, explaining its purpose, objectives, and how to get started.


## PROJECT OVERVIEW

Humanity's dream of colonizing Mars is becoming a reality. 
To make this possible, simulation exercises are crucial to calibrate the software for Mars rovers. 
We created an application that generates randomized maps of Mars based on specific requirements, 
and then a simulation of a rover exploring a map of Mars, determining whether an area is suitable for a future human colony. 

## Motivation

* **What was your motivation?** 
Our motivation was to create a rover simulation that can assess the habitability of Martian 
terrain for future colonization efforts
* **Why did you build this project?** 
We built this project to provide a tool for simulating rover exploration on Mars, 
collecting data about terrain and resources, and generating log files for analysis
* **What problem does it solve?** 
This project addresses the need for realistic rover simulations on Mars, 
enabling us to assess potential colonization sites without the need for physical presence
* **What makes your project stand out?** 
Our project stands out by offering a comprehensive rover simulation with flexibility in parameterization
and the ability to log simulation data for analysis. The most important classes have tests implemented.

## WHAT WE LEARNED

* Applying the open-closed principle
* Working with multidimensional arrays in Java
* Utilizing random number generation
* Practicing data-driven testing with JUnit
* Implementing file input/output (I/O)
* Applying the single responsibility principle
* Implementing interface segregation principle
* Practicing dependency injection
* Logging of simulation steps in a specific format
* Storage of simulation data in an SQLite database

## PROJECT STRUCTURE

The project is organized into several modules, each with specific tasks:
* **Configuration Module:** Creates configuration objects based on predefined requirements
* **Calculator Module**: Implements interfaces to calculate dimensions for 2D arrays and coordinate calculations
* **MapElements Module**: Implements the logic for building and generating map elements
* **Output Module**: Implements a MapFileWriter to write map data into a specified file

## How to Run the Project
Run Application.java file to start Mars Exploration Simulation!

## Credits 
* Paweł Ignaczak https://github.com/C00kier
* Jakub Tlak https://github.com/JakubTlak
* Mateusz Grygier https://github.com/HobbitM
* Iza Bonarowska https://github.com/izabonarowska
* Bartosz Hilawski https://github.com/Hiltwa1
