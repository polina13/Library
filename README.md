# College Register - Many to Many

#### A college with students and classes

#### By Austin Minnon, Polina Nenchev

## Description

This college app organizes students by class and enrollment stage; each student can belong to multiple classes.

## Setup/Installation Requirements

Clone this repository:
```
$ cd ~/Desktop
$ git clone github address
$ cd folder-name
```

Open terminal and run Postgres:
```
$ postgres
```

Open a new tab in terminal and create the `college` database:
```
$ psql
$ CREATE DATABASE college;
$ psql college < college.sql
```

Navigate back to the directory where this repository has been cloned and run gradle:
```
$ gradle run
```
## Known Bugs

- Does not sort results alphabetically or consistently.
- Tasks that share categories not sorting correctly

## Technologies Used

* Java
* junit
* Gradle
* Spark
* fluentlenium
* PostgreSQL
* Bootstrap

### License

Licensed under the GPL.

Copyright (c) 2016 **Polina Nenchev and Austin Minnon**
