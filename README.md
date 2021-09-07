# Covi-19_Extended
This program allows you to simulate covid-19 test roads.
## Table of contents
* [General info](#general-info)
* [Technologies & Requirements](#technologies)
* [Setup](#setup)
* [Library & Requirements](#library)

## General info
This Program allows you to simulate testing stations to check out how many tests are possible in a given time or a day. Different queueing techniques can be used such as First in, first out, also known as FIFO.
The most common queuing technics are using FIFO, LIFO, SPT and LPT. In addition, to create a less random experience at each station a uniform distribution was used. Finally, a car has to go threw 3 stations, where one of them is showing an id and doing the actual test as it is in real life.
## Technologies
This program is developed in Java and uses the Apache Commons Math library.

## Setup
Import the project into your favorite IDe and make sure to add the Apache Commons Math library from [1].
There are 4 different queuing types FIFO,LIFO,SPT,LPT which needs to be specified in the main method.
In addition, by simply launching the program by default there are only three queues active if more or less queues are needed you need to motivate it in the main function itself by simply copy&pasting or removing the threads which are names Teststations.
Moreover, you may be required to change the timing of the arrival of the cars or how long the simulation should be. All these parameters can be changed in the main method.
That's it, have fun and check out what is the maximum possible capacity of your local test stations and compare the results.

## Library
[1](http://commons.apache.org/proper/commons-math/download_math.cgi)
