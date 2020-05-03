## Lightbend Akka Streams for Scala - Professional

---

### man

The `man` command, short for manual, displays the setup instructions (what you are reading now) for the courseware. To view the instructions for the current exercise, use the `e` option. If you are using an IDE, you can also open up the setup instructions (`README.md`) file or the current exercises instructions (`src/test/resources/README.md`) file in your workspace.

```scala
// display the setup instructions
man [e] > ... > initial-state > man

// display the instructions for the current exercise
man [e] > ... > initial-state > man e
```

---

### run

As part of each exercise, we use the `run` command to bootstrap the main class. This command starts the application for the **current** exercise that we interact with and verify our solution.

```scala
man [e] > ...> initial-state > run
```

---

### course navigation and testing

Navigation through the courseware is possibile with a few `sbt` commands. Also, tests are provided to confirm our solution is accurate. It is important to note that the tests make some assumptions about the code, in particular, naming and scope; please adjust your source accordingly. Following are the available `navigation` commands:

```scala
// show the current exercise
man [e] > ... > initial-state > showExerciseId
[INFO] Currently at exercise_000_initial_state

// move to the next exercise
man [e] > ... > ... > nextExercise
[INFO] Moved to ...

// move to the previous exercise
man [e] > ... > ... > prevExercise
[INFO] Moved to exercise_000_initial_state

// save the current state of an exercise for later retrieval and study
man [e] > ... > initial-state > saveState
[INFO] State for exercise exercise_000_initial_state saved successfully

// List previously saved states
man [e] > ... > ... > savedStates
[INFO] Saved exercise states are available for the following exercise(s):
        exercise_000_initial_state
        ...

// Restore a previously saved exercise state
man [e] > ... > initial-state > restoreState exercise_000_initial_state
[INFO] Exercise exercise_000_initial_state restored
```

---

### clean

To clean your current exercise, use the `clean` command from your `sbt` session. Clean deletes all generated files in the `target` directory.

```scala
man [e] > ... > initial-state > clean
```

---

### compile

To compile your current exercise, use the `compile` command from your `sbt` session. This command compiles the source in the `src/main/scala` directory.

```scala
man [e] > ... > initial-state > compile
```

---

### reload

To reload `sbt`, use the `reload` command from your `sbt` session. This command reloads the build definitions, `build.sbt`, `project/.scala` and `project/.sbt` files. Reloading is a **requirement** if you change the build definition files.

```scala
man [e] > ... > initial-state > reload
```

---

### test

To test your current exercise, use the `test` command from your `sbt` session. Test compiles and runs all tests for the current exercise. Automated tests are your safeguard and validate whether or not you have completed the exercise successfully and are ready to move on.

```scala
man [e] > ... > initial-state > test
```

---
---

### Exercise 7: Fusion - Results

| Name               | Order Cars completed in milliseconds |
|--------------------|--------------------------------------|
| 0 ms run           | 1 375 ms                             |
| 16 ms run          | 15 484 ms                            |
| 14 ms run          | 13 439 ms                            |
| 12 ms run          | 11 426 ms                            |
| 14 ms tail run     | 14 140 ms                            |
| 10 ms 2 asyncs run | 10 072 ms                            |

---

### Exercise 8: Streams Telemetry Intro - Results

#### Operator Processing Times

**Question: Why does the list include two install-upgrades-stage-maps? Does this mean the 16 ms run is actually the 18 ms run? Look in the UpgradeShop class. Do you see more than one map operator? Are all the map operators run for each ordered car?**

Answer: The installUpgrades flow includes to map operators (one for DX upgrade and another for Sport upgrade). The map operators don't run for each car, the cars are balanced for the three types of upgrades (DX, Sport and Standard), so each map operator runs for 1/3 of the cars. It continues to be a 16 ms run because for each car only one map operator runs.


**Question: Can you determine how long the UpgradeShop balance and UpgradeShop merge took?**

Answer: balance took 1µs and merge took 723ns.


**Question: What class and method contain the install-engine-stage-iterableSource operator?**

Answer: EngineShop class, shipments source uses an iterator that continually produces shipments.

#### Stream Throughput
     
| Name               | Order Cars completed per sec (stream throughput) |
|--------------------|--------------------------------------------------|
| 16 ms run          | 65                                               |
| 12 ms run          | 88                                               |
| 10 ms 2 asyncs run | 100                                              |