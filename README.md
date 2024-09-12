# Flow Free Game Clone

This project is a clone of the popular mobile game *Flow Free*. The goal of the game is to connect matching colors with pipes by filling the entire grid without any overlaps.

## Files

### 1. `Flow.java`
This file contains the core logic and data structures for the game. It handles the grid layout, pipe connections, and the logic required to check if a level is completed. Key features include:

- **Grid Management**: Represents the game board as a grid with start and end points for each color.
- **Pipe Connections**: Handles drawing connections between points of the same color and checks whether all connections are made correctly without overlaps.
- **Game Logic**: Implements the core rules of the game, including checking for completion and validating moves.

### 2. `FlowViewer.java`
This file handles the graphical representation of the game. It uses Java's Swing or similar libraries to create a user interface for the game. Key features include:

- **Rendering the Grid**: Draws the game grid and updates it based on the player's input.
- **User Interaction**: Captures mouse events to allow users to draw connections between colored points.
- **Game Feedback**: Provides visual feedback to the user when connections are made correctly or when they overlap.

## How to Run

1. **Compilation**: Compile the Java files using `javac`:

```bash
javac Flow.java FlowViewer.java
```

2. **Execution**: Run the program using `java`:

```bash
java FlowViewer
```

This will launch the game window, where you can interact with the grid and start playing.

## Game Play Instructions

1. **Objective**: The goal is to connect all the colored dots with their matching color by drawing a path between them. Every square on the grid must be covered by a path, and paths cannot overlap.

2. **Controls**:
   - Use your mouse to click and drag between two dots of the same color to create a connection.
   - If paths overlap, the previous path is erased.
   
3. **Winning Condition**: You win when all the dots are connected and the entire grid is filled without any overlapping paths.

## Features

- **Customizable Grid**: The game can be configured with different grid sizes and levels.
- **Visual Feedback**: The interface provides real-time visual feedback as the user connects dots and completes the puzzle.
- **Mouse Interactions**: Allows
