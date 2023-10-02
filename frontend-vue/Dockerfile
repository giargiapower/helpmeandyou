# Use an official Node runtime as a parent image
FROM node:16

# Set the working directory in the container
WORKDIR /frontend-vue

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

RUN npm cache clean --force
# Install app dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Expose the port the app runs on
EXPOSE 8080

# Define the command to run your app
CMD ["npm", "run", "serve"]