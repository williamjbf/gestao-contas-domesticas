# Use the official Node.js image to build and serve the Angular application
FROM node:21

# Set the working directory
WORKDIR /app

# Add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH

# Copy the package.json and package-lock.json
COPY package*.json ./

# Install the dependencies
RUN npm install
RUN npm install @angular/cli -g --silent

# Copy the rest of the application files
COPY . .

# Expose port 4200
EXPOSE 4200

# Command to start the Angular application
CMD ["ng", "serve", "--host", "0.0.0.0"]
