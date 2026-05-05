package com.lisdev.transactional_api.application;

public class Messages {

    public static final String START = "Starting --> ";
    public static final String END = "Finished --> ";
    public static final String PATH_NOT_FOUND = "Path not found";
    public static final String DB_ERROR = "Data integrity error in the database";
    public static final String INVALID_REQUEST = "Invalid request. Please verify the input data.";
    public static final String DB_NOT_AVAILABLE = "The database is not available";
    public static final String DOWNSTREAM_SERVICE_NO_RESPONSE = "Error, please try again later";
    public static final String CUSTOMER_NOT_FOUND = "Customer not found with identification:";
    public static final String ACCOUNT_NOT_FOUND = "Account not found.";
    public static final String ACCOUNT_ALREADY_ACTIVE = "The account is already active.";
    public static final String ACCOUNT_ALREADY_INACTIVE = "The account is already inactive.";
    public static final String ACCOUNT_HAS_BALANCE = "Account cannot be deactivated because it has a balance";

    public static final String GET_CUSTOMER_ID_BY_IDENTIFICATION = "/api/v1/customers";
    public static final String GET_CUSTOMER_RESOLVE_BY_ID = "/api/v1/customers/resolve";

    
}
