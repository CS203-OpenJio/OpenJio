# Using an official Node.js runtime as a parent image
FROM node

# Setting the working directory to /openjio/frontend
WORKDIR /

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./
# is this needed?

# Install dependencies
RUN npm install

RUN npm install -g cross-env

# Copy the rest of the application code to the working directory
COPY . .

EXPOSE 3006

# Build the React app for production
CMD npm start

# # Serve the React app using a lightweight HTTP server
# RUN npm install -g serve

# # Expose the port that the app will run on
# EXPOSE 80

# # Define the command to run the application
# CMD ["serve", "-s", "build", "-l", "80"]