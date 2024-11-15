# CSC207_Project

## Description

A drawing app that allows the user to draw a quick sketch using a randomly generated color palette. The user can also choose to generate a custom color palette by uploading an image. The sketch can then be converted to realistic painting.

## Requirements

APIs used:
- Imagga API: https://docs.imagga.com/
- Clipdrop API: https://clipdrop.co/apis/docs/sketch-to-image

## Features
***
1. Basic Drawing Functionality:
- [x] Draw 
- [x] Save as image
- [ ] Erase 
- [ ] Draw shapes
- [ ] Draw with different brush size
- [ ] Fill (fills in the space around the mouse that is enclosed by brush strokes)
***
2. Let the user undo/redo brush strokes and autosave
***
3. Generate random color palette and let user be able to "lock" certain colors and manually change some colours.
***
4. Generate color palette from an uploaded image (api)
***
5. Let User describe their sketch as a string and convert the sketch to a more realistic drawing (api)
***

## Installation and Configuration
- Maven Project (right click on pom.xml > "add as Maven Project")