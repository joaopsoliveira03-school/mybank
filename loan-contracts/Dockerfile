# Use a Maven image with Java to run the application
FROM maven

# Set the working directory
WORKDIR /app

# Copy the whole project into the Docker container
COPY . .

# Expose the port your application will run on
EXPOSE 8094

# Run the application using `mvn spring-boot:run`
CMD ["mvn", "spring-boot:run", "-pl", "loan-contracts"]
