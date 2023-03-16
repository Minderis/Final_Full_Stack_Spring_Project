
# Chess App

This is a web application for managing chess players. It allows users to add, edit, and delete chess players, as well as view a list of all players.
## Tech Stack

**Client:** HTML, SCSS, JavaScript

**Server:** Java 17, Maven, Spring Boot, H2


## Endpoints

**Get all chess players:**
```
GET /chess_player
```
Retrieves a pageable list of all chess players. Returns a JSON array of ChessPlayerDTO objects, along with HTTP headers specifying the total number of elements in the list.

**Get a chess player by ID:**
```
GET /chess_player/{id}
```
Retrieves a single chess player by ID. Returns a ChessPlayerDTO object if the player exists, or an error message if the player is not found.

**Add a chess player:**
```
POST /chess_player
```
Adds a new chess player to the system. Expects a JSON object of type AddChessPlayerDTO in the request body. Returns a success message if the player is added successfully, or an error message if there is a problem with the input data.

**Delete a chess player:**
```
DELETE /chess_player/{id}
```
Deletes a chess player by ID. Returns a success message if the player is deleted successfully, or an error message if the player is not found.

**Edit a chess player:**
```
PATCH /chess_player/{id}
```
Modifies an existing chess player by ID. Expects a JSON object of type AddChessPlayerDTO in the request body. Returns a success message if the player is modified successfully, or an error message if there is a problem with the input data or if the player is not found.


## Deployment

**Back-end**

Run from terminal:

```
  java -jar chess-app.jar
```

or do it manually by opening chess-app source as project in InteliJ and running it.

Back-end reachable by url: hhtp://localhost:8080

**Front-end**

There is many ways to deploy front-end part. Here is a few examples:

1. Put directory *frontend* to your HTTP server;
2. From directory root run one of cmd (depending on your pyhon version):
```
  python3 -m http.server || python -m SimpleHTTPServer
  ```
3. Launch it from Visual Studio Code with Live Server

p.s. before running front-end you should compile .scss files to .css with any preprocessor, like:

Node-sass, Dart Sass or use extension for Visual Studio Code to compile .scss, like Live Sass Compiler.

**Browser**

Once the backend and frontend servers are running, you can access the application by navigating to http://{your_domain}:{your_port} in your web browser. For example *http://localhost:5500*


## Demo

Application is prepared with test data for demonstration.

Few test data records are left invalid to show app behaviour (data validation).
## Examples

HTTP requests examples are provided as Postman collection.
File: *Final_Full_Stack_Spring_Project_Postman_Collection.postman_collection.json*


## Usage

**Adding a new player**

To add a new player:

Click the "Add New Player" button.

Fill in the player's details in the form that appears.

Click the "Save" button.

**Editing an existing player**

To edit an existing player:

Click the player's name in the list of players.

Edit the player's details in the form that appears.

Click the "Save" button.

**Deleting a player**

To delete a player:

Click the player's name in the list of players.

Click the "Delete" button.