package com.example.healthcheckapp.util;

import static com.jayway.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.ExtractableResponse;
import com.jayway.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.Reporter;


/**
 * Created By AnuragP on 14 Mar, 2018
 */
public class RESTHandler {
    public static Boolean retryLogic;
    public static Boolean exceptionOccured;

    private static Logger logger = LoggerFactory.getLogger(RESTHandler.class);
    private static HashMap<String,String> headerMap = new HashMap<>();

    /**
     * Get current Unix time
     */
    public static long getCurrentTime() {
        long time = System.currentTimeMillis();
        return time;
    }

    /**
     * Logging
     *
     * @param message
     */
    public static void log(final String message) {
        Date date = new Date();
        Reporter.log("-----> [" + date + "] " + message, true);
    }

    /**
     * Sleep
     *
     * @param seconds
     */
    public static void sleep(final Integer seconds) {

        try {
            RESTHandler.log("Sleeping for " + seconds + " seconds.");
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            RESTHandler.log("InterruptedException " + e);
        } catch (Exception e) {
            RESTHandler.log("Exception " + e);
        }
    }

    /**
     * Validate Result
     *
     * @param value
     * @param message
     */
    public static void validateResult(final Boolean value, final String message) {
        RESTHandler.log(message);
        Assert.assertTrue(value, message);
    }

    /**
     * Validate Result
     *
     * @param expString
     * @param actualString
     * @param message
     */
    public static void validateResult(final String expString, final String actualString, final String message) {
        RESTHandler.log(message);
        RESTHandler.log("Expected -  " + expString);
        RESTHandler.log("Actual - " + actualString);

        Assert.assertEquals(actualString, expString, message);
    }

    /**
     * Validate Result
     *
     * @param expValue
     * @param actualValue
     * @param message
     */
    public static void validateResult(final Long expValue, final Long actualValue, final String message) {
        RESTHandler.log(message);
        RESTHandler.log("Expected - " + expValue);
        RESTHandler.log("Actual - " + actualValue);

        Assert.assertEquals(actualValue, expValue, message);
    }

    /**
     * Validate Result
     *
     * @param expValue
     * @param actualValue
     * @param message
     */
    public static void validateResult(final int expValue, final int actualValue, final String message) {
        RESTHandler.log(message);
        RESTHandler.log("Expected - " + expValue);
        RESTHandler.log("Actual - " + actualValue);

        Assert.assertEquals(actualValue, expValue, message);
    }

    /**
     * Validate Result
     *
     * @param expValue
     * @param actualValue
     * @param message
     */
    public static void validateResult(final Boolean expValue, final Boolean actualValue, final String message) {
        RESTHandler.log(message);
        RESTHandler.log("Expected - " + expValue);
        RESTHandler.log("Actual - " + actualValue);

        Assert.assertEquals(actualValue, expValue, message);
    }

    /**
     * Get Properties from File
     *
     * @param fileName
     * @return
     */
    public static Properties getPropertyFromFile(final String fileName){
        InputStream inputStream = null;
        Properties properties = new Properties();

        try {
            inputStream = new FileInputStream(fileName);
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            RESTHandler.log("Exception " + e);
        } catch (Exception e) {
            RESTHandler.log("Exception " + e);
        }
        return properties;
    }

    public enum RequestType {
        GET, POST, PUT, DELETE;
    }

    /**
     *
     * RestAssured Function
     *
     */
    protected static Map<Integer, String> getResponse(final RequestType requestType, final Map<String, String> headerMap,
                                        final String requestURL, final String requestBody, final Integer expectedStatusCode) {

        List<String> headerList = new ArrayList<String>();
        if (headerMap != null) {
            Set<String> keySet = headerMap.keySet();
            headerList.addAll(keySet);
        }

        RESTHandler.log("Request URL : " + requestType + " " + requestURL);

        if (requestBody != null)
            Reporter.log("Request Body : " + requestBody);

        ExtractableResponse<Response> response = null;

        int counter = 0;
        do {
            if (counter != 0) {
                RESTHandler.sleep(5);
                RESTHandler.retryLogic = true;
                RESTHandler.log("Response : " + response.asString());
                RESTHandler.log("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                RESTHandler.log("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                RESTHandler.log("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                RESTHandler.log("+++++++++++++++++++++++++       Retrying            +++++++++++++++++++++");
                RESTHandler.log("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                RESTHandler.log("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                RESTHandler.log("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            }

            try {
                switch (requestType) {

                    case GET:

                        if (headerMap != null) {
                            response = given().log().all().headers(headerMap).when().get(requestURL).then().log().all()
                                    .extract();
                        } else {
                            response = given().log().all().when().get(requestURL).then().log().all().extract();
                        }
                        break;

                    case PUT:

                        if (headerMap != null && requestBody == null) {
                            response = given().log().all().headers(headerMap).when().put(requestURL).then().log().all()
                                    .extract();
                        } else if (headerMap != null && requestBody != null) {
                            response = given().log().all().headers(headerMap).body(requestBody).when().put(requestURL)
                                    .then().log().all().extract();
                        } else {
                            response = given().log().all().when().put(requestURL).then().log().all().extract();
                        }
                        break;

                    case POST:
                        if (headerMap != null && requestBody != null) {
                            response = given().log().all().headers(headerMap).body(requestBody).when().post(requestURL)
                                    .then().log().all().extract();
                        } else {
                            response = given().log().all().headers(headerMap).when().post(requestURL).then().log().all()
                                    .extract();
                        }
                        break;

                    case DELETE:

                        if (headerMap != null && requestBody == null) {
                            response = given().log().all().headers(headerMap).when().delete(requestURL).then().log().all()
                                    .extract();
                        } else if (headerMap != null && requestBody != null) {
                            response = given().log().all().headers(headerMap).body(requestBody).when().delete(requestURL)
                                    .then().log().all().extract();
                        } else {
                            response = given().log().all().when().delete(requestURL).then().log().all().extract();
                        }
                        break;
                }

            } catch (Exception e) {
                RESTHandler.log("Exception Occured : " + e.toString());
                RESTHandler.exceptionOccured = true;
                RESTHandler.sleep(2);
                return null;
            }

            counter = counter + 1;

            if (expectedStatusCode == 1000) {
                break;
            }
        } while ((response.statusCode() != expectedStatusCode) && counter < 1);

        logger.info("Response Received from Server: "+response.asString());
        /*if (expectedStatusCode != 1000)
            validateResult(expectedStatusCode, response.statusCode(), "Status Code Validation");
*/
        // RESTHandler.log("Response : " + response.asString());
        RESTHandler.log(
                "-------------------------------------------------------------------------------------------------------------------------");
        Map<Integer, String> responseCodeandContentMap = new HashMap();
        responseCodeandContentMap.put(response.statusCode(), response.asString());
        return responseCodeandContentMap;
    }

    /**
     * @param mmtAuth
     * @param basicAuth
     * @param identifier
     * @param cookies
     *
     */
    protected static void setHeader(String mmtAuth, String basicAuth, String identifier, String cookies) {

        //int lengthOfParameters = parameters.length;
        //String mmtAuth, basicAuth, id, cookie;

        // Assigning the default values in the Header map
        headerMap.put("Content-Type", "application/json");
        headerMap.put("Accept", "application/json");
        if(!mmtAuth.isEmpty())
            headerMap.put("mmtauth", mmtAuth);
        if(!basicAuth.isEmpty())
            headerMap.put("Authorization", basicAuth);
        if(!identifier.isEmpty())
            headerMap.put("id", identifier);
        if(!cookies.isEmpty())
            headerMap.put("Cookie", cookies);

    }

    /**
     * @param parameters
     *            Parameters need to be passed in Order Parameter[1] = mmtAuth
     *            Parameter[2] = BasicAuthorization
     *            Parameter[3] = id
     *            Parameter[4] = cookie
     */
    protected static void setHeaderWithIdentifier(String... parameters) {

        int lengthOfParameters = parameters.length;
        String mmtAuth, basicAuth, id, cookie, user_identifier;

        // Assigning the default values in the Header map
        headerMap.put("Content-Type", "application/json");
        headerMap.put("Accept", "application/json");

        if (lengthOfParameters > 0) {
            if (lengthOfParameters > 5) {
                throw new IllegalArgumentException("There are only 4 parameters defined as per the Java doc");
            } else {
                if (!parameters[0].isEmpty()) {
                    mmtAuth = parameters[0];
                    headerMap.put("mmtauth", parameters[0]);
                }
                if (!parameters[1].isEmpty()) {
                    basicAuth = parameters[1];
                    headerMap.put("Authorization", parameters[1]);
                }
                if (!parameters[2].isEmpty()) {
                    id = parameters[2];
                    headerMap.put("id", parameters[2]);
                }
                if (!parameters[3].isEmpty()) {
                    cookie = parameters[3];
                    headerMap.put("Cookie", parameters[3]);
                }
                if (!parameters[4].isEmpty()) {
                    user_identifier = parameters[4];
                    headerMap.put("user-identifier", parameters[4]);
                }
            }
        } else {
            return;
        }
    }


    /**
     * @Description: getter method for Header
     * @return Map with all the header values
     */
    protected static HashMap<String, String> getHeader() {
        return headerMap;
    }

}

