# EEZE

[![GitHub Actions](https://github.com/rudnaid/spendeeze/actions/workflows/maven.yml/badge.svg)](https://github.com/rudnaid/spendeeze/actions/workflows/maven.yml)

<details>
<summary><h2><strong>Table of Contents</strong><h2></summary>
  
- [About the Project](#about-the-project)
- [Built With](#built-with)
- [Contributors](#contributors)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation Steps](#installation-steps)
- [Usage](#usage)
- [Acknowledgments](#acknowledgments)
  
</details>


## About The Project

This app helps users track their financial transactions by categorizing expenses and incomes.  
It provides insights through data visualization, helping users make informed financial decisions.

### Features

- **Add**, **edit**, and **delete** transactions  
- **Categorize expenses** for better organization  
- View financial trends using **bar** and **pie charts**  
- Secure **authentication** and **role-based user management**


<img width="492" alt="Screenshot 2025-04-02 at 21 52 38" src="https://github.com/user-attachments/assets/704132f7-2839-4503-95e9-9c5b3d3ef769" /> <br>

The entire application is containerized using **Docker Compose**, enabling seamless **deployment** and **scalability**.

A **CI pipeline** helps ensure **code stability** and **correctness** by automatically building and testing code changes on GitHub.
  
## Built With

- **Backend:**  
  [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)

- **Frontend:**  
  [![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)](https://reactjs.org/)  
  [![Vite](https://img.shields.io/badge/Vite-646CFF?style=for-the-badge&logo=vite&logoColor=white)](https://vitejs.dev/)  
  [![TailwindCSS](https://img.shields.io/badge/TailwindCSS-06B6D4?style=for-the-badge&logo=tailwindcss&logoColor=white)](https://tailwindcss.com/)

- **Database:**  
  [![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)

- **Containerization:**  
  [![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)  
  [![NGINX](https://img.shields.io/badge/NGINX-009639?style=for-the-badge&logo=nginx&logoColor=white)](https://www.nginx.com/)


## Contributors

| Name          | GitHub Profile |
|--------------|---------------|
| Éva Gömbös-Jeczuska  | [Vica1921](https://github.com/Vica1921) |
| Erika Oláhné Klár | [o-k-e](https://github.com/o-k-e) |
| Dávid Rudnai | [rudnaid](https://github.com/rudnaid) |


## Getting Started

To get a local copy up and running, follow these steps:

### Prerequisites

Depending on how you want to run the application, different tools are required:

#### If you only want to run the application using Docker, install:

  - #### Docker Desktop
    ➡️ [https://www.docker.com/products/docker-desktop/](https://www.docker.com/products/docker-desktop/)  

#### If you plan to run the code locally without Docker, install:

  - #### Java 23
    ➡️ [https://jdk.java.net/23/](https://jdk.java.net/23/)  

  - #### Node.js 18+
    ➡️ [https://nodejs.org/en/download/](https://nodejs.org/en/download/)  
  
  - #### PostgreSQL 14+
    ➡️ [https://www.postgresql.org/download/](https://www.postgresql.org/download/)  

  - #### Maven 3.9+
    ➡️ [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi) 

### Installation Steps

1. Open a **terminal** and navigate to the directory where you would like to save the repository.
   
2. **Clone the repository** to your machine by executing the command below in your **terminal**, then proceed with one of the installation options below.
   ```bash
   git clone https://github.com/rudnaid/eeze.git
   ```

To simplify setup, example `.env` files with the necessary **environment variables** are already provided in the **config** directory. You can modify their values to customize the app to your preferences, either by manually editing them or by running the provided **starter scripts**.

#### With Docker (recommended)

1. **Ensure Docker is Running**
   - Start **Docker Desktop** or the **Docker daemon** on your system.

2. **Navigate to the scripts directory**
   - The **scripts** will guide you through the initial setup of **environment variables**.
   - Open your **terminal** and navigate to the **scripts** directory located in the **root** directory of the project.

3. **Build and run the containers with the automated script**
   - **On Windows:**
     - Execute the command:
       ```bash
       ./start-with-docker.ps1
       ```
   - **On macOS/Linux:**
     - Execute the command:
       ```bash
       chmod +x start-with-docker.sh && ./start-with-docker.sh
       ```

4. **Access the Application**
   - Open your browser and visit:  
     [http://localhost:3000](http://localhost:3000)

5. **Stopping the application**
   - To remove the containers, execute this command:  
     ```bash
       docker-compose down
     ```

#### Without Docker

1. **Create PostgreSQL database** *(only necessary if running the application for the **first time**)*
   - Open your **terminal** and type `psql` then press Enter to connect to PostgreSQL.
   - Log in with your PostgreSQL credentials (make sure the user has sufficient privileges to create databases)
   - Type `CREATE DATABASE eeze;` then press Enter.
   - All set! To exit PostgreSQL type `quit` and press Enter.

1. **Navigate to the scripts directory**
   - Open your **terminal** and navigate to the **scripts** directory located in the **root** directory of the project.
   - The **scripts** will guide you through the initial setup of **environment variables**.

2. **Run starter script for automated setup**
   - **On Windows:**
        - Execute the command:
          ```bash
          ./start-local.ps1
          ```
   - **On macOS/Linux:**
        - Execute the command:
          ```bash
          chmod +x start-local.sh && ./start-local.sh
          ```
            
3. **Access the Application**
   - Open your browser and visit:  
     [http://localhost:5173](http://localhost:5173)

4. **Stopping the application**
   - In your **terminal** press `Ctrl + C`

## Usage

### Managing Incomes and Expenses

1. **Register** a new user or **log in** if you already have an account.

   You can also use a **default user** to explore the app with six months of randomly generated transactions:
      - **Username:** `john_doe`
      - **Password:** `user`
        
   **With Docker**
      - No additional setup is needed, the dummy data is preloaded.  
      - Simply log in using the credentials above.
        
   **Without Docker**
     -  Open **terminal** and from the project's root directory navigate to `backend/db_init`.
     -  Enter `psql -U [your PostgreSQL username]` and log in with your credentials.
     -  Connect to the 'eeze' database with the command:
        ```bash
        \c eeze;
        ```
     -  Execute the random data generator script with the command:
        ```bash
        \i dummyDataGenerator.sql
        ``` 
     -  All set! You can quit PostgreSQL and log in using the credentials above.
   
3. **Add Income or Expense**
   - Once on the main page, click the **Add Income** or **Add Expense** button.
   - A pop-up will appear where you can enter the required details:
     - **Amount**
     - **Date**
     - If you are adding an *expense*, also **select a category** from the list.

4. **Visualize Your Finances**
   - After adding your data:
     - The **bar chart** will show a **monthly breakdown** of total *income* and *expenses*.
     - The **pie chart** will display how the **current month's expenses** are distributed across categories.
    
<img width="378" alt="Screenshot 2025-04-02 at 21 48 21" src="https://github.com/user-attachments/assets/bb7a4245-e3c1-4a57-a892-8fb632745adc" />

<img width="373" alt="Screenshot 2025-04-02 at 22 02 17" src="https://github.com/user-attachments/assets/909e5839-669e-4dcf-877f-468ca1b8f296" />

## Acknowledgments

- [Best-README-Template](https://github.com/othneildrew/Best-README-Template) for inspiration
- [Shields.io](https://shields.io/) for the badges
