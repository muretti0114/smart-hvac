# smart-hvac
Source code and related documents for Smart HVAC service.
Used for Kobe Univ.-LIG collaboration project "Research on Testing Smart Services"

2015-08-26 created by Masahide nakamura


#HVAC Service
## Overview
An *HVAC (Heating Ventilation and Air Conditioning) service* deployed in a room is a service
that autonomously executes air-conditioning appliances in order to keep the room "comfortable".

A metric *discomfort index* is used to measure the comfort level of the room, which is calculated
from the temperature and humidity of the room. The value of the discomfort index charaterizes
a *room condition* of the room.

The goal of the HVAC service is to maintain the room condition to be "comfortable", with minimal
energy consumption. Therefore, the service moves towards a state such that:

- If somebody is in the room => the room condition is comfortable  AND  no appliance is working
- If nobody is in the room => no appliance is working 

The above two conditions can be merged as follows: 
-  (roomComfortable AND noDeviceWorking) OR (nobodyExist AND noDeviceWorking)

If the current state of the HVAC service does not satisfy the above condition, the service
propose some actions according to a pre-determined (or dynamic) policy.

Finally, All (or some) of the proposed actions are executed in accordance with the execution policy.

## Discomfort Index
For a given temperature t and humidity h, the discomfort index DI is calculated as follows:

**DI = 0.81*t + 0.01 * h * (0.99*t - 14.3) + 46.3;**

The room condition is characterized by the value of DI as follows:

    ----------------------------------------------
    DI value           Condition
    ----------------------------------------------
        DI <=55        Cold
    55< DI <=60        A bit cold
    60< DI <=65        It's OK
    65< DI <=70        Comfortable
    70< DI <=75        Not hot
    75< DI <=80        A bit hot
    80< DI <=85        Hot
    85< DI             Very hot. Call emergency
    ----------------------------------------------

More details of the discomfort index can be found at 
https://ja.wikipedia.org/wiki/%E4%B8%8D%E5%BF%AB%E6%8C%87%E6%95%B0

## Used appliances and sensors
To perform appropriate air-conditioning, an HVAC service may use
- an air-conditioner
- a radiator
- a circulator
- a window

To grasp the state of the room, an HVAC service may use
- a temperature sensor
- a humidity sensor
- a human presence sensor

The dependency between the above objects and thte physical devices
are given in the configuration of the service.

## Execution cycle of the service
Once an HVAC service is launched, it is continueously working until explicitly stopped.
The execution of the service is repeated by the following cycle:
1. Observe a state of the room
2. Interprete the state
3. Propose a set of actions
4. Execute a set of actions
5. Wait some interval and go to 1.

The wait interval (usually several seconds to a few minutes) at Step 5 
is given in the configuration.

## Policy of action proposal (static version)
Depending on the current condition of the room, we are currently using 
the following policy to propose a set of actions:

    ------------------------------------------------------------------------------
    [Condition]                         [Proposed Actions]
                         AC         Radiator      Circulator      Window
    ------------------------------------------------------------------------------
    Cold                 off          on, temp=24     on, speed=1    close
    A bit cold           off          on, temp=22     on, speed=0    close
    It's OK              off          on, temp=22        off         close
    Comfortable          off            off              off         close
    Not hot       on, temp=22,speed=0   off              off         close
    A bit hot     on, temp=21,speed=0   off           on, speed=0    close
    Hot           on, temp=20,speed=1   off           on, speed=1    close
    Emergency     on, temp=19,speed=2   off           on, speed=2    open
    ------------------------------------------------------------------------------

Note that the above policy is static and never changed throughout the execution of the service.

## Policy of action proposal (dynamic version)
TBD

## Policy of action execution (static version)
For the action execution, we are currently executing all the actions proposed.
This is just for simplicity.

## Policy of action execution (dynamic version)
TBD




