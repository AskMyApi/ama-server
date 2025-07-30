package askmyapi.amaserver.util;

public class UrlValidator {

    public static boolean isValidUrl(String url) {
        org.apache.commons.validator.routines.UrlValidator validator = new org.apache.commons.validator.routines.UrlValidator(
                new String[] {"http", "https"} // 허용할 프로토콜\
        );
        return validator.isValid(url);
    }
}
