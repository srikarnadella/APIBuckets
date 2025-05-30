# APIBucket: Turn CSVs Into Instant APIs

This project stems from the core issue I face, where a lot of the data I want to use (ahem, basketball reference) doesn't have it readily available via API and requires data scraping. So what I did was make a tool where I can upload my csv to an application, process it, and store it in the cloud,d ready for retrieval and use. It is a great tool to share and view data, and I am thoroughly excited to use this as my own personal data store (AWS limits).

## What It Does

- Upload a CSV file via a clean UI
- The backend parses and stores it in memory
- Instantly exposes REST API endpoints for:
- Full row access
- Filtering by column values
- Partial string matching, pagination, etc.
- Auto-generates Swagger API documentation per file
- Lets you preview your data, check column stats, and download filtered results

Tech Stack
This was built with modern tools that complement each other:

## Frontend

- Built with React + Vite
- Hosted on Vercel

## Backend

- Spring Boot running on Java
- REST API that dynamically registers endpoints for each CSV uploaded
- Supports dynamic querying, pagination, partial matches, and more
- In-memory data storage (backed by S3 for file persistence)
- Swagger/OpenAPI documentation auto-generated per dataset

## Cloud Usage

- Deployed the Spring Boot backend on AWS Elastic Beanstalk
- CSVs stored in Amazon S3 and rehydrated on boot
- React frontend auto-deploys on push via Vercel CI

## The Application Itself

### This is the Home Screen where users will have the opportunity to upload their data and view there uploaded datasets

![HomePage](https://github.com/user-attachments/assets/a0ad7f22-36e3-4021-86e9-ed4d2c892cae)

### This is the view after you click View Data

![DataLoaded](https://github.com/user-attachments/assets/3cc5855b-2dc5-4c16-8076-1ab8a873ca90)

### This is the auto-generated API doc

![APIDocs](https://github.com/user-attachments/assets/462a26c8-a202-4598-9499-dca3187700a4)

### This is an API call in Postman

![PostMan](https://github.com/user-attachments/assets/b2400cc8-7f1b-4179-b6b6-7aa9e740c755)
