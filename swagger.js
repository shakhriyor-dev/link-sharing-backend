const swaggerJSDoc = require('swagger-jsdoc');

const options = {
  definition: {
    openapi: '3.0.0',
    info: {
      title: 'Link Sharing App API',
      version: '1.0.0',
      description: 'API documentation for Link Sharing App',
    },
    servers: [
      {
        url: 'http://localhost:3000', // Adjust if deploying
      },
    ],
  },
  apis: ['./routes/*.js'], // Path to your route files
};

const swaggerSpec = swaggerJSDoc(options);
module.exports = swaggerSpec;