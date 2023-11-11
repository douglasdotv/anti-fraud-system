package br.com.dv.antifraud.constants;

public final class AntifraudSystemConstants {

    private AntifraudSystemConstants() {
        throw new UnsupportedOperationException(CLASS_CANNOT_BE_INSTANTIATED_MESSAGE);
    }

    public static final int SEVERITY_ALLOWED = 0;
    public static final int SEVERITY_MANUAL_PROCESSING = 1;
    public static final int SEVERITY_PROHIBITED = 2;
    public static final int LAST_HOUR_IP_REGION_COUNT_THRESHOLD = 2;

    public static final long INITIAL_ALLOWED_MAX = 200;
    public static final long INITIAL_MANUAL_PROCESSING_MAX = 1500;

    public static final String STATUS_DELETED_SUCCESSFULLY = "Deleted successfully!";
    public static final String STATUS_LOCKED = "locked";
    public static final String STATUS_UNLOCKED = "unlocked";
    public static final String USER_STATUS_UPDATE_TEMPLATE = "User %s %s!";
    public static final String CARD_REMOVAL_TEMPLATE = "Card %s successfully removed!";
    public static final String IP_REMOVAL_TEMPLATE = "IP %s successfully removed!";

    public static final String NAME_REQUIRED_MESSAGE = "Name is required.";
    public static final String USERNAME_REQUIRED_MESSAGE = "Username is required.";
    public static final String PASSWORD_REQUIRED_MESSAGE = "Password is required.";
    public static final String INVALID_ROLE_MESSAGE = "Invalid role. The available roles are MERCHANT and SUPPORT.";
    public static final String INVALID_USER_OPERATION_MESSAGE =
            "Invalid operation. The available operations are LOCK and UNLOCK.";

    public static final String AMOUNT_REQUIRED_MESSAGE = "Amount is required.";
    public static final String AMOUNT_MUST_BE_POSITIVE_MESSAGE = "Amount must be positive.";
    public static final String INVALID_REGION_MESSAGE =
            "Invalid region. The available regions are: EAP, ECA, HIC, LAC, MENA, SA and SSA.";
    public static final String DATE_CANNOT_BE_NULL_MESSAGE = "Date cannot be null.";
    public static final String DATE_PAST_OR_PRESENT_MESSAGE = "Date must be in the past or present.";
    public static final String ID_NOT_NULL_MESSAGE = "ID cannot be null.";
    public static final String ID_MUST_BE_POSITIVE_MESSAGE = "ID must be positive.";
    public static final String INVALID_FEEDBACK_MESSAGE =
            "Invalid feedback. The available feedbacks are ALLOWED, MANUAL_PROCESSING and PROHIBITED.";

    public static final String IPV4_REGEX = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)(\\.(?!$)|$)){4}$";
    public static final String INVALID_IPV4_MESSAGE = "Invalid IPv4 format.";

    public static final String INVALID_CARD_NUMBER_MESSAGE = "Invalid card number.";
    public static final String INVALID_VALUE_MESSAGE = "Invalid value.";

    public static final String USER_ALREADY_EXISTS_MESSAGE = "User already exists.";
    public static final String USER_NOT_FOUND_MESSAGE = "User not found.";
    public static final String ROLE_ALREADY_ASSIGNED_MESSAGE = "User already has this role.";
    public static final String CANNOT_LOCK_ADMIN_MESSAGE = "Cannot assign LOCK status to ADMIN.";

    public static final String TRANSACTIONS_NOT_FOUND_MESSAGE = "No transactions were found.";
    public static final String TRANSACTION_NOT_FOUND_MESSAGE = "Transaction not found.";
    public static final String FEEDBACK_ALREADY_ASSIGNED_MESSAGE = "This feedback is already assigned.";
    public static final String SAME_RESULT_AND_FEEDBACK_MESSAGE = "Feedback and transaction result cannot be the same.";

    public static final String SUSPICIOUS_IP_ADDRESS_ALREADY_EXISTS_MESSAGE = "Suspicious IP address already exists.";
    public static final String SUSPICIOUS_IP_ADDRESS_NOT_FOUND_MESSAGE = "Suspicious IP address not found.";

    public static final String STOLEN_CARD_ALREADY_EXISTS_MESSAGE = "Stolen card already exists.";
    public static final String STOLEN_CARD_NOT_FOUND_MESSAGE = "Stolen card not found.";
    public static final String CARD_NOT_FOUND_MESSAGE = "Card not found.";

    public static final String ROLE_PREFIX = "ROLE_";

    public static final String CLASS_CANNOT_BE_INSTANTIATED_MESSAGE = "Class cannot be instantiated.";

}
