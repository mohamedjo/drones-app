# Drones App

REST API  service that allows clients to communicate with the drones
## Features
- registering a drone
- loading a drone with medication items
- checking loaded medication items for a given drone
- checking available drones for loading
- check drone battery level for a given drone




## Technologies Used

- Java 17
- Springboot 3
- Spring Data JPA
- H2 in Memory Database
- Junit 5, Mockito, Mock MVC
- Maven

## Build And Run Locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the main method in
the src/main/java/com/jo/drones/global/DronesAppApplication.java class from your IDE.

Alternatively you can use the Spring Boot Maven plugin like so:

```bash
mvn spring-boot:run
```

## DataBase Info

Url: http://localhost:8080/h2-ui/

JDBC URL:  jdbc:h2:mem:testdb

User Name: sa

there is no password

## REST Endpoints

http://localhost:8080/api/

### POST

`Register Drone` http://localhost:8080/api/drone <br/>

**Request Example**

``` 
{
"serialNumber":"ABBBBKS252",
"model":"LightWeight",
"weightLimit":"70",
"batteryCapacity":"75",
"state":"IDLE"
}
```

**Response**

```
201 Created
```

### POST

`load Drone with medications  ` http://localhost:8080/api/loadDrone?droneSerialNumber={serialNumber} <br/>

**Request Example**

``` 
[{
"name": "name1",
"weight": "21",
"code": "MNB"
},
{
"name": "name2",
"weight": "30",
"code": "MNC"
}]
```

**Response**

when success

```
201 Created
```
when Serial Number Not Correct(Drone Not Exist)

```
{
    "status": 404,
    "message": "there is no drone with this SerialNumber {serialNumber}"
}
```

when Drone is already Loaded

```
{
    "status": 500,
    "message": "This Drone  is Loaded with other medications"
}
```

when total weight of medications exceeded WeightLimit of the drone

```
{
    "status": 400,
    "message": "total weight of  medications exceeded WeightLimit of the drone"
}
```

when Drone Battery Level Lower Than 25

```

{
"status": 500,
"message": "Drone Battery Level Lower Than 25"
}
```

### GET

`Cheking loaded medications for Given Drone` http://localhost:8080/api/medication?droneSerialNumber={serialNumber} <br/>

**Response**

when success

```
{
    "medications": [
        {
            "id": 2,
            "name": "name2",
            "weight": 21.0,
            "code": "MNC",
            "image": null
        },
        {
            "id": 3,
            "name": "name1",
            "weight": 21.0,
            "code": "MNB",
            "image": null
        }
    ]
}
```

when Serial Number Not Correct(Drone Not Exist)

```
{
    "status": 404,
    "message": "there is no drone with this SerialNumber {serialNumber}"
}
```

### GET

`Cheking Available Drones for Loading` http://localhost:8080/api/availableDrones <br/>

**Response**

```
[
    {
        "id": 1,
        "serialNumber": "VVVTVmt",
        "model": "LightWeight",
        "weightLimit": 50,
        "batteryCapacity": 75,
        "state": "IDLE"
    },
    {
        "id": 4,
        "serialNumber": "ABBBBKS252",
        "model": "LightWeight",
        "weightLimit": 70,
        "batteryCapacity": 75,
        "state": "IDLE"
    },
    {
        "id": 5,
        "serialNumber": "ABBBBKS252",
        "model": "LightWeight",
        "weightLimit": 70,
        "batteryCapacity": 15,
        "state": "IDLE"
    },
    {
        "id": 6,
        "serialNumber": "ABBBBKS253",
        "model": "LightWeight",
        "weightLimit": 70,
        "batteryCapacity": 15,
        "state": "IDLE"
    }
]
```

### GET

`check drone battery level for a given drone` http://localhost:8080/api/batteryLevel?droneSerialNumber={serialNumber} <br/>

**Response**

when success

```
57
```

when Serial Number Not Correct(Drone Not Exist)

```
{
    "status": 404,
    "message": "there is no drone with this SerialNumber {serialNumber}"
}
```

## Periodic  Task

periodic task to check drones battery levels and create history event log for this every 10 Minutes
and I named it DronePeriodicTask (\src\main\java\com\jo\drones\global\task\DronePeriodicTask.java)




