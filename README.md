# Word Find Game


## Overview
Word find game is a JavaFX application designed to create and display a wordfinding game using a graphical user interface (GUI). 


## Getting Started
To run the Word Find Game, the user should navigate to the project directory and run the following command

    gradle run 


### How to load your own puzzle
To select a different level, the user should press the load button. Then the user may select an existing level file
or navigate to a level.txt file that they have created.

#### Puzzle Format (.txt file)
    xxxx
    xxxx
    xxxx
    xxxx
    ~
    word1,
    word2,
    word3,


## Project Structure

```md
+---data
+---levels
+---src
    +---main
    ª   +---java
    ª   ª   +---model
    ª   ª   ª   +---wordType
    ª   ª   +---observers
    ª   ª   +---run
    ª   +---resources
    ª       +---run
    +---test
        +---java
            +---resources
            ª   +---run
            ª       +---testLevels
            +---suite
```

## Internal Dictionaries and Corpus
For this project, we currently store an in-house corpus, storing MIT's assistant professor Eric Price's 10000-word list. 

It can be found [here](https://www.mit.edu/~ecprice/wordlist.10000).

A larger corpus can be found here (https://websites.umich.edu/~jlawler/wordlist.html).

### Contributors to building this project:
 - Jeremy Perez
 - Jonathan Fischman
 - Miguel Garduno 
 - Janak Subedi