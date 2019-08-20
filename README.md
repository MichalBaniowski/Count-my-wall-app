# Calculate My Wall App
> Simple calculator of heat transfer coefficient of partitions created  as RESTful WEB Service. 

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Features](#features)
* [Status](#status)

## General info
This is project that helps engineers calculate necessary parameters of walls, slabs, roofs etc. 
needed to define energy balance of rooms in building. 
The calculation method is based on norm :PN-EN ISO 6946:1999 and requirements on EU law order.
* The most important model objects:
  - building material - contains the most needed parameters;
  - material category - defines usage of material eg. core, finishes, insulation etc.
  - composite - a multilayer partition with properties based on building materials parameters which it's consist of.
  - composite type - type of partition eg wall, roof, slab etc.

## Technologies
* Java - version - 8
* Spring Boot - version 2.1.4 RELEASE
* Spring Security
* Spring Validation
* Spring Data
* Data Base - MySql - version 8.0.13


## Features
* anonymous user has access to:
  - default list of building material or default searched by category
  - list of building material categories
  - list of composite types 
  - list of default composites
  - calculating composite service
* logged user 
  - has access to: 
    - default and users lists of building materials and composites
  - can:
    - create / read / update / delete his/her building materials and composites

TO-DO list:
* finish Spring Security configuration
* add admin role 
* add Rooms Energy Balance Module which can calculate and summarize energy efficiency of rooms.
* add front end REST API

## Status
Project is: in progress