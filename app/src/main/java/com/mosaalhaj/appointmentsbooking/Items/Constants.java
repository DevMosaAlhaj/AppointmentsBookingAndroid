package com.mosaalhaj.appointmentsbooking.Items;

public class Constants {

    public static String USER_NAME = "username";
    public static String USER_ID = "id";
    public static String PASSWORD = "password";
    public static String REMEMBER = "remember";
    public static String COMPANY = "company";
    public static String COMPANY_NAME = "companyName";
    public static String COMPANY_ID = "companyId";
    public static String COMPANY_EMAIL = "companyEmail";
    public static String COMPANY_PHONE = "companyPhone";
    public static String COMPANY_DESCRIPTION = "companyDescription";
    public static String COMPANY_LONGITUDE = "companyLongitude";
    public static String COMPANY_LATITUDE = "companyLatitude";
    public static String COMPANY_LIST = "companyList";
    public static String WORK_START = "workStart";
    public static String WORK_END = "workEnd";
    public static String COMPANY_NAME_NOT_FOUND = "No Company Selected";
    public static String NOT_FOUND = "notFound";
    public static String POSITION = "position";
    public static String CASHING = "cashing";


    public static String USER_SHARED_PREFERENCE_FILE = "userFile";

    private static String[] COUNTERS_LIST = {"Select Country","Afghanistan","Albania","Algeria","Andorra","Angola","Anguilla","Barbuda","Argentina","Armenia","Aruba","Australia","Austria","Azerbaijan","Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bermuda","Bhutan","Bolivia","Herzegovina","Botswana","Brazil","British Virgin Islands","Brunei","Bulgaria","Burkina Faso","Burundi","Cambodia","Cameroon","Cape Verde","Cayman Islands","Chad","Chile","China","Colombia","Congo","Cook Islands","Costa Rica","Cote D Ivoire","Croatia","Cruise Ship","Cuba","Cyprus","Czech Republic","Denmark","Djibouti","Dominica","Dominican Republic","Ecuador","Egypt","El Salvador","Equatorial Guinea","Estonia","Ethiopia","Falkland Islands","Faroe Islands","Fiji","Finland","France","French Polynesia","French West Indies","Gabon","Gambia","Georgia","Germany","Ghana","Gibraltar","Greece","Greenland","Grenada","Guam","Guatemala","Guernsey","Guinea","Guinea Bissau","Guyana","Haiti","Honduras","Hong Kong","Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland","Isle of Man","Israel","Italy","Jamaica","Japan","Jersey","Jordan","Kazakhstan","Kenya","Kuwait","Kyrgyz Republic","Laos","Latvia","Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg","Macau","Macedonia","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Mauritania","Mauritius","Mexico","Moldova","Monaco","Mongolia","Montenegro","Montserrat","Morocco","Mozambique","Namibia","Nepal","Netherlands","Netherlands Antilles","New Caledonia","New Zealand","Nicaragua","Niger","Nigeria","Norway","Oman","Pakistan","Palestine","Panama","Papua New Guinea","Paraguay","Peru","Philippines","Poland","Portugal","Puerto Rico","Qatar","Reunion","Romania","Russia","Rwanda","Miquelon","Samoa","San Marino","Satellite","Saudi Arabia","Senegal","Serbia","Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia","South Africa","South Korea","Spain","Sri Lanka","Nevis","St Lucia","St Vincent","St. Lucia","Sudan","Suriname","Swaziland","Sweden","Switzerland","Syria","Taiwan","Tajikistan","Tanzania","Thailand","Timor L'Este","Togo","Tonga","Tobago","Tunisia","Turkey","Turkmenistan","Caicos","Uganda","Ukraine","United Arab Emirates","United Kingdom","Uruguay","Uzbekistan","Venezuela","Vietnam","Virgin Islands (US)","Yemen","Zambia","Zimbabwe"};
    private static String API_URL = "https://10.0.2.2:5001/" ;


    public static String PASSWORD_SHORT_CHARACTERS_ERROR = "At least 8 character";
    public static String PASSWORD_NO_NUMBER_ERROR = "Minimum One Number";
    public static String PASSWORD_NO_SPECIAL_CHARACTERS_ERROR = "Minimum One Special Character";
    public static String PASSWORD_NO_UPPERCASE_CHARACTERS_ERROR = "Minimum One Character Upper Case";
    public static String EMAIL_NOT_VALID_ERROR = "Your Email is not Valid";



    public static String[] getCountersList() {
        return COUNTERS_LIST;
    }

    public static String getApiUrl() {
        return API_URL;
    }

}
