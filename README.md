# • Market Bill System

A console-based checkout system built in Java that simulates a real-world market billing experience.
This project allows users to scan product codes, manage cart items, adjust quantities, remove products, and generate a detailed receipt.

---

## • Features

* Scan products using product codes
* Automatically increase quantity for repeated scans
* Remove items or reduce quantity
* Generate a formatted receipt with totals
* Sound effect when scanning items
* View full product catalog
* Input validation to prevent crashes

---

## • Sound File Requirement

Make sure this file exists in the same directory:

```
scanning_beeb_sound.wav
```

Otherwise, the program will throw an error.

---

## • Technologies Used

- Java
- Console (CLI)
- Basic data structures (arrays)

---

## • Background & Reflection
This project was developed during my **Stage 1 (Freshman year)** studies in 2023-2024. It was one of my first independent programming projects, built entirely from scratch without the use of AI tools. 

I've kept this repository in its original form to document my early journey in Java and my passion for building functional tools from the ground up.

A particular challenge was the **Remove** functionality. Since arrays have a fixed size in Java, I implemented a manual shifting algorithm to ensure that when an item is deleted, the subsequent records move up to fill the empty slot. This taught me a great deal about how data is handled at a low level before moving on to higher-level collections like `ArrayList`.

---

## • How to Run

1. Clone the repository

2. Open the project in your IDE

3. Run the `MarketBill.java` file

---

## • License

This project is licensed under the MIT License — feel free to use and modify it with proper credit.
