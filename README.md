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

The entire application is containerized using Docker Compose, enabling seamless deployment and scalability.

A functional CI pipeline ensures the code quality and automate the build and test processes.
  
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

Make sure you have the following installed:

- **Java 23**
- **Node.js 18+**
- **PostgreSQL 14+**
- **Maven**
- **Docker** (optional)

### Installation Steps

**Clone the repository to your machine** with command `git clone https://github.com/rudnaid/spendeeze`**, then proceed with one of the installation options below.**

To simplify setup, example `.env` files with the necessary **environment variables** are already provided in the **config** folder. You can modify their values to customize the app to your preferences.

#### Without Docker

1. **Modify .env.local file with your own PostgreSQL credentials**
2. **Navigate to the scripts folder**
   - Open your terminal and navigate to the **scripts** folder located in the **root** folder of the project.

3. **Run starter scripts for automated setup**
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
         
4. **Access the Application**
   - Open your browser and visit:  
     [http://localhost:5173](http://localhost:5173)

5. **Stop the application**
   - Press `Ctrl + C`

#### With Docker

1. **Ensure Docker is Running**
   - Start Docker Desktop or the Docker daemon on your system.

2. **Navigate to the scripts folder**
   - Open your terminal and navigate to the **scripts** folder located in the **root** folder of the project.

3. **Build and run the containers**
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

5. **Stop the application**
   - To remove the containers, execute this command:  
     ```bash
       docker-compose down
     ```

## Usage

### Managing Incomes and Expenses

1. **Register** a new user or **log in** if you already have an account.

   You can also use a default user to explore the app with some preloaded transactions:
      - **Username:** `john_doe`
      - **Password:** `user`

3. **Add Income or Expense**
   - Once on the main page, click the **Add Income** or **Add Expense** button.
   - A pop-up will appear where you can enter the required details:
     - **Amount**
     - **Date**
     - If it's an *expense*, also **select a category** from the list.

4. **Visualize Your Finances**
   - After adding your data:
     - The **bar chart** will show a **monthly breakdown** of total *income* and *expenses*.
     - The **pie chart** will display how the **current month's expenses** are distributed across categories.
    
<img width="378" alt="Screenshot 2025-04-02 at 21 48 21" src="https://github.com/user-attachments/assets/bb7a4245-e3c1-4a57-a892-8fb632745adc" />

<img width="373" alt="Screenshot 2025-04-02 at 22 02 17" src="https://github.com/user-attachments/assets/909e5839-669e-4dcf-877f-468ca1b8f296" />

## Acknowledgments

- [Best-README-Template](https://github.com/othneildrew/Best-README-Template) for inspiration
- [Shields.io](https://shields.io/) for the badges
