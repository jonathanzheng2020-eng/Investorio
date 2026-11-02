# Investorio
A Java terminal application that lets users manage their stock and index fund investments by recording their holdings, purchase prices, and current prices. It'll calculate their total portfolio value, profit/loss, percent returns, and the allocations of each investment. It'll also allow users to save and load their portfolio using CSV files.
## Project Goal
Many investing brokerages such as Fidelity and Acorns have very convoluted statistics pages showing the performance of their portfolios.  Calculating the total value and gains/losses of each individual investments is very tedious as your portfolio grows. This application will allow users to record their investments and calculate their portfolio value, profit or loss, and allocations.
## MVP Scope
The initial version of Investorio will include:
- Add/remove investments (different tickers, share amounts, purchase/current price
- Calculate total portfolio value, total invested, profit/loss
- Calculate individual returns and portfolio allocations
- Save/load portfolio using CSV files

Stock/ETF and other investment info will be entered manually. Operations won't include external APIs, GUIs, and databases.
## Future Plans
Potential improvements for future versions might include:
- Developing a GUI
- Retrieve live prices using an API
- Display historical performance over time (ex: your portfolio right now vs. your portfolio 10 years ago)
- Adding filters and different ways to sort your investments. 
## Tech Stack
- **Language:** Java
- **IDE:** VS Code
- **Version Control:** Git & GitHub
- **Collection:** ArrayList for investment record storage
- **User Input:** Scanner for terminal input
- **Data Storage:** CSV Files
- **File Handling:** PrintWriter, File for reading and writing CSV files
- **Formatting:** NumberFormat
