
# Tiny Ledger

A simple ledger spring boot application. It supports below functionalities.

* Record Deposits and Withdrawals
* View the Current Balance
* View the Transaction History of an account

## Features

- Account Creation
- Record Deposits and WithDrawal
- View Current Balance
- View Transaction History

## Requirements

- Java 17
- SpringBoot 3.4+
- Junit 5
- Maven
## Assumptions

- Customer must create an account before doing any transactions.
- All the created accounts gets stored in In-memory.
- Each account is associated with availableBalance. Initially the balance is 0 when account gets created. Balance gets updated when there is a transaction occur on the account.
- No validations implemented on accountNumber to reduce the complexity.

## Validations

- Attempting to create Account with existing Account will throw AccountAlreadyExists Exception.
- Account must be created before any transactions are in-place otherwise AccountNotFoundException will be thrown from the application.
- Transaction amount should always send as positive number.
- Only DEPOSIT or WITHDRAWAL are allowed.
- Account should have sufficient balance when transactionType is WITHDRAWAL. API will Throw InSufficientFundException When attempting to WITHDRAW more than the available balance.

## In-Memory Storage

* Accounts gets stored in ConcurrentHasMap and this will be reset when the application gets restarted.
* All the transactions gets stored in ConcurrentHasMap where key as accountNumber and value as list of transactions. This will reset when the application gets restarted.
## What's Not Implemented

- Authentication and Authorization are not implemented. So anyone can access the application.
- Logging and Monitoring was not implemented for Debug and tracing the application

## API End points

### Account API

#### Account Creation

- **URL:** `/api/accounts`
- **Method:** `POST`
- **Description:** create new account.
- **Request Body:**
    ```json
    {
    "accountNumber" : "GB3456988",
    "accountType" : "SAVINGS_ACCOUNT"
    }

    ```
- **Response:**
    ```json
    {
    "accountNumber": "GB3456988",
    "availableBalance": 0,
    "accountType": "SAVINGS_ACCOUNT"
    }

    ```

### Get current balance
- **URL:** `/api/accounts/{accountNumber}/balance`
- **Example:** `/api/accounts/GB3456988/balance`
- **Method:** `GET`
- **Description:** Retrieves current balance of an account by accountNumber
- **Response:**
    ```
    0
    ```

### Ledger API

#### Record Transactions of an account(DEPOSIT/WITHDRAWAL)

- **URL:** `/api/ledger/{accountNumber}/transactions`
- **Example:** `/api/ledger/GB3456988/transactions`
- **Method:** `POST`
- **Description:** create new account.
- **Request Body for Deposit :**
    ```json
    {
    "amount" : 10089.3540,
    "transactionType" : "DEPOSIT",
    "transactionDetails" : "HSBC BANK"
    }

    ```
- **Response:**
    ```json
    {
    "transactionId": "5651197a-3e8e-4a35-9f59-9d871aea9f30",
    "availableBalance": 10089.3540
    }

- **Request Body for Withdrawal :**
    ```json
    {
    "amount" : 120.190,
    "transactionType" : "WITHDRAWAL",
    "transactionDetails" : "TESCO"
    }

    ```
- **Response:**
    ```json
    {
    "transactionId": "f664bc20-021a-43f9-bd42-f97846d25d60",
    "availableBalance": 9969.1640
    }

    ```

#### Get Transaction History
- **URL:** `/api/ledger/{accountNumber}/transactions`
- **Example:** `/api/ledger/GB3456988/transactions`
- **Method:** `GET`
- **Description:** Retrieves all the transaction details of an account by accountNumber
- **Response:**
    ```json
    [
    {
        "transactionId": "2d178683-48e7-46cf-8d3e-304620769384",
        "transactionType": "DEPOSIT",
        "amount": 10089.3540,
        "availableBalance": 10089.3540,
        "transactionDate": "2025-03-11T22:39:38.1056359"
    },
    {
        "transactionId": "07d20a35-3024-477b-9788-d3e46eaf5006",
        "transactionType": "WITHDRAWAL",
        "amount": 120.190,
        "availableBalance": 9969.1640,
        "transactionDate": "2025-03-11T22:40:44.6054413"
    }
    ]
    ```

## Error Handling
The API uses standard HTTP status codes to indicate the success or failure of an API request. Below are the Common status codes That will be returned from API:
- `200 OK` - The request was successful.
- `201 Created` - The resource was successfully created.
- `400 Bad Request` - The request was invalid or cannot be served.
- `404 Not Found` - The requested resource was not found.
- `500 Internal Server Error` - An error occurred on the server.
## Run Locally

Clone the project

```bash
  git clone https://link-to-project
```

Go to the project directory
```bash
./mvnw spring-boot:run
```

There are several ways to run a Spring Boot application on your local machine. One way is to execute the main method in the de.TinyLedgerApplication class from your IDE.

application should start running on 8080 port

## Testing

JUnit and Mockito are used for unit and integration testing.

## Sample cURL for Testing

### Create Accounts
```shell
curl --location 'http://localhost:8080/api/accounts' \
--header 'Content-Type: application/json' \
--data '{
    "accountNumber" : "GB3456989",
    "accountType" : "SAVINGS_ACCOUNT"
}'
```
### Get Account Balance

```shell
curl --location 'http://localhost:8080/api/accounts/GB3456989/balance'
```


### Deposit the amount
```shell
curl --location 'http://localhost:8080/api/ledger/GB3456989/transactions' \
--header 'Content-Type: application/json' \
--data '{
    "amount" : 10089.3540,
    "transactionType" : "DEPOSIT",
    "transactionDetails" : "HSBC BANK"
}'
```
### WITHDRAWAL the amount
```shell
curl --location 'http://localhost:8080/api/ledger/GB3456989/transactions' \
--header 'Content-Type: application/json' \
--data '{
    "amount" : 120.190,
    "transactionType" : "WITHDRAWAL",
    "transactionDetails" : "TESCO"
}'
```

### Get All Transactions of an accountNumber
```shell
curl --location 'http://localhost:8080/api/ledger/GB3456989/transactions'
```


   