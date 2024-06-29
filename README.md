# Project API Collection

## Data Queries and Endpoints

```json
{
	"info": {
		"_postman_id": "8bb9df16-6afb-4759-8948-f6db877faa86",
		"name": "New Collection",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json",
		"_exporter_id": "21992785"
	},
	"item": [
		{
			"name": "Sign up request",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "X_PO_AUTH_KEY",
						"value": "yourPredefinedAuthKey",
						"type": "text"
					},
					{
						"key": "X_PO_ORIGIN",
						"value": "yourPredefinedOrigin",
						"type": "text"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n  \"firstName\": \"jgdg\",\n  \"lastName\": \"Doe\",\n  \"email\": \"testingnewnewmail@gmail.com\",\n  \"mobile\": \"1234567890\",\n  \"username\": \"jhondoe1200\",\n  \"password\": \"P@ssw0rd!\"\n}",
					"options": {
						"raw": {
							"language": "json"
						}
					}
				},
				"url": {
					"raw": "http://localhost:8085/backend_assignment/auth/sign-up",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8085",
					"path": [
						"backend_assignment",
						"auth",
						"sign-up"
					]
				}
			},
			"response": []
		},
		{
			"name": "Update Api",
			"request": {
				"method": "PATCH",
				"header": [
					{
						"key": "X_PO_AUTH_KEY",
						"value": "yourPredefinedAuthKey",
						"type": "text",
						"disabled": true
					},
					{
						"key": "X_PO_ORIGIN",
						"value": "yourPredefinedOrigin",
						"type": "text"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n  \"firstName\": \"Rahul\",\n  \"lastName\": \"Saha\",\n  \"mobile\": \"9679165255\",\n  \"password\": \"P@ssw0rd!\"\n}",
					"options": {
						"raw": {
							"language": "json"
						}
					}
				},
				"url": {
					"raw": "http://localhost:8085/backend_assignment/user-profile/update",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8085",
					"path": [
						"backend_assignment",
						"user-profile",
						"update"
					]
				}
			},
			"response": []
		},
		{
			"name": "Login Request",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "X_PO_AUTH_KEY",
						"value": "predefinedAuthKey",
						"type": "text"
					},
					{
						"key": "X_PO_ORIGIN",
						"value": "predefinedOrigin",
						"type": "text"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n  \"username\": \"johndoe12345\",\n  \"password\": \"P@ssw0rd!\"\n}",
					"options": {
						"raw": {
							"language": "json"
						}
					}
				},
				"url": {
					"raw": "http://localhost:8085/backend_assignment/auth/login",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8085",
					"path": [
						"backend_assignment",
						"auth",
						"login"
					]
				}
			},
			"response": []
		},
		{
			"name": "Coin View",
			"request": {
				"method": "GET",
				"header": [
					{
						"key": "X_PO_AUTH_KEY",
						"value": "yourPredefinedAuthKey",
						"type": "text",
						"disabled": true
					},
					{
						"key": "X_PO_ORIGIN",
						"value": "yourPredefinedOrigin",
						"type": "text"
					}
				],
				"url": {
					"raw": "http://localhost:8085/backend_assignment/coins-view/get-coins-data?symbol=BTS",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8085",
					"path": [
						"backend_assignment",
						"coins-view",
						"get-coins-data"
					],
					"query": [
						{
							"key": "symbol",
							"value": "BTS"
						}
					]
				}
			},
			"response": []
		}
	]
}

To Run
Docker Setup

Run the following command to start the Docker containers:

bash

docker-compose up -d
