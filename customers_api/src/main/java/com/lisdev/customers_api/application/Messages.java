package com.lisdev.customers_api.application;

public final class Messages {

    private Messages() {
    }

    public static final String START = "Starting --> ";
    public static final String END = "Finished --> ";
    public static final String PATH_NOT_FOUND = "Path not found";
    public static final String DB_ERROR = "Data integrity error in the database";
    public static final String INVALID_REQUEST = "Invalid request. Please verify the input data.";
    public static final String CUSTOMER_NOT_FOUND = "Customer not found with identification:";
    public static final String CUSTOMER_ALREADY_ACTIVE = "Customer is already active.";
    public static final String CUSTOMER_ID_NOT_EXIST = "Customer not found with id:";
    public static final String ACTIVE_ACCOUNT = "Customer has active account(s) and cannot be deleted";
    public static final String DB_NOT_AVAILABLE = "The database is not available";
    public static final String DOWNSTREAM_SERVICE_NO_RESPONSE = "Error, please try again later";

    public static final String HAS_ACTIVE_ACCOUNTS_PATH = "/api/v1/accounts/exists?customerId={customerId}";
}
